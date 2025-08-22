package com.github.hurrcook.domain.cookware.service;

import com.github.hurrcook.domain.cookware.dto.request.CookwareRequest;
import com.github.hurrcook.domain.cookware.dto.response.CookwareResponse;
import com.github.hurrcook.domain.cookware.entity.Cookware;
import com.github.hurrcook.domain.cookware.repository.CookwareRepository;
import com.github.hurrcook.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CookwareService {

    private final CookwareRepository cookwareRepository;

    public CookwareResponse getCookwareByUser(User user) {

        Cookware cookware = cookwareRepository.findByUser(user)
                .orElseGet(() -> {
                    Cookware newCookware = Cookware.builder()
                            .user(user)
                            .build();
                    return cookwareRepository.save(newCookware);
                });

        return CookwareResponse.from(cookware);
    }

    public void saveCookware(User user, CookwareRequest cookwareRequest) {

        Cookware cookware = user.getCookware();

        cookware.updateCookwareFromRequest(cookwareRequest);

        cookwareRepository.save(cookware);
    }
}
