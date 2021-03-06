package org.oreoprojekt.minefarm.util;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.oreoprojekt.minefarm.Minefarm;

import java.util.List;

public class mineFarmIslandUtil {
    private final Minefarm plugin;

    public mineFarmIslandUtil(Minefarm plugin) {
        this.plugin = plugin;
    }

    public void printStatus(Player player) {
        if (!(plugin.islandManager.getConfig().getBoolean("playerNow." + player.getName() + ".isIn"))) {
            player.sendMessage(ChatColor.RED + "섬 안에 있지 않습니다.");
            return;
        }
        player.sendMessage(ChatColor.GREEN + "현재 섬 : " + getIslandNameNow(getIslandNow(player)));
    }

    public void setPlayer(Player player) {
        setPlayerLocation(player);
        setPlayerIn(player);
        setPlayerRain(player);
    }

    public int getRank(Player player) {
        return 0; // 시스템 미구현
        //return plugin.islandManager.getConfig().getInt("island" + getId(player) + "rank");
    }

    /****************************************************************************************************************/

    public void addIslandPlayers(Player player, OfflinePlayer playerToAdd) {
        List<String> PlayerList = plugin.islandManager.getConfig().getStringList("island." + getId(player) + ".players");
        if (PlayerList.size() >= 9) {
            player.sendMessage("플레이어를 더 이상 추가할 수 없습니다.");
            return;
        }
        if (PlayerList.contains(playerToAdd.getName())) {
            player.sendMessage("같은 플레이어를 더 이상 추가할 수 없습니다.");
            return;
        }
        PlayerList.add(playerToAdd.getName());
        plugin.islandManager.getConfig().set("island." + getId(player) + ".players", PlayerList);
        plugin.islandManager.saveConfig();
    }

    public void removeIslandPlayers(Player player, OfflinePlayer playerToRemove) {
        List<String> PlayerList = plugin.islandManager.getConfig().getStringList("island." + getId(player) + ".players");
        if (!(PlayerList.contains(playerToRemove.getName()))) {
            return;
        }
        PlayerList.remove(playerToRemove.getName());
        plugin.islandManager.getConfig().set("island." + getId(player) + ".players", null);
        plugin.islandManager.getConfig().set("island." + getId(player) + ".players", PlayerList);
        plugin.islandManager.saveConfig();
    }

    public List<String> getIslandPlayers(Player player) {
        return plugin.islandManager.getConfig().getStringList("island." + getId(player) + ".players");
    }

    /****************************************************************************************************************/

    public void addIslandMember(Player player, OfflinePlayer playerToAdd) {
        List<String> MemberList = plugin.islandManager.getConfig().getStringList("island." + getId(player) + ".members");
        if (MemberList.size() >= 9) {
            player.sendMessage("플레이어를 더이상 멤버로 추가할 수 없습니다.");
            return;
        }
        if (MemberList.contains(playerToAdd.getName())) {
            player.sendMessage("같은 플레이어를 더 이상 추가할 수 없습니다.");
            return;
        }
        MemberList.add(playerToAdd.getName());
        plugin.islandManager.getConfig().set("island." + getId(player) + ".members", MemberList);
        plugin.islandManager.saveConfig();
    }

    public void removeIslandMembers(Player player, OfflinePlayer playerToRemove) {
        List<String> MemberList = plugin.islandManager.getConfig().getStringList("island." + getId(player) + ".members");
        if (!(MemberList.contains(playerToRemove.getName()))) {
            player.sendMessage("이 플레이어는 멤버에 존재하지 않습니다..");
            return;
        }
        MemberList.remove(playerToRemove.getName());
        plugin.islandManager.getConfig().set("island." + getId(player) + ".members", null);
        plugin.islandManager.getConfig().set("island." + getId(player) + ".members", MemberList);
        plugin.islandManager.saveConfig();
    }

    public List<String> getIslandMembers(Player player) {
        return plugin.islandManager.getConfig().getStringList("island." + getId(player) + ".members");
    }

    /****************************************************************************************************************/

    public String getIslandOwner(Player player) {
        for (int count = 1; count <= IslandCount(); count++) {
            int[] origin = new int[3];
            origin[0] = plugin.islandManager.getConfig().getInt("island." + count + ".locationX");
            origin[1] = plugin.islandManager.getConfig().getInt("island." + count + ".locationY");
            origin[2] = plugin.islandManager.getConfig().getInt("island." + count + ".locationZ");
            if (player.getLocation().getX() >= origin[0] && player.getLocation().getX() <= origin[0] + 3200 && player.getLocation().getZ() >= origin[2] && player.getLocation().getZ() <= origin[2] + 3200) {
                return plugin.islandManager.getConfig().getString("island." + count + ".player");
            }
        }
        return "존재하지 않는 섬입니다.";
    }

    public void setPlayerLocation(Player player) {
        for (int count = 1; count <= IslandCount(); count++) {
            int[] origin = new int[3];
            origin[0] = plugin.islandManager.getConfig().getInt("island." + count + ".locationX");
            origin[1] = plugin.islandManager.getConfig().getInt("island." + count + ".locationY");
            origin[2] = plugin.islandManager.getConfig().getInt("island." + count + ".locationZ");
            if (player.getLocation().getX() >= origin[0] && player.getLocation().getX() <= origin[0] + 3200 && player.getLocation().getZ() >= origin[2] && player.getLocation().getZ() <= origin[2] + 3200) {
                plugin.islandManager.getConfig().set("playerNow." + player.getName() + ".now", count);
                plugin.islandManager.saveConfig();
                return;
            }
            else {
                plugin.islandManager.getConfig().set("playerNow." + player.getName() + ".now", 0);
                plugin.islandManager.saveConfig();
            }
        }
    }

    public void setPlayerIn(Player player) {
        for (int count = 1; count <= IslandCount(); count++) {
            int[] origin = new int[3];
            origin[0] = plugin.islandManager.getConfig().getInt("island." + count + ".locationX");
            origin[1] = plugin.islandManager.getConfig().getInt("island." + count + ".locationY");
            origin[2] = plugin.islandManager.getConfig().getInt("island." + count + ".locationZ");
            if (player.getLocation().getX() >= origin[0] && player.getLocation().getX() <= origin[0] + 3200 && player.getLocation().getZ() >= origin[2] && player.getLocation().getZ() <= origin[2] + 3200) {
                plugin.islandManager.getConfig().set("playerNow." + player.getName() + ".isIn", true);
                plugin.islandManager.saveConfig();
                return;
            }
            else {
                plugin.islandManager.getConfig().set("playerNow." + player.getName() + ".isIn", false);
                plugin.islandManager.saveConfig();
            }
        }
    }

    public boolean isRain(Player player) {
        return plugin.islandManager.getConfig().getBoolean("island." + getIslandNow(player) + ".isRain");
    }

    public void setRain(Player player, boolean isRain) { //true 이면 비가옴
        plugin.islandManager.getConfig().set("island." + getId(player) + ".isRain", isRain);
        plugin.islandManager.saveConfig();
    }

    public boolean isPlayerInIsland(Player player) {
        return plugin.islandManager.getConfig().getBoolean("playerNow." + player.getName() + ".isIn");
    }

    public int getIslandNow(Player player) {
        return plugin.islandManager.getConfig().getInt("playerNow." + player.getName() + ".now");
    }

    public void setPlayerRain(Player player) {
        if (isPlayerInIsland(player)) {
            if (isRain(player)) {
                player.setPlayerWeather(WeatherType.DOWNFALL);
            }
            else {
                player.setPlayerWeather(WeatherType.CLEAR);
            }
        }
        player.setPlayerWeather(WeatherType.CLEAR);
    }

    public void setIslandSpawn(Player player) {
        if (!(isInOwnIsland(player))) {
            player.sendMessage("자신의 섬에서만 설정이 가능합니다.");
            return;
        }

        int spawnX = (int) player.getLocation().getX();
        int spawnY = (int) player.getLocation().getY();
        int spawnZ = (int) player.getLocation().getZ();

        plugin.islandManager.getConfig().set("island." + getId(player) + ".spawnX", spawnX);
        plugin.islandManager.getConfig().set("island." + getId(player) + ".spawnY", spawnY);
        plugin.islandManager.getConfig().set("island." + getId(player) + ".spawnZ", spawnZ);

        plugin.islandManager.saveConfig();

        player.sendMessage("스폰 지점이 등록되었습니다.");
    }

    public String getIslandName(Player player) {
        int islandNum = plugin.islandManager.getConfig().getInt("player." + getIslandOwner(player));
        return plugin.islandManager.getConfig().getString("island." + islandNum + ".IslandName");
    }

    public String getIslandNameNow(int island) {
        return plugin.islandManager.getConfig().getString("island." + island + ".IslandName");
    }

    public void setIslandName(Player player, String name) {
        plugin.islandManager.getConfig().set("island." + getId(player) + ".IslandName", name);
        plugin.islandManager.saveConfig();
        player.sendMessage("섬의 이름이" + name + "으로 변경되었습니다.");
    }

    public int getId(OfflinePlayer player) {
        return plugin.islandManager.getConfig().getInt("player." + player.getName());
    }

    public boolean isInOwnIsland(Player player) {
        int[] range = getIslandPosition(player); //1번째는 x , 2번째는 z y는 무시s
        return player.getLocation().getX() >= range[0] && player.getLocation().getX() <= range[0] + 3200 && player.getLocation().getZ() >= range[2] && player.getLocation().getZ() <= range[2] + 3200;
    }

    public boolean isHaveIsland(Player player) {
        if (plugin.islandManager.getConfig().contains("player." + player.getName())) {
            player.sendMessage("섬이 이미 존재합니다.");
            return true;
        }
        return false;
    }

    public void createIsland(Player player) {

        int distanceZ = IslandCount() * 3200;
        int distanceY = 1;
        int distanceX = 0;

        if (IslandCount() >= 9000) {
            distanceZ = (IslandCount() - 9000) * 3200;
            distanceX = 3200;
        }
        if (IslandCount() >= 18000) {
            distanceZ = (IslandCount() - 18000) * 3200;
            distanceX = 6400;
        }
        if (IslandCount() >= 27000) {
            distanceZ = (IslandCount() - 27000) * 3200;
            distanceX = 9600;
        }
        if (IslandCount() >= 36000) {
            distanceZ = (IslandCount() - 36000) * 3200;
            distanceX = 12800;
        }
        if (IslandCount() >= 45000) {
            distanceZ = (IslandCount() - 45000) * 3200;
            distanceX = 16000;
        }

        String def = "unknown Island";

        plugin.islandManager.getConfig().set("player." + player.getName(), IslandCount() + 1);

        plugin.islandManager.getConfig().set("island." + getId(player) + ".locationX", distanceX);
        plugin.islandManager.getConfig().set("island." + getId(player) + ".locationY", distanceY);
        plugin.islandManager.getConfig().set("island." + getId(player) + ".locationZ", distanceZ);

        plugin.islandManager.getConfig().set("island." + getId(player) + ".isRain", false);

        plugin.islandManager.getConfig().set("island." + getId(player) + ".spawnX", distanceX + 1600);
        plugin.islandManager.getConfig().set("island." + getId(player) + ".spawnY", 3);
        plugin.islandManager.getConfig().set("island." + getId(player) + ".spawnZ", distanceZ + 1600);
        plugin.islandManager.getConfig().set("island." + getId(player) + ".resetCount", 0);
        plugin.islandManager.getConfig().set("island." + getId(player) + ".player", player.getName());
        plugin.islandManager.getConfig().set("island." + getId(player) + ".IslandName", def);

        plugin.islandManager.getConfig().set(".count", IslandCount() + 1);

        plugin.islandManager.saveConfig();

        setBorder(player);
        setGround(player);

        int homeX = distanceX + 1600;
        int homeZ = distanceZ + 1600;

        player.sendMessage(homeX + ", " + homeZ + " 에 당신의 섬이 생성되었습니다.");
    }

    public int IslandCount() {
        return plugin.islandManager.getConfig().getInt("count");
    }

    public int[] getIslandPosition(Player player) {
        int[] loc = new int[3];
        if (plugin.islandManager.getConfig().contains("island." + getId(player) + ".locationX")) {
            loc[0] = plugin.islandManager.getConfig().getInt("island." + getId(player) + ".locationX");
        }
        if (plugin.islandManager.getConfig().contains("island." + getId(player) + ".locationY")) {
            loc[1] = plugin.islandManager.getConfig().getInt("island." + getId(player) + ".locationY");
        }
        if (plugin.islandManager.getConfig().contains("island." + getId(player) + ".locationZ")) {
            loc[2] = plugin.islandManager.getConfig().getInt("island." + getId(player) + ".locationZ");
        }
        return loc;
    }

    public int[] getIslandSpawn(Player player) {
        int[] loc = new int[3];
        if (plugin.islandManager.getConfig().contains("island." + getId(player) + ".spawnX")) {
            loc[0] = plugin.islandManager.getConfig().getInt("island." + getId(player) + ".spawnX");
        }
        if (plugin.islandManager.getConfig().contains("island." + getId(player) + ".spawnY")) {
            loc[1] = plugin.islandManager.getConfig().getInt("island." + getId(player) + ".spawnY");
        }
        if (plugin.islandManager.getConfig().contains("island." + getId(player) + ".spawnZ")) {
            loc[2] = plugin.islandManager.getConfig().getInt("island." + getId(player) + ".spawnZ");
        }
        return loc;
    }


    public void setGround(Player player) {
        int[] origin = getIslandPosition(player);

        int originX = origin[0];
        int originZ = origin[2];

        int innerOriginX = originX + 1440;
        int innerOriginZ = originZ + 1440;

        Bukkit.broadcastMessage("originX : " + originX);
        Bukkit.broadcastMessage("originZ : " + originZ);
        Bukkit.broadcastMessage("inner originX : " + innerOriginX);
        Bukkit.broadcastMessage("inner originZ : " + innerOriginZ);

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

    public void resetIsland(Player player) {
        int[] origin = getIslandPosition(player);

        int originX = origin[0];
        int originY = origin[1];
        int originZ = origin[2];

        int innerOriginX = originX + 1440;
        int innerOriginZ = originZ + 1440;
        int changed = 0;

        player.sendMessage(ChatColor.RED + "originX : " + originX);
        player.sendMessage(ChatColor.RED + "originY : " + originY);
        player.sendMessage(ChatColor.RED + "originZ : " + originZ);
        player.sendMessage(ChatColor.RED + "inner originX : " + innerOriginX);
        player.sendMessage(ChatColor.RED + "inner originY : " + originY);
        player.sendMessage(ChatColor.RED + "inner originZ : " + innerOriginZ);

        Material block = Material.AIR;
        Location ground = new Location(player.getWorld(), innerOriginX, 1, innerOriginZ);

        for (int x = 0; x < 320; x++) {
            for (int z = 0; z < 320; z++) {
                for (int y = 1; y < 255; y++) {
                    if (!(ground.getBlock().isEmpty())) {
                        ground.getBlock().setType(block);
                        changed++;
                    }
                    ground.set(innerOriginX + x, originY + y, innerOriginZ + z);
                }
            }
        }
        ground.getBlock().setType(block);
        player.sendMessage("지우개 작동 완료");
        player.sendMessage(changed + "개의 블록이 제거되었습니다");
        int resetCnt = 0;
        if (plugin.islandManager.getConfig().contains("island." + getId(player) + ".resetCount")) {
            resetCnt = plugin.islandManager.getConfig().getInt("island." + getId(player) + ".resetCount");
        }
        plugin.islandManager.getConfig().set("island." + getId(player) + ".resetCount", resetCnt + 1);
        plugin.islandManager.saveConfig();
        setGround(player);
    }
}
