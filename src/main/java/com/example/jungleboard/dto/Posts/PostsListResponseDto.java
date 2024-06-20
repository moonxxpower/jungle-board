package com.example.jungleboard.dto.Posts;

import com.example.jungleboard.domain.Posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long postId;
    private String title;
    private String username;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
    }
}
