package com.streep.EEMODLOADER.core;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.streep.EEMODLOADER.commands.AddListenerHandler;
import com.streep.EEMODLOADER.commands.CraftHandler;
import com.streep.EEMODLOADER.commands.LoadEntityHandler;
import com.streep.EEMODLOADER.commands.LoadItemHandler;
import com.streep.EEMODLOADER.commands.ModsHandler;
import com.streep.EEMODLOADER.commands.SaveEntityHandler;
import com.streep.EEMODLOADER.commands.SaveItemHandler;
import com.streep.EEMODLOADER.commands.SetRarityHandler;
import com.streep.EEMODLOADER.commands.VersionHandler;
import com.streep.EEMODLOADER.commands.core.Command;
import com.streep.EEMODLOADER.entitysystem.EntityManager;
import com.streep.EEMODLOADER.listeners.EEItemEventsListener;
import com.streep.EEMODLOADER.listeners.EntityListener;
import com.streep.EEMODLOADER.listeners.ItemListener;
import com.streep.EEMODLOADER.modloader.MODLOADER;
public class EEMODLOADER extends JavaPlugin {

	private final List<Command> commands = Arrays.asList(new Command[]{
			new Command("version", new VersionHandler()),
			new Command("saveitem", new SaveItemHandler()),
			new Command("loaditem", new LoadItemHandler()),
			new Command("saveentity", new SaveEntityHandler()),
			new Command("loadentity", new LoadEntityHandler()),
			new Command("setrarity", new SetRarityHandler()),
			new Command("addlistener", new AddListenerHandler()),
			new Command("mods", new ModsHandler()),
			new Command("craft", new CraftHandler())
	});
	
	public static EEMODLOADER plugin;
	public static Server server;
	public static Logger logger;
	public EESettings settings = new EESettings();
	
	@Override
	public void onEnable() {
		plugin = this;
		server = getServer();
		logger = getLogger();
		getLogger().info("Starting EE MOD LOADER");
		if(!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}
		settings.load();
		settings.save();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
		    @Override
		    public void run() {
		        EntityManager.UpdateEntities();
		    }
		}, 0L, 10L);
		
		//Register Listeners
		getServer().getPluginManager().registerEvents(new EntityListener(), plugin);
		getServer().getPluginManager().registerEvents(new ItemListener(), plugin);
		getServer().getPluginManager().registerEvents(new EEItemEventsListener(), plugin);
		//JsonFunctionLoader.LoadAllFunctions(); //Currently not supported yet
		EntityManager.initAllEntities();
		MODLOADER.ReLoadMods();
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Shutting down EE MOD LOADER");
		EntityManager.DisableEntities();
		settings.save();
	}
	
	public boolean onCommand(CommandSender  sender, org.bukkit.command.Command command, String label, String[] args) {
	    for(Command cmd : commands) {
	    	cmd.execute(sender, command, label, args);
	    }
	    if(command.getName().equalsIgnoreCase("test")) {
	    }
		return true;
	}
}
