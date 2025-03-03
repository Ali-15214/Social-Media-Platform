package com.socialmediaplatform.dao;

import com.socialmediaplatform.dto.CommentDTO;
import com.socialmediaplatform.dto.CommentRequestDTO;
import com.socialmediaplatform.entities.Comment;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.entities.User;

import java.util.Optional;

public interface CommentDao {

    Comment saveComment(Comment comment);
    Optional<Post> findById(Long id);
    Optional<User> findByUserId(Long id);

}
