package com.socialmediaplatform.service;

import com.socialmediaplatform.dto.LoginDTO;
import com.socialmediaplatform.dto.UserDTO;
import com.socialmediaplatform.entities.User;
import org.springframework.http.ResponseEntity;

public interface UserServices {
    ResponseEntity registerUser(UserDTO userDTO);
    ResponseEntity loginUser(LoginDTO loginDTO);
    ResponseEntity getUserProfile(Long id);

}
