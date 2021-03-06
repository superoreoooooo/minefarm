package org.oreoprojekt.minefarm.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.oreoprojekt.minefarm.Minefarm;

public class mineFarmScoreBoard {

    private Minefarm plugin;

    public mineFarmMoney money;
    public mineFarmCash cash;
    public mineFarmAutoPlantCount APCount;
    public mineFarmFly FLytime;
    public mineFarmLevel level;
    public mineFarmExp exp;
    public mineFarmChatMode chatMode;
    public mineFarmCurrentTimeUtilizer timeUtilizer;

    public mineFarmScoreBoard(Minefarm plugin) {
        this.plugin = plugin;
        this.money = new mineFarmMoney(plugin);
        this.cash = new mineFarmCash(plugin);
        this.FLytime = new mineFarmFly(plugin);
        this.APCount = new mineFarmAutoPlantCount(plugin);
        this.level = new mineFarmLevel(plugin);
        this.exp = new mineFarmExp(plugin);
        this.chatMode = new mineFarmChatMode(plugin);
        this.timeUtilizer = new mineFarmCurrentTimeUtilizer(plugin);

    }

    public void createBoard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("Playerboard", "dummy", "【Project.kr】");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(board);
    }

    public void scoreBoardList(Player player) {
        createBoard(player);
        Score scoreName = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮이름 : " + player.getName().toString());
        scoreName.setScore(11);
        Score scoreMoney = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮은화 : " + ChatColor.GRAY + money.getMoney(player));
        scoreMoney.setScore(10);
        Score scoreCash = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮캐시 : " + ChatColor.GOLD + cash.getCash(player));
        scoreCash.setScore(9);
        Score scoreParty = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮추천 : ");
        scoreParty.setScore(8);
        Score scoreFly = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮날기 : " + ChatColor.AQUA + FLytime.getFlyTime(player));
        scoreFly.setScore(7);
        Score scoreAutoPlantCount = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮심기 : " + ChatColor.GREEN + APCount.getAutoPlantCount(player));
        scoreAutoPlantCount.setScore(6);
        Score scoreChatMode = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮채팅 : " + chatMode.getChatMode(player));
        scoreChatMode.setScore(5);
        Score scoreTime = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮시각 : " + timeUtilizer.getDate());
        scoreTime.setScore(4);
        Score scoreNull1 = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + " ");
        scoreNull1.setScore(3);
        Score scoreLevel = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮레벨 : " + ChatColor.DARK_PURPLE + level.getlevel(player));
        scoreLevel.setScore(2);
        Score scoreExp = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮경험치 : " + ChatColor.DARK_PURPLE + exp.getExp(player));
        scoreExp.setScore(1);
    }
}
