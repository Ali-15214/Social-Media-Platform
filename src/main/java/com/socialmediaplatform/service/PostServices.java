package com.socialmediaplatform.service;

import com.socialmediaplatform.dto.PostDTO;
import com.socialmediaplatform.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PostServices {
    ResponseEntity createPost(PostDTO postDTO, String email);

        Page<Post> getAllPosts(Pageable pageable);
    PostDTO getPostById(Long id);

}
