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
	
	public CraftingRecepie(JSONObject obj) {
		name = obj.getString("Name");
		shapeless = obj.getBoolean("Shapeless");
		result = EEItemStack.FromJsonObject(obj.getJSONObject("Result"));
		JSONArray ingredients = obj.getJSONArray("Ingredients");
		for(int i = 0; i < ingredients.length(); i++) {
			JSONObject ingredient = ingredients.getJSONObject(i);
			int x = ingredient.getInt("X");
			int y = ingredient.getInt("Y");
			items[x][y] = EEItemStack.FromJsonObject(ingredient.getJSONObject("Item"));
		}
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
