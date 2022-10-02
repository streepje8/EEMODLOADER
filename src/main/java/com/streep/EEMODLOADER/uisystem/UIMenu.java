package com.streep.EEMODLOADER.uisystem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class UIMenu {

	private InventoryType type = InventoryType.CHEST;
	private String title = "&5EE Menu";
	private boolean displayBackground = false;
	private ItemStack backgroundItem = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE,1);
	
	public abstract void onOpen(Player p);
	public abstract void onClose(Player p);
	public abstract void onInteract();
	
	public void Display(Player p) {
		if(UIManager.GetOpenuUIFor(p) != null) {
			UIManager.CloseUI(p);
		}
		Inventory display = Bukkit.getServer().createInventory(p, type, title);
		if(displayBackground) for(int i = 0; i < display.getSize(); i++) display.setItem(i, backgroundItem);
		p.openInventory(display);
		this.onOpen(p);
	}
	
	public void Close(Player p) {
		this.onClose(p);
		p.closeInventory();
	}
	
}
