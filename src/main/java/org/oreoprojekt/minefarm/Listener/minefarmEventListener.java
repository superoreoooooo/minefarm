package org.oreoprojekt.minefarm.Listener;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import org.oreoprojekt.minefarm.Minefarm;

public class minefarmEventListener implements Listener {

    private Minefarm plugin;

    public minefarmEventListener(Minefarm plugin) {
        this.plugin = plugin;
    }

    public int task;
    int money = 1000;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        createBoard(player);
        timer(player, true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        timer(player, false);
    }

    @EventHandler
    public void killother(EntityDeathEvent e) {
        if (e.getEntity().getKiller() instanceof Player) {
            Player player = e.getEntity().getKiller();
            addMoney(player, 100);
            addCash(player, 50);
        }
    }

    public void createBoard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("Playerboard", "dummy", "【 Project.kr – " + player.getName().toString() + " 】");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(board);
    }


    public int getMoney(Player player) { // money는 은화
        return moneyNow(player);
    }

    public void addMoney(Player player, int moneyToAdd) {
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".money", moneyNow(player) + moneyToAdd);
        plugin.data.saveConfig();
    }

    public boolean useMoney(Player player, int moneyUseSize) {
        if (moneyNow(player) >= moneyUseSize) {
            plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".money", moneyNow(player) - moneyUseSize);
            plugin.data.saveConfig();
            return true;
        }
        else {
            return false;
        }
    }

    public void setMoney(Player player, int moneyToSet) {
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".money", moneyNow(player) + moneyToSet);
        plugin.data.saveConfig();
    }

    public int moneyNow(Player player) {
        int moneynow = 0;
        if (plugin.data.getConfig().contains("players." + player.getUniqueId().toString() + ".money")) {
            moneynow = plugin.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".money");
        }
        return moneynow;
    }


    public int getCash(Player player) { // cash는 현금
        return cashNow(player);
    }

    public void addCash(Player player, int cashToAdd) {
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".cash", cashNow(player) + cashToAdd);
        plugin.data.saveConfig();
    }

    public boolean useCash(Player player, int cashUseSize) {
        if (moneyNow(player) >= cashUseSize) {
            plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".cash", cashNow(player) - cashUseSize);
            plugin.data.saveConfig();
            return true;
        }
        else {
            return false;
        }
    }

    public void setCash(Player player, int cashToSet) {
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".cash", moneyNow(player) + cashToSet);
        plugin.data.saveConfig();
    }

    public int cashNow(Player player) {
        int cashnow = 0;
        if (plugin.data.getConfig().contains("players." + player.getUniqueId().toString() + ".cash")) {
            cashnow = plugin.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".cash");
        }
        return cashnow;
    }


    public void timer(Player player, Boolean on) {
        if (on) {
            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    scoreBoardList(player);
                }
            }, 0, 10); //0.5초마다 갱신
        }
        else {
            Bukkit.getScheduler().cancelTask(task);
        }
    }

    public void scoreBoardList(Player player) {
        createBoard(player);
        Score scoreMoney = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.BLUE + "⎮은화 : " + ChatColor.GREEN + moneyNow(player));
        scoreMoney.setScore(10);
        Score scoreCash = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.BLUE + "⎮캐시 : " + ChatColor.GREEN + cashNow(player));
        scoreCash.setScore(9);
        Score scoreParty = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.BLUE + "⎮파티 : ");
        scoreParty.setScore(8);
        Score scoreFly = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.BLUE + "⎮날기 : ");
        scoreFly.setScore(7);
        Score scoreAutoSeedCount = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.BLUE + "⎮심기 : ");
        scoreAutoSeedCount.setScore(6);
        Score scoreChatMode = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.BLUE + "⎮채팅 : ");
        scoreChatMode.setScore(5);
        Score scoreTime = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.BLUE + "⎮시각 : ");
        scoreTime.setScore(4);
        Score scoreNull = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.BLUE + " ");
        scoreNull.setScore(3);
        Score scoreLevel = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.BLUE + "⎮레벨 : ");
        scoreLevel.setScore(2);
        Score scoreExp = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.BLUE + "⎮경험치 : ");
        scoreExp.setScore(1);
    }
}
