package org.oreoprojekt.minefarm.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class mineFarmScoreBoard {

    public mineFarmMoney money;
    public mineFarmCash cash;

    public void createBoard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("Playerboard", "dummy", "【 Project.kr – " + player.getName().toString() + " 】");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(board);
    }

    public void scoreBoardList(Player player) {
        createBoard(player);
        Score scoreMoney = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮은화 : " + ChatColor.GREEN + money.moneyNow(player));
        scoreMoney.setScore(10);
        Score scoreCash = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮캐시 : " + ChatColor.GOLD + cash.cashNow(player));
        scoreCash.setScore(9);
        Score scoreParty = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮파티 : ");
        scoreParty.setScore(8);
        Score scoreFly = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮날기 : ");
        scoreFly.setScore(7);
        Score scoreAutoSeedCount = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮심기 : ");
        scoreAutoSeedCount.setScore(6);
        Score scoreChatMode = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮채팅 : ");
        scoreChatMode.setScore(5);
        Score scoreTime = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮시각 : ");
        scoreTime.setScore(4);
        Score scoreNull = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + " ");
        scoreNull.setScore(3);
        Score scoreLevel = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮레벨 : ");
        scoreLevel.setScore(2);
        Score scoreExp = player.getScoreboard().getObjective("Playerboard").getScore(ChatColor.WHITE + "⎮경험치 : ");
        scoreExp.setScore(1);
    }
}
