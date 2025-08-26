package com.github.hurrcook.domain.recipe.entity;

import com.github.hurrcook.domain.recipe._recipe_food.entity.RecipeFood;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.global.infra.BaseSchema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Recipe extends BaseSchema {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    Integer time;

    @Column
    String image;

    @ElementCollection
    @Builder.Default
    List<String> contents = new ArrayList<>();

    @OneToMany(mappedBy = "recipe",  cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @Builder.Default
    List<RecipeFood> ingredients = new ArrayList<>();


}
