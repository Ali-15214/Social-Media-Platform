package com.socialmediaplatform.controller;

import com.socialmediaplatform.Exceptions.CustomException.AlreadyFollowingException;
import com.socialmediaplatform.Response.FollowResponseDTO;
import com.socialmediaplatform.Util.JwtUtil;
import com.socialmediaplatform.dto.*;
import com.socialmediaplatform.service.FollowService;
import com.socialmediaplatform.service.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserServices userServices;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private FollowService followService;
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


@PostMapping("/{id}/follow")
public ResponseEntity<?> followUser(@PathVariable Long id, @RequestHeader(name = "Authorization") String headerToken)  {
    Long followerId = jwtUtil.extractUserIdFromHeader(headerToken);
    return followService.followUser(id, followerId);
}


    @GetMapping("/{id}/followers")
    public ResponseEntity<?> getUserFollowers(@PathVariable Long id) {

        return followService.getUserFollowers(id);
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<List<String>> getFollowing(@PathVariable Long id) {
        List<String> followingUsernames = followService.getFollowingUsernames(id);
        return ResponseEntity.ok(followingUsernames);
    }
//



    @PostMapping("/search")
    public ResponseEntity<Page<UserDTO>> searchUsers(@RequestBody SearchRequestDTO searchRequestDTO) {
        Page<UserDTO> users = userServices.searchUsers(searchRequestDTO);
        return ResponseEntity.ok(users);
    }
}
