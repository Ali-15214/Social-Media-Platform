package com.socialmediaplatform.dao.impl;

import com.socialmediaplatform.Repository.PostRepository;
import com.socialmediaplatform.dao.PostDao;
import com.socialmediaplatform.dto.PostDTO;
import com.socialmediaplatform.entities.Post;
import com.socialmediaplatform.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostDaoImpl implements PostDao {
    @Autowired
    private PostRepository postRepository;

    public Post savePost(PostDTO postDTO, User user) {
        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setTimestamp(LocalDateTime.now());
        post.setUser(user);
        return postRepository.save(post);
    }


    @Override
    public Page<Post> findAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Optional<Post> findPostById(Long id) {
        return postRepository.findById(id);
    }

//    @Override
//    public Optional<Post> getPostById(Long postId) {
//        return Optional.empty();
//    }
//



}
