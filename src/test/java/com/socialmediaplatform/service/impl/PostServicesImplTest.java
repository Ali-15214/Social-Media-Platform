package com.socialmediaplatform.service.impl;

import com.socialmediaplatform.Exceptions.CustomException.PostNotFoundException;
import com.socialmediaplatform.Exceptions.CustomException.UnauthorizedAccessException;
import com.socialmediaplatform.Repository.PostRepository;
import com.socialmediaplatform.Repository.UserRepository;
import com.socialmediaplatform.dao.PostDao;
import com.socialmediaplatform.dao.UserDao;
import com.socialmediaplatform.dto.PostDTO;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServicesImplTest {

    @Mock
    private PostDao postDAO;

    @Mock
    private UserDao userDAO;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostServicesImpl postServices;

    private User mockUser;
    private Post mockPost;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setUsername("TestUser");

        mockPost = new Post();
        mockPost.setId(1L);
        mockPost.setContent("Sample Post");
        mockPost.setUser(mockUser);
    }

    @Test
    void testCreatePost_Success() {
        // Arrange
        PostDTO postDTO = new PostDTO();
        postDTO.setContent("New Post");

        when(userDAO.findByEmail("test@example.com")).thenReturn(mockUser);
        when(postDAO.savePost(any(PostDTO.class), any(User.class))).thenReturn(mockPost);


        ResponseEntity response = postServices.createPost(postDTO, "test@example.com");


        assertEquals(201, response.getStatusCode().value());
        verify(userDAO, times(1)).findByEmail("test@example.com");
        verify(postDAO, times(1)).savePost(any(PostDTO.class), any(User.class));
    }

    @Test
    void testGetPostById_Success() {

        when(postDAO.findPostById(1L)).thenReturn(Optional.of(mockPost));


        PostDTO result = postServices.getPostById(1L);


        assertNotNull(result);
        assertEquals("Sample Post", result.getContent());
    }

    @Test
    void testGetPostById_NotFound() {

        when(postDAO.findPostById(1L)).thenReturn(Optional.empty());


        assertThrows(PostNotFoundException.class, () -> postServices.getPostById(1L));
    }

    @Test
    void testUpdatePost_Success() {

        PostDTO updatedPostDTO = new PostDTO();
        updatedPostDTO.setContent("Updated Post");

        when(postDAO.findById(1L)).thenReturn(Optional.of(mockPost));
        when(postDAO.updatePost(any(Post.class))).thenReturn(mockPost);


        PostDTO result = postServices.updatePost(1L, 1L, updatedPostDTO);


        assertNotNull(result);
        assertEquals("Updated Post", result.getContent());
        verify(postDAO, times(1)).updatePost(any(Post.class));
    }

    @Test
    void testUpdatePost_Unauthorized() {

        when(postDAO.findById(1L)).thenReturn(Optional.of(mockPost));


        assertThrows(UnauthorizedAccessException.class, () -> postServices.updatePost(1L, 2L, new PostDTO()));
    }

    @Test
    void testDeletePost_Success() {

        when(postDAO.findById(1L)).thenReturn(Optional.of(mockPost));


        postServices.deletePost(1L, 1L);


        verify(postDAO, times(1)).removePost(1L);
    }

    @Test
    void testDeletePost_Unauthorized() {

        when(postDAO.findById(1L)).thenReturn(Optional.of(mockPost));


        assertThrows(UnauthorizedAccessException.class, () -> postServices.deletePost(1L, 2L));
    }

    @Test
    void testGetAllPosts_Success() {

        Pageable pageable = mock(Pageable.class);
        Page<Post> page = mock(Page.class);
        when(postDAO.findAllPosts(pageable)).thenReturn(page);


        Page<Post> result = postServices.getAllPosts(pageable);


        assertNotNull(result);
        verify(postDAO, times(1)).findAllPosts(pageable);
    }
}
