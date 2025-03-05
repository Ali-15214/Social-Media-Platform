package com.socialmediaplatform.service.impl;

import com.socialmediaplatform.Exceptions.CustomException.PostNotFoundException;
import com.socialmediaplatform.dao.CommentDao;
import com.socialmediaplatform.entities.Comment;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.entities.User;
import com.socialmediaplatform.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentDao commentDao;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Post post;
    private User user;
    private Comment comment;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        post = new Post();
        post.setId(1L);
        post.setContent("Test Post");

        comment = new Comment(user, post, "This is a test comment");
    }

    @Test
    void testAddComment_Success() {
        when(commentDao.findById(1L)).thenReturn(Optional.of(post));
        when(commentDao.findByUserId(1L)).thenReturn(Optional.of(user));
        when(commentDao.saveComment(any(Comment.class))).thenReturn(comment);

        Comment result = commentService.addComment(1L, 1L, "This is a test comment");

        assertNotNull(result);
        assertEquals("This is a test comment", result.getContent());
        verify(commentDao, times(1)).saveComment(any(Comment.class));
    }

    @Test
    void testAddComment_PostNotFound() {
        when(commentDao.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(PostNotFoundException.class, () -> 
            commentService.addComment(1L, 1L, "This is a test comment"));

        assertEquals("Post not found with ID: 1", exception.getMessage());
    }

    @Test
    void testAddComment_UserNotFound() {
        when(commentDao.findById(1L)).thenReturn(Optional.of(post));
        when(commentDao.findByUserId(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> 
            commentService.addComment(1L, 1L, "This is a test comment"));

        assertEquals("User not found with ID: 1", exception.getMessage());
    }
}
