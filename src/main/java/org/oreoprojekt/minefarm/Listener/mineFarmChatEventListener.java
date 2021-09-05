package org.oreoprojekt.minefarm.Listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.oreoprojekt.minefarm.Minefarm;
import org.oreoprojekt.minefarm.command.mineFarmChatCommand;
import org.oreoprojekt.minefarm.util.mineFarmChatMode;

public class mineFarmChatEventListener implements Listener {

    private mineFarmChatCommand chat;
    private Minefarm plugin;
    private mineFarmChatMode chatMode;

    public mineFarmChatEventListener(Minefarm plugin) {
        this.plugin = plugin;
        this.chat = new mineFarmChatCommand(plugin);
        this.chatMode = new mineFarmChatMode(plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        String msg = e.getMessage();

        String Prefix = ChatColor.AQUA + "[거리채팅]";
        String Prefix2 = ChatColor.WHITE + "[전체채팅]";
        String Prefix3 = ChatColor.YELLOW + "[팜채팅]";
        String Prefix4 = ChatColor.RED + "[MineFarmPlugin]";

        int distance = 20;
        if (chatMode.getChatMode(player) == null){
            e.setCancelled(true);
            player.sendMessage(Prefix4 + "오류가 발생했습니다. 자동으로 전체 채팅 모드로 전환합니다.");
            chatMode.setChatMode(player, "All");
            return;
        }
        if (chatMode.getChatMode(player).equalsIgnoreCase("Distance")) {
            e.setCancelled(true);
            for (Player target : Bukkit.getServer().getOnlinePlayers()) {
                if (target.getWorld().getEnvironment() == player.getWorld().getEnvironment()) {
                    double Loc = player.getLocation().distance(target.getLocation());
                    if (Loc <= distance) {
                        target.sendMessage(Prefix + " " + player.getDisplayName() + ChatColor.AQUA + " ▶ " + msg);
                    }
                }
            }
            Bukkit.getConsoleSender().sendMessage(Prefix + " " + player.getDisplayName() + ChatColor.AQUA + " ▶ " + msg);
        }

        else if (chatMode.getChatMode(player).equalsIgnoreCase("All")) {
            e.setCancelled(true);
            for (Player target : Bukkit.getServer().getOnlinePlayers()) {
                target.sendMessage(Prefix2 + " " + ChatColor.WHITE + player.getDisplayName() + " ▶ " + msg);
            }
            Bukkit.getConsoleSender().sendMessage(Prefix2 + " " + ChatColor.WHITE + player.getDisplayName() + " ▶ " + msg);
        }

        else if (chatMode.getChatMode(player).equalsIgnoreCase("Farm")) {
            e.setCancelled(true);
            for (Player target : Bukkit.getServer().getOnlinePlayers()) {
                target.sendMessage(Prefix3 + " " + ChatColor.WHITE + player.getDisplayName() + " ▶ " + msg + "팜채팅 기능 (아직)미구현");
            }
        }
    }
}
