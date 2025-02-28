package com.socialmediaplatform.service.impl;

import com.socialmediaplatform.Exceptions.CustomException.InvalidCredentialsException;
import com.socialmediaplatform.Exceptions.CustomException.UserNotFoundException;
import com.socialmediaplatform.Response.RegisterUserResponse;
import com.socialmediaplatform.Response.UserProfileResponse;
import com.socialmediaplatform.dao.UserDao;
import com.socialmediaplatform.dto.LoginDTO;
import com.socialmediaplatform.dto.UserDTO;
import com.socialmediaplatform.entities.User;
import com.socialmediaplatform.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    UserDao userDAO;

    @Override
    public ResponseEntity registerUser(UserDTO userDTO) {
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
        User user = userDAO.findByEmail(loginDTO.getEmail());

        if (user == null) {
            throw new UserNotFoundException("User not found. Please register first.");
        }

        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        return ResponseEntity.ok("Login successful");
    }

    @Override
    public ResponseEntity getUserProfile(Long id) {
        User user = userDAO.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }

//        UserProfileResponse response = new UserProfileResponse(
//                user.getId(),
//                user.getUsername(),
//                user.getEmail(),
//                user.getProfilePicture(),
//                user.getBio()
//        );
        // Return user profile
        return ResponseEntity.status(200).body(user);


    }
    }

