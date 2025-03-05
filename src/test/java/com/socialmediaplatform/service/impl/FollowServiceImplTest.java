package com.socialmediaplatform.service.impl;

import com.socialmediaplatform.dao.FollowDAO;
import com.socialmediaplatform.entities.Follow;
import com.socialmediaplatform.entities.User;
import com.socialmediaplatform.service.FollowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceImplTest {

    @Mock
    private FollowDAO followDAO;

    @InjectMocks
    private FollowServiceImpl followService;

    private User follower;
    private User targetUser;
    private Follow follow;

    @BeforeEach
    void setUp() {
        follower = new User();
        follower.setId(1L);
        follower.setUsername("followerUser");

        targetUser = new User();
        targetUser.setId(2L);
        targetUser.setUsername("targetUser");

        follow = new Follow(follower, targetUser);
    }

    @Test
    void testFollowUser_Success() {
        when(followDAO.findById(1L)).thenReturn(Optional.of(follower));
        when(followDAO.findById(2L)).thenReturn(Optional.of(targetUser));
        when(followDAO.isAlreadyFollowing(follower, targetUser)).thenReturn(false);

        ResponseEntity<?> response = followService.followUser(2L, 1L);

        assertEquals(201, response.getStatusCodeValue());
        verify(followDAO, times(1)).saveFollow(any(Follow.class));
    }

    @Test
    void testFollowUser_AlreadyFollowing() {
        when(followDAO.findById(1L)).thenReturn(Optional.of(follower));
        when(followDAO.findById(2L)).thenReturn(Optional.of(targetUser));
        when(followDAO.isAlreadyFollowing(follower, targetUser)).thenReturn(true);

        ResponseEntity<?> response = followService.followUser(2L, 1L);

        assertEquals(409, response.getStatusCodeValue());
        verify(followDAO, never()).saveFollow(any(Follow.class));
    }

    @Test
    void testFollowUser_UserNotFound() {
        when(followDAO.findById(1L)).thenReturn(Optional.empty());
        when(followDAO.findById(2L)).thenReturn(Optional.of(targetUser));

        ResponseEntity<?> response = followService.followUser(2L, 1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetUserFollowers_Success() {
        when(followDAO.findById(2L)).thenReturn(Optional.of(targetUser));
        when(followDAO.getFollowers(targetUser)).thenReturn(List.of(follower));

        ResponseEntity<?> response = followService.getUserFollowers(2L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof List);
    }

    @Test
    void testGetUserFollowers_UserNotFound() {
        when(followDAO.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = followService.getUserFollowers(2L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetFollowingUsernames_Success() {
        when(followDAO.findFollowingUsers(1L)).thenReturn(List.of(targetUser));

        List<String> usernames = followService.getFollowingUsernames(1L);

        assertFalse(usernames.isEmpty());
        assertEquals("targetUser", usernames.get(0));
    }

    @Test
    void testGetFollowingUsernames_UserNotFound() {
        when(followDAO.findFollowingUsers(1L)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> followService.getFollowingUsernames(1L));

        assertEquals("User not found with ID: 1", exception.getMessage());
    }
}
