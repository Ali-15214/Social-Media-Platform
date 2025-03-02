package com.socialmediaplatform.dao.impl;

import com.socialmediaplatform.Repository.CommentRepository;
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

    @Override
    public Comment saveComment(User user, Post post, String content) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(content);

        return commentRepository.save(comment);


    }
}
