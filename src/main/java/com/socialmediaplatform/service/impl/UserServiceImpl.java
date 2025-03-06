package com.socialmediaplatform.service.impl;

import com.socialmediaplatform.Exceptions.CustomException.ActionAlreadyPerformedException;
import com.socialmediaplatform.Response.LoginResponse;
import com.socialmediaplatform.Response.RegisterUserResponse;
import com.socialmediaplatform.Response.UserProfileResponse;
import com.socialmediaplatform.Util.JwtUtil;
import com.socialmediaplatform.dao.UserDao;
import com.socialmediaplatform.dto.LoginDTO;
import com.socialmediaplatform.dto.RegisterDTO;
import com.socialmediaplatform.dto.SearchRequestDTO;
import com.socialmediaplatform.dto.UserDTO;
import com.socialmediaplatform.entities.User;
import com.socialmediaplatform.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@EnableCaching
@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    UserDao userDAO;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

//    @Autowired
//    private CustomUserDetailsService userDetailsService;

    @Override
    public ResponseEntity registerUser(RegisterDTO userDTO) {
        User user = userDAO.findByEmail(userDTO.getEmail());
        if (user != null) {
            throw new ActionAlreadyPerformedException("An account with this email already exists. Please try another email.");
        }

        user=userDAO.registerUser(userDTO);
       RegisterUserResponse response = new RegisterUserResponse("User registered " +
               "successfully",user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity loginUser(LoginDTO loginDTO) {

       authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );



       User user = userDAO.findByEmail(loginDTO.getEmail());


        final String jwt = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new LoginResponse(jwt));
    }

    @Cacheable(value = "users", key = "#id")
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

        return ResponseEntity.status(200).body(response);


    }

    @Override
    public Page<UserDTO> searchUsers(SearchRequestDTO searchRequestDTO) {

        int page = Math.max(searchRequestDTO.getPage(), 0); // Ensure page >= 0
        int size = Math.max(searchRequestDTO.getSize(), 2); // Ensure size >= 5
        String sortBy = searchRequestDTO.getSortBy() != null ? searchRequestDTO.getSortBy() : "username";
        String sortDirection = searchRequestDTO.getSortDirection() != null ? searchRequestDTO.getSortDirection() : "ASC";

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> users = userDAO.searchUsers(searchRequestDTO.getKeyword(), pageable);


        return users.map(this::convertToUserDTO);
    }

    private UserDTO convertToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getProfilePicture(), user.getBio());
    }
    }

