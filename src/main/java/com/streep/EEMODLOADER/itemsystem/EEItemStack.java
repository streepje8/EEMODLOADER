package com.streep.EEMODLOADER.itemsystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.json.JSONObject;

import com.streep.EEMODLOADER.utils.ChatUtil;

import net.minecraft.nbt.NBTTagCompound;

@SuppressWarnings("deprecation")
public class EEItemStack {
	
	public String type = "stone";
	public String itemType = Material.STONE.name();
	public int amount = 1;
	public MaterialData data;
	public ItemMeta meta;
	
	public EEItemStack(String name, String type, Material templateType, int amount) {
		this(new ItemStack(templateType).getItemMeta(), type, templateType, amount);
		meta.setDisplayName(name);
	}
	
	public EEItemStack(ItemMeta meta, String type, Material templateType, int amount, MaterialData data) {
		this.meta = meta;
		this.type = type;
		this.itemType = templateType.name();
		this.amount = amount;
		this.data = data;
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
		this(new ItemStack(Material.STONE).getItemMeta(), "null", Material.STONE, 1, new MaterialData(Material.STONE));
	}

	public EEItemStack(String name, String type, Material itemType, int amount, List<String> lore) {
		this(new ItemStack(itemType).getItemMeta(),type,itemType, amount, new MaterialData(itemType));
		meta.setDisplayName(ChatUtil.format(name));
		meta.setLore(lore);
	}

	public static EEItemStack FromItemStack(ItemStack i) {
		EEItemStack item = new EEItemStack();
		net.minecraft.world.item.ItemStack stack = CraftItemStack.asNMSCopy(i);
		NBTTagCompound tag = stack.u(); //stack.getOrCreateTag
		String eetype = tag.l("EEType"); //tag.getString
		if(eetype != null) {
			item.type = eetype;
		} else {
			item.type = i.getType().name();
		}
		item.itemType = i.getType().name();
		item.data = i.getData();
		if(i.hasItemMeta()) {
			item.meta = i.getItemMeta();
		} else {
			item.meta = new ItemStack(i.getType()).getItemMeta();
		}
		item.amount = i.getAmount();
		return item;
	}
	
	public ItemStack toItemStack() {
		ItemStack result = new ItemStack(Material.valueOf(itemType));
		result.setAmount(amount);
		result.setItemMeta(meta);
		result.setData(data);
		net.minecraft.world.item.ItemStack stack = CraftItemStack.asNMSCopy(result);
		NBTTagCompound tag = stack.u(); //stack.getOrCreateTag
		tag.a("EEType", type); //tag.setString
		stack.c(tag); //stack.setTag
		result = CraftItemStack.asBukkitCopy(stack);
		return result;
	}
	
	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();
		obj.put("type", type);
		obj.put("itemType", itemType);
		obj.put("amount", amount);
		obj.put("itemData", SerializeMaterialData(data));
		obj.put("itemMeta", SerializeItemMeta(meta));
		return obj;
	}

	public static EEItemStack FromJsonObject(JSONObject object) {
		//return new EEItemStack(object.getString("name"), object.getString("type"), Material.valueOf(object.getString("itemType")), object.getInt("amount"), castList(object.getJSONArray("lore").toList()), object.getInt("damage"), DeSerializeMaterialData(object.getString("itemType"), object.getString("itemData")));
		return new EEItemStack(DeSerializeItemMeta(object.getJSONObject("itemMeta")), object.getString("type"), Material.valueOf(object.getString("itemType")), object.getInt("amount"), DeSerializeMaterialData(object.getString("itemType"), object.getString("itemData")));
	}
	
	private static String SerializeMaterialData(MaterialData data) {
		return data.getData() + "";
	}
	
	private static MaterialData DeSerializeMaterialData(String itemType, String s) {
		return new MaterialData(Material.valueOf(itemType), Byte.parseByte(s));
	}
	
	public static JSONObject SerializeItemMeta(ItemMeta meta) {
		JSONObject obj = new JSONObject();
		Map<String, Object> map = meta.serialize();
		for(String key : map.keySet()) {
			obj.put(key, map.get(key));
		}
		return obj;
	}

	public static ItemMeta DeSerializeItemMeta(JSONObject obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		for(String key : JSONObject.getNames(obj)) {
			map.put(key, obj.get(key));
		}
		map.put("==", "ItemMeta");
        return (ItemMeta) ConfigurationSerialization.deserializeObject((Map<String, Object>) map);
    }
}
