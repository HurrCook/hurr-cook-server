package com.github.hurrcook.domain.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PromptRequest {

    @Schema(name = "프롬프트")
    private String message;

}
