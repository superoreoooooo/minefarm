package org.oreoprojekt.minefarm.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.oreoprojekt.minefarm.Minefarm;
import org.oreoprojekt.minefarm.util.mineFarmMailSendSystem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class mineFarmMailInventory implements Listener {
    private Inventory inv;    //보여줄 인벤토리 창//아이템에 붙을 인챈트 목록
    private mineFarmMailSendSystem mailSendSystem;
    private Minefarm plugin;

    public mineFarmMailInventory(Minefarm plugin) {
        this.plugin = plugin;
        this.mailSendSystem = new mineFarmMailSendSystem(plugin);

        inv = Bukkit.createInventory(null, 54, "메일함");  //인벤토리 초기화




        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);   //다이아몬드 검 생성
        //sword.addUnsafeEnchantments(enchantments);  //255레벨은 일반적인 수치가 아니니 addUnsafeEnchantments로 추가해야 한다.
        ItemMeta swordMeta = sword.getItemMeta();   //검의 메타데이터
        swordMeta.setDisplayName("개쩌는 칼");      //검의 이름 설정
        swordMeta.setLore(Arrays.asList("말 그대로 개쩌는 칼이다.")); //검의 설명 설정
        sword.setItemMeta(swordMeta);   //메타데이터 저장
        inv.addItem(sword); //인벤토리에 검 추가

        ItemStack bow = new ItemStack(Material.BOW, 1); //활 생성
        //bow.addUnsafeEnchantments(enchantments);    //255레벨은 일반적인 수치가 아니니 addUnsafeEnchantments로 추가해야 한다.
        ItemMeta bowMeta = bow.getItemMeta();   //활의 메타데이터
        bowMeta.setDisplayName("개쩌는 활");    //활의 이름 설정
        bowMeta.setLore(Arrays.asList("말 그대로 개쩌는 활이다."));   //활의 설명 설정
        bow.setItemMeta(bowMeta); //메타데이터 저장
        inv.addItem(bow);   //인벤토리에 활 추가
    }

    @EventHandler
    public void onOpenOpItemInventory(OpenOpItemInventoryEvent e) {
        e.player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {   //인벤토리 클릭 시
        if (e.getInventory() != inv) return;    //이 인벤토리를 클릭한게 아니라면 취소
        e.setCancelled(true);   //위치 변경 취소
        ItemStack clickedItem = e.getCurrentItem(); //클릭된 아이템
        //만약 클릭된 아이템이 없다면 취소
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        Player player = (Player) e.getWhoClicked(); //클릭한 사람에게
        player.getInventory().addItem(clickedItem); //아이템 지급
        player.closeInventory();    //인벤토리 닫기
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) { //인벤토리 드래그 시
        if (e.getInventory() == inv) {  //만약 드래그된 인벤토리가 이 인벤토리라면
            e.setCancelled(true);   //위치 변경 취소
        }
    }
}

class OpenOpItemInventoryEvent extends Event {
    //이벤트를 수신하는 처리기들의 목록
    private static final HandlerList HANDLERS = new HandlerList();
    //이벤트를 일으킨 플레이어
    public Player player;
    public OpenOpItemInventoryEvent(Player player) {
        this.player = player;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
