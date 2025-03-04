package com.socialmediaplatform.controller;


import com.socialmediaplatform.Util.JwtUtil;
import com.socialmediaplatform.dto.*;
import com.socialmediaplatform.service.FollowService;
import com.socialmediaplatform.service.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Operations related to user management")
public class UserController {

    @Autowired
    UserServices userServices;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private FollowService followService;
    // Register a new user

    @Operation(summary = "User Registration", description = "Registers a new user with email and password validation.")
    @ApiResponse(responseCode = "201", description = "User registered successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid input: Email format incorrect, password must be 5 characters and contain a number.")
    @ApiResponse(responseCode = "409", description = "Conflict: An account with this email already exists. Please try another email.")

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegisterDTO userDTO) {
        return userServices.registerUser(userDTO);

    }


    // Login user
    @Operation(summary = "Login user", description = "Authenticates a user and returns a JWT token")
    @ApiResponse(responseCode = "200", description = "Login successful.")
    @ApiResponse(responseCode = "400", description = "Bad Request: Invalid email format or password requirements not met.")
    @ApiResponse(responseCode = "401", description = "Unauthorized: Incorrect email or password.")
    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody LoginDTO loginDTO) {
        return userServices.loginUser(loginDTO);
    }




    // Retrieve a user profile by ID
    @Operation(summary = "Get user profile", description = "Fetches a user profile by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity getUserProfile(@Parameter(description = "User ID")@PathVariable Long id) {
        return userServices.getUserProfile(id);
    }


    @Operation(summary = "Follow a user", description = "Allows an authenticated user to follow another user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User followed successfully"),
            @ApiResponse(responseCode = "409", description = "Already following the user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })

@PostMapping("/{id}/follow")
public ResponseEntity<?> followUser( @Parameter(description = "ID of the user to be followed")@PathVariable Long id,@Parameter(description = "JWT token for authentication", required = true) @RequestHeader(name = "Authorization") String headerToken)  {
    Long followerId = jwtUtil.extractUserIdFromHeader(headerToken);
    return followService.followUser(id, followerId);
}


    @Operation(summary = "Get followers", description = "Retrieves the list of followers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of followers users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}/followers")
    public ResponseEntity<?> getUserFollowers(@Parameter(description = "User ID")@PathVariable Long id) {

        return followService.getUserFollowers(id);
    }

    @Operation(summary = "Get following users", description = "Retrieves the list of following")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of following users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}/following")
    public ResponseEntity<List<String>> getFollowing(@Parameter(description = "User ID")@PathVariable Long id) {
        List<String> followingUsernames = followService.getFollowingUsernames(id);
        return ResponseEntity.ok(followingUsernames);
    }



    @Operation(summary = "Search for users", description = "Search for users based on keywords in the username, email, or bio with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found successfully")
    })

    @PostMapping("/search")
    public ResponseEntity<Page<UserDTO>> searchUsers(@Parameter(description = "Search request containing keywords")@RequestBody SearchRequestDTO searchRequestDTO) {
        Page<UserDTO> users = userServices.searchUsers(searchRequestDTO);
        return ResponseEntity.ok(users);
    }
}
