package com.streep.EEMODLOADER.commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.streep.EEMODLOADER.commands.core.CommandHandler;
import com.streep.EEMODLOADER.core.EEMODLOADER;
import com.streep.EEMODLOADER.itemsystem.EEItemStack;
import com.streep.EEMODLOADER.modloader.MODItem;
import com.streep.EEMODLOADER.modloader.MODLOADER;
import com.streep.EEMODLOADER.utils.ChatUtil;
import com.streep.EEMODLOADER.utils.JsonFile;
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
			MODItem moditem = MODLOADER.ResolveItem(name);
			if(sender instanceof Player) {
				Player player = (Player)sender;
				File modFile;
				if(moditem == null) {
					File folder = new File(EEMODLOADER.plugin.getDataFolder() + "/Items");
					if(!folder.exists()) {
						folder.mkdirs();
					}
					modFile = new File(EEMODLOADER.plugin.getDataFolder() + "/Items/" + name + ".json");
				} else {
					modFile = new File(moditem.filepath);
				}
				if(modFile.exists()) {
					JsonFile file = new JsonFile(modFile.getAbsolutePath(),true);
					sender.sendMessage(ChatUtil.format("&aLoading item: " + file.object.getString("Name")));
					sender.sendMessage(ChatUtil.format("&f&lItem Author: &b" + file.object.getString("Author")));
					EEItemStack item = EEItemStack.FromJsonObject(file.object.getJSONObject("Item"));
					player.getInventory().addItem(item.toItemStack());
				} else {
					sender.sendMessage(ChatUtil.format("&4No file found at: ./EEMODLOADER/Items/" + name + ".json! Please check if you correctly installed the mod, or the individual item!"));
				}
			} else {
				sender.sendMessage(ChatUtil.format("&4Only players can use this command!"));
			}
		} else {
			
			sender.sendMessage(ChatUtil.format("&4Usage: /loaditem <name>"));
		}
	}
}
