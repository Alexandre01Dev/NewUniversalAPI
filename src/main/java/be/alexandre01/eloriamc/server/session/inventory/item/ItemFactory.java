package be.alexandre01.eloriamc.server.session.inventory.item;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFactory {
    SpigotPlugin plugin;
    public ItemFactory(SpigotPlugin spigotPlugin){
        this.plugin = spigotPlugin;
    }

    public SpecialItem createItem(String itemName, ItemMeta itemMeta){
        return new SpecialItem(itemName, itemMeta);
    }
}
