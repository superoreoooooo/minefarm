package org.oreoprojekt.minefarm.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.oreoprojekt.minefarm.Minefarm;
import org.oreoprojekt.minefarm.domain.mineFarm;

import java.io.*;
import java.util.*;

public class mineFarmTxtManager {
    private final Minefarm minefarm;
    private final HashMap<String, mineFarm> inventories;
    private final HashMap<String, List<String>> list;
    private final File folder;

    public mineFarmTxtManager(Minefarm minefarm, File folder) {
        this.minefarm = minefarm;
        this.inventories = new HashMap<>();
        this.list = new HashMap<>();
        this.folder = folder;
        if (!folder.exists()) {
            folder.mkdir();
            Bukkit.broadcastMessage("mkdir " + folder.getAbsolutePath());
        }
    }

    public Minefarm minefarm() {
        return minefarm;
    }
    public List<String> getList(Player player) {
        return this.list.get(player.getName().toLowerCase());
    }
    public mineFarm getInventory(Player player, String inventoryName) {
        String playerName = player.getName().toLowerCase();
        String json = playerName+"."+inventoryName;
        mineFarm inventory = this.inventories.get(json);
        if(inventory==null||inventory.isDelete()){
            return null;
        }
        inventory.setModify(true);
        return inventory;
    }
    public mineFarm createInventory(Player player, String inventoryName){
        mineFarm inventory = getInventory(player, inventoryName);
        if(inventory==null||inventory.isDelete()){
            if(getMaxInventory()<getAmountInventory(player)){
                player.sendMessage(ChatColor.RED + "인벤토리가 이미 존재합니다.");
                return null;
            }
            String playerName = player.getName().toLowerCase();
            String json = playerName+"."+inventoryName;
            List<String> stringList = this.list.get(playerName);
            if(stringList==null){
                stringList = new ArrayList<>();
            }
            stringList.add(inventoryName);
            this.list.put(playerName,stringList);
            String title = "TEST";
            inventory = new mineFarm(Bukkit.createInventory(player, 9,ChatColor.translateAlternateColorCodes('&',title)),true);
            inventories.put(json,inventory);
            return inventory;
        }
        player.sendMessage(ChatColor.RED + "asdfasd");
        return inventory;
    }
    public int getAmountInventory(Player player){
        List<String> list = this.list.get(player.getName().toLowerCase());
        if(list==null){
            return 0;
        }
        return list.size();
    }

    public int getMaxInventory(){
        return this.minefarm.getConfig().getInt("max-inventory");
    }

    public void load() throws IOException, ClassNotFoundException {
        boolean loadMessaging = true;
        String loadMessage = ChatColor.GREEN + "Hello this is TEST";

        for(File file :this.folder.listFiles()){
            if(file.getName().endsWith("txt")){
                /*
                file.getName() ex) KOO_MA.1.json
                 */
                if(loadMessaging){
                    String message = loadMessage.replace("#{fileName}",file.getName());
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',message));
                }

                String[] property = file.getName().split("\\.");
                List<String> stringList = this.list.get(property[0]);
                if(stringList==null){
                    stringList = new ArrayList<>();
                }
                stringList.add(property[1]);
                this.list.put(property[0], stringList);

                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                ItemStack[] itemStacks = new ItemStack[9];
                String reader="";
                for(int i = 0; i < 9; i++){
                    reader = bufferedReader.readLine();

                    if((reader) ==null){
                        break;
                    }
                    itemStacks[i] = deserialize(reader);
                }
                bufferedReader.close();
                fileInputStream.close();
                String title = "title";
                title = title.replace("#{inventoryName}",property[1]);
                Inventory inventory = Bukkit.createInventory(Bukkit.getPlayer(property[0]), 9, ChatColor.translateAlternateColorCodes('&',title));
                inventory.setContents(itemStacks);
                this.inventories.put(property[0]+"."+property[1],new mineFarm(inventory,true));
            }
        }
    }
    public byte[] serialize(ItemStack itemStack) throws IOException {
        if(itemStack==null) return null;
        Map<String, Object> serialize = itemStack.serialize();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BukkitObjectOutputStream oos = new BukkitObjectOutputStream(baos);
        oos.writeObject(serialize);
        oos.close();
        baos.close();
        return Base64.getEncoder().encode(baos.toByteArray());
    }
    public ItemStack deserialize(String data) throws IOException, ClassNotFoundException {
        if(data.equalsIgnoreCase("")){
            return null;
        }
        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(data));
            BukkitObjectInputStream ois = new BukkitObjectInputStream(bais);

            Map<String, Object> serialize = (Map<String, Object>) ois.readObject();
            ois.close();
            bais.close();
            return ItemStack.deserialize(serialize);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public void saveFile(File file, mineFarm mineFarm, boolean force) throws IOException {
        if(file.exists() && mineFarm.isDelete()){
            file.delete();
            return;
        }
        /**
         * 강제로 창고의 정보를 전부 저장하게 함.
         * 서버가 종료될 때, force가 true로 입력된다.
         */
        if((!force) &&! mineFarm.isModify()){
            /**
             * 창고의 정보가 일부 누락될 수 있기 때문에
             * 변경점이 발견되지 않아도 저장 n번당 한번씩 저장한다.
             */
            if(mineFarm.getCount()>=this.minefarm.getConfig().getInt("save-once-per-several-times")){
                mineFarm.setModify(true);
            }else{
                mineFarm.setCount(mineFarm.getCount()+1);
            }
            return;
        }
        mineFarm.setCount(1);
        mineFarm.setModify(false);
        FileOutputStream fos = new FileOutputStream(file);

        for(ItemStack itemStack :mineFarm.getInventory().getContents()){
            byte[] serializeByte = serialize(itemStack);
            if(serializeByte!=null){
                fos.write(serializeByte);
            }
            fos.write("\n".getBytes());
        }
        fos.close();
    }

    public void saveAll(boolean force) throws IOException {

        for (Map.Entry<String, mineFarm> entry : this.inventories.entrySet()) {
            mineFarm mineFarm = entry.getValue();
            File file = new File(folder.getPath()+"/"+entry.getKey()+".txt");
            saveFile(file, mineFarm, force);
        }
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "saved");
    }
}
