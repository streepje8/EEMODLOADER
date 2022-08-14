package com.streep.EEMODLOADER.itemsystem;

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
	
}
