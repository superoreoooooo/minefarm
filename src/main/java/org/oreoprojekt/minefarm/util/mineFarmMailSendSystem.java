package org.oreoprojekt.minefarm.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.oreoprojekt.minefarm.Minefarm;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.util.Base64;

public class mineFarmMailSendSystem {
    private Minefarm plugin;

    public mineFarmMailSendSystem(Minefarm plugin) {
        this.plugin = plugin;
    }

    mineFarmMailAlarmSystem alarmSystem = new mineFarmMailAlarmSystem();

    public void ClearMail(Player player) {
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".mailcount", 0);
        plugin.data.getConfig().set("players_mails." + "player Name " + player.getUniqueId().toString(), null);
        plugin.data.getConfig().set("Players_mailsToBase64." + player.getUniqueId().toString(), null);
        plugin.data.saveConfig();
    }

    public void setGet(Player player, int mailnumber) {
        plugin.data.getConfig().set("players_mails." + "player Name " + player.getUniqueId().toString() + ".mail." + mailnumber + ".isget", true);
        plugin.data.saveConfig();
    }

    public boolean isGet(Player player, int mailnum) { // true이면 받은거임
        return plugin.data.getConfig().getBoolean("players_mails." + "player Name " + player.getUniqueId().toString() + ".mail." + mailnum + ".isget");
    }

    public void addCount(Player player) { // 메일 개수 추가
        int cnt = getCount(player);
        cnt++;
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".mailcount", cnt);
        plugin.data.saveConfig();
    }

    public int getCount(Player player) { // 메일 개수 가져옴
        int cnt = 0;
        if (plugin.data.getConfig().contains("players." + player.getUniqueId().toString() + ".mailcount")) {
            cnt = plugin.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".mailcount");
        }
        return cnt;
    }

    public void sendmail(Player player, Player target, Material type, ItemMeta meta, int itemcount, ItemStack item) throws IOException {
        int mailcount = getCount(target);
        boolean isget = false;
        if (meta == null) {
            player.sendMessage("오류");
            return;
        }
        String item1 = serialize(item);
        plugin.data.getConfig().set("players_mails." + "player Name " + target.getUniqueId().toString() + ".mail." + mailcount + ".itemtype", type.toString());
        plugin.data.getConfig().set("players_mails." + "player Name " + target.getUniqueId().toString() + ".mail." + mailcount + ".itemcount", itemcount);
        plugin.data.getConfig().set("players_mails." + "player Name " + target.getUniqueId().toString() + ".mail." + mailcount + ".sentby", player.getUniqueId().toString());
        plugin.data.getConfig().set("players_mails." + "player Name " + target.getUniqueId().toString() + ".mail." + mailcount + ".isget", isget);
        plugin.data.getConfig().set("Players_mailsToBase64." + target.getUniqueId().toString() + ".mails." + mailcount + ".toBASE64", item1);
        addCount(target);
        //.sendMessage(String.valueOf(mailcount));
        alarmSystem.alarm(player, target);
        player.sendMessage(type.name() + "을 보냈습니다.");
    }

    public ItemStack getmail(Player player, int mailcount) throws IOException {
        String str = plugin.data.getConfig().getString("Players_mailsToBase64." + player.getUniqueId().toString() + ".mails." + mailcount + ".toBASE64");
        return deserialize(str);
    }

    public String serialize(ItemStack item) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(item);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("asdf");
            return null;
        }
    }
    public ItemStack deserialize(String data) throws IOException {
        if (data == null) {
            return null;
        }
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
