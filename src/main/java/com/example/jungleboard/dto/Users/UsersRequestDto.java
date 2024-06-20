package com.example.jungleboard.dto.Users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersRequestDto {
    private String username;
    private String password;

    @Builder
    public UsersRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
