package com.socialmediaplatform.Repository;

import com.socialmediaplatform.entities.Comment;
import com.socialmediaplatform.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
