package org.oreoprojekt.minefarm.util;

import org.bukkit.entity.Player;
import org.oreoprojekt.minefarm.Minefarm;

public class mineFarmAutoPlantCount {
    private Minefarm plugin;

    public mineFarmAutoPlantCount(Minefarm plugin) {
        this.plugin = plugin;
    }

    public int getAutoPlantCount(Player player) { // autoPlantCount는 자동심기 횟수
        return autoPlantCountNow(player);
    }

    public void addAutoPlantCount(Player player, int autoPlantCountToAdd) {
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".autoPlantCount", autoPlantCountNow(player) + autoPlantCountToAdd);
        plugin.data.saveConfig();
    }

    public boolean useAutoPlantCount(Player player, int autoPlantCountUseSize) {
        if (autoPlantCountNow(player) >= autoPlantCountUseSize) {
            plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".autoPlantCount", autoPlantCountNow(player) - autoPlantCountUseSize);
            plugin.data.saveConfig();
            return true;
        }
        else {
            return false;
        }
    }

    public void setAutoPlantCount(Player player, int autoPlantCountToSet) {
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".autoPlantCount", autoPlantCountNow(player) + autoPlantCountToSet);
        plugin.data.saveConfig();
    }

    public int autoPlantCountNow(Player player) {
        int autoPlantCountNow = 0;
        if (plugin.data.getConfig().contains("players." + player.getUniqueId().toString() + ".autoPlantCount")) {
            autoPlantCountNow = plugin.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".autoPlantCount");
        }
        return autoPlantCountNow;
    }
}
