package org.oreoprojekt.minefarm.util;

import org.bukkit.entity.Player;
import org.oreoprojekt.minefarm.Minefarm;

public class mineFarmMoney {

    private Minefarm plugin;

    public mineFarmMoney(Minefarm plugin) {
        this.plugin = plugin;
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
}
