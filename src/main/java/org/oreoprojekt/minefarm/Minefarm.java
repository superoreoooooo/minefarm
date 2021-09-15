package org.oreoprojekt.minefarm;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.oreoprojekt.minefarm.Listener.*;
import org.oreoprojekt.minefarm.command.*;
import org.oreoprojekt.minefarm.manager.MineFarmIslandYmlManager;
import org.oreoprojekt.minefarm.manager.MineFarmYmlManager;
import org.oreoprojekt.minefarm.util.mineFarmChatMode;
import org.oreoprojekt.minefarm.util.mineFarmIslandUtil;
import org.oreoprojekt.minefarm.util.mineFarmScoreBoard;
import org.oreoprojekt.minefarm.util.mineFarmScoreBoardTimer;

public final class Minefarm extends JavaPlugin {

    public mineFarmScoreBoardTimer scoreBoardTimer;
    public mineFarmScoreBoard scoreBoard;
    public MineFarmYmlManager data;
    public MineFarmIslandYmlManager islandManager;
    public mineFarmChatMode chatMode;
    public mineFarmIslandUtil islandUtil;

    minefarmEventListener eventListener = new minefarmEventListener(this);
    public static String Prefix = ChatColor.WHITE + "[" + ChatColor.GREEN + "MineFarm" + ChatColor.WHITE + "]";

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(Prefix + ChatColor.GREEN + "MineFarm Plugin On");
        getServer().getPluginManager().registerEvents(new minefarmEventListener(this), this);
        getServer().getPluginManager().registerEvents(new mineFarmMailEventListener(this), this);
        getServer().getPluginManager().registerEvents(new mineFarmChatEventListener(this), this);
        getServer().getPluginManager().registerEvents(new mineFarmIslandResetEventListener(this), this);
        getServer().getPluginManager().registerEvents(new mineFarmIslandLeftEventListener(this), this);

        getCommand("cm").setExecutor(new mineFarmChatCommand(this));
        getCommand("우편").setExecutor(new mineFarmMailCommand(this));
        getCommand("우편함").setExecutor(new mineFarmOpenMailCommand(this));
        getCommand("우편정리").setExecutor(new mineFarmMailClearCommand(this));
        getCommand("섬").setExecutor(new mineFarmIslandCommand(this));

        this.data = new MineFarmYmlManager(this);
        this.scoreBoard = new mineFarmScoreBoard(this);
        this.scoreBoardTimer = new mineFarmScoreBoardTimer(this);
        this.chatMode = new mineFarmChatMode(this);
        this.islandManager = new MineFarmIslandYmlManager(this);
        this.islandUtil = new mineFarmIslandUtil(this);

        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                scoreBoard.createBoard(player);
                scoreBoardTimer.timer(player, true);
                chatMode.setChatMode(player, "All");
            }
        }
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(Prefix + ChatColor.RED + "MineFarm Plugin Off");
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                scoreBoardTimer.timer(player, false);
            }
        }
        data.saveConfig();
    }
}
