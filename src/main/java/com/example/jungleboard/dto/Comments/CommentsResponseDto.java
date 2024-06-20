package com.example.jungleboard.dto.Comments;

import com.example.jungleboard.domain.Comments.Comments;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentsResponseDto {
    private Long commentId;
    private String username;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public CommentsResponseDto(Comments comment) {
        this.commentId = comment.getCommentId();
        this.username = comment.getUser().getUsername();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}
