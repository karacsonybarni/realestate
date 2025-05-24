package com.realestate.backend.dto.mcp;

import com.realestate.backend.dto.PropertyRequest;
import lombok.Data;

import java.util.List;

@Data
public class PropertyUpdateParameters {
    private Long id;
    private String title;
    private String description;
    private String location;
    private Double price;
    private Long ownerId;
    private List<String> imageUrls;
    
    public PropertyRequest toPropertyRequest() {
        PropertyRequest request = new PropertyRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setLocation(location);
        request.setPrice(price);
        request.setOwnerId(ownerId);
        request.setImageUrls(imageUrls);
        return request;
    }
}
