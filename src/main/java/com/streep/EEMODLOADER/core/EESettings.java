package com.streep.EEMODLOADER.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;

import com.streep.EEMODLOADER.utils.JsonFile;

public class EESettings {

	private JsonFile settingsFile;
	public boolean serverSavePriority = false;
	public boolean autoEnableHealthbar = false;
	public String barFormat = "&8[&f%name%&8][&cHP&f%hpnum%&8/&f%maxhp%&8][%hpbar%&8]";
	public int barSegments = 30;
	public boolean useRarities = false;
	public List<String> rarities = Arrays.asList(new String[] {"&f&lCOMMON", "&a&lUNCOMMON", "&b&lRARE", "&5&lEPIC", "&6&lLEGENDARY", "&b&k..&r&5&lEE&r&b&k.."});
	public boolean autoConvertItems = false;
	
	public void load() {
		settingsFile = new JsonFile("Settings");
		if(settingsFile.object.has("ServerSavePriority")) 
			serverSavePriority = settingsFile.object.getBoolean("ServerSavePriority");
		if(settingsFile.object.has("AutoEnableHealthbar"))
			autoEnableHealthbar = settingsFile.object.getBoolean("AutoEnableHealthbar");
		if(settingsFile.object.has("HealthBarFormat")) 
			barFormat = settingsFile.object.getString("HealthBarFormat");
		if(settingsFile.object.has("HealthBarSegments")) 
			barSegments = settingsFile.object.getInt("HealthBarSegments");
		if(settingsFile.object.has("UseRarities")) 
			useRarities = settingsFile.object.getBoolean("UseRarities");
		if(settingsFile.object.has("Rarities")) 
			rarities = toStringArray(settingsFile.object.getJSONArray("Rarities").toList());
		if(settingsFile.object.has("AutoConvertItems")) 
			autoConvertItems = settingsFile.object.getBoolean("AutoConvertItems");
		//When the list is empty, reset!
		if(rarities.size() < 1) {
			rarities = Arrays.asList(new String[] {"&f&lCOMMON", "&a&lUNCOMMON", "&b&lRARE", "&5&lEPIC", "&6&lLEGENDARY", "&b&k..&r&5&lEE&r&b&k.."});
		}
	}

	public void save() {
		if(!serverSavePriority)
			load();
		if(settingsFile != null) {
			settingsFile.object.put("ServerSavePriority", serverSavePriority);
			settingsFile.object.put("AutoEnableHealthbar", autoEnableHealthbar);
			settingsFile.object.put("HealthBarFormat", barFormat);
			settingsFile.object.put("HealthBarSegments", barSegments);
			settingsFile.object.put("UseRarities", useRarities);
			settingsFile.object.put("Rarities", new JSONArray(rarities));
			settingsFile.object.put("AutoConvertItems", autoConvertItems);
			settingsFile.save();
		}
	}
	
	private static List<String> toStringArray(List<Object> objArray) {
		List<String> result = new ArrayList<String>();
		for(Object o : objArray) {
			result.add((String)o);
		}
		return result;
	}
}
