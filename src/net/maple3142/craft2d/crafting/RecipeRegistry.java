package net.maple3142.craft2d.crafting;

import net.maple3142.craft2d.ReflectionHelper;
import net.maple3142.craft2d.item.Item;

import java.util.ArrayList;
import java.util.List;

public class RecipeRegistry {

    private static RecipeRegistry instance;
    private List<Recipe<? extends Item>> recipes = new ArrayList<>();

    private RecipeRegistry() {
    }

    public static RecipeRegistry getInstance() {
        if (instance == null) {
            instance = new RecipeRegistry();
            try {
                var classes = ReflectionHelper.getClasses("net.maple3142.craft2d.item");
                for (var clz : classes) {
                    try {
                        var field = clz.getField("recipe");
                        instance.addRecipe((Recipe<? extends Item>) field.get(null));
                    } catch (NoSuchFieldException ignored) {
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        }
        return instance;
    }

    public <T extends Item> void addRecipe(Recipe<T> recipe) {
        recipes.add(recipe);
    }

    public Class<? extends Item> findCraftedItem(CraftingInput input) {
        for (var recipe : recipes) {
            if (recipe.matchInput(input)) {
                return recipe.getResultClass();
            }
        }
        return null;
    }
}
