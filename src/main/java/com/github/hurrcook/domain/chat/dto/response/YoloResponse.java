package com.github.hurrcook.domain.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class YoloResponse {

    private List<IngredientImage> ingredients;

    @Data
    public static class IngredientImage {
        private String name;
        private int amount;
        
        @JsonProperty("crop_image")
        private String[] cropImage;
    }
}
