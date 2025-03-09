package com.socialmediaplatform.dto;

import com.socialmediaplatform.entities.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public class CommentDTO {
    @NotNull(message = "User ID is required")
    private Long userId;

    private String username;
    public CommentDTO() {}
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = "Post ID is required")
    private Long postId;

    @NotBlank(message = "Content is required")
    private String content;

    public CommentDTO(Comment comment) {
        this.userId = comment.getId();
        this.username = comment.getUser().getUsername();
        this.content = comment.getContent();
    }
    public CommentDTO(Long userId, String username, String content) {
        this.userId = userId;
        this.username = username;
        this.content = content;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", postId=" + postId +
                ", content='" + content + '\'' +
                '}';
    }
}