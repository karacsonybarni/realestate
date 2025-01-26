package com.realestate.backend.repository;

import com.realestate.backend.model.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("unused")
public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {
    // Additional queries if needed
}
