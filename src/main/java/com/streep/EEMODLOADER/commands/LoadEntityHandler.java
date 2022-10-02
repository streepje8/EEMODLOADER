package com.streep.EEMODLOADER.commands;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.streep.EEMODLOADER.commands.core.CommandHandler;
import com.streep.EEMODLOADER.core.EEMODLOADER;
import com.streep.EEMODLOADER.entitysystem.EEEntity;
import com.streep.EEMODLOADER.modloader.MODEntity;
import com.streep.EEMODLOADER.modloader.MODLOADER;
import com.streep.EEMODLOADER.utils.ChatUtil;
import com.streep.EEMODLOADER.utils.JsonFile;
import com.streep.EEMODLOADER.utils.SMath;

public class LoadEntityHandler implements CommandHandler {
	
	@Override
	public String getPermission() {
		return "EEMODLOADER.loadentity";
	}
	
	@Override
	public void runAlways(CommandSender sender, Command command, String label, String[] arguments) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			boolean success = false;
			Location spawnloc = null;
			if(arguments.length == 1) {
				spawnloc = player.getLocation();
				success = true;
			} else {
				if(arguments.length == 4) {
					if(SMath.isNum(arguments[1]) || SMath.isNum(arguments[2]) || SMath.isNum(arguments[3])) {
						double x = Double.parseDouble(arguments[1]);
						double y = Double.parseDouble(arguments[2]);
						double z = Double.parseDouble(arguments[3]);
						spawnloc = new Location(player.getWorld(), x, y, z).add(0.5d, 0.5d, 0.5d);
						success = true;
					} else {
						sender.sendMessage(ChatUtil.format("&4Usage: /loadentity <name> [x] [y] [z]"));
					}
				} else {
					sender.sendMessage(ChatUtil.format("&4Usage: /loadentity <name> [x] [y] [z]"));
				}
			}
			if(success) {
				sender.sendMessage(ChatUtil.format("&fLoading Entity...."));
				String name = arguments[0];
				name = name.replaceAll("/", "__FORWARDSLASH__");
				name = name.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
				name = name.replaceAll("__FORWARDSLASH__", "/");
				MODEntity moditem = MODLOADER.ResolveEntity(name);
				
					File modFile;
					if(moditem == null) {
						File folder = new File(EEMODLOADER.plugin.getDataFolder() + "/Entities");
						if(!folder.exists()) {
							folder.mkdirs();
						}
						modFile = new File(EEMODLOADER.plugin.getDataFolder() + "/Entities/" + name + ".json");
					} else {
						modFile = new File(moditem.filepath);
					}
					if(modFile.exists()) {
						JsonFile file = new JsonFile(modFile.getAbsolutePath(),true);
						sender.sendMessage(ChatUtil.format("&aLoading Entity: " + file.object.getString("Name")));
						sender.sendMessage(ChatUtil.format("&f&lEntity Author: &b" + file.object.getString("Author")));
						new EEEntity(file.object.getJSONObject("Entity"), spawnloc);
					} else {
						sender.sendMessage(ChatUtil.format("&4No file found at: ./EEMODLOADER/Entities/" + name + ".json! Please check if you correctly installed the mod, or the individual entity!"));
					}
				
			}
		} else {
			sender.sendMessage(ChatUtil.format("&4Only players can use this command!"));
		}
	}
}
