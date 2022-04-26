package com.streep.EEMODLOADER.api;

import org.bukkit.entity.Player;

import com.streep.EEMODLOADER.utils.ChatUtil;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class STREEPNETAPI {

	public static void sendActionBar(Player player, String message) {
        message = ChatUtil.format(message, player);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }
	
	public static void sendTitle(Player player, String message) {
        message = ChatUtil.format(message, player);
        player.sendTitle(message, null, 10,70,20);
    }
	
	public static void sendTitle(Player player, String message, int fadein, int stay, int fadeout) {
        message = ChatUtil.format(message, player);
        player.sendTitle(message, null, fadein,stay,fadeout);
    }
	
	public static void sendTitle(Player player, String message, String subtitle, int fadein, int stay, int fadeout) {
        message = ChatUtil.format(message, player);
        subtitle = ChatUtil.format(subtitle, player);
        player.sendTitle(message, subtitle, fadein,stay,fadeout);
    }
}
