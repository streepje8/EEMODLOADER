package com.streep.EEMODLOADER.Utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtil {

	public static String format(String toFormat) {
		return ChatColor.translateAlternateColorCodes('&', toFormat.replaceAll("%time%", System.currentTimeMillis() + ""));
	}
	
	public static String format(String toFormat, Player player) {
		return ChatColor.translateAlternateColorCodes('&', toFormat.replaceAll("%time%", System.currentTimeMillis() + "")
				.replaceAll("%name%", player.getDisplayName())
				.replaceAll("%hp%", player.getHealth() + "")
				.replaceAll("%pos%", player.getLocation().toString())
				.replaceAll("%item%", player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()));
	}
	
}
