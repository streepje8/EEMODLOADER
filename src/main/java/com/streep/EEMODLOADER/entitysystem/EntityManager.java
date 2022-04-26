package com.streep.EEMODLOADER.entitysystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Entity;

public class EntityManager {

	private static HashMap<UUID, EEEntity> entityMap = new HashMap<UUID, EEEntity>();
	
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
	
}
