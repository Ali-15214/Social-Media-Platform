package com.socialmediaplatform.dao;

import com.socialmediaplatform.dto.FollowDTO;
import com.socialmediaplatform.entities.Follow;
import com.socialmediaplatform.entities.User;

import java.util.List;

public interface FollowDAO {
    Follow saveFollow(Follow follow);

    List<User> getFollowers(User user);

    List<User> findFollowingUsers(Long userId);

    boolean isAlreadyFollowing(User follower, User following);
}