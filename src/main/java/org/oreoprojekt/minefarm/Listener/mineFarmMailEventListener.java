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
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.oreoprojekt.minefarm.Minefarm;
import org.oreoprojekt.minefarm.util.mineFarmMailSendSystem;

import java.io.IOException;
import java.util.Arrays;

public class mineFarmMailEventListener implements Listener {
    private final mineFarmMailSendSystem mineFarmMailSendSystem;
    private Minefarm plugin;

    public mineFarmMailEventListener(Minefarm plugin) {
        this.plugin = plugin;
        this.mineFarmMailSendSystem = new mineFarmMailSendSystem(plugin);
    }

    @EventHandler
    public void openMailBox(OpenMailBox e) throws IOException {
        Player player = e.player;
        player.openInventory(Bukkit.createInventory(player, 54, player.getName() + "의 메일함"));

        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = barrier.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "이미 받은 우편입니다.");
        itemMeta.setLore(Arrays.asList(ChatColor.GREEN + "우편함 초기화(모든 우편이 없어짐) 하는 방법", ChatColor.GREEN + "/우편함 리셋", ChatColor.RED + "(주의)삭제된 아이템은 복구가 불가능합니다."));
        barrier.setItemMeta(itemMeta);

        ItemStack window = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta itemMeta2 = window.getItemMeta();
        itemMeta2.setDisplayName(" ");
        window.setItemMeta(itemMeta2);

        ItemStack remove = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta itemMeta3 = remove.getItemMeta();
        itemMeta3.setDisplayName("누르시면 우편함이 초기화 됩니다.");
        remove.setItemMeta(itemMeta3);

        ItemStack getAll = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta itemMeta4 = getAll.getItemMeta();
        itemMeta4.setDisplayName("누르시면 우편함의 모든 아이템이 받아집니다.");
        itemMeta4.setLore(Arrays.asList(ChatColor.RED + "만약에 인벤토리에 공간이 없으면 아이템이 증발합니다."));
        getAll.setItemMeta(itemMeta4);

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwner(player.getName().toString());
        headMeta.setDisplayName(player.getName());
        head.setItemMeta(headMeta);

        for (int i = 0; i < 45; i++) {
            if (!mineFarmMailSendSystem.isGet(player, i)) {
                player.getOpenInventory().setItem(i, mineFarmMailSendSystem.getmail(player, i));
            }
            else {
                player.getOpenInventory().setItem(i, barrier);
            }
        }

        player.getOpenInventory().setItem(45, window);
        player.getOpenInventory().setItem(46, window);
        player.getOpenInventory().setItem(47, remove);
        player.getOpenInventory().setItem(48, window);
        player.getOpenInventory().setItem(49, head);
        player.getOpenInventory().setItem(50, window);
        player.getOpenInventory().setItem(51, getAll);
        player.getOpenInventory().setItem(52, window);
        player.getOpenInventory().setItem(53, window);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!(player.getOpenInventory().getTitle().contains("의 메일함"))) {
            return;
        }
        if (e.getClick().isShiftClick()) {
            player.sendMessage("시프트 클릭 캐치");
            e.setCancelled(true);
            return;
        }
        if (e.getClick().isLeftClick() || e.getClick().isRightClick() || e.getClick().isKeyboardClick()) {
            if (e.getClickedInventory() != null) {
                if (!(e.getClickedInventory().getType().equals(player.getOpenInventory().getType()))) {
                    e.setCancelled(true);
                    player.sendMessage("됬나요?");
                    return;
                }
            }
        }
        e.setCancelled(true);
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR || clickedItem.getType() == Material.BARRIER) return;
        if (clickedItem.getType() == Material.GREEN_STAINED_GLASS_PANE || clickedItem.getType() == Material.RED_STAINED_GLASS_PANE || clickedItem.getItemMeta().hasDisplayName()) {
            if (clickedItem.getItemMeta().getDisplayName().equals("누르시면 우편함이 초기화 됩니다.")) {
                mineFarmMailSendSystem.ClearMail(player);
                return;
            }
            if (clickedItem.getItemMeta().getDisplayName().equals("누르시면 우편함의 모든 아이템이 받아집니다.")) {
                return;
            }
            return;
        }
        player.getInventory().addItem(clickedItem);
        mineFarmMailSendSystem.setGet(player, e.getSlot());
        changeToNull(e.getSlot(), player);
        player.sendMessage("받기 처리 완료");
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getWhoClicked().getOpenInventory().getTitle().contains("의 메일함")) {
            e.setCancelled(true);
        }
    }

    public void changeToNull(int e, Player player) {
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = barrier.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "이미 받은 우편입니다.");
        itemMeta.setLore(Arrays.asList(ChatColor.GREEN + "우편함 초기화(모든 우편이 없어짐) 하는 방법", ChatColor.RED + "/우편함 리셋"));
        barrier.setItemMeta(itemMeta);
        player.getOpenInventory().setItem(e, barrier);
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
