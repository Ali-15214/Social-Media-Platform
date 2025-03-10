package com.socialmediaplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentRequestDTO {

    @NotBlank(message = "Content is required")
    @Size(max = 500, message = "Comment content must be less than 500 characters")
    private String content;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentRequestDTO{" +
                "content='" + content + '\'' +
                '}';
    }
}