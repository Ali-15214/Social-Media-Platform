package com.socialmediaplatform.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FollowService {
    ResponseEntity<?> followUser(Long targetUserId, Long followerId);

    ResponseEntity<?> getUserFollowers(Long targetUserId);

    List<String> getFollowingUsernames(Long userId);
}
