package com.socialmediaplatform.service.impl;

import com.socialmediaplatform.Exceptions.CustomException.PostNotFoundException;
import com.socialmediaplatform.Exceptions.CustomException.UnauthorizedAccessException;
import com.socialmediaplatform.Exceptions.CustomException.UserNotFoundException;
import com.socialmediaplatform.Repository.PostRepository;
import com.socialmediaplatform.Repository.UserRepository;
import com.socialmediaplatform.Response.PostResponseDTO;
import com.socialmediaplatform.dao.PostDao;
import com.socialmediaplatform.dao.UserDao;
import com.socialmediaplatform.dto.PostDTO;
import com.socialmediaplatform.dto.SearchRequestDTO;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.entities.User;
import com.socialmediaplatform.service.PostServices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class PostServicesImpl implements PostServices {

    @Autowired
    PostDao postDAO;
    @Autowired
    UserDao userDAO;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity createPost(PostDTO postDTO, String email) {
        User user = userDAO.findByEmail(email);

        Post savedPost = postDAO.savePost(postDTO, user);
        PostResponseDTO responseDTO = convertToPostResponseDTO(savedPost);

        // Return ApiResponse
        return ResponseEntity.status(201).body(responseDTO);
    }

    private PostResponseDTO convertToPostResponseDTO(Post post) {
        PostResponseDTO responseDTO = new PostResponseDTO();
        responseDTO.setId(post.getId());
        responseDTO.setContent(post.getContent());
        responseDTO.setTimestamp(post.getTimestamp());
        responseDTO.setUserEmail(post.getUser().getEmail());
        responseDTO.setUserName(post.getUser().getUsername());
        return responseDTO;


    }

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postDAO.findAllPosts(pageable);
    }


    public PostDTO getPostById(Long id) {
        Post post = postDAO.findPostById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        return new PostDTO(post);
    }

    @Override
    public PostDTO updatePost(Long postId, PostDTO postDto) {

        // Fetch the post by ID, or throw an error if it doesn’t exist
        Post post = postDAO.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        post.setContent(postDto.getContent());

        //  Save updated post
        Post updatedPost = postDAO.updatePost(post);

        //  Return updated data as DTO
        return new PostDTO(updatedPost.getContent(), updatedPost.getId());
    }






    @Override
    public void deletePost(Long postId,Long userId) {

        Post post = postDAO.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
        if (!post.getUser().equals(userId)) {
            throw new UnauthorizedAccessException("You are not allowed to delete this post");
        }

      postDAO.removePost(postId);
}


    @Override
    public Page<PostDTO> searchPosts(SearchRequestDTO searchRequestDTO) {
        // Search for posts using the DAO layer
        Page<Post> posts = postDAO.searchPosts(
                searchRequestDTO.getKeyword(),
                searchRequestDTO.getPage(),
                searchRequestDTO.getSize(),
                searchRequestDTO.getSortBy(),
                searchRequestDTO.getSortDirection()
        );

        // Convert Post entities to PostDTOs
        return posts.map(this::convertToPostDTO);
    }

    private PostDTO convertToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO(post.getId(), post.getContent(), post.getLikedByUsers(), post.getComments());
        postDTO.setId(post.getId());
        postDTO.setContent(post.getContent());
        return postDTO;
    }
}
