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

@org.bukkit.plugin.java.annotation.command.Command(name = "saveitem", desc = "Save item to file", permission = "EEMODLOADER.saveitem", permissionMessage = "You do not have permission to run this command!", usage = "/<command> <itemname>")
public class SaveItemHandler implements CommandHandler {
	
	@Override
	public String getPermission() {
		return "EEMODLOADER.saveitem";
	}
	
	@Override
	public void runAlways(CommandSender sender, Command command, String label, String[] arguments) {
		if(arguments.length == 1) {
			sender.sendMessage(ChatUtil.format("&fSaving Item...."));
			String name = arguments[0];
			name = name.replaceAll("/", "__FORWARDSLASH__");
			name = name.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
			name = name.replaceAll("__FORWARDSLASH__", "/");
			File folder = new File(EEMODLOADER.plugin.getDataFolder() + "/Items/" + name.substring(0,name.lastIndexOf('/')));
			if(!folder.exists()) {
				folder.mkdirs();
			}
			if(sender instanceof Player) {
				Player player = (Player)sender;
				if(player.getInventory().getItemInMainHand() != null) {
					if(player.getInventory().getItemInMainHand().getType() != Material.AIR) {
						JsonFile file = new JsonFile("Items/" + name);
						file.object.put("Name", name);
						file.object.put("Author", player.getDisplayName());
						file.object.put("Item", EEItemStack.FromItemStack(player.getInventory().getItemInMainHand()).toJsonObject());
						file.save();
						sender.sendMessage(ChatUtil.format("&aFile saved at: ./EEMODLOADER/Items/" + name + ".json"));
					} else {
						sender.sendMessage(ChatUtil.format("&4You must hold an item to use this command!"));
					}
				} else {
					sender.sendMessage(ChatUtil.format("&4You must hold an item to use this command!"));
				}
			} else {
				sender.sendMessage(ChatUtil.format("&4Only players can use this command!"));
			}
		} else {
			sender.sendMessage(ChatUtil.format("&4Usage: /saveitem <name>"));
		}
	}
}
