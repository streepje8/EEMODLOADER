package com.streep.EEMODLOADER.modloader;

import java.io.File;

import com.streep.EEMODLOADER.core.EEMODLOADER;
import com.streep.EEMODLOADER.utils.JsonFile;

public class EEMOD {

	public String name = "defaultName";
	public String displayName = "defaultDisplayName";
	public String author = "defaultAuthor";
	
	public void Load(File modDirectory, JsonFile modinfo) {
		File itemsDir = new File(modDirectory.getAbsolutePath() + "/Items");
		if(itemsDir.exists()) {
			for(File itemFile : itemsDir.listFiles()) {
				if(itemFile.getAbsolutePath().endsWith(".json")) {
					MODItem item = new MODItem();
					item.filepath = itemFile.getAbsolutePath();
					item.modName = name;
					MODLOADER.RegisterItem(name + "/" + itemFile.getName().replace(".json", ""), item);
					EEMODLOADER.plugin.getLogger().info("Loaded item: " + item.filepath);
				}
			}
		}
		
	}
	
}
