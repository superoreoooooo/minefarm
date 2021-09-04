package org.oreoprojekt.minefarm;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.oreoprojekt.minefarm.Listener.minefarmEventListener;
import org.oreoprojekt.minefarm.manager.MineFarmYmlManager;
import org.oreoprojekt.minefarm.util.mineFarmScoreBoard;
import org.oreoprojekt.minefarm.util.mineFarmScoreBoardTimer;

public final class Minefarm extends JavaPlugin {


    public mineFarmScoreBoardTimer scoreBoardTimer;
    public mineFarmScoreBoard scoreBoard;
    public MineFarmYmlManager data;
    minefarmEventListener eventListener = new minefarmEventListener(this);
    public static String Prefix = ChatColor.WHITE + "[" + ChatColor.GREEN + "MineFarm" + ChatColor.WHITE + "]";

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(Prefix + ChatColor.GREEN + "MineFarm Plugin On");
        getServer().getPluginManager().registerEvents(new minefarmEventListener(this), this);
        this.data = new MineFarmYmlManager(this);

        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                scoreBoard.createBoard(player);
                scoreBoardTimer.timer(player, true);
            }
        }
    }

    @Override
    public void onDisable() {getServer().getConsoleSender().sendMessage(Prefix + ChatColor.RED + "MineFarm Plugin Off");
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                scoreBoardTimer.timer(player, false);
            }
        }
        data.saveConfig();
    }
}
