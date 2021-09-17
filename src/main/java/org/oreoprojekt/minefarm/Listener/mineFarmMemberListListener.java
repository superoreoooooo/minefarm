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
import org.bukkit.inventory.meta.SkullMeta;
import org.oreoprojekt.minefarm.Minefarm;
import org.oreoprojekt.minefarm.util.mineFarmIslandUtil;

import java.util.Arrays;
import java.util.List;

public class mineFarmMemberListListener implements Listener {

    private mineFarmIslandUtil util;
    private Minefarm plugin;
    private Inventory inv;

    public mineFarmMemberListListener(Minefarm plugin) {
        this.plugin = plugin;
        this.util = new mineFarmIslandUtil(plugin);
        inv = Bukkit.createInventory(null, 9, "섬 멤버 리스트");
    }

    @EventHandler
    public void openMailBox(MemberListEvent e) {
        Player player = e.player;
        setItem(player);
        player.openInventory(inv);
    }

    public void setItem(Player player) {
        List<String> players = util.getIslandPlayers(player);
        inv.clear();
        for (int i = 0; i < players.size(); i++) {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta headMeta = (SkullMeta) head.getItemMeta();
            headMeta.setOwner(players.get(i));
            headMeta.setDisplayName(players.get(i));
            if (util.getIslandMembers(player).contains(players.get(i))) {
                headMeta.setLore(Arrays.asList(ChatColor.AQUA + "멤버 " + players.get(i)));
            }
            else {
                headMeta.setLore(Arrays.asList(ChatColor.GREEN + "게스트 " + players.get(i)));
            }
            head.setItemMeta(headMeta);
            inv.setItem(i, head);
        }
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

    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getInventory() == inv) {
            e.setCancelled(true);
        }
    }

    public static class MemberListEvent extends Event {
        private static final HandlerList HANDLERS = new HandlerList();

        public Player player;

        public MemberListEvent(Player player) {
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
