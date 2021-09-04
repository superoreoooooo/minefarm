package org.oreoprojekt.minefarm.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.oreoprojekt.minefarm.Minefarm;

public class mineFarmScoreBoardTimer {

    private Minefarm plugin;

    public mineFarmScoreBoardTimer(Minefarm plugin) {
        this.plugin = plugin;
    }

    public mineFarmMoney money;
    public mineFarmCash cash;
    public mineFarmScoreBoard scoreBoard;

    public int task;

    public void timer(Player player, Boolean on) {
        if (on) {
            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    scoreBoard.scoreBoardList(player);
                }
            }, 0, 40); //2초마다 갱신 (20 * 초) 20틱이 1초
        }
        else {
            Bukkit.getScheduler().cancelTask(task);
        }
    }
}
