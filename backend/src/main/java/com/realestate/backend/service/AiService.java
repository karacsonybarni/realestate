package com.realestate.backend.service;

import org.springframework.stereotype.Service;

@SuppressWarnings("unused")
@Service
public class AiService {

    @SuppressWarnings("unused")
    public String suggestPropertyImprovements(String title, String description, String location) {
        // Placeholder logic
        return "Consider adding key amenities for location: " + location;
    }

    public String suggestPropertyMatches(String buyerPreferences) {
        // Placeholder logic
        return "Try focusing on properties near: " + buyerPreferences;
    }
}
