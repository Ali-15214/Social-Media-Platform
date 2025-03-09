package com.socialmediaplatform.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;



    private String content;
    

    private LocalDateTime timestamp = LocalDateTime.now();

    // One post can have multiple comments
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    // ManyToMany: Posts can be liked by multiple users (Bidirectional)
    @ManyToMany(mappedBy = "likedPosts")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Set<User> likedByUsers = new HashSet<>();

    public Post() {
    }

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public Post(Long id, User user, String content, LocalDateTime timestamp, Set<Comment> comments, Set<User> likedByUsers) {
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<User> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(Set<User> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", comments=" + comments +
                ", likedByUsers=" + likedByUsers +
                ", createdAt=" + createdAt +
                '}';
    }
}
