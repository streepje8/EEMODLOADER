package com.streep.EEMODLOADER.core;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LoadOn;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import com.streep.EEMODLOADER.commands.LoadItemHandler;
import com.streep.EEMODLOADER.commands.SaveItemHandler;
import com.streep.EEMODLOADER.commands.VersionHandler;
import com.streep.EEMODLOADER.commands.core.Command;
import com.streep.EEMODLOADER.entitysystem.EntityManager;
import com.streep.EEMODLOADER.listeners.EntityListener;
import com.streep.EEMODLOADER.listeners.ItemListener;

@Plugin(name="EEMODLOADER", version="1.0")
@Description(desc = "The modloader for EE Server Side Mods")
@LoadOn(loadOn = PluginLoadOrder.STARTUP)
@Author(name = "streepje8")
@Website(url = "https://github.com/streepje8/EEMODLOADER")
public class EEMODLOADER extends JavaPlugin {

	private final List<Command> commands = Arrays.asList(new Command[]{
			new Command("version", new VersionHandler()),
			new Command("saveitem", new SaveItemHandler()),
			new Command("loaditem", new LoadItemHandler())
	});
	
	public static EEMODLOADER plugin;
	public EESettings settings = new EESettings();
	
	@Override
	public void onEnable() {
		plugin = this;
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
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Shutting down EE MOD LOADER");
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
