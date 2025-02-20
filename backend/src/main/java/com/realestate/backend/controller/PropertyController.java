package com.realestate.backend.controller;

import com.realestate.backend.dto.PropertyRequest;
import com.realestate.backend.model.Property;
import com.realestate.backend.service.PropertyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    public Property createProperty(@RequestBody PropertyRequest request) {
        return propertyService.createProperty(request);
    }

    @GetMapping
    public List<Property> listAllProperties() {
        return propertyService.listAllProperties();
    }

    @GetMapping("/{id}")
    public Property getPropertyById(@PathVariable Long id) {
        return propertyService.getPropertyById(id);
    }

    @GetMapping("/search")
    public List<Property> searchPropertiesByLocation(@RequestParam String location) {
        return propertyService.searchByLocation(location);
    }

    @PutMapping("/{id}")
    public Property updateProperty(@PathVariable Long id, @RequestBody PropertyRequest request) {
        return propertyService.updateProperty(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
    }

    @GetMapping("/recommendations")
    public String getPropertyRecommendations(@RequestParam Long userId) {
        return propertyService.getPropertyRecommendations(userId);
    }

    @GetMapping("/notifications")
    public String getPersonalizedNotifications(@RequestParam Long userId) {
        return propertyService.getPersonalizedNotifications(userId);
    }
}
