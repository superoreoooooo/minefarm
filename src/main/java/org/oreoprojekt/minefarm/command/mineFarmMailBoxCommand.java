package org.oreoprojekt.minefarm.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.oreoprojekt.minefarm.Minefarm;
import org.oreoprojekt.minefarm.domain.mineFarm;
import org.oreoprojekt.minefarm.util.mineFarmMailSendSystem;

import java.io.IOException;
import java.util.List;

public class mineFarmMailBoxCommand implements CommandExecutor {

    private Minefarm plugin;

    public mineFarmMailBoxCommand(Minefarm plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length==0){
            getMyInventory(sender, "mailBox");
            return true;
        }

        if(args.length==1 && args[0].equalsIgnoreCase("create")){
            createInventory(sender, "mailBox");
            return true;
        }

        if(args.length==1 && args[0].equalsIgnoreCase("open")){
            getMyInventory(sender, "mailBox");
            return true;
        }

        if(args.length==3 && args[0].equalsIgnoreCase("openother")){
            getOtherInventory(sender, args[1],"mailBox");
            return true;
        }

        if(args.length==1 && (args[0].equalsIgnoreCase("saveAll"))){
            try {
                saveAll(sender);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
    

    private void saveAll(CommandSender sender) throws IOException {
        if(!checkPermission(sender,"administrator")){
            return ;
        }
        this.plugin.mineFarmTxtManager.saveAll(false);
    }

    public boolean checkPermission(CommandSender sender,String permission){
        if(sender.hasPermission(permission)){
            return true;
        }
        String message = "권한이 부족합니다.";
        sender.sendMessage(message);
        return false;
    }
    public boolean checkPlayer(CommandSender sender, Player player){
        if(player==null){
            sender.sendMessage("사람이 존재하지 않습니다.");
            return false;
        }
        return true;
    }
    public boolean isPlayer(CommandSender sender){
        if(sender instanceof Player) {
            return true;
        }
        sender.sendMessage("사람이 아닙니다.");
        return false;
    }
    private void getOtherInventory(CommandSender sender, String name, String inventoryName) {
        if(!checkPermission(sender,"administrator")){
            return ;
        }

        Player targetPlayer = Bukkit.getPlayer(name);
        if(!checkPlayer(sender,targetPlayer)){
            return;
        }

        if(isPlayer(sender)) {
            Player player = (Player) sender;
            mineFarm mineFarm = this.plugin.mineFarmTxtManager.getInventory(targetPlayer, inventoryName);
            openInventory(player,mineFarm,inventoryName);
        }
    }
    private void openInventory(Player player,mineFarm mineFarm,String inventoryName){
        if(mineFarm==null){
            player.sendMessage(ChatColor.RED + "우편함이 존재하지 않습니다. 왜일까요?");
            return;
        }
        player.openInventory(mineFarm.getInventory());
    }
    private void getMyInventory(CommandSender sender, String inventoryName) {
        if(isPlayer(sender)) {
            Player player = (Player) sender;
            mineFarm mineFarm = this.plugin.mineFarmTxtManager.getInventory(player, inventoryName);
            openInventory(player,mineFarm,inventoryName);
        }
    }

    private void createInventory(CommandSender sender, String inventoryName) {
        if(isPlayer(sender)) {
            Player player = (Player) sender;
            mineFarm mineFarm = this.plugin.mineFarmTxtManager.createInventory(player, inventoryName);
            if(mineFarm!=null){
                player.openInventory(mineFarm.getInventory());
            }
            return;
        }
    }
    
    private void getMyList(CommandSender sender) {
        if(isPlayer(sender)) {
            printList(sender,this.plugin.mineFarmTxtManager.getList((Player) sender));
            return;
        }
    }

    private void getOtherList(CommandSender sender, String name) {
        if(!checkPermission(sender,"administrator")){
            return;
        }
        Player targetPlayer = Bukkit.getPlayer(name);
        if(!checkPlayer(sender,targetPlayer)){
            return;
        }
        printList(sender,this.plugin.mineFarmTxtManager.getList(targetPlayer));
    }
    private void printList(CommandSender sender, List<String> list){
        sender.sendMessage(ChatColor.YELLOW +"lists :");
        if (list==null){
            return;
        }
        for(String inventoryName : list){
            sender.sendMessage(ChatColor.GOLD+inventoryName);
        }
    }
}
