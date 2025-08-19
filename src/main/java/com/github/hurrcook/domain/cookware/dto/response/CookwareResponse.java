package com.github.hurrcook.domain.cookware.dto.response;

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
}
