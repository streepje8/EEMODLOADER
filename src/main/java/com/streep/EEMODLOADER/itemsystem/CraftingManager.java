package com.streep.EEMODLOADER.itemsystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.RecipeChoice.ExactChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import com.streep.EEMODLOADER.core.EEMODLOADER;

public class CraftingManager {

	public static void RegisterRecepie(CraftingRecepie recipe) {
		NamespacedKey key = new NamespacedKey(EEMODLOADER.plugin, recipe.getName());
		if(recipe.shapeless) { CreateShapelessRecipe(key, recipe); } else { CreateShapedRecipe(key, recipe); }
	}

	private static void CreateShapedRecipe(NamespacedKey key, CraftingRecepie recipe) {
		ShapedRecipe srecipe = new ShapedRecipe(key, recipe.result.toItemStack());
		char currentLetter = 'A';
		String currentString = "";
		List<String> strings = new ArrayList<String>();
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 3; x++) {
				if(recipe.items[x][y] != null) {
					currentString += currentLetter;
					srecipe.setIngredient(currentLetter, new ExactChoice(recipe.items[x][y].toItemStack()));
					currentLetter++;
				} else {
					currentString += " ";
				}
			}
			strings.add(currentString);
			currentString = "";
		}
		srecipe.shape(strings.get(0),strings.get(1),strings.get(2));
		EEMODLOADER.server.addRecipe(srecipe);
	}

	private static void CreateShapelessRecipe(NamespacedKey key, CraftingRecepie recipe) {
		ShapelessRecipe srecipe = new ShapelessRecipe(key,recipe.result.toItemStack());
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 3; x++) {
				if(recipe.items[x][y] != null)
					srecipe.addIngredient(new ExactChoice(recipe.items[x][y].toItemStack()));
			}
		}
		EEMODLOADER.server.addRecipe(srecipe);
	}
	
}
