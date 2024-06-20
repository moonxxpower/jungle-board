package com.example.jungleboard.service;

import com.example.jungleboard.domain.Posts.Posts;
import com.example.jungleboard.domain.Posts.PostsRepository;
import com.example.jungleboard.domain.Users.Users;
import com.example.jungleboard.domain.Users.UsersRepository;
import com.example.jungleboard.dto.Posts.PostsCreateRequestDto;
import com.example.jungleboard.dto.Posts.PostsResponseDto;
import com.example.jungleboard.dto.Posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;
    
    // 게시물 작성
    @Transactional
    public PostsResponseDto createPost(PostsCreateRequestDto postsCreateRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = getUsernameFromAuthentication(authentication);

        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 아이디가 없습니다. username=" + username));

        Posts post = postsRepository.save(postsCreateRequestDto.toEntity(user));
        return new PostsResponseDto(post);
    }
    
    // 게시물 개별 조회
    public PostsResponseDto getPost(Long postId) {
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
        return new PostsResponseDto(post);
    }
    
    // 게시물 목록 조회
    @Transactional(readOnly = true)
    public List<PostsResponseDto> getAllPosts() {
        return postsRepository.findAll(Sort.by(Sort.Direction.DESC, "postId")).stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }
    
    // 게시물 수정
    @Transactional
    public PostsResponseDto updatePost(Long postId, PostsUpdateRequestDto postsUpdateRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = getUsernameFromAuthentication(authentication);

        String title = postsUpdateRequestDto.getTitle();
        String content = postsUpdateRequestDto.getContent();

        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 아이디가 없습니다. username=" + username));

        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ postId));

        if (!post.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("해당 게시글을 수정할 권한이 없습니다.");
        }

        post.update(title, content);

        return new PostsResponseDto(post);
    }
    
    // 게시물 삭제
    @Transactional
    public String deletePost(Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = getUsernameFromAuthentication(authentication);

        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 아이디가 없습니다. username=" + username));

        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ postId));

        if (!post.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("해당 게시글을 삭제할 권한이 없습니다.");
        }

        postsRepository.deleteById(postId);
        return postId + "가 삭제되었습니다.";
    }

    private String getUsernameFromAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("인증되지 않은 사용자입니다.");
        }

        return (String) authentication.getPrincipal();
    }
}
