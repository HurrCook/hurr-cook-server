package com.github.hurrcook.domain.cookware.dto.request;

import lombok.Data;

@Data
public class CookwareRequest {

    private boolean hasPot;

    private boolean hasPan;

    private boolean hasCooker;

    private boolean hasSteamer;

    private boolean hasOven;

    private boolean hasMicro;

    private boolean hasToaster;

    private boolean hasAirFryer;
}
