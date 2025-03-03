package com.socialmediaplatform.dao.impl;

import com.socialmediaplatform.Repository.CommentRepository;
import com.socialmediaplatform.Repository.PostRepository;
import com.socialmediaplatform.Repository.UserRepository;
import com.socialmediaplatform.dao.CommentDao;
import com.socialmediaplatform.dto.CommentDTO;
import com.socialmediaplatform.dto.CommentRequestDTO;
import com.socialmediaplatform.entities.Comment;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentDaoImpl implements CommentDao {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Post> findById(Long id) {
       return postRepository.findById(id);
    }

    @Override
    public Optional<User> findByUserId(Long id) {
        return userRepository.findById(id);
    }
}
