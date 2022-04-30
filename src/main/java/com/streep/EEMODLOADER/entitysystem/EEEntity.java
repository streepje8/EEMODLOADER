package com.streep.EEMODLOADER.entitysystem;

import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import com.streep.EEMODLOADER.core.EEMODLOADER;
import com.streep.EEMODLOADER.utils.ChatUtil;
import com.streep.EEMODLOADER.utils.SMath;

public class EEEntity {

	public Entity entity;
	public EntityType type;
	public String name = "";
	public boolean living = false;
	public boolean isValid = false;
	public boolean isNameVisible = false;
	public boolean drawHealthBar = false;
	public double maxHP = 20d;
	public UUID id;
	public int aliveTicks = 0;
	
	public EEEntity(Entity entity) {
		this.entity = entity;
		this.type = entity.getType();
		this.living = entity instanceof LivingEntity;
		this.isValid = entity.isValid();
		this.name = entity.getCustomName();
		if(name == null) {
			name = "";
		}
		if(name.length() < 1) {
			this.name = this.type.name();
		}
		if(living) {
			this.maxHP = ((LivingEntity)entity).getHealth();
		}
		this.isNameVisible = entity.isCustomNameVisible();
		this.id = entity.getUniqueId();
	}
	
	public void SetName(String name) {
		this.name = name;
		this.isNameVisible = true;
		entity.setCustomNameVisible(true);
	}
	
	public void HideName() {
		this.isNameVisible = false;
		entity.setCustomNameVisible(false);
	}
	
	public void SetHP(double HP) {
		if(living) {
			((LivingEntity)entity).setHealth(HP);
			if(HP > this.maxHP)
				this.maxHP = HP;
		}
	}
	
	public void kill() {
		if(living) {
			SetHP(0);
			living = false;
		} else {
			entity.remove();
		}
	}
	
	public void update() {
		if(entity.isValid()) {
			aliveTicks++;
			this.isValid = true;
			if(drawHealthBar && living) {
				String bar = "&a";
				double percentage = ((LivingEntity)entity).getHealth() / this.maxHP;
				int filledBars = (int) SMath.clamp(Math.round(EEMODLOADER.plugin.settings.barSegments * percentage),0,EEMODLOADER.plugin.settings.barSegments);
				for(int i = 0; i < filledBars; i++) {
					bar += "|";
				}
				bar += "&c";
				for(int i = filledBars + 1; i < EEMODLOADER.plugin.settings.barSegments; i++) {
					bar += "|";
				}
				entity.setCustomName(ChatUtil.format(EEMODLOADER.plugin.settings.barFormat
						.replaceAll("%name%", this.name)
						.replaceAll("%hpnum%", (Math.round(((LivingEntity)entity).getHealth() * 10f) / 10f) + "")
						.replaceAll("%maxhp%",  (Math.round(this.maxHP * 10f) / 10f) + "")
						.replaceAll("%hpbar%", bar)
						));
				entity.setCustomNameVisible(true);
			} else {
				if(isNameVisible) {
					entity.setCustomName(name);
					entity.setCustomNameVisible(true);
				} else {
					entity.setCustomNameVisible(false);
				}
			}
		} else {
			entity.setCustomName(name);
			this.isValid = false;
		}
	}
	
}
