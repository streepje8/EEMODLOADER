package com.streep.EEMODLOADER.itemsystem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.json.JSONObject;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.streep.EEMODLOADER.core.EEMODLOADER;
import com.streep.EEMODLOADER.eventsystem.EEItemEvents;
import com.streep.EEMODLOADER.utils.ChatUtil;

import net.minecraft.nbt.NBTTagCompound;

@SuppressWarnings("deprecation")
public class EEItemStack {
	
	public String type = "stone";
	public String itemType = Material.STONE.name();
	public String name = "";
	public int amount = 1;
	public MaterialData data;
	public ItemMeta meta;
	public boolean displayRarity = false;
	public String rarity = "";
	private HashMap<String, String> customData = new HashMap<String, String>();
	public EEItemEvents events = new EEItemEvents();
	
	public EEItemStack(String name, String type, Material templateType, int amount) {
		this(new ItemStack(templateType).getItemMeta(), type, templateType, amount);
		meta.setDisplayName(name);
	}
	
	public EEItemStack(ItemMeta meta, String type, Material templateType, int amount, MaterialData data, JSONObject jsonObject) {
		this.meta = meta;
		this.type = type;
		this.itemType = templateType.name();
		this.amount = amount;
		this.data = data;
		if(EEMODLOADER.plugin.settings.useRarities) {
			this.rarity = EEMODLOADER.plugin.settings.rarities.get(0);
			this.displayRarity = true;
		}
		if(this.meta.getDisplayName() != null) {
			this.name = this.meta.getDisplayName();
		} else {
			this.name = templateType.name();
		}
		this.customData = dataFromJSONObject(jsonObject);
		this.events = new EEItemEvents();
	}
	
	public EEItemStack(ItemMeta meta, String type, Material templateType, int amount, MaterialData data) {
		this(meta,type,templateType,amount,data,new JSONObject());
	}
	
	public EEItemStack(ItemMeta meta, String type, Material templateType, int amount) {
		this(meta, type, templateType, amount,new MaterialData(templateType));
	}
	
	public EEItemStack(ItemMeta meta, String type, Material templateType) {
		this(meta, type, templateType, 1,new MaterialData(templateType));
	}
	
	public EEItemStack(ItemMeta meta, String type) {
		this(meta, type, Material.STONE, 1,new MaterialData(Material.STONE));
	}
	
	public EEItemStack(ItemMeta meta) {
		this(meta, "null", Material.STONE, 1,new MaterialData(Material.STONE));
	}
	
	public EEItemStack() {
		this(Bukkit.getItemFactory().getItemMeta(Material.STONE), "null", Material.STONE, 1, new MaterialData(Material.STONE));
	}

	public EEItemStack(String name, String type, Material itemType, int amount, List<String> lore) {
		this(new ItemStack(itemType).getItemMeta(),type,itemType, amount, new MaterialData(itemType));
		meta.setDisplayName(ChatUtil.format(name));
		meta.setLore(lore);
	}
	
	public void setCustomData(String key, String value) {
		if(this.customData.containsKey(key))
			this.customData.remove(key);
		this.customData.put(key, value);
	}
	
	public String getCustomData(String key) {
		if(this.customData.containsKey(key)) {
			return this.customData.get(key);
		} else {
			return null;
		}
	}

	public static EEItemStack FromItemStack(ItemStack i) {
		if(i == null)
			i = new ItemStack(Material.AIR);
		EEItemStack item = new EEItemStack();
		net.minecraft.world.item.ItemStack stack = CraftItemStack.asNMSCopy(i);
		NBTTagCompound tag = stack.u(); //stack.getOrCreateTag
		String eetype = tag.l("EEType"); //tag.getString
		String eerarity = tag.l("EERarity");
		String eecustomdata = tag.l("EECustomData");
		String eeeventsdata = tag.l("EEEventsData");
		if(eetype != null) {
			item.type = eetype;
		} else {
			item.type = i.getType().name();
		}
		if(eerarity != null) {
			if(EEMODLOADER.plugin.settings.rarities.contains(eerarity))
				item.rarity = eerarity;
		}
		if(eecustomdata != null) {
			item.customData = dataFromString(eecustomdata);
		}
		if(eeeventsdata != null) {
			item.events = new EEItemEvents(eeeventsdata);
		} else {
			item.events = new EEItemEvents();
		}
		item.itemType = i.getType().name();
		item.data = i.getData();
		if(i.getType() != Material.AIR) {
			if(i.hasItemMeta()) {
				item.meta = i.getItemMeta();
			} else {
				item.meta = Bukkit.getItemFactory().getItemMeta(i.getType());
			}
		}
		item.amount = i.getAmount();
		return item;
	}
	
	public ItemStack toItemStack() {
		ItemStack result = new ItemStack(Material.valueOf(itemType));
		result.setAmount(amount);
		if(!(result.getType() == Material.AIR)) {
			if(meta == null) {
				meta = Bukkit.getItemFactory().getItemMeta(result.getType());
			}
			List<String> lore = meta.getLore();
			if(lore == null) {
				lore = new ArrayList<String>();
			}
			int rarityIndex = 0;
			boolean foundchars = false;
			for(String s : lore) {
				if(s.contains("§Q")) {
					foundchars = true;
					break;
				}
				rarityIndex++;
			}
			if(foundchars) {
				lore = lore.subList(0, rarityIndex);
			}
			lore.add("§Q");
			lore.add(ChatUtil.format(rarity));
			meta.setLore(lore);
			result.setItemMeta(meta);
		}
		//
		result.setData(data);
		net.minecraft.world.item.ItemStack stack = CraftItemStack.asNMSCopy(result);
		NBTTagCompound tag = stack.u(); //stack.getOrCreateTag
		tag.a("EEType", type); //tag.setString
		tag.a("EERarity", rarity);
		tag.a("EEEventsData", events.toJSONObject().toString(1));
		tag.a("EECustomData", dataToString(customData));
		stack.c(tag); //stack.setTag
		result = CraftItemStack.asBukkitCopy(stack);
		return result;
	}
	
	private static String dataToString(HashMap<String, String> customData2) {
		JSONObject result = new JSONObject();
		for(String key : customData2.keySet()) {
			result.put(key, customData2.get(key));
		}
		return result.toString(0);
	}
	
	private static HashMap<String, String> dataFromString(String data) {
		if(data.length() < 2) {
			data = "{}";
		}
		JSONObject obj = new JSONObject(data);
		HashMap<String, String> result = new HashMap<String, String>();
		if(JSONObject.getNames(obj) != null) {
			for(String key : JSONObject.getNames(obj)) {
				result.put(key, obj.getString(key));
			}
		}
		return result;
	}
	
	private static JSONObject dataToJSONObject(HashMap<String, String> customData2) {
		JSONObject result = new JSONObject();
		for(String key : customData2.keySet()) {
			result.put(key, customData2.get(key));
		}
		return result;
	}
	
	private static HashMap<String, String> dataFromJSONObject(JSONObject obj) {
		HashMap<String, String> result = new HashMap<String, String>();
		if(JSONObject.getNames(obj) != null) {
			for(String key : JSONObject.getNames(obj)) {
				result.put(key, obj.getString(key));
			}
		}
		return result;
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();
		obj.put("type", type);
		obj.put("itemType", itemType);
		obj.put("amount", amount);
		if(name.length() < 1) {
			if(this.meta.getDisplayName() != null) {
				name = this.meta.getDisplayName();
			}
		}
		obj.put("itemName", name);
		obj.put("itemData", SerializeMaterialData(data));
		obj.put("itemMeta", SerializeItemMeta(meta, this.toItemStack()));
		obj.put("EECustomData", dataToJSONObject(customData));
		obj.put("EEEventsData", events.toJSONObject());
		return obj;
	}

	public static EEItemStack FromJsonObject(JSONObject object) {
		EEItemStack stack = new EEItemStack(DeSerializeItemMeta(object.getJSONObject("itemMeta")), object.getString("type"), Material.valueOf(object.getString("itemType")), object.getInt("amount"), DeSerializeMaterialData(object.getString("itemType"), object.getString("itemData")), object.getJSONObject("EECustomData"));
		stack.name = object.getString("itemName");
		stack.updateName();
		net.minecraft.world.item.ItemStack NMSstack = CraftItemStack.asNMSCopy(stack.toItemStack());
		NBTTagCompound tag = NMSstack.u(); //stack.getOrCreateTag
		String eerarity = tag.l("EERarity");
		if(eerarity != null) {
			if(EEMODLOADER.plugin.settings.rarities.contains(eerarity))
				stack.rarity = eerarity;
		}
		stack.events = new EEItemEvents(object.getJSONObject("EEEventsData").toString());
		return stack;
	}
	
	private void updateName() {
		this.meta.setDisplayName(this.name);
	}

	private static String SerializeMaterialData(MaterialData data) {
		return data.getData() + "";
	}
	
	private static MaterialData DeSerializeMaterialData(String itemType, String s) {
		return new MaterialData(Material.valueOf(itemType), Byte.parseByte(s));
	}
	
	public static JSONObject SerializeItemMeta(ItemMeta meta, ItemStack stack) {
		JSONObject obj = new JSONObject();
		Map<String, Object> map = meta.serialize();
		boolean jsonCompatible = true;
		for(String key : map.keySet()) {
			obj.put(key, map.get(key));
			obj = new JSONObject(obj.toString(0));
			if(map.get(key).getClass().getName().equalsIgnoreCase("java.util.LinkedHashMap") || map.get(key).getClass().getName().equalsIgnoreCase("org.bukkit.Color")) {
				jsonCompatible = false;
				break;
			}
			if(obj.get(key) instanceof JSONObject) {
					obj.getJSONObject(key).put("__CLASSTYPE__", map.get(key).getClass().getName());
			}
		}
		if(!jsonCompatible) {
			obj = new JSONObject();
			obj.put("__JSONCOMPATIBLE__", jsonCompatible);
			obj.put("__DATA__", itemStackToBase64(stack));
		} else {
			obj.put("__JSONCOMPATIBLE__", jsonCompatible);
		}
		return obj;
	}
	
	public static ItemMeta DeSerializeItemMeta(JSONObject obj) {
		if(obj.getBoolean("__JSONCOMPATIBLE__")) {
			Map<String, Object> map = new HashMap<String, Object>();
			for(String key : JSONObject.getNames(obj)) {
				Object found = obj.get(key);
				if(found instanceof JSONObject) {
					JSONObject foundobj = (JSONObject) found;
					String classname = foundobj.getString("__CLASSTYPE__");
					switch(classname) {
						case "com.google.common.collect.RegularImmutableMap":
							Map<String, Integer> mapboi = new HashMap<String, Integer>();
							for(String keyy : JSONObject.getNames(foundobj)) {
								if(!keyy.equalsIgnoreCase("__CLASSTYPE__")) {
									mapboi.put(keyy, foundobj.getInt(keyy));
								}
							}
							map.put(key, mapboi);
							break;
						case "java.util.LinkedHashMap":
							LinkedHashMap<String, ArrayList<AttributeModifier>> linkedboi = new LinkedHashMap<String, ArrayList<AttributeModifier>>();
							for(String keyy : JSONObject.getNames(foundobj)) {
								if(!keyy.equalsIgnoreCase("__CLASSTYPE__")) {
									JSONObject foundobjj = foundobj.getJSONArray(keyy).getJSONObject(0);
									ArrayList<AttributeModifier> modlist = new ArrayList<AttributeModifier>();
									AttributeModifier attbm = new AttributeModifier(foundobjj.getString("name"), foundobjj.getDouble("amount"), AttributeModifier.Operation.valueOf(foundobjj.getString("operation")));
									modlist.add(attbm);
									linkedboi.put(keyy, modlist);
								}
							}
							break;
						default:
							map.put(key, found);
							break;
					}
				} else {
					map.put(key, found);
				}
				EEMODLOADER.plugin.getLogger().info("KEY: " + key + " /// VALUE: " + obj.get(key).getClass().getName());
			}
			map.put("==", "ItemMeta");
			return (ItemMeta) ConfigurationSerialization.deserializeObject(map);
		} else {
			return itemStackFromBase64(obj.getString("__DATA__")).getItemMeta();
		}
        
    }
	
	public static String itemStackToBase64(ItemStack item) {
    	try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(item);
            
            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
        	 EEMODLOADER.plugin.getLogger().info("COULD NOT SAVE ITEM. REASON: " + e.getMessage());
        	 return null;
        }
    }
	
	public static ItemStack itemStackFromBase64(String data) {
    	try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack item = (ItemStack) dataInput.readObject();
            
            dataInput.close();
            return item;
        } catch (Exception e) {
            EEMODLOADER.plugin.getLogger().info("COULD NOT LOAD ITEM. REASON: " + e.getMessage());
            return null;
        }
    }
}
