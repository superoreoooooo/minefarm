package org.oreoprojekt.minefarm.Listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.oreoprojekt.minefarm.Minefarm;
import org.oreoprojekt.minefarm.util.mineFarmIslandUtil;

import java.util.Arrays;

public class mineFarmIslandLeftEventListener implements Listener {

    private mineFarmIslandUtil util;
    private Minefarm plugin;
    private Inventory inv;

    String leftTXT = ChatColor.RED + "떠나기";
    String cancelTXT = ChatColor.GREEN + "취소";

    public mineFarmIslandLeftEventListener(Minefarm plugin) {
        this.plugin = plugin;
        this.util = new mineFarmIslandUtil(plugin);

        inv = Bukkit.createInventory(null, 9, "섬 떠나기");

        ItemStack left = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta leftMeta = left.getItemMeta();
        leftMeta.setDisplayName(leftTXT);
        leftMeta.setLore(Arrays.asList(ChatColor.RED + "떠난 후에는 초대를 받아야만 돌아갈 수 있습니다."));
        left.setItemMeta(leftMeta);
        inv.setItem(2, left);

        ItemStack cancel = new ItemStack(Material.BARRIER, 1);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName(cancelTXT);
        cancelMeta.setLore(Arrays.asList(ChatColor.GREEN + "떠나기를 취소합니다."));
        cancel.setItemMeta(cancelMeta);
        inv.setItem(6, cancel);
    }

    @EventHandler
    public void openMailBox(OpenMailBox e) {
        e.player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getInventory() != inv) return;

        if (e.getClick().isShiftClick()) {
            e.setCancelled(true);
            return;
        }

        if (e.getClick().isLeftClick() || e.getClick().isRightClick()) {
            if (e.getClickedInventory() != inv) {
                e.setCancelled(true);
                return;
            }
        }
        if (e.getClick().isKeyboardClick()) {
            if (e.getClickedInventory() == inv) {
                e.setCancelled(true);
                return;
            }
        }

        e.setCancelled(true);

        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        if (clickedItem.getType() == Material.BARRIER || clickedItem.getType() == Material.NETHER_STAR) {
            if (clickedItem.getItemMeta().hasDisplayName()) {
                if (clickedItem.getItemMeta().getDisplayName().equals(leftTXT)) {
                    Location Spawn = new Location(player.getWorld(), -10000, 2, -10000);
                    player.teleport(Spawn);
                    player.sendMessage("섬을 떠났습니다.");
                    player.getOpenInventory().close();
                    return;
                }
                if (clickedItem.getItemMeta().getDisplayName().equals(cancelTXT)) {
                    player.getOpenInventory().close();
                }
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getInventory() == inv) {
            e.setCancelled(true);
        }
    }

    public static class OpenMailBox extends Event {
        private static final HandlerList HANDLERS = new HandlerList();

        public Player player;

        public OpenMailBox(Player player) {
            this.player = player;
        }

        public HandlerList getHandlers() {
            return HANDLERS;
        }

        public static HandlerList getHandlerList() {
            return HANDLERS;
        }
    }
}
