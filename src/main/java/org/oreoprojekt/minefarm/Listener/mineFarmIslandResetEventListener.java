package org.oreoprojekt.minefarm.Listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

public class mineFarmIslandResetEventListener implements Listener {

    private mineFarmIslandUtil util;
    private Minefarm plugin;
    private Inventory inv;

    String resetTXT = ChatColor.RED + "초기화(되돌릴 수 없습니다!)";
    String cancelTXT = ChatColor.GREEN + "취소";

    public mineFarmIslandResetEventListener(Minefarm plugin) {
        this.plugin = plugin;
        this.util = new mineFarmIslandUtil(plugin);

        inv = Bukkit.createInventory(null, 9, "초기화");

        ItemStack reset = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta resetMeta = reset.getItemMeta();
        resetMeta.setDisplayName(resetTXT);
        resetMeta.setLore(Arrays.asList(ChatColor.RED + "초기화 후에는 복구 불가능합니다."));
        reset.setItemMeta(resetMeta);
        inv.setItem(2, reset);

        ItemStack cancel = new ItemStack(Material.BARRIER, 1);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName(cancelTXT);
        cancelMeta.setLore(Arrays.asList(ChatColor.GREEN + "초기화 작업을 취소합니다."));
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
                if (clickedItem.getItemMeta().getDisplayName().equals(resetTXT)) {
                    player.sendMessage("섬이 초기화 되었습니다.");
                    player.getOpenInventory().close();
                    util.resetIsland(player);
                    return;
                }
                if (clickedItem.getItemMeta().getDisplayName().equals(cancelTXT)) {
                    player.sendMessage("초기화가 취소 되었습니다.");
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
