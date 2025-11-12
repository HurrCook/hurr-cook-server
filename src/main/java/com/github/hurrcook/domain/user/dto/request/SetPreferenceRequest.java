package com.github.hurrcook.domain.user.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SetPreferenceRequest {

    @Schema(description = "개인 맞춤 설정 프롬프트")
    private String personalPreference;
}
