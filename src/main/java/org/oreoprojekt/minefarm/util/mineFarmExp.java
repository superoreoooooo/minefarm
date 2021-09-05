package org.oreoprojekt.minefarm.util;

import org.bukkit.entity.Player;
import org.oreoprojekt.minefarm.Minefarm;

public class mineFarmExp {
    private Minefarm plugin;
    private mineFarmLevel level;

    public mineFarmExp(Minefarm plugin) {
        this.plugin = plugin;
        this.level = new mineFarmLevel(plugin);
    }

    public int getExp(Player player) { // Exp 값을 불러옴
        return ExpNow(player);
    }

    public void addExp(Player player, int ExpToAdd) {
        if (ExpNow(player) + ExpToAdd <= 100) {
            plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".Exp", ExpNow(player) + ExpToAdd);
            plugin.data.saveConfig();
        }
        else {
            plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".Exp", ExpNow(player) + ExpToAdd - 100);
            plugin.data.saveConfig();
            level.addlevel(player, 1);
        }
    }

    public void setExp(Player player, int ExpToSet) {
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".Exp", ExpNow(player) + ExpToSet);
        plugin.data.saveConfig();
    }

    public int ExpNow(Player player) {
        int Expnow = 0;
        if (plugin.data.getConfig().contains("players." + player.getUniqueId().toString() + ".Exp")) {
            Expnow = plugin.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".Exp");
        }
        return Expnow;
    }
}
