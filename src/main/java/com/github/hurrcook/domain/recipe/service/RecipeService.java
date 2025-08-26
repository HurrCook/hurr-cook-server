package com.github.hurrcook.domain.recipe.service;

import com.github.hurrcook.domain.recipe.dto.request.SaveRecipeRequest;
import com.github.hurrcook.domain.recipe.entity.Recipe;
import com.github.hurrcook.domain.recipe.repository.RecipeRepository;
import com.github.hurrcook.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public void saveRecipe(SaveRecipeRequest request, User user){
        Recipe newRecipe = Recipe.builder()
                .title(request.getTitle())
                .time(request.getTime())
                .steps(request.getSteps())
                .user(user)
                .build();

        newRecipe.addIngredients(request.getIngredients());

        recipeRepository.save(newRecipe);
    }


}
