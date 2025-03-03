package com.socialmediaplatform.dao;

import com.socialmediaplatform.dto.PostDTO;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostDao {
    Post savePost(PostDTO postDTO, User user);
    Post updatePost(Post post);
//    Optional<Post> getPostById(Long postId);
     Page<Post> findAllPosts(Pageable pageable);
    Optional<Post> findPostById(Long id);

    void removePost(Long postId);

    Optional<Post> findById(Long id);

    Page<Post> searchPosts(String keyword, int page, int size, String sortBy, String sortDirection);

}
