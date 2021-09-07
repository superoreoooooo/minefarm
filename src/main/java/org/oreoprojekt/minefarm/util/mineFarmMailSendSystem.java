package org.oreoprojekt.minefarm.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.oreoprojekt.minefarm.Minefarm;

public class mineFarmMailSendSystem {
    private Minefarm plugin;

    public mineFarmMailSendSystem(Minefarm plugin) {
        this.plugin = plugin;
    }

    mineFarmMailAlarmSystem alarmSystem = new mineFarmMailAlarmSystem();

    public void readmail(Player player, Player target, boolean isget) {
        plugin.data.getConfig().set("maillist." + "player is " + player.getUniqueId().toString() + ".target is " + target.getUniqueId().toString() + ".isget", isget);
        plugin.data.saveConfig();
    }

    public void addCount(Player player, Player target) {
        int cnt = getCount(player, target);
        cnt++;
        plugin.data.getConfig().set("players." + target.getUniqueId().toString() + ".mailcount", cnt);
        plugin.data.saveConfig();
    }

    public int getCount(Player player, Player target) {
        int cnt = 0;
        if (plugin.data.getConfig().contains("players." + target.getUniqueId().toString() + ".mailcount")) {
            cnt = plugin.data.getConfig().getInt("players." + target.getUniqueId().toString() + ".mailcount");
        }
        return cnt;
    }

    public void sendmail(Player player, Player target, Material type, ItemMeta meta, int itemcount) {
        int mailcount = getCount(player, target);
        boolean isget = false;
        if (meta == null) {
            player.sendMessage("오류");
            return;
        }
        plugin.data.getConfig().set("maillist." + "player is " + player.getUniqueId().toString() + ".target is " + target.getUniqueId().toString() + ".mail." + mailcount + ".itemtype", type.toString());
        plugin.data.getConfig().set("maillist." + "player is " + player.getUniqueId().toString() + ".target is " + target.getUniqueId().toString() + ".mail." + mailcount + ".itemmeta", meta.toString());
        plugin.data.getConfig().set("maillist." + "player is " + player.getUniqueId().toString() + ".target is " + target.getUniqueId().toString() + ".mail." + mailcount + ".itemcount", itemcount);
        plugin.data.getConfig().set("maillist." + "player is " + player.getUniqueId().toString() + ".target is " + target.getUniqueId().toString() + ".mail." + mailcount + ".isget", isget);
        plugin.data.saveConfig();
        addCount(player, target);
        player.sendMessage(String.valueOf(mailcount));
        alarmSystem.alarm(player, target);
        player.sendMessage(type.name() + "을 보냈습니다.");
    }

    public void getmail(Player player, Player target, Material type, ItemMeta meta, int itemcount) {

    }
}
