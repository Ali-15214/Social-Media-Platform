package com.socialmediaplatform.service.impl;

import com.socialmediaplatform.Exceptions.CustomException.PostNotFoundException;
import com.socialmediaplatform.Exceptions.CustomException.UnauthorizedAccessException;
import com.socialmediaplatform.dao.LikeDao;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.entities.User;
import com.socialmediaplatform.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeDao likeDao;

    @Transactional
    @Override
    public void likePost(Long postId,Long userId) {
        User user = likeDao.findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Post post = likeDao.findPostById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        if (user.getLikedPosts().contains(post)) {
            throw new UnauthorizedAccessException("User has already liked this post");
        }

        user.getLikedPosts().add(post);
        likeDao.saveUser(user);
    }
}
