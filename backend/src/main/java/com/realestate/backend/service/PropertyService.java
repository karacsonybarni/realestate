package com.realestate.backend.service;

import com.realestate.backend.dto.PropertyRequest;
import com.realestate.backend.model.AppUser;
import com.realestate.backend.model.Property;
import com.realestate.backend.model.PropertyImage;
import com.realestate.backend.repository.AppUserRepository;
import com.realestate.backend.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final AppUserRepository userRepository;

    public PropertyService(PropertyRepository propertyRepository,
                           AppUserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Property createProperty(PropertyRequest request) {
        long ownerId = request.getOwnerId() != null
                ? request.getOwnerId()
                : 1;
        Optional<AppUser> ownerOpt = userRepository.findById(ownerId);
        if (ownerOpt.isEmpty()) {
            throw new RuntimeException("Owner not found with ID: " + request.getOwnerId());
        }
        AppUser owner = ownerOpt.get();

        // Build property
        Property property = Property.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .price(request.getPrice())
                .owner(owner)
                .build();

        // Add images if any
        if (request.getImageUrls() != null) {
            request.getImageUrls().forEach(url -> {
                PropertyImage img = PropertyImage.builder().url(url).build();
                property.addImage(img);
            });
        }

        return propertyRepository.save(property);
    }

    public List<Property> listAllProperties() {
        return propertyRepository.findAll();
    }

    public List<Property> searchByLocation(String location) {
        return propertyRepository.findByLocationContainingIgnoreCase(location);
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + id));
    }

    @Transactional
    public Property updateProperty(Long id, PropertyRequest request) {
        Property existing = getPropertyById(id);

        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setLocation(request.getLocation());
        existing.setPrice(request.getPrice());

        // If owner changes
        if (request.getOwnerId() != null && !request.getOwnerId().equals(existing.getOwner().getId())) {
            AppUser newOwner = userRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("Owner not found with ID: " + request.getOwnerId()));
            existing.setOwner(newOwner);
        }

        // Update images (in a naive way, replace all)
        existing.getImages().clear();
        if (request.getImageUrls() != null) {
            request.getImageUrls().forEach(url -> {
                PropertyImage img = PropertyImage.builder().url(url).property(existing).build();
                existing.getImages().add(img);
            });
        }

        return propertyRepository.save(existing);
    }

    @Transactional
    public void deleteProperty(Long id) {
        Property existing = getPropertyById(id);
        propertyRepository.delete(existing);
    }
}

