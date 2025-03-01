package com.socialmediaplatform.service.impl;

import com.socialmediaplatform.Exceptions.CustomException.PostNotFoundException;
import com.socialmediaplatform.Repository.PostRepository;
import com.socialmediaplatform.Repository.UserRepository;
import com.socialmediaplatform.Response.PostResponseDTO;
import com.socialmediaplatform.dao.PostDao;
import com.socialmediaplatform.dao.UserDao;
import com.socialmediaplatform.dto.PostDTO;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.entities.User;
import com.socialmediaplatform.service.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    @Override
    public PostDTO getPostById(Long id) {
        Optional<Post> post = postDAO.findPostById(id);
        if (post.isEmpty()) {
            throw new PostNotFoundException("Post with ID " + id + " not found");

        }


        return new PostDTO(post.get().getContent(), post.get().getId());
    }


}
