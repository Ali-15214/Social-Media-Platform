package com.socialmediaplatform.controller;

import com.socialmediaplatform.Response.PostResponseDTO;
import com.socialmediaplatform.dto.PostDTO;
import com.socialmediaplatform.Util.JwtUtil;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.service.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostServices postService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostDTO postDTO) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

            // Step 2: Create post for the logged-in user
            return postService.createPost(postDTO, email);

    }

    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "timestamp,desc") String[] sort) {

        // Create Pageable object for pagination and sorting
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("timestamp")));

        // Fetch posts from service layer
         Page<Post> posts = postService.getAllPosts(pageable);

        return ResponseEntity.ok(posts);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        PostDTO postDto = postService.getPostById(id);
        return ResponseEntity.ok(postDto);
    }
}
