package org.oreoprojekt.minefarm.util;

import org.bukkit.entity.Player;
import org.oreoprojekt.minefarm.Minefarm;

public class mineFarmFly {

    private Minefarm plugin;

    public mineFarmFly(Minefarm plugin) {
        this.plugin = plugin;
    }

    public int getFlyTime(Player player) { // fly 시간 얻기
        return flyTimeNow(player);
    }

    public void addFlyTime(Player player, int flyTimeToAdd) {
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".Flytime", flyTimeNow(player) + flyTimeToAdd);
        plugin.data.saveConfig();
    }

    public boolean useFlyTime(Player player, int FlyTimeUseSize) {
        if (flyTimeNow(player) >= FlyTimeUseSize) {
            plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".Flytime", flyTimeNow(player) - FlyTimeUseSize);
            plugin.data.saveConfig();
            return true;
        }
        else {
            return false;
        }
    }

    public void setFlyTime(Player player, int flyTimeToSet) {
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".Flytime", flyTimeNow(player) + flyTimeToSet);
        plugin.data.saveConfig();
    }

    public int flyTimeNow(Player player) {
        int flytimeNow = 0;
        if (plugin.data.getConfig().contains("players." + player.getUniqueId().toString() + ".Flytime")) {
            flytimeNow = plugin.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".Flytime");
        }
        return flytimeNow;
    }
}
