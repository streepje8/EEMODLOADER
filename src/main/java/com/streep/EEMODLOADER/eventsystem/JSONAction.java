package com.streep.EEMODLOADER.eventsystem;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.json.JSONObject;

import com.streep.EEMODLOADER.itemsystem.EEItemStack;
import com.streep.EEMODLOADER.utils.ChatUtil;

public class JSONAction {

	public static enum JSONActionType {
		SEND_CHAT,
		TELEPORT,
		DAMAGE,
		LOAD_ITEM,
		CANCEL_EVENT,
		UNCANCEL_EVENT
	}
	
	public JSONActionType type;
	public JSONObject myObject;
	
	public JSONAction(JSONObject object) {
		if(object.has("action")) {
			this.type = JSONActionType.valueOf(object.getString("action").toUpperCase());
		}
		myObject = object;
	}
	
	public JSONAction(JSONActionType action) {
		this.type = action;
		this.myObject = new JSONObject();
	}
	
	public void Execute(Event context) {
		PlayerInteractEvent pie = null;
		if(context instanceof PlayerInteractEvent)
			 pie = (PlayerInteractEvent) context;
		Player player = null;
		if(pie != null) {
			player = pie.getPlayer();
		}
		switch(type) {
			case SEND_CHAT:
				if(player != null)
					player.sendMessage(ChatUtil.format(myObject.getString("message"), player));
				break;
			case TELEPORT:
				if(player != null)
					player.teleport(new Location(Bukkit.getServer().getWorld(myObject.getString("world")),myObject.getDouble("x"),myObject.getDouble("y"),myObject.getDouble("z")));
				break;
			case DAMAGE:
				if(player != null)
					player.damage(myObject.getDouble("amount"));
				break;
			case LOAD_ITEM:
				if(player != null)
					player.getInventory().addItem(EEItemStack.FromJsonObject(myObject.getJSONObject("item")).toItemStack());
				break;
			case CANCEL_EVENT:
				((Cancellable) context).setCancelled(true);
				break;
			case UNCANCEL_EVENT:
				((Cancellable) context).setCancelled(false);
				break;
			default:
				
				break;
		}
	}
	
	public JSONObject toJSONObject() {
		if(myObject.has("action"))
			myObject.remove("action");
		myObject.put("action", type);
		return myObject;
	}
	
}
