package com.example.jungleboard.service;

import com.example.jungleboard.domain.Users.Users;
import com.example.jungleboard.domain.Users.UsersRepository;
import com.example.jungleboard.dto.Users.UsersRequestDto;
import com.example.jungleboard.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;
    private Long expiredTime = 1000 * 60 * 60l;    // 1시간

    public String join(UsersRequestDto usersRequestDto) {
        String username = usersRequestDto.getUsername();
        String password = usersRequestDto.getPassword();

        usersRepository.findByUsername(usersRequestDto.getUsername())
                .ifPresent(user -> {
                    throw new RuntimeException("이미 존재하는 회원 아이디입니다.");
                });

        Users user = Users.builder()
                        .username(username)
                        .password(encoder.encode(password))
                        .build();

        usersRepository.save(user);

        return username + "님의 회원 가입이 성공했습니다.";
    }

    public String login(UsersRequestDto usersRequestDto) {
        String username = usersRequestDto.getUsername();
        String password = usersRequestDto.getPassword();

        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 아이디가 없습니다. username=" + username));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("일치하는 아이디가 아닙니다.");
        }

        String token = JwtTokenUtil.createToken(username, secretKey, expiredTime);

        return token;
    }
}

