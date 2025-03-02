package com.socialmediaplatform.service;

import com.socialmediaplatform.dto.LoginDTO;
import com.socialmediaplatform.dto.RegisterDTO;
import com.socialmediaplatform.dto.SearchRequestDTO;
import com.socialmediaplatform.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface UserServices {
    ResponseEntity registerUser(RegisterDTO userDTO);
    ResponseEntity loginUser(LoginDTO loginDTO);
    ResponseEntity getUserProfile(Long id);

    Page<UserDTO> searchUsers(SearchRequestDTO searchRequestDTO);
}
