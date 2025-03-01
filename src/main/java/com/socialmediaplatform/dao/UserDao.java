package com.socialmediaplatform.dao;

import com.socialmediaplatform.dto.RegisterDTO;
import com.socialmediaplatform.entities.User;

public interface UserDao {
    User registerUser(RegisterDTO userDTO);

    User findByEmail(String email);

    User findById(Long id);
}
