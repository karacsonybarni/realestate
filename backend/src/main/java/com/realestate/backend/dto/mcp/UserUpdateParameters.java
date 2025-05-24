package com.realestate.backend.dto.mcp;

import com.realestate.backend.model.AppUser;
import lombok.Data;

@Data
public class UserUpdateParameters {
    private Long id;
    private String name;
    private String email;
    private String role;
    
    public AppUser toAppUser() {
        return AppUser.builder()
                .id(id)
                .name(name)
                .email(email)
                .role(role)
                .build();
    }
}
