package com.streep.EEMODLOADER.eventsystem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Event;
import org.json.JSONArray;
import org.json.JSONObject;

import com.streep.EEMODLOADER.core.EEMODLOADER;
import com.streep.EEMODLOADER.utils.JsonFile;
import com.streep.EEMODLOADER.utils.JsonFunctionLoader;

public class JSONListener extends EEEventListener {

	private List<JSONFunction> functions = new ArrayList<JSONFunction>();
	private List<JsonFile> functionFiles = new ArrayList<JsonFile>();
	
	public JSONListener(JsonFile file) {
		functionFiles.add(file);
		for(JsonFile f : functionFiles) {
			EEMODLOADER.plugin.getLogger().info(file.object.toString());
			functions.add(new JSONFunction(f.object.getJSONArray("function")));
		}
	}
	
	public JSONListener(JSONObject obj) {
		File functionsdir = new File(EEMODLOADER.plugin.getDataFolder() + "/Functions");
		if(!functionsdir.exists()) {
			functionsdir.mkdirs();
		}
		JSONArray functionsarr = obj.getJSONArray("functions");
		for(int i = 0; i < functionsarr.length(); i++) {
			if(JsonFunctionLoader.functions.containsKey("Functions/" + functionsarr.getString(i).replaceAll(".json", ""))) {
				functionFiles.add(JsonFunctionLoader.functions.get("Functions/" + functionsarr.getString(i).replaceAll(".json", "")));
			} 
		}
		for(JsonFile f : functionFiles) {
			functions.add(new JSONFunction(f.object.getJSONArray("function")));
		}
	}

	@Override
	public void Raise(Event e) {
		for(JSONFunction function : functions) {
			function.Execute(e);
		}
	}
	
	@Override
	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		result.put("type", "jsonListener".toLowerCase());
		JSONArray arr = new JSONArray();
		for(JsonFile f : functionFiles) {
			arr.put(f.getName().replaceFirst("Functions/", ""));
		}
		result.put("functions", arr);
		return result;
	}
}
