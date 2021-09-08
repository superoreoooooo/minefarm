package org.oreoprojekt.minefarm.domain;

import org.bukkit.inventory.Inventory;

public class mineFarm {
    private Inventory inventory;
    private boolean isModify;
    private boolean isDelete;
    private int count;
    public mineFarm(Inventory inventory, boolean isModify) {
        this.inventory = inventory;
        this.isModify = isModify;
        this.isDelete = false;
        this.count=0;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean isModify() {
        return isModify;
    }

    public void setModify(boolean modify) {
        isModify = modify;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
