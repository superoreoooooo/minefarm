package org.oreoprojekt.minefarm.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.oreoprojekt.minefarm.Minefarm;

public class mineFarmMailSendSystem {
    private Minefarm plugin;

    public mineFarmMailSendSystem(Minefarm plugin) {
        this.plugin = plugin;
    }

    public void readmail(Player player, Player target, boolean isget) {
        plugin.data.getConfig().set("maillist." + "player is " + player.getUniqueId().toString() + ".target is " + target.getUniqueId().toString() + ".isget", isget);
        plugin.data.saveConfig();
    }

    public void sendmail(Player player, Player target, Material type, ItemMeta meta, int itemcount) {
        boolean isget = false;
        plugin.data.getConfig().set("maillist." + "player is " + player.getUniqueId().toString() + ".target is " + target.getUniqueId().toString() + ".itemtype", type.toString());
        plugin.data.getConfig().set("maillist." + "player is " + player.getUniqueId().toString() + ".target is " + target.getUniqueId().toString() + ".itemmeta", meta.toString());
        plugin.data.getConfig().set("maillist." + "player is " + player.getUniqueId().toString() + ".target is " + target.getUniqueId().toString() + ".itemcount", itemcount);
        plugin.data.getConfig().set("maillist." + "player is " + player.getUniqueId().toString() + ".target is " + target.getUniqueId().toString() + ".isget", isget);
        plugin.data.saveConfig();
    }
}
