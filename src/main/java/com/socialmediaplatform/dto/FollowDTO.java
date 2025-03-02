package com.socialmediaplatform.dto;

import com.socialmediaplatform.entities.User;
import jakarta.validation.constraints.NotNull;



public class FollowDTO {
    @NotNull(message = "Follower ID is required")
    private Long followerId;

    @NotNull(message = "Following ID is required")
    private Long followingId;

    public FollowDTO(Long followerId, Long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Long followingId) {
        this.followingId = followingId;
    }
}
