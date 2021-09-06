package org.oreoprojekt.minefarm.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.oreoprojekt.minefarm.Minefarm;
import org.oreoprojekt.minefarm.util.mineFarmChatMode;
import org.oreoprojekt.minefarm.util.mineFarmMailAlarmSystem;
import org.oreoprojekt.minefarm.util.mineFarmMailSendSystem;

public class mineFarmMailCommand implements CommandExecutor {

    private Minefarm plugin;

    private final mineFarmMailSendSystem mailSendSystem;

    public mineFarmMailCommand(Minefarm plugin) {
        this.plugin = plugin;
        this.mailSendSystem = new mineFarmMailSendSystem(plugin);
    }

    mineFarmMailAlarmSystem alarmSystem = new mineFarmMailAlarmSystem();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("콘솔에선 입력하지 말아주세요.");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("send")) {
                if (Bukkit.getOnlinePlayers().toString().contains(args[1])) {
                    Player target = Bukkit.getPlayer(args[1]);
                    sendItem(player, target);
                    return true;
                }
                else {
                    player.sendMessage("플레이어 이름을 입력해주세요.");
                    return false;
                }
            }
            else {
                player.sendMessage("/mail send 플레이어 이름");
                return false;
            }
        }
        else {
            player.sendMessage("/mail send 플레이어 이름");
            return false;
        }
    }

    public void sendItem(Player player, Player target) {
        ItemStack Item = player.getInventory().getItemInMainHand();
        ItemMeta meta = Item.getItemMeta();
        Material type = Item.getType();
        int count = Item.getAmount();
        alarmSystem.alarm(player, target);
        mailSendSystem.sendmail(player, target, type, meta, count);

        player.getInventory().removeItem(Item);
    }
}
