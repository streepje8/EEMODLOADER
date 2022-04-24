package com.streep.EEMODLOADER.core;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
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
public class Main extends JavaPlugin {

	private final List<Command> commands = Arrays.asList(new Command[]{
			new Command("version", new VersionHandler())
	});
	
	@Override
	public void onEnable() {
		getLogger().info("Starting EE MOD LOADER");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Shutting down EE MOD LOADER");
	}
	
	public boolean onCommand(CommandSender  sender, org.bukkit.command.Command command, String label, String[] args) {
	    for(Command cmd : commands) {
	    	cmd.execute(sender, command, label, args);
	    }
		return true;
	}
}
