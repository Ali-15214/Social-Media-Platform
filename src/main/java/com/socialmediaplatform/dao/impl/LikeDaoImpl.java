package com.socialmediaplatform.dao.impl;
import com.socialmediaplatform.dao.LikeDao;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.socialmediaplatform.Repository.UserRepository;
import com.socialmediaplatform.Repository.PostRepository;
import java.util.Optional;

@Service
public class LikeDaoImpl implements LikeDao {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<Post> findPostById(Long postId) {
        return postRepository.findById(postId);
    }
}
