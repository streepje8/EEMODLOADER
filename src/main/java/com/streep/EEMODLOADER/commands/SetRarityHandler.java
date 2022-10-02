package com.streep.EEMODLOADER.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.streep.EEMODLOADER.commands.core.CommandHandler;
import com.streep.EEMODLOADER.core.EEMODLOADER;
import com.streep.EEMODLOADER.itemsystem.EEItemStack;
import com.streep.EEMODLOADER.utils.ChatUtil;

public class SetRarityHandler implements CommandHandler {
	
	@Override
	public String getPermission() {
		return "EEMODLOADER.setrarity";
	}
	
	@Override
	public void runAlways(CommandSender sender, Command command, String label, String[] arguments) {
		if(arguments.length == 1) {
			String rarity = arguments[0];
			for(String rar : EEMODLOADER.plugin.settings.rarities) {
				String cleanrar = "";
				boolean deletenext = false;
				for(char c : rar.toCharArray()) {
					if(c == '&') {
						deletenext = true;
					} else {
						if(!deletenext) {
							cleanrar += c;
						} else {
							deletenext = false;
						}
					}
				}
				if(cleanrar.replaceAll("[^a-zA-Z0-9-_\\.]", "_").equalsIgnoreCase(rarity)) {
					rarity = rar;
				}
			}
			if(EEMODLOADER.plugin.settings.rarities.contains(rarity) && EEMODLOADER.plugin.settings.useRarities) {
				if(sender instanceof Player) {
					Player player = (Player)sender;
					if(player.getInventory().getItemInMainHand() != null) {
						if(player.getInventory().getItemInMainHand().getType() != Material.AIR) {
							EEItemStack stack = EEItemStack.FromItemStack(player.getInventory().getItemInMainHand());
							stack.rarity = rarity;
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
			} else {
				sender.sendMessage(ChatUtil.format("&4You did not specify a valid rarity or rarities are disabled on this server (case sensitive)!"));
			}
		} else {
			sender.sendMessage(ChatUtil.format("&4Usage: /setrarity <rarity>"));
		}
	}
}
