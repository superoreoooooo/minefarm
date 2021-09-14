package org.oreoprojekt.minefarm.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.oreoprojekt.minefarm.Listener.mineFarmMailEventListener;
import org.oreoprojekt.minefarm.Minefarm;
import org.oreoprojekt.minefarm.util.mineFarmMailSendSystem;

import java.io.IOException;


public class mineFarmOpenMailCommand implements CommandExecutor {
    private mineFarmMailSendSystem mineFarmMailSendSystem;
    private Minefarm plugin;
    public mineFarmOpenMailCommand(Minefarm plugin) {
        this.plugin = plugin;
        this.mineFarmMailSendSystem = new mineFarmMailSendSystem(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            mineFarmMailEventListener.OpenMailBox event = new mineFarmMailEventListener.OpenMailBox((Player)sender);
            Bukkit.getPluginManager().callEvent(event);
            return true;
        }
        else if(sender instanceof ConsoleCommandSender) {
            sender.sendMessage("no console.");
            return false;
        }
        return false;
    }
}
