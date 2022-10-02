package com.streep.EEMODLOADER.commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.streep.EEMODLOADER.commands.core.CommandHandler;
import com.streep.EEMODLOADER.core.EEMODLOADER;
import com.streep.EEMODLOADER.entitysystem.EEEntity;
import com.streep.EEMODLOADER.entitysystem.EntityManager;
import com.streep.EEMODLOADER.utils.ChatUtil;
import com.streep.EEMODLOADER.utils.JsonFile;
public class SaveEntityHandler implements CommandHandler {
	
	@Override
	public String getPermission() {
		return "EEMODLOADER.saveentity";
	}
	
	@Override
	public void runAlways(CommandSender sender, Command command, String label, String[] arguments) {
		if(arguments.length == 1) {
			sender.sendMessage(ChatUtil.format("&fSaving Entity...."));
			String name = arguments[0];
			name = name.replaceAll("/", "__FORWARDSLASH__");
			name = name.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
			name = name.replaceAll("__FORWARDSLASH__", "/");
			String addon = "";
			if(name.lastIndexOf('/') > 0) {
				addon = "/" + name.substring(0,name.lastIndexOf('/'));
			}
			File folder = new File(EEMODLOADER.plugin.getDataFolder() + "/Entities" + addon);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			if(sender instanceof Player) {
				Player player = (Player)sender;
				JsonFile file = new JsonFile("Entities/" + name);
				file.object.put("Name", name);
				file.object.put("Author", player.getDisplayName());
				Entity result = null;
		    	double lastDistance = Double.MAX_VALUE;
		    	for(Entity e : ((Player)sender).getWorld().getEntities()) {
		    		if(e.getType() == EntityType.PLAYER)
		    			continue;
		    		if(!(e instanceof LivingEntity))
		    			continue;
		    		
		    	    double distance = ((Player)sender).getLocation().distance(e.getLocation());
		    	    if(distance < lastDistance) {
		    	        lastDistance = distance;
		    	        result = e;
		    	    }
		    	}
		    	if(result != null) {
		    		EEEntity tosave = EntityManager.getEEEntity(result);
		    	    file.object.put("Entity", tosave.toJSON());
		    	    file.save();
		    	    sender.sendMessage(ChatUtil.format("&aSaved entity:&r&f " + tosave.name));
		    	    sender.sendMessage(ChatUtil.format("&aFile saved at: ./EEMODLOADER/Entities/" + name + ".json"));
		    	} else {
		    		sender.sendMessage(ChatUtil.format("&4You must be near an entity to use this command, it saves the nearest living entity!"));
		    	}						
			} else {
				sender.sendMessage(ChatUtil.format("&4Only players can use this command!"));
			}
		} else {
			sender.sendMessage(ChatUtil.format("&4Usage: /saveentity <name>"));
		}
	}
}
