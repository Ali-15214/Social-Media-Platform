package com.socialmediaplatform.service;

import com.socialmediaplatform.Response.CommentResponseDTO;
import com.socialmediaplatform.dto.CommentDTO;
import com.socialmediaplatform.dto.CommentRequestDTO;
import com.socialmediaplatform.entities.Comment;

public interface CommentService {
        Comment addComment(CommentDTO commentDTO);
    }
