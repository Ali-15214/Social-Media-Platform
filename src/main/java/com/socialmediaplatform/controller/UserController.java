package com.socialmediaplatform.controller;

import com.socialmediaplatform.dto.LoginDTO;
import com.socialmediaplatform.dto.RegisterDTO;
import com.socialmediaplatform.service.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserServices userServices;
    // Register a new user
    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegisterDTO userDTO) {
        return userServices.registerUser(userDTO);

    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody LoginDTO loginDTO) {
        return userServices.loginUser(loginDTO);
    }

    // Retrieve a user profile by ID
    @GetMapping("/{id}")
    public ResponseEntity getUserProfile(@PathVariable Long id) {
        return userServices.getUserProfile(id);
    }
}
