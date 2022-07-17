package com.streep.EEMODLOADER.modloader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.streep.EEMODLOADER.core.EEMODLOADER;
import com.streep.EEMODLOADER.utils.ChatUtil;
import com.streep.EEMODLOADER.utils.JsonFile;

public class MODLOADER {

	private static ArrayList<EEMOD> mods = new ArrayList<EEMOD>();
	private static HashMap<String, MODItem> itemDB = new HashMap<String, MODItem>();
	
	public static void ReLoadMods() {
		mods = new ArrayList<EEMOD>();
		String modsFolderPath = EEMODLOADER.plugin.getDataFolder() + "/Mods";
		File modsFolder = new File(modsFolderPath);
		if(!modsFolder.exists()) {
			modsFolder.mkdirs();
		}
		EEMODLOADER.plugin.getLogger().info("Loading mods");
		for(File f : modsFolder.listFiles()) {
			if(f.isDirectory()) {
				File modinfo = new File(f.getAbsolutePath() + "/modinfo.json");
				if(modinfo.exists()) {
					JsonFile file = new JsonFile(modinfo.getAbsolutePath(), true);
					EEMOD mod = new EEMOD();
					mod.name = ChatUtil.format(file.object.getString("ModName"));
					mod.displayName = ChatUtil.format(file.object.getString("DisplayName"));
					mod.author = ChatUtil.format(file.object.getString("AuthorName"));
					EEMODLOADER.plugin.getLogger().info("Loading mod: " + mod.displayName + ", by:" + mod.author);
					mod.Load(f, file);
					mods.add(mod);
				}
			}
		}
	}
	
	public static void RegisterItem(String path, MODItem item) {
		if(!itemDB.containsKey(path)) {
			itemDB.put(path, item);
		} else {
			EEMODLOADER.plugin.getLogger().info("Item: " + path + " could not be loaded, it got registered twice!");
		}
	}
	
	public static MODItem ResolveItem(String path) {
		if(itemDB.containsKey(path)) {
			return itemDB.get(path);
		}
		return null;
	}
	
	public static ArrayList<EEMOD> getMods() {
		return mods;
	}
	
}
