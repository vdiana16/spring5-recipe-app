package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository,
                           UnitOfMeasureRepository unitOfMeasureRepository,
                           RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // This method is called when the application context is refreshed
        // We can use it to initialize our data
        recipeRepository.saveAll(getRecipes());
        log.debug("Loading Bootstrap Data");
    }

    private List<Recipe> getRecipes(){
        List<Recipe> recipes = new ArrayList(2);

        //get the UOMs
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");

        if (!eachUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> tablespoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
        if (!tablespoonUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> teaspoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        if (!teaspoonUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");
        if (!dashUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");
        if (!pintUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> cupUomOptional = unitOfMeasureRepository.findByDescription("Cup");
        if (!cupUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        //get optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tablespoonUom = tablespoonUomOptional.get();
        UnitOfMeasure teaspoonUom = teaspoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupUom = cupUomOptional.get();

        //get the categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        if (!americanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
        if (!mexicanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category Not Found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        //Yummy Guacamole
        Recipe guacamoleRecipe = new Recipe();
        guacamoleRecipe.setDescription("Perfect Guacamole");
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setCookTime(0);
        guacamoleRecipe.setDifficulty(Difficulty.EASY);
        guacamoleRecipe.setDirections(
                "1. Cut avocado, remove flesh: Cut avocados in half. Remove seed. Scoop out flesh with a spoon and place in a bowl.\n" +
                "2. Mash with a fork: Using a fork, mash the avocado until it is as smooth or as chunky as you like.\n" +
                "3. Add lime juice, salt, and other ingredients: Add lime juice and salt to the mashed avocado. Stir in diced onion, cilantro, tomatoes, and jalapeño if using.\n" +
                "4. Taste and adjust seasoning: Taste the guacamole and add more salt or lime juice if needed.\n" +
                "5. Serve immediately: Serve with tortilla chips or use as a topping for tacos or burritos." +
                        "Read more at: https://www.simplyrecipes.com/recipes/perfect_guacamole/\n"
        );

        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("For the best flavor, use ripe avocados. You can also add garlic or cumin for extra flavor. \n" +
                "Store leftover guacamole in an airtight container with plastic wrap pressed directly onto the surface to prevent browning. \n" +
                "The simplest version of guacamole is just mashed avocado with lime juice and salt, but you can customize it with your favorite ingredients.");

        guacamoleRecipe.setNotes(guacamoleNotes);

        guacamoleRecipe.addIngredient(new Ingredient("Ripe avocados", new BigDecimal(2), eachUom));
        guacamoleRecipe.addIngredient(new Ingredient("Lime juice", new BigDecimal(1), tablespoonUom));
        guacamoleRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(0.5), teaspoonUom));
        guacamoleRecipe.addIngredient(new Ingredient("Diced onion", new BigDecimal(0.25), cupUom));
        guacamoleRecipe.addIngredient(new Ingredient("Chopped cilantro", new BigDecimal(2), tablespoonUom));
        guacamoleRecipe.addIngredient(new Ingredient("Diced tomatoes", new BigDecimal(0.5), cupUom));
        guacamoleRecipe.addIngredient(new Ingredient("Minced jalapeño (optional)", new BigDecimal(1), eachUom));

        guacamoleRecipe.getCategories().add(americanCategory);
        guacamoleRecipe.getCategories().add(mexicanCategory);

        guacamoleRecipe.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamoleRecipe.setServings(4);
        guacamoleRecipe.setSource("Simply Recipes");

        recipes.add(guacamoleRecipe);

        //Yummy Tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Tacos");
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setCookTime(15);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);
        tacosRecipe.setDirections(
                "1. Prepare the marinade: In a bowl, mix together lime juice, chili powder, cumin, garlic powder, onion powder, salt, and pepper.\n" +
                "2. Marinate the chicken: Place chicken breasts in a resealable bag or shallow dish and pour the marinade over them. Let it marinate for at least 30 minutes.\n" +
                "3. Grill the chicken: Preheat the grill to medium-high heat. Remove chicken from marinade and grill for about 6-7 minutes per side or until cooked through.\n" +
                "4. Warm the tortillas: While the chicken is grilling, warm the corn tortillas on the grill for about 30 seconds on each side.\n" +
                "5. Assemble the tacos: Slice the grilled chicken and place it on the warmed tortillas. Top with diced onions, chopped cilantro, and lime wedges." +
                        "Read more at: https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/\n"
        );

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("For the best flavor, use fresh lime juice and high-quality chili powder. \n" +
                "You can also add toppings like avocado, salsa, or cheese to customize your tacos. \n" +
                "If you prefer a milder flavor, reduce the amount of chili powder or omit the jalapeño.");

        tacosRecipe.setNotes(tacoNotes);

        tacosRecipe.addIngredient(new Ingredient("Boneless, skinless chicken breasts", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredient(new Ingredient("Lime juice", new BigDecimal(2), tablespoonUom));
        tacosRecipe.addIngredient(new Ingredient("Chili powder", new BigDecimal(1), tablespoonUom));
        tacosRecipe.addIngredient(new Ingredient("Ground cumin", new BigDecimal(1), teaspoonUom));
        tacosRecipe.addIngredient(new Ingredient("Garlic powder", new BigDecimal(0.5), teaspoonUom));
        tacosRecipe.addIngredient(new Ingredient("Onion powder", new BigDecimal(0.5), teaspoonUom));
        tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(0.5), teaspoonUom));
        tacosRecipe.addIngredient(new Ingredient("Black pepper", new BigDecimal(0.25), teaspoonUom));
        tacosRecipe.addIngredient(new Ingredient("Corn tortillas", new BigDecimal(8), eachUom));
        tacosRecipe.addIngredient(new Ingredient("Diced onions", new BigDecimal(0.5), cupUom));
        tacosRecipe.addIngredient(new Ingredient("Chopped cilantro", new BigDecimal(0.5), cupUom));
        tacosRecipe.addIngredient(new Ingredient("Lime wedges", new BigDecimal(2), eachUom));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        tacosRecipe.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacosRecipe.setServings(4);
        tacosRecipe.setSource("Simply Recipes");

        recipes.add(tacosRecipe);

        return recipes;
    }
}
