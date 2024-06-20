package com.example.jungleboard.dto.Posts;

import com.example.jungleboard.domain.Posts.Posts;
import com.example.jungleboard.domain.Users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsCreateRequestDto {
    private String title;
    private String content;

    @Builder
    public PostsCreateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Posts toEntity(Users user) {
        return Posts.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
