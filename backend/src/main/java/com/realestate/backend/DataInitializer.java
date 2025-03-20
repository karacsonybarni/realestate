package com.realestate.backend;

import com.realestate.backend.model.AppUser;
import com.realestate.backend.model.Property;
import com.realestate.backend.model.PropertyImage;
import com.realestate.backend.repository.AppUserRepository;
import com.realestate.backend.repository.PropertyImageRepository;
import com.realestate.backend.repository.PropertyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(AppUserRepository userRepository, 
                                         PropertyRepository propertyRepository,
                                         PropertyImageRepository imageRepository) {
        return args -> {
            // Create users
            AppUser seller1 = AppUser.builder()
                    .name("John Doe")
                    .email("john@example.com")
                    .role("SELLER")
                    .build();
            
            AppUser seller2 = AppUser.builder()
                    .name("Jane Smith")
                    .email("jane@example.com")
                    .role("SELLER")
                    .build();
            
            AppUser landlord = AppUser.builder()
                    .name("Robert Johnson")
                    .email("robert@example.com")
                    .role("LANDLORD")
                    .build();
            
            List<AppUser> users = userRepository.saveAll(Arrays.asList(seller1, seller2, landlord));
            
            // Create properties
            Property property1 = Property.builder()
                    .title("Modern Apartment in Downtown")
                    .description("A beautiful modern apartment with 2 bedrooms, located in the heart of downtown.")
                    .location("123 Main St, Downtown")
                    .price(350000.0)
                    .owner(seller1)
                    .build();
            
            Property property2 = Property.builder()
                    .title("Cozy Family House with Garden")
                    .description("Spacious family house with 4 bedrooms and a large garden in a quiet neighborhood.")
                    .location("456 Oak Ave, Suburbia")
                    .price(550000.0)
                    .owner(seller2)
                    .build();
            
            Property property3 = Property.builder()
                    .title("Luxury Penthouse with City View")
                    .description("Stunning penthouse with panoramic city views, 3 bedrooms, and private terrace.")
                    .location("789 Sky Tower, Uptown")
                    .price(1200000.0)
                    .owner(seller1)
                    .build();
            
            Property property4 = Property.builder()
                    .title("Studio Apartment for Rent")
                    .description("Cozy studio apartment, fully furnished, available for long-term rent.")
                    .location("101 College St, University District")
                    .price(1200.0)
                    .owner(landlord)
                    .build();
            
            List<Property> properties = propertyRepository.saveAll(
                    Arrays.asList(property1, property2, property3, property4));
            
            // Create property images
            PropertyImage image1 = PropertyImage.builder()
                    .url("https://example.com/images/apartment1.jpg")
                    .property(property1)
                    .build();
            
            PropertyImage image2 = PropertyImage.builder()
                    .url("https://example.com/images/apartment1_living.jpg")
                    .property(property1)
                    .build();
            
            PropertyImage image3 = PropertyImage.builder()
                    .url("https://example.com/images/house1.jpg")
                    .property(property2)
                    .build();
            
            PropertyImage image4 = PropertyImage.builder()
                    .url("https://example.com/images/house1_garden.jpg")
                    .property(property2)
                    .build();
            
            PropertyImage image5 = PropertyImage.builder()
                    .url("https://example.com/images/penthouse1.jpg")
                    .property(property3)
                    .build();
            
            PropertyImage image6 = PropertyImage.builder()
                    .url("https://example.com/images/studio1.jpg")
                    .property(property4)
                    .build();
            
            imageRepository.saveAll(Arrays.asList(image1, image2, image3, image4, image5, image6));
            
            System.out.println("Database initialized with dummy data!");
        };
    }
}