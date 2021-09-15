package org.oreoprojekt.minefarm.Listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.oreoprojekt.minefarm.Minefarm;
import org.oreoprojekt.minefarm.util.*;

public class minefarmEventListener implements Listener {

    public mineFarmMoney money;
    public mineFarmCash cash;
    public mineFarmScoreBoardTimer scoreBoardTimer;
    private mineFarmChatMode chatMode;
    private mineFarmExp exp;
    private mineFarmLevel level;
    private mineFarmIslandUtil IslandUtil;

    private Minefarm plugin;


    public minefarmEventListener(Minefarm plugin) {
        this.plugin = plugin;
        this.money = new mineFarmMoney(plugin);
        this.cash = new mineFarmCash(plugin);
        this.scoreBoardTimer = new mineFarmScoreBoardTimer(plugin);
        this.chatMode = new mineFarmChatMode(plugin);
        this.exp = new mineFarmExp(plugin);
        this.level = new mineFarmLevel(plugin);
        this.IslandUtil = new mineFarmIslandUtil(plugin);
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
        Location Spawn = new Location(player.getWorld(), -10000, 2, -10000);
        player.teleport(Spawn);
        player.sendMessage("스폰 지점으로 이동되었습니다.");
        if (IslandUtil.isHaveIsland(player)) {
            return;
        }
        IslandUtil.createIsland(player);
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
}
