package com.socialmediaplatform.controller;

import com.socialmediaplatform.dao.UserDao;
import com.socialmediaplatform.dto.*;
import com.socialmediaplatform.Util.JwtUtil;
import com.socialmediaplatform.entities.Comment;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.service.CommentService;
import com.socialmediaplatform.service.LikeService;
import com.socialmediaplatform.service.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostServices postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    LikeService likeService;

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


    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDto) {
        PostDTO updatedPost = postService.updatePost(id, postDto);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully.");
    }

//    @PostMapping("/{postId}/comments")
//    public ResponseEntity<CommentResponseDTO> addCommentToPost(
//            @PathVariable Long postId,
//            @RequestHeader("userId") Long userId,
//            @Valid @RequestBody CommentRequestDTO commentRequestDTO) {
//        CommentResponseDTO savedComment = commentService.addCommentToPost(postId, userId, commentRequestDTO);
//        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
//    }

    @PostMapping("{id}/comments")
    public Comment addComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        commentDTO.setPostId(id);
        return commentService.addComment(commentDTO);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable Long id,@RequestHeader(name = "Authorization") String headerToken) {
        Long currentUserId = jwtUtil.extractUserIdFromHeader(headerToken);
        likeService.likePost(id,currentUserId );
        return ResponseEntity.ok("Post liked successfully");
    }

    @PostMapping("/search")
    public Page<PostDTO> searchPosts(@RequestBody SearchRequestDTO searchRequestDTO) {
        return postService.searchPosts(searchRequestDTO);
    }
}
