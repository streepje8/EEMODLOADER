package com.streep.EEMODLOADER.itemsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.json.JSONObject;

import com.streep.EEMODLOADER.core.EEMODLOADER;
import com.streep.EEMODLOADER.utils.ChatUtil;

import net.minecraft.nbt.NBTTagCompound;

@SuppressWarnings("deprecation")
public class EEItemStack {
	
	public String type = "stone";
	public String itemType = Material.STONE.name();
	public int amount = 1;
	public MaterialData data;
	public ItemMeta meta;
	public boolean displayRarity = false;
	public String rarity = "";
	private HashMap<String, String> customData = new HashMap<String, String>();
	
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
		this.customData = dataFromJSONObject(jsonObject);
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
		obj.put("itemData", SerializeMaterialData(data));
		obj.put("itemMeta", SerializeItemMeta(meta));
		obj.put("EECustomData", dataToJSONObject(customData));
		return obj;
	}

	public static EEItemStack FromJsonObject(JSONObject object) {
		//return new EEItemStack(object.getString("name"), object.getString("type"), Material.valueOf(object.getString("itemType")), object.getInt("amount"), castList(object.getJSONArray("lore").toList()), object.getInt("damage"), DeSerializeMaterialData(object.getString("itemType"), object.getString("itemData")));
		return new EEItemStack(DeSerializeItemMeta(object.getJSONObject("itemMeta")), object.getString("type"), Material.valueOf(object.getString("itemType")), object.getInt("amount"), DeSerializeMaterialData(object.getString("itemType"), object.getString("itemData")), object.getJSONObject("EECustomData"));
	}
	
	private static String SerializeMaterialData(MaterialData data) {
		return data.getData() + "";
	}
	
	private static MaterialData DeSerializeMaterialData(String itemType, String s) {
		return new MaterialData(Material.valueOf(itemType), Byte.parseByte(s));
	}
	
	private static List<String> saveabletypes = Arrays.asList(new String[] {"java.lang.String"});
	
	public static JSONObject SerializeItemMeta(ItemMeta meta) {
		JSONObject obj = new JSONObject();
		Map<String, Object> map = meta.serialize();
		for(String key : map.keySet()) {
			obj.put(key, map.get(key));
			if(!saveabletypes.contains(obj.get(key).getClass().getName())) {
				obj.getJSONObject(key).put("__CLASSTYPE__", map.get(key).getClass().getName());
			}
			EEMODLOADER.plugin.getLogger().info("KEY: " + key + " /// VALUE: " + obj.get(key).getClass().getName());
		}
		return obj;
	}

	public static ItemMeta DeSerializeItemMeta(JSONObject obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		for(String key : JSONObject.getNames(obj)) {
			map.put(key, obj.get(key));
			EEMODLOADER.plugin.getLogger().info("KEY: " + key + " /// VALUE: " + obj.get(key).getClass().getName());
		}
		map.put("==", "ItemMeta");
        return (ItemMeta) ConfigurationSerialization.deserializeObject((Map<String, Object>) map);
    }
}
