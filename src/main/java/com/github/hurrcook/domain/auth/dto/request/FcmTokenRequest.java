package com.github.hurrcook.domain.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FcmTokenRequest {

    @NotNull
    String fcmToken;
}
