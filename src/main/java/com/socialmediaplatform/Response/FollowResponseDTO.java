package com.socialmediaplatform.Response;

public class FollowResponseDTO {
    private Long followId;
    private Long followerId;
    private String followerName;
    private Long followingId;
    private String followingName;
    private String message;


    public FollowResponseDTO(Long followId, Long followerId, String followerName,
                             Long followingId, String followingName, String message) {
        this.followId = followId;
        this.followerId = followerId;
        this.followerName = followerName;
        this.followingId = followingId;
        this.followingName = followingName;
        this.message = message;
    }


    public Long getFollowId() {
        return followId;
    }

    public void setFollowId(Long followId) {
        this.followId = followId;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public String getFollowerName() {
        return followerName;
    }

    public void setFollowerName(String followerName) {
        this.followerName = followerName;
    }

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Long followingId) {
        this.followingId = followingId;
    }

    public String getFollowingName() {
        return followingName;
    }

    public void setFollowingName(String followingName) {
        this.followingName = followingName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
