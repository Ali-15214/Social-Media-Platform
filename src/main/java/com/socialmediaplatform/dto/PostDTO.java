package com.socialmediaplatform.dto;

public class PostDTO {
    private long id;


    private String content;

    public PostDTO() {
    }

    public PostDTO(String content, long id) {
        this.content = content;
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
