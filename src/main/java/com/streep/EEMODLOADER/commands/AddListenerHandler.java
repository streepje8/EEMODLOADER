package com.streep.EEMODLOADER.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import com.streep.EEMODLOADER.commands.core.CommandHandler;
import com.streep.EEMODLOADER.eventsystem.EEEventListener;
import com.streep.EEMODLOADER.eventsystem.JSONListener;
import com.streep.EEMODLOADER.itemsystem.EEItemStack;
import com.streep.EEMODLOADER.utils.ChatUtil;
import com.streep.EEMODLOADER.utils.JsonFile;

@org.bukkit.plugin.java.annotation.command.Command(name = "addlistener", desc = "Add an event listener to an item", permission = "EEMODLOADER.addlistener", permissionMessage = "You do not have permission to run this command!", usage = "/<command> <type> <event> <eventarg>")
public class AddListenerHandler implements CommandHandler {
	
	@Override
	public String getPermission() {
		return "EEMODLOADER.addlistener";
	}
	
	public enum ItemListnerTypes {
		JSON,
		JAVA
	}
	
	@Override
	public void runAlways(CommandSender sender, Command command, String label, String[] arguments) {
		if(arguments.length > 1) {
			String typestring = arguments[0];
			ItemListnerTypes type = null;
			try {
				type = ItemListnerTypes.valueOf(typestring.toUpperCase());
			} catch(Exception e) {
				sender.sendMessage(ChatUtil.format("&4Cannot add listener, reason: " + e.getMessage()));
			}
			if(type != null) {
				if(sender instanceof Player) {
					Player player = (Player)sender;
					if(player.getInventory().getItemInMainHand() != null) {
						if(player.getInventory().getItemInMainHand().getType() != Material.AIR) {
							EEItemStack stack = EEItemStack.FromItemStack(player.getInventory().getItemInMainHand());
							String eventName = arguments[1];
							String eventarg = arguments[2];
							boolean niceboi = false;
							switch(type) {
								case JAVA:
									JSONObject obj = new JSONObject();
									obj.put("type","javaListener");
									obj.put("class", eventarg);
									niceboi = stack.events.addLisntener(eventName, EEEventListener.fromJSONObject(obj));
									break;
								case JSON:
									niceboi = stack.events.addLisntener(eventName, new JSONListener(new JsonFile("Functions/" + eventarg)));
									break;
							}
							if(niceboi) {
								sender.sendMessage(ChatUtil.format("&aAdded event listener to existing event"));
							} else {
								sender.sendMessage(ChatUtil.format("&aAdded event listener to new event: " + eventName));
							}
							player.getInventory().setItemInMainHand(stack.toItemStack());
						} else {
							sender.sendMessage(ChatUtil.format("&4You must hold an item to use this command!"));
						}
					} else {
						sender.sendMessage(ChatUtil.format("&4You must hold an item to use this command!"));
					}
				} else {
					sender.sendMessage(ChatUtil.format("&4Only players can use this command!"));
				}
			}
		} else {
			sender.sendMessage(ChatUtil.format("&4Usage: /<command> <type> <event> <eventarg>"));
		}
	}
}
