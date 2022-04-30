package com.streep.EEMODLOADER.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

import com.streep.EEMODLOADER.core.EEMODLOADER;
import com.streep.EEMODLOADER.itemsystem.EEItemStack;

public class ItemListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onItemClicked(InventoryClickEvent e) {
		if(EEMODLOADER.plugin.settings.autoConvertItems) {
			if(e.getWhoClicked().getItemOnCursor() != null && (e.getInventory().getType() == InventoryType.CREATIVE || e.getInventory().getType() == InventoryType.CRAFTING))
				e.setCursor(EEItemStack.FromItemStack(e.getCursor()).toItemStack());
			e.setCurrentItem(EEItemStack.FromItemStack(e.getCurrentItem()).toItemStack());
		}
	}
	
	@EventHandler
	public void onItemPickedUp(EntityPickupItemEvent e) {
		if(EEMODLOADER.plugin.settings.autoConvertItems) {
			e.getItem().setItemStack(EEItemStack.FromItemStack(e.getItem().getItemStack()).toItemStack());
		}
	}
	
	@EventHandler
	public void onItemCraftPrepare(PrepareItemCraftEvent e) {
		if(EEMODLOADER.plugin.settings.autoConvertItems) {
			e.getInventory().setResult(EEItemStack.FromItemStack(e.getInventory().getResult()).toItemStack());
		}
	}
	
	@EventHandler
	public void onItemCraft(CraftItemEvent e) {
		if(EEMODLOADER.plugin.settings.autoConvertItems) {
			e.getInventory().setResult(EEItemStack.FromItemStack(e.getInventory().getResult()).toItemStack());
		}
	}
	
}
