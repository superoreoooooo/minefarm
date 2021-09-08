package org.oreoprojekt.minefarm.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.oreoprojekt.minefarm.Minefarm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    public byte[] serialize(ItemStack itemStack) throws IOException {
        if(itemStack==null) return null;
        Map<String, Object> serialize = itemStack.serialize();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BukkitObjectOutputStream oos = new BukkitObjectOutputStream(baos);
        oos.writeObject(serialize);
        oos.close();
        baos.close();
        return Base64.getEncoder().encode(baos.toByteArray());
    }

    public ItemStack deserialize(String data) throws IOException, ClassNotFoundException {
        if(data.equalsIgnoreCase("")){
            return null;
        }
        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(data));
            BukkitObjectInputStream ois = new BukkitObjectInputStream(bais);

            Map<String, Object> serialize = (Map<String, Object>) ois.readObject();
            ois.close();
            bais.close();
            return ItemStack.deserialize(serialize);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void sendmail(Player player, Player sender, Material type, ItemMeta meta, int itemcount, ItemStack item) throws IOException {
        int mailcount = getCount(player);
        boolean isget = false;
        if (meta == null) {
            player.sendMessage("오류");
            return;
        }
        plugin.data.getConfig().set("mails." + "player Name " + player.getUniqueId().toString() + ".mail." + mailcount + ".itemtype", type.toString());
        //plugin.data.getConfig().set("mails." + "player Name " + player.getUniqueId().toString() + ".mail." + mailcount + ".itemmeta", meta.toString());
        plugin.data.getConfig().set("mails." + "player Name " + player.getUniqueId().toString() + ".mail." + mailcount + ".itemcount", itemcount);
        plugin.data.getConfig().set("mails." + "player Name " + player.getUniqueId().toString() + ".mail." + mailcount + ".sentby", sender.getUniqueId().toString());
        plugin.data.getConfig().set("mails." + "player Name " + player.getUniqueId().toString() + ".mail." + mailcount + ".isget", isget);
        plugin.data.saveConfig();
        addCount(player);
        player.sendMessage(String.valueOf(mailcount));
        alarmSystem.alarm(player, sender);
        player.sendMessage(type.name() + "을 보냈습니다.");
    }

    public ItemStack getmail(Player player) throws IOException, ClassNotFoundException {
        int mailcount = getCount(player);
        mailcount--;
        return null;
    }
}
