package com.streep.EEMODLOADER.entitysystem;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import com.streep.EEMODLOADER.core.EEMODLOADER;
import com.streep.EEMODLOADER.itemsystem.EEItemStack;
import com.streep.EEMODLOADER.utils.ChatUtil;
import com.streep.EEMODLOADER.utils.PotionEffectSerialization;
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
	
	public JSONObject toJSON() {
		JSONObject entityjson = new JSONObject();
		if(entity instanceof LivingEntity) {
			LivingEntity livinge = ((LivingEntity)entity);
			entityjson.put("EntityType", type.toString()); 
			entityjson.put("Name", this.name); 
			entityjson.put("IsNameVisible", isNameVisible); 
			entityjson.put("DrawHealthBar", drawHealthBar); 
			entityjson.put("MaxHP", maxHP); 
			entityjson.put("World", entity.getLocation().getWorld().getName()); 
			entityjson.put("X", entity.getLocation().getX()); 
			entityjson.put("Y", entity.getLocation().getY()); 
			entityjson.put("Z", entity.getLocation().getZ()); 
			entityjson.put("Pitch", entity.getLocation().getPitch()); 
			entityjson.put("Yaw", entity.getLocation().getYaw()); 
			entityjson.put("UseGravity", livinge.hasGravity()); 
			entityjson.put("Glowing", livinge.isGlowing()); 
			entityjson.put("Invunerable", livinge.isInvulnerable()); 
			entityjson.put("Silent", livinge.isSilent()); 
			entityjson.put("AI", livinge.hasAI()); 
			entityjson.put("Invisible", livinge.isInvisible()); 
			entityjson.put("Flies", livinge.isGliding());
			entityjson.put("PotionEffects", PotionEffectSerialization.serializeEffects(livinge.getActivePotionEffects()));
			entityjson.put("CurrentHP", livinge.getHealth());
			entityjson.put("AliveTicks", aliveTicks);
			JSONObject armor = new JSONObject();
			armor.put("Head", EEItemStack.FromItemStack(livinge.getEquipment().getHelmet()).toJsonObject());
			armor.put("Chest", EEItemStack.FromItemStack(livinge.getEquipment().getChestplate()).toJsonObject());
			armor.put("Legs", EEItemStack.FromItemStack(livinge.getEquipment().getLeggings()).toJsonObject());
			armor.put("Boots", EEItemStack.FromItemStack(livinge.getEquipment().getBoots()).toJsonObject());
			armor.put("MainHand", EEItemStack.FromItemStack(livinge.getEquipment().getItemInMainHand()).toJsonObject());
			armor.put("OffHand", EEItemStack.FromItemStack(livinge.getEquipment().getItemInOffHand()).toJsonObject());
			entityjson.put("Armor", armor);
			JSONObject dropChances = new JSONObject();
			dropChances.put("Head", livinge.getEquipment().getHelmetDropChance());
			dropChances.put("Chest", livinge.getEquipment().getChestplateDropChance());
			dropChances.put("Legs", livinge.getEquipment().getLeggingsDropChance());
			dropChances.put("Boots", livinge.getEquipment().getBootsDropChance());
			dropChances.put("MainHand", livinge.getEquipment().getItemInMainHandDropChance());
			dropChances.put("OffHand", livinge.getEquipment().getItemInOffHandDropChance());
			entityjson.put("DropChances", dropChances);
			JSONObject attributes = new JSONObject();
			for(Attribute a : Attribute.values()) {
				AttributeInstance atrb = livinge.getAttribute(a);
				if(atrb != null) {
					attributes.put(a.name() + "_BASE", atrb.getBaseValue());
					JSONArray atmodifiers = new JSONArray();
					for(AttributeModifier atbm : atrb.getModifiers()) {
						JSONObject atbmod = new JSONObject();
						atbmod.put("Name", atbm.getName());
						atbmod.put("Amount", atbm.getAmount());
						atbmod.put("Operation", atbm.getOperation().name());
						atmodifiers.put(atbmod);
					}
					attributes.put(a.name() + "_MODIFIERS", atmodifiers);
				}
			}
			entityjson.put("Attributes", attributes);
		} else {
			entityjson.put("ENTITY_NOT_SUPPORTED!!", "ITS NOT SERIALIZEABLE");
		}
		return entityjson;
	}
	
	public EEEntity(JSONObject obj) {
		World w = Bukkit.getServer().getWorld(obj.getString("World"));
		Location l = new Location(w,obj.getDouble("X"),obj.getDouble("Y"),obj.getDouble("Z"));
		l.setPitch(obj.getFloat("Pitch"));
		l.setYaw(obj.getFloat("Yaw"));
		this.entity = w.spawnEntity(l, EntityType.valueOf(obj.getString("EntityType")));
		LivingEntity livinge = (LivingEntity)this.entity;
		livinge.setGravity(obj.getBoolean("UseGravity"));
		livinge.setGlowing(obj.getBoolean("Glowing"));
		livinge.setInvulnerable(obj.getBoolean("Invunerable"));
		livinge.setAI(obj.getBoolean("AI"));
		livinge.setSilent(obj.getBoolean("Silent"));
		livinge.setGliding(obj.getBoolean("Flies"));
		livinge.setHealth(obj.getDouble("CurrentHP"));
		livinge.setInvisible(obj.getBoolean("Invisible"));
		livinge.addPotionEffects(PotionEffectSerialization.getPotionEffects(obj.getString("PotionEffects")));
		JSONObject armor = obj.getJSONObject("Armor");
		livinge.getEquipment().setHelmet(EEItemStack.FromJsonObject(armor.getJSONObject("Head")).toItemStack());
		livinge.getEquipment().setChestplate(EEItemStack.FromJsonObject(armor.getJSONObject("Chest")).toItemStack());
		livinge.getEquipment().setLeggings(EEItemStack.FromJsonObject(armor.getJSONObject("Legs")).toItemStack());
		livinge.getEquipment().setBoots(EEItemStack.FromJsonObject(armor.getJSONObject("Boots")).toItemStack());
		livinge.getEquipment().setItemInMainHand(EEItemStack.FromJsonObject(armor.getJSONObject("MainHand")).toItemStack());
		livinge.getEquipment().setItemInOffHand(EEItemStack.FromJsonObject(armor.getJSONObject("OffHand")).toItemStack());
		JSONObject dropChances = obj.getJSONObject("DropChances");
		livinge.getEquipment().setHelmetDropChance(dropChances.getFloat("Head"));
		livinge.getEquipment().setChestplateDropChance(dropChances.getFloat("Chest"));
		livinge.getEquipment().setLeggingsDropChance(dropChances.getFloat("Legs"));
		livinge.getEquipment().setBootsDropChance(dropChances.getFloat("Boots"));
		livinge.getEquipment().setItemInMainHandDropChance(dropChances.getFloat("MainHand"));
		livinge.getEquipment().setItemInOffHandDropChance(dropChances.getFloat("OffHand"));
		if(obj.has("Attributes")) {
			JSONObject attributes = obj.getJSONObject("Attributes");
			for(Attribute a : Attribute.values()) {
				AttributeInstance atrb = livinge.getAttribute(a);
				if(atrb != null) {
					atrb.setBaseValue(attributes.getDouble(a.name() + "_BASE"));
					JSONArray atmodifiers = attributes.getJSONArray(a.name() + "_MODIFIERS");
					for(int i = 0; i < atmodifiers.length(); i++) {
						JSONObject atbmod = atmodifiers.getJSONObject(i);
						AttributeModifier modifier = new AttributeModifier(atbmod.getString("Name"), atbmod.getDouble("Amount"), Operation.valueOf(atbmod.getString("Operation")));
						atrb.addModifier(modifier);
					}
				}
			}
		}
		this.type = obj.getEnum(EntityType.class, "EntityType");
		this.living = entity instanceof LivingEntity;
		this.isValid = entity.isValid();
		this.name = obj.getString("Name");
		this.aliveTicks = obj.getInt("AliveTicks");
		this.drawHealthBar = obj.getBoolean("DrawHealthBar");
		if(name == null) {
			name = "";
		}
		if(name.length() < 1) {
			this.name = this.type.name();
		}
		if(living) {
			this.maxHP = obj.getDouble("MaxHP");
		}
		this.isNameVisible = obj.getBoolean("IsNameVisible");
		this.id = entity.getUniqueId();
		EntityManager.RegisterEntity(this.entity.getUniqueId(), this);
	}
	
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
	
	public EEEntity(JSONObject obj, Location location) {
		this.entity = location.getWorld().spawnEntity(location, EntityType.valueOf(obj.getString("EntityType")));
		LivingEntity livinge = (LivingEntity)this.entity;
		livinge.setGravity(obj.getBoolean("UseGravity"));
		livinge.setGlowing(obj.getBoolean("Glowing"));
		livinge.setInvulnerable(obj.getBoolean("Invunerable"));
		livinge.setAI(obj.getBoolean("AI"));
		livinge.setSilent(obj.getBoolean("Silent"));
		livinge.setGliding(obj.getBoolean("Flies"));
		livinge.setHealth(obj.getDouble("CurrentHP"));
		livinge.setInvisible(obj.getBoolean("Invisible"));
		livinge.addPotionEffects(PotionEffectSerialization.getPotionEffects(obj.getString("PotionEffects")));
		JSONObject armor = obj.getJSONObject("Armor");
		livinge.getEquipment().setHelmet(EEItemStack.FromJsonObject(armor.getJSONObject("Head")).toItemStack());
		livinge.getEquipment().setChestplate(EEItemStack.FromJsonObject(armor.getJSONObject("Chest")).toItemStack());
		livinge.getEquipment().setLeggings(EEItemStack.FromJsonObject(armor.getJSONObject("Legs")).toItemStack());
		livinge.getEquipment().setBoots(EEItemStack.FromJsonObject(armor.getJSONObject("Boots")).toItemStack());
		livinge.getEquipment().setItemInMainHand(EEItemStack.FromJsonObject(armor.getJSONObject("MainHand")).toItemStack());
		livinge.getEquipment().setItemInOffHand(EEItemStack.FromJsonObject(armor.getJSONObject("OffHand")).toItemStack());
		JSONObject dropChances = obj.getJSONObject("DropChances");
		livinge.getEquipment().setHelmetDropChance(dropChances.getFloat("Head"));
		livinge.getEquipment().setChestplateDropChance(dropChances.getFloat("Chest"));
		livinge.getEquipment().setLeggingsDropChance(dropChances.getFloat("Legs"));
		livinge.getEquipment().setBootsDropChance(dropChances.getFloat("Boots"));
		livinge.getEquipment().setItemInMainHandDropChance(dropChances.getFloat("MainHand"));
		livinge.getEquipment().setItemInOffHandDropChance(dropChances.getFloat("OffHand"));
		if(obj.has("Attributes")) {
			JSONObject attributes = obj.getJSONObject("Attributes");
			for(Attribute a : Attribute.values()) {
				AttributeInstance atrb = livinge.getAttribute(a);
				if(atrb != null) {
					atrb.setBaseValue(attributes.getDouble(a.name() + "_BASE"));
					JSONArray atmodifiers = attributes.getJSONArray(a.name() + "_MODIFIERS");
					for(int i = 0; i < atmodifiers.length(); i++) {
						JSONObject atbmod = atmodifiers.getJSONObject(i);
						AttributeModifier modifier = new AttributeModifier(atbmod.getString("Name"), atbmod.getDouble("Amount"), Operation.valueOf(atbmod.getString("Operation")));
						atrb.addModifier(modifier);
					}
				}
			}
		}
		this.type = obj.getEnum(EntityType.class, "EntityType");
		this.living = entity instanceof LivingEntity;
		this.isValid = entity.isValid();
		this.name = obj.getString("Name");
		this.aliveTicks = obj.getInt("AliveTicks");
		this.drawHealthBar = obj.getBoolean("DrawHealthBar");
		if(name == null) {
			name = "";
		}
		if(name.length() < 1) {
			this.name = this.type.name();
		}
		if(living) {
			this.maxHP = obj.getDouble("MaxHP");
		}
		this.isNameVisible = obj.getBoolean("IsNameVisible");
		this.id = entity.getUniqueId();
		EntityManager.RegisterEntity(this.entity.getUniqueId(), this);
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
