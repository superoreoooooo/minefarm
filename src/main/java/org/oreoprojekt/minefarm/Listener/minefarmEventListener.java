package org.oreoprojekt.minefarm.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.oreoprojekt.minefarm.Minefarm;
import org.oreoprojekt.minefarm.command.mineFarmChatCommand;
import org.oreoprojekt.minefarm.util.*;

public class minefarmEventListener implements Listener {

    public mineFarmMoney money;
    public mineFarmCash cash;
    public mineFarmScoreBoardTimer scoreBoardTimer;
    private mineFarmChatMode chatMode;
    private mineFarmExp exp;
    private mineFarmLevel level;

    private Minefarm plugin;
    public int task;


    public minefarmEventListener(Minefarm plugin) {
        this.plugin = plugin;
        this.money = new mineFarmMoney(plugin);
        this.cash = new mineFarmCash(plugin);
        this.scoreBoardTimer = new mineFarmScoreBoardTimer(plugin);
        this.chatMode = new mineFarmChatMode(plugin);
        this.exp = new mineFarmExp(plugin);
        this.level = new mineFarmLevel(plugin);
    }

    mineFarmScoreBoard scoreBoard = new mineFarmScoreBoard(plugin);

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        scoreBoard.createBoard(player);
        scoreBoardTimer.timer(player, true);
        if (chatMode.getChatMode(player) == null) {
            chatMode.setChatMode(player, "All");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        scoreBoardTimer.timer(player, false);
    }

    @EventHandler
    public void killEntity(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            Player player = e.getEntity().getKiller();
            money.addMoney(player, 100);
            cash.addCash(player, 50);
            exp.addExp(player, 33);
        }
    }

    @EventHandler
    public void blockGen(BlockPlaceEvent e) {
        if (e.getBlock().getType().equals(Material.CRYING_OBSIDIAN)) {
            e.getPlayer().sendMessage("hello this is a test");
            Location loc = e.getBlock().getLocation();
            loc.add(0,1,0);
            Blocktimer(true, loc);
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        if (e.getBlock().getType().equals(Material.CRYING_OBSIDIAN)) {
            e.getPlayer().sendMessage("test111");
            Location loc = e.getBlock().getLocation();
            loc.add(0,1,0);
            Blocktimer(false, loc);
        }
    }

    public void Blocktimer(Boolean on, Location loc) {
        if (on) {
            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    Location loc2 = loc;
                    loc2.add(0,-1,0);
                    if (loc2.getBlock().getType().equals(Material.CRYING_OBSIDIAN)) {
                        if (loc.getBlock().isEmpty()) {
                            loc.getBlock().setType(Material.DIAMOND_ORE);
                        }
                    }
                }
            }, 0, 20); //1초마다 갱신 (20 * 초) 20틱이 1초
        }
        else {
            Bukkit.getScheduler().cancelTask(task);
        }
    }
}
