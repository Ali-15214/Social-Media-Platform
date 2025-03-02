package com.socialmediaplatform.dao;

import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.entities.User;

import java.util.Optional;

public interface LikeDao {
    void saveUser(User user);
    Optional<User> findUserById(Long userId);
    Optional<Post> findPostById(Long postId);
}
