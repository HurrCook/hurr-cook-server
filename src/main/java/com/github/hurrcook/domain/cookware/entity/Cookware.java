package com.github.hurrcook.domain.cookware.entity;

import com.github.hurrcook.domain.cookware.dto.request.CookwareRequest;
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
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    @Builder.Default private boolean hasPot = false;

    @Column(nullable = false)
    @Builder.Default private boolean hasPan = false;

    @Column(nullable = false)
    @Builder.Default private boolean hasCooker = false;

    @Column(nullable = false)
    @Builder.Default private boolean hasSteamer = false;

    @Column(nullable = false)
    @Builder.Default private boolean hasOven = false;

    @Column(nullable = false)
    @Builder.Default private boolean hasMicro = false;

    @Column(nullable = false)
    @Builder.Default private boolean hasToaster = false;

    @Column(nullable = false)
    @Builder.Default private boolean hasAirFryer = false;

    public void updateCookwareFromRequest(CookwareRequest request) {
        this.hasPot = request.isHasPot();
        this.hasPan = request.isHasPan();
        this.hasCooker = request.isHasCooker();
        this.hasSteamer = request.isHasSteamer();
        this.hasOven = request.isHasOven();
        this.hasMicro = request.isHasMicro();
        this.hasToaster = request.isHasToaster();
        this.hasAirFryer = request.isHasAirFryer();
    }
}
