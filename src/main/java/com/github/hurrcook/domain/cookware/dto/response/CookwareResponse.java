package com.github.hurrcook.domain.cookware.dto.response;

import com.github.hurrcook.domain.cookware.entity.Cookware;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CookwareResponse {

    private boolean hasPot;

    private boolean hasPan;

    private boolean hasCooker;

    private boolean hasSteamer;

    private boolean hasOven;

    private boolean hasMicro;

    private boolean hasToaster;

    private boolean hasAirFryer;

    public static CookwareResponse from(Cookware cookware) {
        return CookwareResponse.builder()
                .hasPot(cookware.isHasPot())
                .hasPan(cookware.isHasPan())
                .hasCooker(cookware.isHasCooker())
                .hasSteamer(cookware.isHasSteamer())
                .hasOven(cookware.isHasOven())
                .hasMicro(cookware.isHasMicro())
                .hasToaster(cookware.isHasToaster())
                .hasAirFryer(cookware.isHasAirFryer())
                .build();
    }
}
