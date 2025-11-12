package com.github.hurrcook.domain.ingredient.entity;

import com.github.hurrcook.domain.ingredient.dto.request.IngredientRequest;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.global.common.Unit;
import com.github.hurrcook.global.infra.BaseSchema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Ingredient extends BaseSchema {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    int amount;

    @Column(columnDefinition = "TEXT")
    String imageUrl;

    @Column(nullable = false)
    LocalDateTime expireDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Unit unit;

    public static Ingredient from(User user, IngredientRequest request) {
        return Ingredient.builder()
                .user(user)
                .name(request.getName())
                .imageUrl(request.getImageUrl())
                .amount(request.getAmount())
                .expireDate(request.getExpireDate())
                .unit(request.getUnit())
                .build();
    }
}