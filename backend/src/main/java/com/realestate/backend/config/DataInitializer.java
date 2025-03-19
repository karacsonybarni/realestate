package com.realestate.backend.config;

import com.realestate.backend.model.AppUser;
import com.realestate.backend.model.Property;
import com.realestate.backend.model.PropertyImage;
import com.realestate.backend.repository.AppUserRepository;
import com.realestate.backend.repository.PropertyImageRepository;
import com.realestate.backend.repository.PropertyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(AppUserRepository userRepository, 
                                      PropertyRepository propertyRepository,
                                      PropertyImageRepository propertyImageRepository) {
        return args -> {
            // Create sample users
            AppUser user1 = new AppUser();
            user1.setName("John Doe");
            user1.setEmail("john@example.com");
            user1.setRole("LANDLORD");
            userRepository.save(user1);

            AppUser user2 = new AppUser();
            user2.setName("Jane Smith");
            user2.setEmail("jane@example.com");
            user2.setRole("SELLER");
            userRepository.save(user2);

            // Create sample properties
            Property property1 = new Property();
            property1.setTitle("Luxury Apartment in Downtown");
            property1.setDescription("A beautiful luxury apartment in the heart of downtown with amazing views.");
            property1.setLocation("Downtown");
            property1.setPrice(350000.0);
            property1.setOwner(user1);
            propertyRepository.save(property1);

            Property property2 = new Property();
            property2.setTitle("Cozy Family Home");
            property2.setDescription("Perfect family home with a large backyard in a quiet neighborhood.");
            property2.setLocation("Suburbia");
            property2.setPrice(450000.0);
            property2.setOwner(user1);
            propertyRepository.save(property2);

            Property property3 = new Property();
            property3.setTitle("Modern Beach House");
            property3.setDescription("Beautiful modern beach house with direct access to the beach.");
            property3.setLocation("Beachside");
            property3.setPrice(650000.0);
            property3.setOwner(user2);
            propertyRepository.save(property3);

            // Add images to properties
            PropertyImage image1 = new PropertyImage();
            image1.setUrl("https://images.unsplash.com/photo-1502672260266-1c1ef2d93688");
            image1.setProperty(property1);
            propertyImageRepository.save(image1);

            PropertyImage image2 = new PropertyImage();
            image2.setUrl("https://images.unsplash.com/photo-1560448204-e02f11c3d0e2");
            image2.setProperty(property1);
            propertyImageRepository.save(image2);

            PropertyImage image3 = new PropertyImage();
            image3.setUrl("https://images.unsplash.com/photo-1583608205776-bfd35f0d9f83");
            image3.setProperty(property2);
            propertyImageRepository.save(image3);

            PropertyImage image4 = new PropertyImage();
            image4.setUrl("https://images.unsplash.com/photo-1564013799919-ab600027ffc6");
            image4.setProperty(property3);
            propertyImageRepository.save(image4);
        };
    }
}