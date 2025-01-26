package com.realestate.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "property_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
}
