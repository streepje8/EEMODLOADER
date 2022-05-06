package com.streep.EEMODLOADER.entitysystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import com.streep.EEMODLOADER.core.EEMODLOADER;

public class EntityManager {

	private static HashMap<UUID, EEEntity> entityMap = new HashMap<UUID, EEEntity>();
	
	public static void initAllEntities() {
		for(World w : Bukkit.getServer().getWorlds()) {
			for(Entity e : w.getEntities()) {
				if(EEMODLOADER.plugin.settings.autoEnableHealthbar) {
					EEEntity entity = EntityManager.getEEEntity(e);
					entity.drawHealthBar = true;
				}
			}
		}
	}
	
	public static EEEntity getEEEntity(Entity e) {
		if(entityMap.containsKey(e.getUniqueId())) {
			return entityMap.get(e.getUniqueId());
		} else {
			EEEntity entity = new EEEntity(e);
			entityMap.put(entity.id, entity);
			return entity;
		}
	}
	
	public static void UpdateEntities() {
		List<EEEntity> toremove = new ArrayList<EEEntity>();
		for(EEEntity entity : entityMap.values()) {
			entity.update();
			if(entity.aliveTicks > 5 && !entity.entity.isValid()) {
				toremove.add(entity);
			}
		}
		for(EEEntity entity : toremove) {
			entityMap.remove(entity.id);
		}
	}

	public static void DisableEntities() {
		for(EEEntity entity : entityMap.values()) {
			entity.entity.setCustomName(entity.name);
		}
		entityMap = new HashMap<UUID, EEEntity>();
	}
	
}
