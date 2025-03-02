package com.socialmediaplatform.Repository;

import com.socialmediaplatform.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    @Query("SELECT p FROM Post p WHERE p.content LIKE %:keyword%")
    Page<Post> searchByContent(@Param("keyword") String keyword, Pageable pageable);
}
