package com.realestate.backend.controller;

import com.realestate.backend.model.AppUser;
import com.realestate.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public AppUser createUser(@RequestBody AppUser user) {
        return userService.createUser(user);
    }

    @GetMapping
    public List<AppUser> listAllUsers() {
        return userService.listAllUsers();
    }

    @GetMapping("/{id}")
    public AppUser getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public AppUser updateUser(@PathVariable Long id, @RequestBody AppUser userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/subscribe")
    public String subscribeToPremium(@RequestParam Long userId) {
        return userService.subscribeToPremium(userId);
    }

    @PostMapping("/unsubscribe")
    public String unsubscribeFromPremium(@RequestParam Long userId) {
        return userService.unsubscribeFromPremium(userId);
    }

    @GetMapping("/premium-feature")
    public String getPremiumFeature(@RequestParam Long userId) {
        return userService.getPremiumFeature(userId);
    }
}
