package com.socialmediaplatform.service.impl;

import com.socialmediaplatform.Exceptions.CustomException.PostNotFoundException;
import com.socialmediaplatform.dao.CommentDao;
import com.socialmediaplatform.dto.CommentDTO;
import com.socialmediaplatform.dto.CommentRequestDTO;
import com.socialmediaplatform.Response.CommentResponseDTO;
import com.socialmediaplatform.entities.Comment;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.entities.User;
import com.socialmediaplatform.Repository.CommentRepository;
import com.socialmediaplatform.Repository.PostRepository;
import com.socialmediaplatform.Repository.UserRepository;

import com.socialmediaplatform.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    CommentDao commentDao;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Comment addComment(CommentDTO commentDTO) {
        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));


                Comment comment=commentDao.saveComment(user,post,commentDTO.getContent());
                return comment;

    }
}



