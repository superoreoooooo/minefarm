package org.oreoprojekt.minefarm.util;

import org.bukkit.entity.Player;
import org.oreoprojekt.minefarm.Minefarm;

public class mineFarmLevel {
    private Minefarm plugin;

    public mineFarmLevel(Minefarm plugin) {
        this.plugin = plugin;
    }

    public int getlevel(Player player) { // level 값 불러옴
        return levelNow(player);
    }

    public void addlevel(Player player, int levelToAdd) {
        int level = levelNow(player) + levelToAdd;
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".level", level);
        plugin.data.saveConfig();
    }

    public void setlevel(Player player, int levelToSet) {
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".level", levelToSet);
        plugin.data.saveConfig();
    }

    public int levelNow(Player player) {
        int levelnow = 0;
        if (plugin.data.getConfig().contains("players." + player.getUniqueId().toString() + ".level")) {
            levelnow = plugin.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".level");
        }
        return levelnow;
    }
}
