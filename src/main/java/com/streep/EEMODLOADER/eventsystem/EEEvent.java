package com.streep.EEMODLOADER.eventsystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Event;
import org.json.JSONArray;
import org.json.JSONObject;

public class EEEvent {

	private List<EEEventListener> listeners = new ArrayList<EEEventListener>();
	
	public EEEvent() {};
	
	public EEEvent(JSONArray json) {
		for(int i = 0; i < json.length(); i++) {
			JSONObject listobj = json.getJSONObject(i);
			EEEventListener listener = EEEventListener.fromJSONObject(listobj);
			if(listener != null)
				listeners.add(listener);
		}
	}

	public void addListener(EEEventListener e) {
		if(!listeners.contains(e) && e != null)
			listeners.add(e);
	}
	
	public void removeListener(EEEventListener e) {
		if(e != null && listeners.contains(e)) {
			listeners.remove(e);
		}
	}
	
	public List<EEEventListener> getListeners() {
		return listeners;
	}
	
	public void Raise(Event e) {
		for(EEEventListener li : listeners) {
			li.Raise(e);
		}
	}

	public JSONArray toJSONArray() {
		JSONArray array = new JSONArray();
		for(EEEventListener list : listeners) {
			array.put(list.toJSONObject());
		}
		return array;
	}
	
}
