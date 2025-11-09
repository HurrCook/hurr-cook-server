package com.github.hurrcook.domain.user.repository;

import com.github.hurrcook.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByKakaoId(Long kakaoId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.ingredients WHERE u.id = :userId")
    User findByIdWithIngredients(@NotNull UUID id);
}
