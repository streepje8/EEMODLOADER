package com.streep.EEMODLOADER.core;

import com.streep.EEMODLOADER.utils.JsonFile;

public class EESettings {

	private JsonFile settingsFile;
	public boolean autoEnableHealthbar = false;
	
	public void load() {
		settingsFile = new JsonFile("Settings");
		if(settingsFile.object.has("autoEnableHealthbar"))
			autoEnableHealthbar = settingsFile.object.getBoolean("autoEnableHealthbar");
	}

	public void save() {
		if(settingsFile != null) {
			settingsFile.object.put("autoEnableHealthbar", autoEnableHealthbar);
			settingsFile.save();
		}
	}
}
