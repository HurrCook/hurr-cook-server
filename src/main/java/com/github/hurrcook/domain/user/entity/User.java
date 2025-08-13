package com.github.hurrcook.domain.user.entity;

import com.github.hurrcook.domain.cookware.entity.Cookware;
import com.github.hurrcook.global.infra.BaseSchema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseSchema {

    @Column(nullable = false)
    String name;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Cookware cookware;
}
