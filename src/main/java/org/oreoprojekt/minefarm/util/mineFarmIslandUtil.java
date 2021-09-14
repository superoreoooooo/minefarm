package org.oreoprojekt.minefarm.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.oreoprojekt.minefarm.Minefarm;

public class mineFarmIslandUtil {
    private final Minefarm plugin;

    public BossBar bar;

    public mineFarmIslandUtil(Minefarm plugin) {
        this.plugin = plugin;
    }

    public String getIslandName(Player player) {
        return plugin.IslandManager.getConfig().getString("Islands." + player.getName() + ".IslandName");
    }

    public void setIslandSpawn(Player player) {
        if (!(isInOwnIsland(player))) {
            player.sendMessage("자신의 섬에서만 설정이 가능합니다.");
            return;
        }

        int spawnX = (int) player.getLocation().getX();
        int spawnY = (int) player.getLocation().getY();
        int spawnZ = (int) player.getLocation().getZ();

        plugin.IslandManager.getConfig().set("Islands." + player.getName() + ".spawnX", spawnX);
        plugin.IslandManager.getConfig().set("Islands." + player.getName() + ".spawnY", spawnY);
        plugin.IslandManager.getConfig().set("Islands." + player.getName() + ".spawnZ", spawnZ);

        plugin.IslandManager.saveConfig();

        player.sendMessage("스폰 지점이 등록되었습니다.");
    }

    public void setIslandName(Player player, String name) {
        plugin.IslandManager.getConfig().set("Islands." + player.getName() + ".IslandName", name);

        plugin.IslandManager.saveConfig();
    }


    public boolean isInOwnIsland(Player player) {
        int[] range = getIslandPosition(player); //1번째는 x , 2번째는 z y는 무시

        if (player.getLocation().getX() >= range[0] && player.getLocation().getX() <= range[0] + 3200) {
            if (player.getLocation().getZ() >= range[2] && player.getLocation().getZ() <= range[2] + 3200) {
                player.sendMessage("테스트용");
                return true;
            }
            return false;
        }
        else {
            return false;
        }
    }

    public boolean isHaveIsland(Player player) {
        if (plugin.IslandManager.getConfig().contains("Islands." + player.getName())) {
            player.sendMessage("섬이 이미 존재합니다.");
            return true;
        }
        return false;
    }

    public void createIsland(Player player) {
        int islandCnt = IslandCount();

        int distanceZ = islandCnt * 3200;
        int distanceX = 0;

        if (islandCnt >= 9000) {
            distanceZ = (islandCnt - 9000) * 3200;
            distanceX = 3200;
        }
        if (islandCnt >= 18000) {
            distanceZ = (islandCnt - 18000) * 3200;
            distanceX = 6400;
        }
        if (islandCnt >= 27000) {
            distanceZ = (islandCnt - 27000) * 3200;
            distanceX = 9600;
        }
        if (islandCnt >= 36000) {
            distanceZ = (islandCnt - 36000) * 3200;
            distanceX = 12800;
        }
        if (islandCnt >= 45000) {
            distanceZ = (islandCnt - 45000) * 3200;
            distanceX = 16000;
        }

        plugin.IslandManager.getConfig().set("Islands." + player.getName() + ".locationX", distanceX);
        plugin.IslandManager.getConfig().set("Islands." + player.getName() + ".locationZ", distanceZ);
        plugin.IslandManager.getConfig().set("Islands." + player.getName() + ".locationZ", distanceZ);

        plugin.IslandManager.getConfig().set("Islands." + player.getName() + ".spawnX", distanceX + 1600);
        plugin.IslandManager.getConfig().set("Islands." + player.getName() + ".spawnY", 3);
        plugin.IslandManager.getConfig().set("Islands." + player.getName() + ".spawnZ", distanceZ + 1600);

        plugin.IslandManager.getConfig().set(".count", IslandCount() + 1);

        plugin.IslandManager.saveConfig();

        setBorder(player);
        setFlatWorld(player);

        int homeX = distanceX + 1600;
        int homeZ = distanceZ + 1600;
        player.sendMessage(homeX + ", " + homeZ + " 에 당신의 섬이 생성되었습니다.");
    }

    public int IslandCount() {
        return plugin.IslandManager.getConfig().getInt("count");
    }

    public int[] getIslandPosition(Player player) {
        int[] loc = new int[3];
        if (plugin.IslandManager.getConfig().contains("Islands." + player.getName() + ".locationX")) {
            loc[0] = plugin.IslandManager.getConfig().getInt("Islands." + player.getName() + ".locationX");
        }
        if (plugin.IslandManager.getConfig().contains("Islands." + player.getName() + ".locationY")) {
            loc[1] = plugin.IslandManager.getConfig().getInt("Islands." + player.getName() + ".locationY");
        }
        if (plugin.IslandManager.getConfig().contains("Islands." + player.getName() + ".locationZ")) {
            loc[2] = plugin.IslandManager.getConfig().getInt("Islands." + player.getName() + ".locationZ");
        }
        return loc;
    }

    public int[] getIslandSpawn(Player player) {
        int[] loc = new int[3];
        if (plugin.IslandManager.getConfig().contains("Islands." + player.getName() + ".spawnX")) {
            loc[0] = plugin.IslandManager.getConfig().getInt("Islands." + player.getName() + ".spawnX");
        }
        if (plugin.IslandManager.getConfig().contains("Islands." + player.getName() + ".spawnY")) {
            loc[1] = plugin.IslandManager.getConfig().getInt("Islands." + player.getName() + ".spawnY");
        }
        if (plugin.IslandManager.getConfig().contains("Islands." + player.getName() + ".spawnZ")) {
            loc[2] = plugin.IslandManager.getConfig().getInt("Islands." + player.getName() + ".spawnZ");
        }
        return loc;
    }

    public void setFlatWorld(Player player) {
        int[] origin = getIslandPosition(player);

        int originX = origin[0];
        int originZ = origin[2];

        int innerOriginX = originX + 1440;
        int innerOriginZ = originZ + 1440;

        Bukkit.broadcastMessage("originX : " + originX);
        Bukkit.broadcastMessage("originY : " + originZ);
        Bukkit.broadcastMessage("inner originX : " + innerOriginX);
        Bukkit.broadcastMessage("inner originY : " + innerOriginZ);

        Material block = Material.GRASS_BLOCK;
        Location ground = new Location(player.getWorld(), innerOriginX, 1, innerOriginZ);

        for (int x = 0; x < 320; x++) {
            for (int z = 0; z < 320; z++) {
                ground.getBlock().setType(block);
                ground.set(innerOriginX + x, 1, innerOriginZ + z);
            }
        }
        ground.getBlock().setType(block);

        Location loc = new Location(player.getWorld(), originX, 0, originZ);
        loc.getBlock().setType(Material.DIAMOND_BLOCK);
        Location loc2 = new Location(player.getWorld(), originX + 3200 - 1, 0, originZ + 3200 - 1);
        loc2.getBlock().setType(Material.DIAMOND_BLOCK);
    }

    public void setBorder(Player player) {
        int[] origin = getIslandPosition(player);

        int originX = origin[0];
        int originZ = origin[2];

        int innerOriginX = originX + 1440;
        int innerOriginZ = originZ + 1440;

        Material block = Material.GOLD_BLOCK;

        Location ground = new Location(player.getWorld(), innerOriginX, 1, innerOriginZ);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 352; z++) {
                ground.getBlock().setType(block);
                ground.set(innerOriginX + x - 16, 1, innerOriginZ + z - 16);
            }
        }
        ground.getBlock().setType(block);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 352; z++) {
                ground.getBlock().setType(block);
                ground.set(innerOriginX + x + 320, 1, innerOriginZ + z - 16);
            }
        }
        ground.getBlock().setType(block);

        for (int x = 0; x < 320; x++) {
            for (int z = 0; z < 16; z++) {
                ground.getBlock().setType(block);
                ground.set(innerOriginX + x, 1, innerOriginZ + z - 16);
            }
        }
        ground.getBlock().setType(block);

        for (int x = 0; x < 320; x++) {
            for (int z = 0; z < 16; z++) {
                ground.getBlock().setType(block);
                ground.set(innerOriginX + x, 1, innerOriginZ + z + 320);
            }
        }
        ground.getBlock().setType(block);
    }
}
