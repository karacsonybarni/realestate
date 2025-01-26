package com.realestate.backend.service;

import com.realestate.backend.model.AppUser;
import com.realestate.backend.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final AppUserRepository userRepository;

    public UserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser createUser(AppUser user) {
        return userRepository.save(user);
    }

    public List<AppUser> listAllUsers() {
        return userRepository.findAll();
    }

    public AppUser getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    public AppUser updateUser(Long id, AppUser userDetails) {
        AppUser existing = getUserById(id);
        existing.setName(userDetails.getName());
        existing.setEmail(userDetails.getEmail());
        existing.setRole(userDetails.getRole());
        return userRepository.save(existing);
    }

    public void deleteUser(Long id) {
        AppUser existing = getUserById(id);
        userRepository.delete(existing);
    }
}

