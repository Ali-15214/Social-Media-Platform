package com.socialmediaplatform.dao;

import com.socialmediaplatform.dto.UserDTO;
import com.socialmediaplatform.entities.User;

public interface UserDao {
    User registerUser(UserDTO userDTO);

    User findByEmail(String userName);

    User findById(Long id);
}
