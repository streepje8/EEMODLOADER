package com.streep.EEMODLOADER.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import com.streep.EEMODLOADER.core.EEMODLOADER;
import com.streep.EEMODLOADER.entitysystem.EEEntity;
import com.streep.EEMODLOADER.entitysystem.EntityManager;

public class EntityListener implements Listener {

	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent e) {
		if(EEMODLOADER.plugin.settings.autoEnableHealthbar) {
			EEEntity entity = EntityManager.getEEEntity(e.getEntity());
			entity.drawHealthBar = true;
		}
	}
	
}
