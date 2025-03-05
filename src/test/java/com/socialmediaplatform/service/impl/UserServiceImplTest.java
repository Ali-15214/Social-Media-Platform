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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    private User mockUser;
    private RegisterDTO registerDTO;
    private LoginDTO loginDTO;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testUser");
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("encodedPassword");

        registerDTO = new RegisterDTO();
        registerDTO.setEmail("test@example.com");
        registerDTO.setPassword("password");
        registerDTO.setUsername("testUser");

        loginDTO = new LoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("password");
    }

    @Test
    void testRegisterUser_Success() {
        when(userDao.findByEmail(registerDTO.getEmail())).thenReturn(null);
        when(userDao.registerUser(registerDTO)).thenReturn(mockUser);

        ResponseEntity response = userService.registerUser(registerDTO);

        assertEquals(201, response.getStatusCodeValue());
        RegisterUserResponse body = (RegisterUserResponse) response.getBody();
        assertNotNull(body);
        assertEquals("User registered successfully", body.getMessage());
    }

    @Test
    void testRegisterUser_UserAlreadyExists() {
        when(userDao.findByEmail(registerDTO.getEmail())).thenReturn(mockUser);

        assertThrows(ActionAlreadyPerformedException.class, () -> userService.registerUser(registerDTO));
    }

    @Test
    void testLoginUser_Success() {
        when(userDao.findByEmail(loginDTO.getEmail())).thenReturn(mockUser);
        when(jwtUtil.generateToken(mockUser)).thenReturn("fake-jwt-token");

        doNothing().when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        ResponseEntity response = userService.loginUser(loginDTO);

        assertEquals(200, response.getStatusCodeValue());
        LoginResponse body = (LoginResponse) response.getBody();
        assertNotNull(body);
        assertEquals("fake-jwt-token", body.getToken());
    }

    @Test
    void testGetUserProfile_Success() {
        when(userDao.findById(1L)).thenReturn(mockUser);

        ResponseEntity response = userService.getUserProfile(1L);

        assertEquals(200, response.getStatusCodeValue());
        UserProfileResponse body = (UserProfileResponse) response.getBody();
        assertNotNull(body);
        assertEquals("testUser", body.getUsername());
    }

    @Test
    void testGetUserProfile_UserNotFound() {
        when(userDao.findById(1L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> userService.getUserProfile(1L));
    }
}
