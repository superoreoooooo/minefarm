package org.oreoprojekt.minefarm.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.oreoprojekt.minefarm.Listener.mineFarmIslandResetEventListener;
import org.oreoprojekt.minefarm.Minefarm;
import org.oreoprojekt.minefarm.util.mineFarmIslandUtil;

public class mineFarmIslandCommand implements CommandExecutor {

    private Minefarm plugin;
    private final mineFarmIslandUtil islandUtil;

    public mineFarmIslandCommand(Minefarm plugin) {
        this.plugin = plugin;
        this.islandUtil = new mineFarmIslandUtil(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        mineFarmIslandResetEventListener.OpenMailBox openMailBox = new mineFarmIslandResetEventListener.OpenMailBox(player);

        if (args.length == 0) {
            int[] spawn = islandUtil.getIslandSpawn(player);
            int spawnX = spawn[0];
            int spawnY = spawn[1];
            int spawnZ = spawn[2];
            Location loc = player.getLocation();
            loc.set(spawnX, spawnY, spawnZ);
            player.teleport(loc);
            player.sendMessage("섬으로 이동되었습니다.");
        }
        if (args.length == 1) {
            if (args[0].equals("셋홈") || args[0].equals("tptgha")) {
                islandUtil.setIslandSpawn(player);
                return true;
            }
            if (args[0].equals("이름") || args[0].equals("dlfma")) {
                player.sendMessage("섬이름 : " + "!아직미구현");
                return true;
            }
            if (args[0].equals("초기화") || args[0].equals("reset")) {
                Bukkit.getPluginManager().callEvent(openMailBox);
                return true;
            }
            else {
                player.sendMessage("잘못된 접근입니다.");
                return false;
            }
        }
        if (args.length == 2) {
            if (args[0].equals("이름") || args[0].equals("dlfma")) {
                islandUtil.setIslandName(player, args[1]);
                return true;
            }
            else {
                player.sendMessage("잘못된 접근입니다.");
                return false;
            }
        }
        return false;
    }
}
