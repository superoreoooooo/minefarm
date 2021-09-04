package org.oreoprojekt.minefarm.util;

import org.bukkit.entity.Player;
import org.oreoprojekt.minefarm.Minefarm;

public class mineFarmCash {

    private Minefarm plugin;

    public mineFarmCash(Minefarm plugin) {
        this.plugin = plugin;
    }

    public int getCash(Player player) { // cash는 현금
        return cashNow(player);
    }

    public void addCash(Player player, int cashToAdd) {
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".cash", cashNow(player) + cashToAdd);
        plugin.data.saveConfig();
    }

    public boolean useCash(Player player, int cashUseSize) {
        if (cashNow(player) >= cashUseSize) {
            plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".cash", cashNow(player) - cashUseSize);
            plugin.data.saveConfig();
            return true;
        }
        else {
            return false;
        }
    }

    public void setCash(Player player, int cashToSet) {
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".cash", cashNow(player) + cashToSet);
        plugin.data.saveConfig();
    }

    public int cashNow(Player player) {
        int cashnow = 0;
        if (plugin.data.getConfig().contains("players." + player.getUniqueId().toString() + ".cash")) {
            cashnow = plugin.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".cash");
        }
        return cashnow;
    }
}
