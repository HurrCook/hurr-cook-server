package com.github.hurrcook.domain.auth.dto.response;

import com.github.hurrcook.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CheckLoginFirst {
    private User user;
    private boolean isFirstLogin;

    public static CheckLoginFirst of(User user, boolean isFirstLogin) {
        return CheckLoginFirst.builder()
                .user(user)
                .isFirstLogin(isFirstLogin)
                .build();

    }
}
