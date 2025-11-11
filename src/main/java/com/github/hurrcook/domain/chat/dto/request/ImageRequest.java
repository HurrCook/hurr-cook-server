package com.github.hurrcook.domain.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ImageRequest {
    @Schema(description = "ocr 인식 이미지 인코딩 문자열")
    private String[] base64_image;
}
