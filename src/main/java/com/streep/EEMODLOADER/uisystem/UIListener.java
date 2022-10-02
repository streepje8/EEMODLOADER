package com.streep.EEMODLOADER.uisystem;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

public class UIListener implements Listener {

	public void onCloseUI(InventoryCloseEvent e) {
		UIManager.CloseUI((Player)e.getPlayer());
	}
	
	public void onClickUI(InventoryInteractEvent e) {
		UIManager.Interact(e);
	}
	
}
