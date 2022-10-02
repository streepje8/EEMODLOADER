package com.streep.EEMODLOADER.uisystem;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryInteractEvent;

public class UIManager {

	private static HashMap<Player, UIMenu> openUI = new HashMap<Player, UIMenu>();
	
	public static UIMenu GetOpenuUIFor(Player p) {
		if(openUI.containsKey(p)) return openUI.get(p);
		return null;
	}
	
	public static void CloseUI(Player p) {
		if(openUI.containsKey(p)) {
			openUI.get(p).Close(p);
			openUI.remove(p);
		}
	}

	public static void Interact(InventoryInteractEvent e) {
		if(!openUI.containsKey(e.getViewers().get(0))) return; //Player has no special ui open
		
	}
	
}
