package net.maple3142.craft2d.game.crafting;

import net.maple3142.craft2d.game.item.Item;
import net.maple3142.craft2d.game.utils.ReflectionHelper;

import java.util.ArrayList;
import java.util.List;

public class RecipeRegistry {

    private static final RecipeRegistry instance = new RecipeRegistry();

    static {
        try {
            var classes = ReflectionHelper.getClasses("net.maple3142.craft2d.game.item");
            for (var clz : classes) {
                try {
                    var field = clz.getField("recipe");
                    var recipe = (Recipe<? extends Item>) field.get(null);
                    if (recipe.getResultClass() != clz) {
                        System.out.printf("Invalid attached recipe: %s's recipe result class is not %s. ", clz.getName(), clz.getName());
                    }
                    instance.addRecipe(recipe);
                } catch (NoSuchFieldException ignored) {
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private final List<Recipe<? extends Item>> recipes = new ArrayList<>();

    private RecipeRegistry() {
    }

    public static RecipeRegistry getInstance() {
        return instance;
    }

    public <T extends Item> void addRecipe(Recipe<T> recipe) {
        recipes.add(recipe);
    }

    public Recipe<? extends Item> findMatchedRecipe(CraftingInput input) {
        for (var recipe : recipes) {
            if (recipe.matchInput(input)) {
                return recipe;
            }
        }
        return null;
    }
}
