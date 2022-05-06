package com.streep.EEMODLOADER.eventsystem;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Event;
import org.json.JSONObject;

import com.streep.EEMODLOADER.core.EEMODLOADER;

public class EEItemEvents {

	private Map<String, EEEvent> lookupTable = new HashMap<String, EEEvent>();
	
	public EEItemEvents() {
		RegisterDefaults();
	}
	
	public EEItemEvents(String s) {
		if(s.length() > 2) {
		JSONObject obj = new JSONObject(s);
			if(JSONObject.getNames(obj) != null) {
				for(String key : JSONObject.getNames(obj)) {
					RegisterEEEvent(key, new EEEvent(obj.getJSONArray(key)));
				}
			}
		} else {
			RegisterDefaults();
		}
	}
	
	private void RegisterDefaults() {
		RegisterEEEvent("leftClick", new EEEvent());
		RegisterEEEvent("rightClick", new EEEvent());
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		for(String key : lookupTable.keySet()) {
			obj.put(key, lookupTable.get(key).toJSONArray());
		}
		return obj;
	}
	
	public boolean addLisntener(String eventname, EEEventListener e) {
		if(lookupTable.containsKey(eventname.toLowerCase())) {
			lookupTable.get(eventname.toLowerCase()).addListener(e);
			return true;
		}
		RegisterEEEvent(eventname, new EEEvent());
		addLisntener(eventname, e);
		return false;
	}
	
	public void fire(String name, Event e) {
		if(lookupTable.containsKey(name.toLowerCase()))
			lookupTable.get(name.toLowerCase()).Raise(e);
	}
	
	private void RegisterEEEvent(String name, EEEvent e) {
		if(!lookupTable.containsKey(name.toLowerCase()))
			lookupTable.put(name.toLowerCase(), e);
	}
	
}
