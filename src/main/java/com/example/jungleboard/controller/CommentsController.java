package com.example.jungleboard.controller;

import com.example.jungleboard.dto.Comments.CommentsRequestDto;
import com.example.jungleboard.dto.Comments.CommentsResponseDto;
import com.example.jungleboard.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/posts/{postId}")
@RequiredArgsConstructor
@RestController
public class CommentsController {
    private final CommentsService commentsService;

    // 댓글 작성
    @PostMapping
    public CommentsResponseDto createComment(@PathVariable Long postId, @RequestBody CommentsRequestDto commentsRequestDto) {
        return commentsService.createComment(postId, commentsRequestDto);
    }

    // 댓글 수정
    @PatchMapping("/comments/{commentId}")
    public CommentsResponseDto updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentsRequestDto commentsRequestDto) {
        return commentsService.updateComment(commentId, commentsRequestDto);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        return commentsService.deleteComment(commentId);
    }
}
