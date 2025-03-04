package com.socialmediaplatform.dao.impl;

import com.socialmediaplatform.Repository.PostRepository;
import com.socialmediaplatform.Repository.UserRepository;
import com.socialmediaplatform.dao.FollowDAO;

import com.socialmediaplatform.entities.Follow;
import com.socialmediaplatform.Repository.FollowRepository;

import com.socialmediaplatform.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FollowDAOImpl implements FollowDAO {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Follow saveFollow(Follow follow) {
        return followRepository.save(follow);
    }


    @Override
    public List<User> getFollowers(User user) {
        return followRepository.findByFollowing(user).stream()
                .map(Follow::getFollower)
                .collect(Collectors.toList());
    }


    @Override
    public List<User> findFollowingUsers(Long userId) {
        return followRepository.findFollowingUsers(userId);
    }

    @Override
    public boolean isAlreadyFollowing(User follower, User following) {
        return followRepository.existsByFollowerAndFollowing(follower, following);
    }




    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

}