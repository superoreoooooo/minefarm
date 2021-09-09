package org.oreoprojekt.minefarm.Listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.oreoprojekt.minefarm.Minefarm;
import org.oreoprojekt.minefarm.util.mineFarmMailSendSystem;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class mineFarmMailEventListener implements Listener {
    private final Inventory inv;
    private final Inventory inv2;
    private final Inventory inv3;
    private final Inventory inv4;
    private final mineFarmMailSendSystem mineFarmMailSendSystem;
    private Minefarm plugin;

    public mineFarmMailEventListener(Minefarm plugin) {
        this.plugin = plugin;
        inv = Bukkit.createInventory(null, 54, "메일함");
        inv2 = Bukkit.createInventory(null, 54, "메일함");
        inv3 = Bukkit.createInventory(null, 54, "메일함");
        inv4 = Bukkit.createInventory(null, 54, "메일함");
        this.mineFarmMailSendSystem = new mineFarmMailSendSystem(plugin);
    }

    @EventHandler
    public void openMailBox(OpenMailBox e) throws IOException {
        Player player = e.player;
        player.openInventory(inv);

        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = barrier.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "이미 받은 우편입니다.");
        itemMeta.setLore(Arrays.asList(ChatColor.GREEN + "우편함 초기화(모든 우편이 없어짐) 하는 방법", ChatColor.RED + "/우편함 리셋"));
        barrier.setItemMeta(itemMeta);

        ItemStack window = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta itemMeta2 = window.getItemMeta();
        itemMeta2.setDisplayName(" ");
        window.setItemMeta(itemMeta2);

        ItemStack remove = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta itemMeta3 = remove.getItemMeta();
        itemMeta3.setDisplayName(" ");
        window.setItemMeta(itemMeta3);

        ItemStack getAll = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta itemMeta4 = getAll.getItemMeta();
        itemMeta4.setDisplayName(" ");
        window.setItemMeta(itemMeta4);

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwner(player.getName().toString());
        headMeta.setDisplayName("HEAD");
        head.setItemMeta(headMeta);

        for (int i = 0; i < 45; i++) {
            if (!mineFarmMailSendSystem.isGet(player, i)) {
                inv.setItem(i, mineFarmMailSendSystem.getmail(player, i));
            }
            else {
                inv.setItem(i, barrier);
            }
        }
        inv.setItem(45, window);
        inv.setItem(46, window);
        inv.setItem(47, remove);
        inv.setItem(48, window);
        inv.setItem(49, head);
        inv.setItem(50, window);
        inv.setItem(51, getAll);
        inv.setItem(52, window);
        inv.setItem(53, window);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {   //인벤토리 클릭 시
        if (e.getInventory() != inv) return;    //이 인벤토리를 클릭한게 아니라면 취소
        e.setCancelled(true);   //위치 변경 취소
        ItemStack clickedItem = e.getCurrentItem(); //클릭된 아이템
        //만약 클릭된 아이템이 없다면 취소
        if (clickedItem == null || clickedItem.getType() == Material.AIR || clickedItem.getType() == Material.BARRIER || clickedItem.getType() == Material.BLACK_STAINED_GLASS_PANE || clickedItem.getType() == Material.RED_STAINED_GLASS_PANE || clickedItem.getType() == Material.GREEN_STAINED_GLASS_PANE || clickedItem.getType() == Material.PLAYER_HEAD) return;
        Player player = (Player) e.getWhoClicked(); //클릭한 사람에게
        if (player.getInventory().contains(clickedItem)) {
            e.setCancelled(true);
            return;
        }
        mineFarmMailSendSystem.setGet(player, e.getSlot());
        player.getInventory().addItem(clickedItem); //아이템 지급
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = barrier.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "이미 받은 우편입니다.");
        itemMeta.setLore(Arrays.asList(ChatColor.GREEN + "우편함 초기화(모든 우편이 없어짐) 하는 방법", ChatColor.RED + "/우편함 리셋"));
        barrier.setItemMeta(itemMeta);
        inv.setItem(e.getSlot(), barrier);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) { //인벤토리 드래그 시
        if (e.getInventory() == inv) { //만약 드래그된 인벤토리가 이 인벤토리라면
            e.setCancelled(true); //위치 변경 취소
        }
    }

    public static class OpenMailBox extends Event {
        //이벤트를 수신하는 처리기들의 목록
        private static final HandlerList HANDLERS = new HandlerList();
        //이벤트를 일으킨 플레이어
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
