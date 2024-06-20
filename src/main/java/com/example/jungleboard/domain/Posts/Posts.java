package com.example.jungleboard.domain.Posts;

import com.example.jungleboard.domain.BaseTime;
import com.example.jungleboard.domain.Comments.Comments;
import com.example.jungleboard.domain.Users.Users;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comments> comments;

    @Builder
    public Posts(String title, String content, Users user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
