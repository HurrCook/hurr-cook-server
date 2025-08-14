package com.github.hurrcook.domain.cookware.repository;

import com.github.hurrcook.domain.cookware.entity.Cookware;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CookwareRepository extends JpaRepository<Cookware, UUID> {
}
