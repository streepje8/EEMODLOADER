package com.streep.EEMODLOADER.eventsystem;

import org.bukkit.event.Event;
import org.json.JSONObject;

public class JSONAction {

	public static enum JSONActionType {
		SEND_CHAT,
		SETHP,
		HEAL
	}
	
	public JSONActionType type;
	public JSONObject myObject;
	
	@SuppressWarnings("unchecked")
	public JSONAction(JSONObject object) {
		if(object.has("action")) {
			this.type = object.getEnum((Class<JSONActionType>) type.getClass(), "action");
		}
		myObject = object;
	}
	
	public JSONAction(JSONActionType action) {
		this.type = action;
		this.myObject = new JSONObject();
	}
	
	public void Execute(Event context) {
		
	}
	
	public JSONObject toJSONObject() {
		if(myObject.has("action"))
			myObject.remove("action");
		myObject.put("action", type);
		return myObject;
	}
	
}
