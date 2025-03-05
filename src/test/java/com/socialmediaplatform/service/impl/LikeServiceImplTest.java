package com.socialmediaplatform.service.impl;

import com.socialmediaplatform.Exceptions.CustomException.ActionAlreadyPerformedException;
import com.socialmediaplatform.Exceptions.CustomException.PostNotFoundException;
import com.socialmediaplatform.dao.LikeDao;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {
    
    @Mock
    private LikeDao likeDao;
    
    @InjectMocks
    private LikeServiceImpl likeService;
    
    private User user;
    private Post post;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setLikedPosts(new HashSet<>());

        post = new Post();
        post.setId(1L);
    }

    @Test
    void likePost_Success() {
        when(likeDao.findUserById(1L)).thenReturn(Optional.of(user));
        when(likeDao.findPostById(1L)).thenReturn(Optional.of(post));

        likeService.likePost(1L, 1L);

        assertTrue(user.getLikedPosts().contains(post));
        verify(likeDao, times(1)).saveUser(user);
    }

    @Test
    void likePost_UserNotFound_ShouldThrowException() {
        when(likeDao.findUserById(1L)).thenReturn(Optional.empty());
        
        assertThrows(UsernameNotFoundException.class, () -> likeService.likePost(1L, 1L));
    }

    @Test
    void likePost_PostNotFound_ShouldThrowException() {
        when(likeDao.findUserById(1L)).thenReturn(Optional.of(user));
        when(likeDao.findPostById(1L)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> likeService.likePost(1L, 1L));
    }

    @Test
    void likePost_AlreadyLiked_ShouldThrowException() {
        user.getLikedPosts().add(post);
        
        when(likeDao.findUserById(1L)).thenReturn(Optional.of(user));
        when(likeDao.findPostById(1L)).thenReturn(Optional.of(post));

        assertThrows(ActionAlreadyPerformedException.class, () -> likeService.likePost(1L, 1L));
    }
}
