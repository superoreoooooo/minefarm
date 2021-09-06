package org.oreoprojekt.minefarm.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class mineFarmMailAlarmSystem {
    public void alarm(Player player, Player target) {
        if (Bukkit.getOnlinePlayers().contains(target)) {
            target.sendMessage(player.getName() + "님이 우편을 보내셨습니다.");
        }
        else {
            player.sendMessage("알람이 가지 않았습니다.");
        }
    }
}
