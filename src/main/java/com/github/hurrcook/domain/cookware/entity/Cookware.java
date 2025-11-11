package com.github.hurrcook.domain.cookware.entity;

import com.github.hurrcook.domain.cookware.dto.request.CookwareRequest;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.global.infra.BaseSchema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(nullable = false)
    @Builder.Default
    private boolean hasPot = false;

    @Column(nullable = false)
    @Builder.Default
    private boolean hasPan = false;

    @Column(nullable = false)
    @Builder.Default
    private boolean hasCooker = false;

    @Column(nullable = false)
    @Builder.Default
    private boolean hasSteamer = false;

    @Column(nullable = false)
    @Builder.Default
    private boolean hasOven = false;

    @Column(nullable = false)
    @Builder.Default
    private boolean hasMicro = false;

    @Column(nullable = false)
    @Builder.Default
    private boolean hasToaster = false;

    @Column(nullable = false)
    @Builder.Default
    private boolean hasAirFryer = false;

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

    public List<String> getAvailableToolNames() {
        List<String> tools = new ArrayList<>();

        if (hasPot) {
            tools.add("냄비");
        }
        if (hasPan) {
            tools.add("프라이팬");
        }
        if (hasCooker) {
            tools.add("밥솥");
        }
        if (hasSteamer) {
            tools.add("찜기");
        }
        if (hasOven) {
            tools.add("오븐");
        }
        if (hasMicro) {
            tools.add("전자레인지");
        }
        if (hasToaster) {
            tools.add("토스터");
        }
        if (hasAirFryer) {
            tools.add("에어프라이어");
        }

        return tools;
    }
}
