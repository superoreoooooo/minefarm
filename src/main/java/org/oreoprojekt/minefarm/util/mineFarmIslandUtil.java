package org.oreoprojekt.minefarm.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.oreoprojekt.minefarm.Minefarm;

public class mineFarmIslandUtil {
    private Minefarm plugin;

    public mineFarmIslandUtil(Minefarm plugin) {
        this.plugin = plugin;
    }

    public Location getIsland(Player player) { //자신의 섬 얻기?
        return myIsland(player);
    }

    public boolean haveIsland(Player player) {
        if (plugin.IslandManager.getConfig().contains("Islands." + player.getUniqueId().toString())) {
            player.sendMessage("섬이 이미 존재합니다.");
            return true;
        }
        player.sendMessage("섬이 존재하지 않습니다.");
        return false;
    }

    public void createIsland(Player player) {
        int cnt = IslandCount();
        int distance = cnt * 3200;

        plugin.IslandManager.getConfig().set("Islands." + player.getUniqueId().toString() + ".location", distance);
        plugin.IslandManager.getConfig().set("Islands." + ".count", IslandCount() + 1);
        plugin.IslandManager.saveConfig();
        setBorder(player);
        setFlatWorld(player);
        player.sendMessage(distance + "에 당신의 섬이 생성되었습니다.");
    }

    public int IslandCount() {
        return plugin.IslandManager.getConfig().getInt("Islands." + ".count");
    }

    public Location myIsland(Player player) {
        Location loc = null;
        if (plugin.IslandManager.getConfig().contains("Islands." + player.getUniqueId().toString() + ".location")) {
            loc = plugin.IslandManager.getConfig().getLocation("Islands." + player.getUniqueId().toString() + ".location");
        }
        return loc;
    }

    public void setFlatWorld(Player player) {
        int cnt = IslandCount();
        int distance = cnt * 3200;
        int midpoint = distance + 1440;
        Location ground = new Location(player.getWorld(), midpoint, 1, midpoint);

        for (int x = 0; x < 320; x++) {
            for (int z = 0; z < 320; z++) {
                ground.getBlock().setType(Material.GRASS_BLOCK);
                ground.set(midpoint + x, 1, midpoint + z);
            }
        }
        ground.getBlock().setType(Material.GRASS_BLOCK);

        Location loc = new Location(player.getWorld(), distance, 0, 0);
        loc.getBlock().setType(Material.DIAMOND_BLOCK);
        Location loc2 = new Location(player.getWorld(), distance + 3200 - 1, 0, distance + 3200 - 1);
        loc2.getBlock().setType(Material.DIAMOND_BLOCK);
    }

    public void setBorder(Player player) {
        int cnt = IslandCount();
        int distance = cnt * 3200;
        int midpoint = distance + 1440;

        Location ground = new Location(player.getWorld(), midpoint, 1, midpoint);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 352; z++) {
                ground.getBlock().setType(Material.STONE);
                ground.set(midpoint + x - 16, 1, midpoint + z - 16);
            }
        }
        ground.getBlock().setType(Material.STONE);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 352; z++) {
                ground.getBlock().setType(Material.STONE);
                ground.set(midpoint + x + 320, 1, midpoint + z - 16);
            }
        }
        ground.getBlock().setType(Material.STONE);

        for (int x = 0; x < 320; x++) {
            for (int z = 0; z < 16; z++) {
                ground.getBlock().setType(Material.STONE);
                ground.set(midpoint + x, 1, midpoint + z - 16);
            }
        }
        ground.getBlock().setType(Material.STONE);

        for (int x = 0; x < 320; x++) {
            for (int z = 0; z < 16; z++) {
                ground.getBlock().setType(Material.STONE);
                ground.set(midpoint + x, 1, midpoint + z + 320);
            }
        }
        ground.getBlock().setType(Material.STONE);
    }
}
