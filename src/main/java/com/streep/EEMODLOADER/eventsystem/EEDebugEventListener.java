package com.streep.EEMODLOADER.eventsystem;

import org.bukkit.event.Event;

import com.streep.EEMODLOADER.core.EEMODLOADER;

public class EEDebugEventListener extends EEEventListener {

	@Override
	public void Raise(Event e) {
		EEMODLOADER.plugin.getLogger().info("THIS IS THE DEBUG EVENTLISTENER FOR: " + e.getEventName());
	}

}
