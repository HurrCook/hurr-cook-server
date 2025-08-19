package com.github.hurrcook.domain.cookware.service;

import com.github.hurrcook.domain.cookware.dto.request.CookwareRequest;
import com.github.hurrcook.domain.cookware.dto.response.CookwareResponse;
import com.github.hurrcook.domain.cookware.entity.Cookware;
import com.github.hurrcook.domain.cookware.exception.CookwareException;
import com.github.hurrcook.domain.cookware.repository.CookwareRepository;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.domain.user.exception.UserExceptions;
import com.github.hurrcook.domain.user.repository.UserRepository;
import com.github.hurrcook.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CookwareService {

    private final UserRepository userRepository;
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

    public void saveCookware(UUID userId, CookwareRequest cookwareRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserExceptions.USER_NOT_FOUND));

        Cookware cookware = user.getCookware();

        cookware.setHasPot(cookwareRequest.isHasPot());
        cookware.setHasPan(cookwareRequest.isHasPan());
        cookware.setHasCooker(cookwareRequest.isHasCooker());
        cookware.setHasSteamer(cookwareRequest.isHasSteamer());
        cookware.setHasOven(cookwareRequest.isHasOven());
        cookware.setHasMicro(cookwareRequest.isHasMicro());
        cookware.setHasToaster(cookwareRequest.isHasToaster());
        cookware.setHasAirFryer(cookwareRequest.isHasAirFryer());
    }
}
