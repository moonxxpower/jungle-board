package com.example.jungleboard.controller;

import com.example.jungleboard.dto.Users.UsersRequestDto;
import com.example.jungleboard.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UsersController {
    private final UsersService usersService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UsersRequestDto usersRequestDto) {
        return ResponseEntity.ok().body(usersService.join(usersRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsersRequestDto usersRequestDto) {
        String token = usersService.login(usersRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        return ResponseEntity.ok().headers(headers).body("로그인이 성공했습니다.");
    }
}
