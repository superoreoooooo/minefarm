package org.oreoprojekt.minefarm;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.oreoprojekt.minefarm.Listener.mineFarmChatEventListener;
import org.oreoprojekt.minefarm.Listener.minefarmEventListener;
import org.oreoprojekt.minefarm.command.mineFarmChatCommand;
import org.oreoprojekt.minefarm.command.mineFarmMailBoxCommand;
import org.oreoprojekt.minefarm.command.mineFarmMailCommand;
import org.oreoprojekt.minefarm.manager.MineFarmYmlManager;
import org.oreoprojekt.minefarm.manager.mineFarmTxtManager;
import org.oreoprojekt.minefarm.util.mineFarmChatMode;
import org.oreoprojekt.minefarm.util.mineFarmScoreBoard;
import org.oreoprojekt.minefarm.util.mineFarmScoreBoardTimer;

import java.io.File;
import java.io.IOException;

public final class Minefarm extends JavaPlugin {

    public mineFarmScoreBoardTimer scoreBoardTimer;
    public mineFarmScoreBoard scoreBoard;
    public MineFarmYmlManager data;
    public mineFarmChatMode chatMode;
    public mineFarmTxtManager mineFarmTxtManager;

    minefarmEventListener eventListener = new minefarmEventListener(this);
    public static String Prefix = ChatColor.WHITE + "[" + ChatColor.GREEN + "MineFarm" + ChatColor.WHITE + "]";

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(Prefix + ChatColor.GREEN + "MineFarm Plugin On");
        getServer().getPluginManager().registerEvents(new minefarmEventListener(this), this);
        getServer().getPluginManager().registerEvents(new mineFarmChatEventListener(this), this);
        getCommand("cm").setExecutor(new mineFarmChatCommand(this));
        getCommand("우편").setExecutor(new mineFarmMailCommand(this));
        getCommand("우편함").setExecutor(new mineFarmMailBoxCommand(this));

        this.data = new MineFarmYmlManager(this);
        this.scoreBoard = new mineFarmScoreBoard(this);
        this.scoreBoardTimer = new mineFarmScoreBoardTimer(this);
        this.chatMode = new mineFarmChatMode(this);
        this.mineFarmTxtManager = new mineFarmTxtManager(this, new File(getDataFolder(), "inventory"));

        try{
            mineFarmTxtManager.load();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                scoreBoard.createBoard(player);
                scoreBoardTimer.timer(player, true);
                chatMode.setChatMode(player, "All");
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

    private void repeatSaveMail(){
        int second = this.getConfig().getInt("auto-save");
        if(second!=0){
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    try {
                        mineFarmTxtManager.saveAll(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 0L, second*20L); //0 Tick initial delay, 20 Tick (1 Second) between repeats
        }
    }
}
