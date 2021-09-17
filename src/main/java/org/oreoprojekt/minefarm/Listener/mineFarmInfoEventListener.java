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

public class mineFarmInfoEventListener implements Listener {

    private mineFarmIslandUtil util;
    private Minefarm plugin;
    private Inventory inv;

    String rankTXT = ChatColor.RED + "섬 랭킹";
    String membersTXT = ChatColor.GREEN + "멤버 리스트";
    String memberRankTXT = ChatColor.RED + "멤버 등급";

    public mineFarmInfoEventListener(Minefarm plugin) {
        this.plugin = plugin;
        this.util = new mineFarmIslandUtil(plugin);

        inv = Bukkit.createInventory(null, 9, "섬 정보");

        ItemStack rank = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta rankMeta = rank.getItemMeta();
        rankMeta.setDisplayName(rankTXT);
        rankMeta.setLore(Arrays.asList(ChatColor.RED + "섬 랭킹 시스템입니다.", ChatColor.RED + "아직 미구현"));
        rank.setItemMeta(rankMeta);
        inv.setItem(2, rank);

        ItemStack members = new ItemStack(Material.CREEPER_HEAD, 1);
        ItemMeta memberMeta = members.getItemMeta();
        memberMeta.setDisplayName(membersTXT);
        memberMeta.setLore(Arrays.asList(ChatColor.GREEN + "섬 멤버 리스트를 확인합니다."));
        members.setItemMeta(memberMeta);
        inv.setItem(4, members);

        ItemStack memberRank = new ItemStack(Material.OAK_SIGN, 1);
        ItemMeta memberRankMeta = memberRank.getItemMeta();
        memberRankMeta.setDisplayName(memberRankTXT);
        memberRankMeta.setLore(Arrays.asList(ChatColor.YELLOW + "섬 멤버들의 등급을 확인 및 조정합니다."));
        memberRank.setItemMeta(memberRankMeta);
        inv.setItem(6, memberRank);
    }

    @EventHandler
    public void openMailBox(openIslandConfig e) {

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
        if (clickedItem.getType() == Material.CREEPER_HEAD || clickedItem.getType() == Material.NETHER_STAR || clickedItem.getType() == Material.OAK_SIGN) {
            if (clickedItem.getItemMeta().hasDisplayName()) {
                if (clickedItem.getItemMeta().getDisplayName().equals(rankTXT)) {
                    player.sendMessage("아직 미구현 상태입니다.");
                    player.getOpenInventory().close();
                    return;
                }
                if (clickedItem.getItemMeta().getDisplayName().equals(membersTXT)) {
                    player.sendMessage("멤버 리스트 창으로 이동합니다...");
                    player.getOpenInventory().close();
                    mineFarmMemberListListener.MemberListEvent memberListEvent = new mineFarmMemberListListener.MemberListEvent(player);
                    Bukkit.getPluginManager().callEvent(memberListEvent);
                    return;
                }
                if (clickedItem.getItemMeta().getDisplayName().equals(memberRankTXT)) {
                    player.sendMessage("멤버 관리 창으로 이동합니다...");
                    player.getOpenInventory().close();
                    mineFarmMemberPermissionListener.MemberPermissionSetEvent MemberPermissionSetEvent = new mineFarmMemberPermissionListener.MemberPermissionSetEvent(player);
                    Bukkit.getPluginManager().callEvent(MemberPermissionSetEvent);
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

    public static class openIslandConfig extends Event {
        private static final HandlerList HANDLERS = new HandlerList();

        public Player player;

        public openIslandConfig(Player player) {
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
