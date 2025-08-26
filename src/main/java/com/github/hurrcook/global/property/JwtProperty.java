package com.github.hurrcook.global.property;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperty {

    @NotBlank
    private String secretKey;

    @NotBlank
    Long accessExpiration;

    @NotBlank
    Long refreshExpiration;
}
