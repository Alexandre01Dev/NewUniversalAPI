package be.alexandre01.eloriamc.server.session.inventory.item;

import org.bukkit.inventory.meta.ItemMeta;

public class SpecialItem {

    private String itemName;
    private ItemMeta itemMeta;

    public SpecialItem(String itemName, ItemMeta itemMeta) {
        this.itemName = itemName;
        this.itemMeta = itemMeta;
    }
    boolean isDroppable;

    public boolean isDroppable() {
        return isDroppable;
    }
}
