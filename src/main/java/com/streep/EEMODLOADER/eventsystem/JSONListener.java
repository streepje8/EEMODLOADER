package com.streep.EEMODLOADER.eventsystem;

import org.bukkit.event.Event;
import org.json.JSONObject;

public class JSONListener extends EEEventListener {

	private JSONFunction function = new JSONFunction();
	
	public JSONListener(JSONObject obj) {
		function = new JSONFunction(obj.getJSONArray("function"));
		
		//TODO make this support multiple functions, make the raise work, make it save the function, add function call support to different files. If you have time left over, maybe add skript support
	}

	@Override
	public void Raise(Event e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		result.put("type", "jsonListener".toLowerCase());
		return result;
	}
}
