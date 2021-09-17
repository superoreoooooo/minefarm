package org.oreoprojekt.minefarm.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class mineFarmSpawncommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("no console");
            return false;
        }
        Player player = (Player) sender;
        player.sendMessage("스폰으로 이동합니다...");
        Location loc = player.getLocation();
        loc.set(-10000,5,-10000);
        player.teleport(loc);
        return true;
    }
}
