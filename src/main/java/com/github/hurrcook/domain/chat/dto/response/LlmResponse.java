package com.github.hurrcook.domain.chat.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class LlmResponse {

    @Schema(name = "레시피 명")
    private String title;

    @Schema(name = "카테고리")
    private String category;

    @Schema(name = "레시피 종류")
    private String cuisine_type;

    @Schema(name = "필요 재료")
    private List<SimpleIngredientResponse> ingredients;

    @Schema(name = "요리도구")
    private List<String> tools;

    @Schema(name = "레시피 단계")
    private List<String> steps;

    @Schema(name = "소요 시간")
    private String time;

    @Schema(name = "칼로리")
    private String calorie;

}
