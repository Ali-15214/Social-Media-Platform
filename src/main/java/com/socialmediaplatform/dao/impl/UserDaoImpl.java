package com.socialmediaplatform.dao.impl;

import com.socialmediaplatform.Repository.UserRepository;
import com.socialmediaplatform.dao.UserDao;
import com.socialmediaplatform.dto.RegisterDTO;
import com.socialmediaplatform.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterDTO userDTO) {
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());

        User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(encryptedPassword); // In real-world, hash the password
            user.setProfilePicture(userDTO.getProfilePicture());
            user.setBio(userDTO.getBio());
            return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Page<User> searchUsers(String keyword, int page, int size, String sortBy, String sortDirection) {
        // Create a Pageable object for pagination and sorting
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // Search for users using the repository
        return userRepository.searchByUsernameOrEmailOrBio(keyword, pageable);
    }

    @Override
    public Page<User> searchUsers(String keyword, Pageable pageable) {
        return userRepository.searchByUsernameOrEmailOrBio(keyword, pageable);
    }
}
