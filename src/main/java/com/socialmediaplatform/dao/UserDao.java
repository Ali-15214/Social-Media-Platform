package com.socialmediaplatform.dao;

import com.socialmediaplatform.dto.RegisterDTO;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserDao {
    User registerUser(RegisterDTO userDTO);

    User findByEmail(String email);

    User findById(Long id);

    Page<User> searchUsers(String keyword, int page, int size, String sortBy, String sortDirection);

    Page<User> searchUsers(String keyword, Pageable pageable);
}
