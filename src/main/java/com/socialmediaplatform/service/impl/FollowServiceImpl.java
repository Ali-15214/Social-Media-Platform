package com.socialmediaplatform.service.impl;

import com.socialmediaplatform.dao.FollowDAO;
import com.socialmediaplatform.entities.Follow;
import com.socialmediaplatform.entities.User;
import com.socialmediaplatform.Repository.UserRepository;
import com.socialmediaplatform.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowDAO followDAO;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> followUser(Long targetUserId, Long followerId) {
        // **User Fetch from DB (Only Once)**
        User follower = userRepository.findById(followerId).orElse(null);
        User targetUser = userRepository.findById(targetUserId).orElse(null);

        if (follower == null || targetUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("{\"message\": \"User not found\"}");
        }

        // **Check if already following (No extra DB query)**
        if (followDAO.isAlreadyFollowing(follower, targetUser)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("{\"message\": \"You are already following this user.\"}");
        }

        // **Save follow entry**
        Follow follow = new Follow(follower, targetUser);
        followDAO.saveFollow(follow);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body("{\"message\": \"You are now following " + targetUser.getUsername() + "\"}");
    }

    @Override
    public ResponseEntity<?> getUserFollowers(Long targetUserId) {
        User targetUser = userRepository.findById(targetUserId).orElse(null);


        if ( targetUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"User not found\"}");
        }
        List<User> followers = followDAO.getFollowers(targetUser);
        List<String> followerUsernames = new ArrayList<>();
        for (User user : followers) {
            followerUsernames.add(user.getUsername());
        }

        return ResponseEntity.ok(followerUsernames);

    }
    @Override
    public List<String> getFollowingUsernames(Long userId) {
        List<User> followingUsers = followDAO.findFollowingUsers(userId);
        return followingUsers.stream()
                .map(User::getUsername)  // Sirf usernames extract kar rahe hain
                .collect(Collectors.toList());
    }
}
