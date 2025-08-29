package com.github.hurrcook.domain.recipe.service;

import com.github.hurrcook.domain.recipe._recipe_food.repository.RecipeFoodRepository;
import com.github.hurrcook.domain.recipe.dto.request.ModifyRecipeRequest;
import com.github.hurrcook.domain.recipe.dto.request.SaveRecipeRequest;
import com.github.hurrcook.domain.recipe.dto.response.RecipeListResponse;
import com.github.hurrcook.domain.recipe.dto.response.RecipeResponse;
import com.github.hurrcook.domain.recipe.entity.Recipe;
import com.github.hurrcook.domain.recipe.repository.RecipeRepository;
import com.github.hurrcook.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeFoodRepository recipeFoodRepository;

    @Transactional
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
    @Transactional
    @PreAuthorize("#recipe.user.id == authentication.principal.id")
    public void updateRecipe(ModifyRecipeRequest request,Recipe recipe){


        recipe.setTitle(request.getTitle());
        recipe.getSteps().clear();
        recipe.setSteps(request.getSteps());


        request.getIngredients().forEach(ing -> recipeFoodRepository.findById(ing.getId()).ifPresent(recipeFood -> recipeFood.setAmount(ing.getAmount())));

    }

    @Transactional
    @PreAuthorize("#recipe.user.id == authentication.principal.id")
    public void deleteRecipe(Recipe recipe){


        recipeRepository.delete(recipe);
    }

    @Transactional
    public RecipeListResponse getRecipeList(User user){
        return RecipeListResponse.from(recipeRepository.findAllByUser(user));
    }

    @Transactional
    @PreAuthorize("#recipe.user.id == authentication.principal.id")
    public RecipeResponse getRecipe(Recipe recipe){

        return  RecipeResponse.from(recipe);
    }


}
