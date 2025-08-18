package com.github.hurrcook.domain.user.entity;

import com.github.hurrcook.domain.cookware.entity.Cookware;
import com.github.hurrcook.global.infra.BaseSchema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseSchema {

    @Column(unique = true, nullable = false)
    private Long kakaoId;

    @Column(nullable = false)
    String nickname; // 카카오 프로필 이름

//    @Column(nullable = false)
//    String name;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Cookware cookware;
}
