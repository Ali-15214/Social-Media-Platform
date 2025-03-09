package com.socialmediaplatform.Response;

import java.time.LocalDateTime;

public class CommentResponseDTO {
    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private LocalDateTime timestamp;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CommentResponseDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", postId=" + postId +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}