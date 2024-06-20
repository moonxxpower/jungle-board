package com.example.jungleboard.controller;

import com.example.jungleboard.dto.Posts.PostsCreateRequestDto;
import com.example.jungleboard.dto.Posts.PostsResponseDto;
import com.example.jungleboard.dto.Posts.PostsUpdateRequestDto;
import com.example.jungleboard.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/posts")
@RequiredArgsConstructor
@RestController
public class PostsController {
    private final PostsService postsService;

    // 게시물 작성
    @PostMapping
    public PostsResponseDto createPost(@RequestBody PostsCreateRequestDto postsCreateRequestDto){
        return postsService.createPost(postsCreateRequestDto);
    }

    // 게시물 개별 조회
    @GetMapping("/{postId}")
    public PostsResponseDto getPost(@PathVariable Long postId) {
        return postsService.getPost(postId);
    }

    // 게시물 목록 조회
    @GetMapping
    public List<PostsResponseDto> getAllPosts() {
        return postsService.getAllPosts();
    }

    // 게시물 수정
    @PatchMapping("/{postId}")
    public PostsResponseDto updatePost(@PathVariable Long postId, @RequestBody PostsUpdateRequestDto postsUpdateRequestDto) {
        return postsService.updatePost(postId, postsUpdateRequestDto);
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId) {
        return postsService.deletePost(postId);
    }
}
