package org.oreoprojekt.minefarm.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.oreoprojekt.minefarm.Listener.mineFarmInfoEventListener;
import org.oreoprojekt.minefarm.Listener.mineFarmIslandLeftEventListener;
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
        mineFarmIslandLeftEventListener.OpenMailBox openMailBox1 = new mineFarmIslandLeftEventListener.OpenMailBox(player);
        mineFarmInfoEventListener.openIslandConfig openIslandConfig = new mineFarmInfoEventListener.openIslandConfig(player);

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
            if (args[0].equals("status")) {
                islandUtil.printStatus(player);
                return true;
            }
            if (args[0].equals("정보") || args[0].equals("info")) {
                Bukkit.getPluginManager().callEvent(openIslandConfig);
                return true;
            }
            if (args[0].equals("이름") || args[0].equals("dlfma")) {
                player.sendMessage(islandUtil.getIslandName(player));
                return true;
            }
            if (args[0].equals("초기화") || args[0].equals("reset")) {
                Bukkit.getPluginManager().callEvent(openMailBox);
                return true;
            }
            if (args[0].equals("떠나기") || args[0].equals("left")) {
                Bukkit.getPluginManager().callEvent(openMailBox1);
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
            if (args[0].equals("추가") || args[0].equals("add")) {
                islandUtil.addIslandPlayers(player, Bukkit.getOfflinePlayer(args[1]));
                return true;
            }
            if (args[0].equals("rm")) {
                islandUtil.removeIslandPlayers(player,Bukkit.getOfflinePlayer(args[1]));
                return true;
            }
            if (args[0].equals("날씨") || args[0].equals("wt")) {
                if (args[1].equals("clear")) {
                    islandUtil.setRain(player, false);
                    player.sendMessage("날씨를 맑음으로 선택하였습니다.");
                }
                else if (args[1].equals("rain")) {
                    islandUtil.setRain(player, true);
                    player.sendMessage("날씨를 비로 선택하였습니다.");
                }
                else {
                    player.sendMessage("잘못된 접근입니다.");
                    return false;
                }
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
