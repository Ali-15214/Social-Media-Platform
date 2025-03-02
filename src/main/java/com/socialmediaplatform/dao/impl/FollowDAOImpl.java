package com.socialmediaplatform.dao.impl;

import com.socialmediaplatform.dao.FollowDAO;
import com.socialmediaplatform.dto.FollowDTO;
import com.socialmediaplatform.entities.Follow;
import com.socialmediaplatform.Repository.FollowRepository;
import com.socialmediaplatform.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FollowDAOImpl implements FollowDAO {

    @Autowired
    private FollowRepository followRepository;

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

}