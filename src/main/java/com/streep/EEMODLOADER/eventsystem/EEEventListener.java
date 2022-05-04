package com.streep.EEMODLOADER.eventsystem;

import org.bukkit.event.Event;
import org.json.JSONObject;

import com.streep.EEMODLOADER.core.EEMODLOADER;

public abstract class EEEventListener {
	public abstract void Raise(Event e);
	
	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		result.put("type", "javaListener".toLowerCase());
		result.put("class", this.getClass().getName());
		return result;
	}
	
	public static EEEventListener fromJSONObject(JSONObject obj) {
		switch(obj.getString("type").toLowerCase()) {
			case "javalistener":
				try {
				    Class<?> clazz = Class.forName(obj.getString("class"));
				    Object listobj = clazz.getDeclaredConstructor().newInstance(new Object[0]);
				    if(listobj instanceof EEEventListener) {
				    	return (EEEventListener)listobj;
				    }
				} catch (Exception e) {
					EEMODLOADER.plugin.getLogger().info("COULD NOT LOAD EVENT LISTENER: " + obj.getString("class") + "! REASON: " + e.getMessage());
				}
				break;
			case "jsonlistener":
				return new JSONListener(obj);
		}
		return null;
	}
}
