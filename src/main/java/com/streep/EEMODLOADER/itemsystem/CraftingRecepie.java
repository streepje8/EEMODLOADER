package com.streep.EEMODLOADER.itemsystem;

import org.json.JSONArray;
import org.json.JSONObject;

public class CraftingRecepie {

	public boolean shapeless = false;
	public EEItemStack result;
	public EEItemStack[][] items = new EEItemStack[3][3];
	
	private String name;
	
	public CraftingRecepie(String name, EEItemStack result) {
		this.name = name;
		this.result = result;
	}

	public String getName() {
		return name;
	}
	
	public JSONObject toJSON() {
		JSONObject result = new JSONObject();
		result.put("Name", name);
		result.put("Shapeless", shapeless);
		result.put("Result", this.result.toJsonObject());
		JSONArray ingredients = new JSONArray();
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 3; x++) {
				if(this.items[x][y] != null) {
					JSONObject ingredient = new JSONObject();
					ingredient.put("X", x);
					ingredient.put("Y", y);
					ingredient.put("Item", this.items[x][y].toJsonObject());
					ingredients.put(ingredient);
				}
			}
		}
		result.put("Ingredients", ingredients);
		return result;
	}
	
}
