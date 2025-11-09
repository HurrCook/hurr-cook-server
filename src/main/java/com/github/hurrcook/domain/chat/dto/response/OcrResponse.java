package com.github.hurrcook.domain.chat.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class OcrResponse {
    private List<Ingredient> ingredients;

    @Data
    public static class Ingredient {
        private String name;
        private int amount;
    }
}
