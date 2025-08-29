package com.github.hurrcook.domain.ingredient.entity;

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
@EqualsAndHashCode(callSuper = true)
public class Ingredient extends BaseSchema {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    int amount;

    @Column(nullable = false)
    LocalDateTime expire_time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Unit unit;
}
