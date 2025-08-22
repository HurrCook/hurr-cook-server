package com.github.hurrcook.domain.cookware.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CookwareRequest {

    @NotNull
    private boolean hasPot;

    @NotNull
    private boolean hasPan;

    @NotNull
    private boolean hasCooker;

    @NotNull
    private boolean hasSteamer;

    @NotNull
    private boolean hasOven;

    @NotNull
    private boolean hasMicro;

    @NotNull
    private boolean hasToaster;

    @NotNull
    private boolean hasAirFryer;
}
