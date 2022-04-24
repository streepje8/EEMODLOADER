package com.streep.EEMODLOADER.core;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LoadOn;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import com.streep.EEMODLOADER.commands.VersionHandler;
import com.streep.EEMODLOADER.commands.core.Command;

@Plugin(name="EEMODLOADER", version="1.0")
@Description(desc = "The modloader for EE Server Side Mods")
@LoadOn(loadOn = PluginLoadOrder.STARTUP)
@Author(name = "streepje8")
@Website(url = "https://github.com/streepje8/EEMODLOADER")
public class EEMODLOADER extends JavaPlugin {

	private final List<Command> commands = Arrays.asList(new Command[]{
			new Command("version", new VersionHandler())
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
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Shutting down EE MOD LOADER");
	}
	
	public boolean onCommand(CommandSender  sender, org.bukkit.command.Command command, String label, String[] args) {
	    for(Command cmd : commands) {
	    	cmd.execute(sender, command, label, args);
	    }
	    if(command.getName().equalsIgnoreCase("test")) {
	    	settings.save();
	    	((Player)sender).getInventory().addItem(settings.testItem.toItemStack());
	    }
		return true;
	}
}
