package com.streep.EEMODLOADER.commands.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface CommandHandler {

	public default boolean execute(CommandSender sender, Command command, String label, String[] arguments) {
    	runAlways(sender, command, label, arguments);
    	if (sender instanceof Player) {
            if (arguments.length == 0) {
                return runNoArguments(sender, command, label, arguments); 
            } else {
                return run(sender, command, label, arguments);
            }
           
        } else {
            if (arguments.length == 0) {
                return runConsoleNoArguments(sender, command, label, arguments);
               
            } else {
                return runConsole(sender, command, label, arguments);
            }
        }
    }
	
	public default String getPermission() {
		return null;
	}
    
    public default void runAlways(CommandSender sender, Command command, String label, String[] arguments) {}
   
    public default boolean runNoArguments(CommandSender sender, Command command, String label, String[] arguments) {
        return true; // logic here
    }
   
    public default boolean run(CommandSender sender, Command command, String label, String[] arguments) {
        return true; // logic here
    }
   
    public default boolean runConsoleNoArguments(CommandSender sender, Command command, String label, String[] arguments) {
        return true; // logic here
    }
   
    public default boolean runConsole(CommandSender sender, Command command, String label, String[] arguments) {
        return true; // logic here
    }
   
}