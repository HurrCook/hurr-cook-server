package com.github.hurrcook.domain.auth.entity;


import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Builder
@RedisHash(value = "refresh_token")
public record RefreshToken(
        @Id
        Long id,

        @Indexed
        String userId,

        @Indexed
        String refreshToken,

        @TimeToLive(unit = TimeUnit.HOURS)
        long ttl
){}
