package com.github.hurrcook.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PreferenceResponse {

    private String personalPreference;
}
