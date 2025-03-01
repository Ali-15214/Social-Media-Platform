package com.socialmediaplatform.service.impl;

import com.socialmediaplatform.Exceptions.CustomException.UserNotFoundException;
import com.socialmediaplatform.Repository.UserRepository;
import com.socialmediaplatform.Response.LoginResponse;
import com.socialmediaplatform.Response.RegisterUserResponse;
import com.socialmediaplatform.Response.UserProfileResponse;
import com.socialmediaplatform.Util.JwtUtil;
import com.socialmediaplatform.dao.UserDao;
import com.socialmediaplatform.dto.LoginDTO;
import com.socialmediaplatform.dto.RegisterDTO;
import com.socialmediaplatform.entities.User;
import com.socialmediaplatform.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    UserDao userDAO;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public ResponseEntity registerUser(RegisterDTO userDTO) {
        User user = userDAO.findByEmail(userDTO.getEmail());
        if (user != null) {
            throw new UserNotFoundException("An account with this email already exists. Please try another email.");
        }

        user=userDAO.registerUser(userDTO);
       RegisterUserResponse response = new RegisterUserResponse("User registered " +
               "successfully",user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity loginUser(LoginDTO loginDTO) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        // Load user details
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());

        // Generate JWT token
        final String jwt = jwtUtil.generateToken(userDetails);

        // Return token in response
        return ResponseEntity.ok(new LoginResponse(jwt));
    }

    @Override
    public ResponseEntity getUserProfile(Long id) {
        User user = userDAO.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }

        UserProfileResponse response = new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getProfilePicture(),
                user.getBio()
        );
        // Return user profile
        return ResponseEntity.status(200).body(response);


    }
    }

