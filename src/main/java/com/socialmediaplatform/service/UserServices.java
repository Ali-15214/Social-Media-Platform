package com.socialmediaplatform.service;

import com.socialmediaplatform.dto.LoginDTO;
import com.socialmediaplatform.dto.RegisterDTO;
import org.springframework.http.ResponseEntity;

public interface UserServices {
    ResponseEntity registerUser(RegisterDTO userDTO);
    ResponseEntity loginUser(LoginDTO loginDTO);
    ResponseEntity getUserProfile(Long id);

}
