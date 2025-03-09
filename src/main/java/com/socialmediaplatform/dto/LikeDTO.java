package com.socialmediaplatform.dto;

import jakarta.validation.constraints.NotNull;



public class LikeDTO {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Post ID is required")
    private Long postId;

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

    @Override
    public String toString() {
        return "LikeDTO{" +
                "userId=" + userId +
                ", postId=" + postId +
                '}';
    }
}