package org.oreoprojekt.minefarm.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.oreoprojekt.minefarm.Minefarm;
import org.oreoprojekt.minefarm.util.mineFarmChatMode;

import java.util.ArrayList;
import java.util.HashMap;

public class mineFarmChatCommand implements CommandExecutor {

    private Minefarm plugin;

    private final mineFarmChatMode chatMode;
    
    public mineFarmChatCommand(Minefarm plugin) {
        this.plugin = plugin;
        this.chatMode = new mineFarmChatMode(plugin);
    }

    String Prefix = Minefarm.Prefix;
    String Enabled = ChatColor.translateAlternateColorCodes('&', Prefix + ChatColor.AQUA + "&6거리 채팅이 활성화 되었습니다.");
    String Disabled = ChatColor.translateAlternateColorCodes('&', Prefix + ChatColor.AQUA + "&6거리 채팅이 비활성화 되었습니다.");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("cm")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("콘솔에서는 사용하실 수 없습니다.");
                return false;
            }
            Player player = (Player) sender;

            if (args.length == 0) {
                player.sendMessage(Prefix + "원하시는 채팅 모드를 선택해 주세요.");
            }

            if (args.length == 1) {
                if (chatMode.getChatMode(player) != null) {
                    if (args[0].equalsIgnoreCase("All") || args[0].equalsIgnoreCase("A")) {
                        if (chatMode.getChatMode(player).equalsIgnoreCase("All")) {
                            player.sendMessage(Prefix + "이미 전체 채팅 모드입니다.");
                        }
                        else {
                            chatMode.setChatMode(player, "All");
                            player.sendMessage(Prefix + "전체 채팅 모드로 전환하셨습니다.");
                        }
                    }

                    if (args[0].equalsIgnoreCase("Farm") || args[0].equalsIgnoreCase("F")) {
                        if (chatMode.getChatMode(player).equalsIgnoreCase("Farm")) {
                            player.sendMessage(Prefix + "이미 팜 채팅 모드입니다.");
                        }
                        else {
                            chatMode.setChatMode(player, "Farm");
                            player.sendMessage(Prefix + "팜 채팅 모드로 전환하셨습니다.");
                        }
                    }

                    if (args[0].equalsIgnoreCase("Distance") || args[0].equalsIgnoreCase("D")) {
                        if (chatMode.getChatMode(player).equalsIgnoreCase("Distance")) {
                            player.sendMessage(Prefix + "이미 거리 채팅 모드입니다.");
                        }
                        else {
                            chatMode.setChatMode(player, "Distance");
                            player.sendMessage(Prefix + "거리 채팅 모드로 전환하셨습니다.");
                        }
                    }
                    player.sendMessage(chatMode.getChatMode(player));
                }
                else if (chatMode.getChatMode(player) == null) {
                    player.sendMessage("전체 채팅 모드로 변경되었습니다.");
                    chatMode.setChatMode(player, "All");
                }
            }
        }
        return false;
    }
}
