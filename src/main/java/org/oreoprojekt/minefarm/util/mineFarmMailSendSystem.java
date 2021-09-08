package org.oreoprojekt.minefarm.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.oreoprojekt.minefarm.Minefarm;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.util.Base64;
import java.util.Map;

public class mineFarmMailSendSystem {
    private Minefarm plugin;

    public mineFarmMailSendSystem(Minefarm plugin) {
        this.plugin = plugin;
    }

    mineFarmMailAlarmSystem alarmSystem = new mineFarmMailAlarmSystem();

    /**
    public void readmail(Player player, Player target, boolean isget) {
        plugin.data.getConfig().set("maillist." + "player is " + player.getUniqueId().toString() + ".target is " + target.getUniqueId().toString() + ".isget", isget);
        plugin.data.saveConfig();
    }**/

    public void addCount(Player player) {
        int cnt = getCount(player);
        cnt++;
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".mailcount", cnt);
        plugin.data.saveConfig();
    }

    public int getCount(Player player) {
        int cnt = 0;
        if (plugin.data.getConfig().contains("players." + player.getUniqueId().toString() + ".mailcount")) {
            cnt = plugin.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".mailcount");
        }
        return cnt;
    }


    public void sendmail(Player player, Player sender, Material type, ItemMeta meta, int itemcount, ItemStack item) throws IOException {
        int mailcount = getCount(player);
        boolean isget = false;
        if (meta == null) {
            player.sendMessage("오류");
            return;
        }
        String item1 = serialize(item);
        player.sendMessage(item1);
        plugin.data.getConfig().set("players_mails." + "player Name " + player.getUniqueId().toString() + ".mail." + mailcount + ".itemtype", type.toString());
        plugin.data.getConfig().set("players_mails." + "player Name " + player.getUniqueId().toString() + ".mail." + mailcount + ".itemcount", itemcount);
        plugin.data.getConfig().set("players_mails." + "player Name " + player.getUniqueId().toString() + ".mail." + mailcount + ".sentby", sender.getUniqueId().toString());
        plugin.data.getConfig().set("players_mails." + "player Name " + player.getUniqueId().toString() + ".mail." + mailcount + ".isget", isget);
        plugin.data.getConfig().set("Players_mailsToBase64." + player.getUniqueId().toString() + ".mails." + mailcount + ".toBASE64", item1);
        addCount(player);
        player.sendMessage(String.valueOf(mailcount));
        alarmSystem.alarm(player, sender);
        player.sendMessage(type.name() + "을 보냈습니다.");
    }

    public ItemStack getmail(Player player) throws IOException {
        int mailcount = getCount(player);
        mailcount--;
        String str = plugin.data.getConfig().getString("Players_mailsToBase64." + player.getUniqueId().toString() + ".mails." + mailcount + ".toBASE64");
        player.getInventory().addItem(deserialize(str));
        return null;
    }

    public String serialize(ItemStack item) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(item);
            dataOutput.close();
            Bukkit.getConsoleSender().sendMessage("fuck");
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }
    public ItemStack deserialize(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack item = (ItemStack) dataInput.readObject();
            dataInput.close();
            return item;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }
}
