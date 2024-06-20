package com.example.jungleboard.dto.Comments;

import com.example.jungleboard.domain.Comments.Comments;
import com.example.jungleboard.domain.Posts.Posts;
import com.example.jungleboard.domain.Users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentsRequestDto {
    private String content;

    @Builder
    public CommentsRequestDto(String content) {
        this.content = content;
    }

    public Comments toEntity(Posts post, Users user) {
        return Comments.builder()
                .content(content)
                .post(post)
                .user(user)
                .build();
    }
}
