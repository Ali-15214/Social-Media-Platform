package com.socialmediaplatform.controller;

import com.socialmediaplatform.dao.UserDao;
import com.socialmediaplatform.dto.*;
import com.socialmediaplatform.Util.JwtUtil;
import com.socialmediaplatform.entities.Comment;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.service.CommentService;
import com.socialmediaplatform.service.LikeService;
import com.socialmediaplatform.service.PostServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/posts")
@Tag(name = "Post Controller", description = "APIs related to posts management")
public class PostController {

    @Autowired
    private PostServices postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    LikeService likeService;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Create a new post", description = "Creates a new post for the logged-in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post created successfully"),
            @ApiResponse(responseCode = "400", description = "Content exceeds maximum limit"),
    })

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostDTO postDTO) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();


            return postService.createPost(postDTO, email);

    }


    @Operation(summary = "Get all posts", description = "Retrieves paginated posts with sorting options")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts")
    })

    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "timestamp,desc") String[] sort) {


        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("timestamp")));


         Page<Post> posts = postService.getAllPosts(pageable);

        return ResponseEntity.ok(posts);

    }

    @Operation(summary = "Get post by ID", description = "Retrieves a single post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post found successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }


    @Operation(summary = "Update a post", description = "Updates an existing post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id,@RequestHeader("Authorization") String token, @RequestBody PostDTO postDto) {
        Long userId = jwtUtil.extractUserIdFromHeader(token);
        PostDTO updatedPost = postService.updatePost(id,userId, postDto);
        return ResponseEntity.ok(updatedPost);
    }


    @Operation(summary = "Delete a post", description = "Deletes a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, @RequestHeader("Authorization") String token) {

        Long userId = jwtUtil.extractUserIdFromHeader(token);
        postService.deletePost(id,userId);
        return ResponseEntity.ok("Post deleted successfully.");
    }



    @Operation(summary = "Add a comment to a post", description = "Adds a comment to a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment added successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @PostMapping("/{id}/comments")
    public Comment addComment(@PathVariable Long id, @RequestBody Map<String, String> request, @RequestHeader(name = "Authorization") String headerToken) {
        Long userId = jwtUtil.extractUserIdFromHeader(headerToken);
        String content = request.get("content");
        return commentService.addComment(id, userId, content);

}

    @Operation(summary = "Like a post", description = "Likes a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post liked successfully"),
            @ApiResponse(responseCode = "409", description = "Already liked the post"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @PostMapping("/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable Long id,@RequestHeader(name = "Authorization") String headerToken) {
        Long currentUserId = jwtUtil.extractUserIdFromHeader(headerToken);
        likeService.likePost(id,currentUserId );
        return ResponseEntity.ok("Post liked successfully");
    }

}
