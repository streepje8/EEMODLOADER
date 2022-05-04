package com.streep.EEMODLOADER.eventsystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Event;
import org.json.JSONArray;

public class JSONFunction {

	public List<JSONAction> actions = new ArrayList<JSONAction>();
	
	public JSONFunction() {}
	
	public void addAction(JSONAction a) {
		actions.add(a);
	}
	
	public JSONFunction(JSONArray array) {
		for(int i = 0; i < array.length(); i++) {
			actions.add(new JSONAction(array.getJSONObject(i)));
		}
	}
	
	public void Execute(Event context) {
		for(JSONAction a : actions) {
			a.Execute(context);
		}
	}
	
	public JSONArray toJSONArray() {
		JSONArray array = new JSONArray();
		for(JSONAction action : actions) {
			array.put(action.toJSONObject());
		}
		return array;
	}
	
}
