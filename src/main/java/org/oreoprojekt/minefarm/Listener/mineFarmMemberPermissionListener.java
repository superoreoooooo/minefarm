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

public class mineFarmMemberPermissionListener implements Listener {

    private mineFarmIslandUtil util;
    private Minefarm plugin;
    private Inventory inv;

    public mineFarmMemberPermissionListener(Minefarm plugin) {
        this.plugin = plugin;
        this.util = new mineFarmIslandUtil(plugin);
        inv = Bukkit.createInventory(null, 9, "섬 멤버 리스트");
    }

    @EventHandler
    public void openMailBox(MemberPermissionSetEvent e) {
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
                headMeta.setLore(Arrays.asList(ChatColor.AQUA + "멤버 " + players.get(i), "우클릭으로 플레이어를 제거할 수 있습니다."));
            }
            else {
                headMeta.setLore(Arrays.asList(ChatColor.GREEN + "게스트 " + players.get(i), "우클릭으로 플레이어를 제거할 수 있습니다."));
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

        if (clickedItem.getItemMeta().hasDisplayName()) {
            String targetPlayer = clickedItem.getItemMeta().getDisplayName();
            Bukkit.getOfflinePlayer(targetPlayer);
            if (!(e.getClick().isRightClick())) {
                if (!(util.getIslandMembers(player).contains(targetPlayer))) {
                    player.sendMessage(targetPlayer + "이(가) 멤버 등급으로 바뀌었습니다.");
                    util.addIslandMember(player, Bukkit.getOfflinePlayer(targetPlayer));
                }
                else {
                    player.sendMessage(targetPlayer + "이(가) 게스트 등급으로 바뀌었습니다.");
                    util.removeIslandMembers(player, Bukkit.getOfflinePlayer(targetPlayer));
                }
                setItem(player);
            }
            else {
                if (util.getIslandMembers(player).contains(targetPlayer)) {
                    util.removeIslandMembers(player, Bukkit.getOfflinePlayer(targetPlayer));
                }
                else {
                    util.removeIslandPlayers(player, Bukkit.getOfflinePlayer(targetPlayer));
                }
                setItem(player);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getInventory() == inv) {
            e.setCancelled(true);
        }
    }

    public static class MemberPermissionSetEvent extends Event {
        private static final HandlerList HANDLERS = new HandlerList();

        public Player player;

        public MemberPermissionSetEvent(Player player) {
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
