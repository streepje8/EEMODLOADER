package com.streep.EEMODLOADER.commands;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.streep.EEMODLOADER.commands.core.CommandHandler;
import com.streep.EEMODLOADER.core.EEMODLOADER;
import com.streep.EEMODLOADER.itemsystem.EEItemStack;
import com.streep.EEMODLOADER.utils.ChatUtil;
import com.streep.EEMODLOADER.utils.JsonFile;

@org.bukkit.plugin.java.annotation.command.Command(name = "loaditem", desc = "Load an item", permission = "EEMODLOADER.loaditem", permissionMessage = "You do not have permission to run this command!", usage = "/<command> <itemname>")
public class LoadItemHandler implements CommandHandler {
	
	@Override
	public String getPermission() {
		return "EEMODLOADER.loaditem";
	}
	
	@Override
	public void runAlways(CommandSender sender, Command command, String label, String[] arguments) {
		if(arguments.length == 1) {
			sender.sendMessage(ChatUtil.format("&fLoading Item...."));
			String name = arguments[0];
			name = name.replaceAll("/", "__FORWARDSLASH__");
			name = name.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
			name = name.replaceAll("__FORWARDSLASH__", "/");
			File folder = new File(EEMODLOADER.plugin.getDataFolder() + "/Items");
			if(!folder.exists()) {
				folder.mkdirs();
			}
			if(sender instanceof Player) {
				Player player = (Player)sender;
				if(new File(EEMODLOADER.plugin.getDataFolder() + "/Items/" + name + ".json").exists()) {
					JsonFile file = new JsonFile("Items/" + name);
					sender.sendMessage(ChatUtil.format("&aLoading item: " + file.object.getString("Name")));
					sender.sendMessage(ChatUtil.format("&f&lItem Author: &b" + file.object.getString("Author")));
					EEItemStack item = EEItemStack.FromJsonObject(file.object.getJSONObject("Item"));
					player.getInventory().addItem(item.toItemStack());
				} else {
					sender.sendMessage(ChatUtil.format("&4No file found at: ./EEMODLOADER/Items/" + name + ".json"));
				}
			} else {
				sender.sendMessage(ChatUtil.format("&4Only players can use this command!"));
			}
		} else {
			sender.sendMessage(ChatUtil.format("&4Usage: /loaditem <name>"));
		}
	}
}
