package com.streep.EEMODLOADER.core;

import java.util.Arrays;

import org.bukkit.Material;

import com.streep.EEMODLOADER.itemsystem.EEItemStack;
import com.streep.EEMODLOADER.utils.JsonFile;

public class EESettings {

	private JsonFile settingsFile;
	public EEItemStack testItem = new EEItemStack("&5Awesome &bTest &5Item!", "TestItem", Material.DIAMOND_SWORD, 1, Arrays.asList(new String[] {"Awesome Line One", "Awesome Line Two"}));
	
	public void load() {
		settingsFile = new JsonFile("Settings");
		if(settingsFile.object.has("testItem"))
			testItem = EEItemStack.FromJsonObject(settingsFile.object.getJSONObject("testItem"));
	}

	public void save() {
		if(settingsFile != null) {
			settingsFile.object.put("testItem", testItem.toJsonObject());
			settingsFile.save();
		}
	}
}
