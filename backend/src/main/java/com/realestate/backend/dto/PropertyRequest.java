package com.realestate.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class PropertyRequest {
    private String title;
    private String description;
    private String location;
    private Double price;
    private Long ownerId;
    private List<String> imageUrls; // e.g. ["http://.../image1.jpg", ...]
}

