package com.github.hurrcook.domain.chat.dto.response;

import com.github.hurrcook.global.common.Unit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SimpleIngredientResponse {

    @Schema(name = "재료 명")
    private String name;

    @Schema(name = "양")
    private int amount;

    @Schema(name = "단위")
    private Unit unit;

}
