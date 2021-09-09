package org.oreoprojekt.minefarm.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.oreoprojekt.minefarm.Minefarm;
import org.oreoprojekt.minefarm.util.mineFarmMailSendSystem;

public class mineFarmMailClearCommand implements CommandExecutor {
    private Minefarm plugin;

    private final mineFarmMailSendSystem mailSendSystem;

    public mineFarmMailClearCommand(Minefarm plugin) {
        this.plugin = plugin;
        this.mailSendSystem = new mineFarmMailSendSystem(plugin);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            mailSendSystem.ClearMail(player);
            player.sendMessage("메일함이 초기화 되었습니다.");
            return true;
        }
        else {
            sender.sendMessage("어딜");
        }
        return false;
    }
}
