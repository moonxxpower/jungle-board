package com.example.jungleboard.domain.Comments;

import com.example.jungleboard.domain.BaseTime;
import com.example.jungleboard.domain.Posts.Posts;
import com.example.jungleboard.domain.Users.Users;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Comments extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Posts post;

    @Builder
    public Comments(String content, Posts post, Users user) {
        this.content = content;
        this.user = user;
        this.post = post;

    }

    public void update(String content) {
        this.content = content;
    }
}
