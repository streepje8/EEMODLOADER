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
        player.sendTitle(message, "", 1,1,1); //WIP
        //TODO ^^FIX THIS
    }
}
