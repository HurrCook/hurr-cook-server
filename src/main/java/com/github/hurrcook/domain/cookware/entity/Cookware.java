package com.github.hurrcook.domain.cookware.entity;

import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.global.infra.BaseSchema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cookwares")
public class Cookware extends BaseSchema {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Builder.Default
    @Column(nullable = false)
    private boolean hasPot = false;

    @Builder.Default
    @Column(nullable = false)
    private boolean hasPan = false;

    @Builder.Default
    @Column(nullable = false)
    private boolean hasCooker = false;

    @Builder.Default
    @Column(nullable = false)
    private boolean hasSteamer = false;

    @Builder.Default
    @Column(nullable = false)
    private boolean hasOven = false;

    @Builder.Default
    @Column(nullable = false)
    private boolean hasMicro = false;

    @Builder.Default
    @Column(nullable = false)
    private boolean hasToaster = false;

    @Builder.Default
    @Column(nullable = false)
    private boolean hasAirFryer = false;
}
