package com.github.hurrcook.global.infra;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseSchema {

    @Id
    @NotNull
    @UuidGenerator
    @Column(nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    UUID id;

    @Column(nullable = false, updatable = false)
    @NotNull
    @CreatedDate
    LocalDateTime createdAt;

    @Column(nullable = false)
    @NotNull
    @LastModifiedDate
    LocalDateTime updatedAt;
}
