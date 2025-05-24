package com.realestate.backend.dto.mcp;

import com.realestate.backend.model.AppUser;
import lombok.Data;

@Data
public class UserCreateParameters {
    private String name;
    private String email;
    private String role;
    
    public AppUser toAppUser() {
        return AppUser.builder()
                .name(name)
                .email(email)
                .role(role)
                .build();
    }
}
