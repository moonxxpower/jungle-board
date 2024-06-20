package com.example.jungleboard.dto.Posts;

import com.example.jungleboard.domain.Posts.Posts;
import com.example.jungleboard.dto.Comments.CommentsResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostsResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<CommentsResponseDto> comments;

    public PostsResponseDto(Posts post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUser().getUsername();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();

        if (post.getComments() != null) {
            this.comments = post.getComments().stream()
                    .map(comment -> new CommentsResponseDto(comment))
                    .collect(Collectors.toList());
        } else {
            this.comments = null;
        }
    }
}
