package com.socialmediaplatform.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    private String content;

    private LocalDateTime timestamp = LocalDateTime.now();

    // One post can have multiple comments
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // ManyToMany: Posts can be liked by multiple users (Bidirectional)
    @ManyToMany(mappedBy = "likedPosts")
    private List<User> likedByUsers = new ArrayList<>();

    public Post() {
    }

    public Post(Long id, User user, String content, LocalDateTime timestamp, List<Comment> comments, List<User> likedByUsers) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.timestamp = timestamp;
        this.comments = comments;
        this.likedByUsers = likedByUsers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<User> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(List<User> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }
}
