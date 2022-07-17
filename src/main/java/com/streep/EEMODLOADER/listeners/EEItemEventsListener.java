package com.streep.EEMODLOADER.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.streep.EEMODLOADER.itemsystem.EEItemStack;

public class EEItemEventsListener implements Listener {

	@EventHandler
	public void onClickEvent(PlayerInteractEvent e) {
		ItemStack stack = e.getItem();
		if(stack != null) {
			EEItemStack estack = EEItemStack.FromItemStack(stack);
			switch(e.getAction()) {
			case LEFT_CLICK_AIR:
				estack.events.fire("leftClick", e);
				break;
			case LEFT_CLICK_BLOCK:
				estack.events.fire("leftClick", e);
				break;
			case PHYSICAL:
				break;
			case RIGHT_CLICK_AIR:
				estack.events.fire("rightClick", e);
				break;
			case RIGHT_CLICK_BLOCK:
				estack.events.fire("rightClick", e);
				break;
			default:
				break;
			}
		}
	}
	
}
