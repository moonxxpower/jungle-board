package com.example.jungleboard.service;

import com.example.jungleboard.domain.Comments.Comments;
import com.example.jungleboard.domain.Comments.CommentsRepository;
import com.example.jungleboard.domain.Posts.Posts;
import com.example.jungleboard.domain.Posts.PostsRepository;
import com.example.jungleboard.domain.Users.Users;
import com.example.jungleboard.domain.Users.UsersRepository;
import com.example.jungleboard.dto.Comments.CommentsRequestDto;
import com.example.jungleboard.dto.Comments.CommentsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public CommentsResponseDto createComment(Long postId, CommentsRequestDto commentsRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = getUsernameFromAuthentication(authentication);

        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 아이디가 없습니다. username=" + username));

        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));

        Comments comment = commentsRepository.save(commentsRequestDto.toEntity(post, user));
        return new CommentsResponseDto(comment);
    }

    @Transactional
    public CommentsResponseDto updateComment(Long commentId, CommentsRequestDto commentsRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = getUsernameFromAuthentication(authentication);

        String content = commentsRequestDto.getContent();

        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 아이디가 없습니다. username=" + username));

        Comments comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id="+ commentId));

        if (!comment.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("해당 댓글을 수정할 권한이 없습니다.");
        }

        comment.update(content);
        return new CommentsResponseDto(comment);
    }

    @Transactional
    public String deleteComment(Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = getUsernameFromAuthentication(authentication);

        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 아이디가 없습니다. username=" + username));

        Comments comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id="+ commentId));

        if (!comment.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("해당 댓글을 삭제할 권한이 없습니다.");
        }

        commentsRepository.deleteById(commentId);
        return commentId + "가 삭제되었습니다.";
    }

    private String getUsernameFromAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("인증되지 않은 사용자입니다.");
        }

        return (String) authentication.getPrincipal();
    }
}
