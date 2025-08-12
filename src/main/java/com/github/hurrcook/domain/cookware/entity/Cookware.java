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
public class Cookware extends BaseSchema {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private boolean hasPot;

    @Column(nullable = false)
    private boolean hasPan;

    @Column(nullable = false)
    private boolean hasCooker;

    @Column(nullable = false)
    private boolean hasSteamer;

    @Column(nullable = false)
    private boolean hasOven;

    @Column(nullable = false)
    private boolean hasMicro;

    @Column(nullable = false)
    private boolean hasToaster;

    @Column(nullable = false)
    private boolean hasAirFryer;
}
