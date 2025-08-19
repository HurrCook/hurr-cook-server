package com.github.hurrcook.domain.cookware.service;

import com.github.hurrcook.domain.cookware.dto.response.CookwareResponse;
import com.github.hurrcook.domain.cookware.entity.Cookware;
import com.github.hurrcook.domain.cookware.exception.CookwareException;
import com.github.hurrcook.domain.cookware.repository.CookwareRepository;
import com.github.hurrcook.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CookwareService {

    private final CookwareRepository cookwareRepository;

    public CookwareResponse getCookwareByUserId(UUID userId) {

        Cookware cookware = cookwareRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException(CookwareException.COOKWARE_NOT_FOUND));

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
