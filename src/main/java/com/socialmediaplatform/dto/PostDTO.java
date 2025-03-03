package com.socialmediaplatform.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.socialmediaplatform.entities.Comment;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {
//    private long id;
//
//
//    private String content;
//
//    private Set<User> postLikes; // Total likes count
//    private Set<CommentDTO> comments;
//
//    public PostDTO(Long id, String content, Set<User> likedByUsers, Set<Comment> comments) {
//    }
//
//    public PostDTO(String content, long id) {
//        this.content = content;
//        this.id = id;
//    }
//
//    public PostDTO(long id, String content, Set<User> postLikes, Set<CommentDTO> comments) {
//        this.id = id;
//        this.content = content;
//        // Extract usernames from likedByUsers
//
//    }
//
//    public PostDTO(Optional<Post> post) {
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }

    private Long id;
    private String content;
    private Set<String> likedByUsernames;
    private List<CommentDTO> comments;
    public PostDTO() {
    }
    public PostDTO(String content, Long id) {
        this.content = content;
        this.id = id;
    }
    public PostDTO(Post post) {
        this.id = post.getId();
        this.content = post.getContent();

        // Extract usernames from likedByUsers
        this.likedByUsernames = post.getLikedByUsers().stream()
                .map(User::getUsername)
                .collect(Collectors.toSet());

        // Convert comments to DTOs
        this.comments = post.getComments().stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }



    public PostDTO(Long id, String content, Set<User> likedByUsers, Set<Comment> comments) {
    }


    // Getters
    public Long getId() { return id; }
    public String getContent() { return content; }
    public Set<String> getLikedByUsernames() { return likedByUsernames; }
    public List<CommentDTO> getComments() { return comments; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLikedByUsernames(Set<String> likedByUsernames) {
        this.likedByUsernames = likedByUsernames;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }
}


