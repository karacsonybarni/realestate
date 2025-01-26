package com.realestate.backend.repository;

import com.realestate.backend.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    // Additional queries by email, role, etc. if needed
    @SuppressWarnings("unused")
    AppUser findByEmail(String email);
}
