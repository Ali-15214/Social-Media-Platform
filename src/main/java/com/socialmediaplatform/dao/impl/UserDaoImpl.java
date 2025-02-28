package com.socialmediaplatform.dao.impl;

import com.socialmediaplatform.Repository.UserRepository;
import com.socialmediaplatform.dao.UserDao;
import com.socialmediaplatform.dto.UserDTO;
import com.socialmediaplatform.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserDTO userDTO) {
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

}
