package com.socialmediaplatform.Repository;

import com.socialmediaplatform.entities.Follow;
import com.socialmediaplatform.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository  extends JpaRepository<Follow, Long> {

    List<Follow> findByFollowing(User user);

    @Query("SELECT f.following FROM Follow f WHERE f.follower.id = :userId")
    List<User> findFollowingUsers(@Param("userId") Long userId);

//    @Query("SELECT f.follower FROM Follow f WHERE f.following.id = :userId")
//    List<User> findFollowersByUserId(@Param("userId") Long userId);
//
//    @Query("SELECT f.following FROM Follow f WHERE f.follower.id = :userId")
//    List<User> findFollowingByUserId(@Param("userId") Long userId);

//    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END FROM Follow f WHERE f.follower.id = :followerId AND f.following.id = :followingId")
//    boolean existsByFollowerAndFollowing(@Param("targetUserId") Long followerId, @Param("currentUserId") Long followingId);

    boolean existsByFollowerAndFollowing(User follower, User following);
}
