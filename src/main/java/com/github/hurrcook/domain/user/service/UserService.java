package com.github.hurrcook.domain.user.service;

import com.github.hurrcook.domain.user.dto.response.PreferenceResponse;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void setPersonalPreference(User user, String personalPreference) {

        user.setPersonalPreference(personalPreference);
        userRepository.save(user);
    }

    public PreferenceResponse getPersonalPreference(User user) {

        return PreferenceResponse.builder()
                .personalPreference(user.getPersonalPreference())
                .build();
    }

    public void deleteUser(User user) {
        
        userRepository.deleteById(user.getId());
    }
}
