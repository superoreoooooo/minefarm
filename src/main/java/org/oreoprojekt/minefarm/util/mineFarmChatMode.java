package org.oreoprojekt.minefarm.util;

import org.bukkit.entity.Player;
import org.oreoprojekt.minefarm.Minefarm;

public class mineFarmChatMode {

    private Minefarm plugin;

    public mineFarmChatMode(Minefarm plugin) {
        this.plugin = plugin;
    }

    public String getChatMode(Player player) { // 현재 채팅 모드를 리턴
        return ChatModeNow(player);
    }

    public void setChatMode(Player player, String ChatModeToSet) {
        plugin.data.getConfig().set("players." + player.getUniqueId().toString() + ".ChatMode", ChatModeToSet);
        plugin.data.saveConfig();
    }

    public String ChatModeNow(Player player) {
        String ChatModenow = null;
        if (plugin.data.getConfig().contains("players." + player.getUniqueId().toString() + ".ChatMode")) {
            ChatModenow = plugin.data.getConfig().getString("players." + player.getUniqueId().toString() + ".ChatMode");
        }
        return ChatModenow;
    }
}
