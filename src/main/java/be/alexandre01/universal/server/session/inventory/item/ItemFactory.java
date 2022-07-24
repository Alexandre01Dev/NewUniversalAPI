package be.alexandre01.universal.server.session.inventory.item;

import be.alexandre01.universal.server.session.Session;
import be.alexandre01.universal.server.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ItemFactory {
        Session session;


    @Getter private HashMap<String, SessionItem> universalSpecialItems = new HashMap<>();
    @Getter private HashMap<String, SessionItem> specialByName = new HashMap<>();
    public ItemFactory(Session session){
        this.session = session;
        addItem(new SessionItem("waiting_game_session", new ItemBuilder(Material.IRON_SWORD).setDisplayName("§ctest").build(true)),true);
        this.session.getListenerManager().registerEvent(new ItemInteractEvent(session,this));
    }

    public SessionItem createItem(String itemName, ItemStack itemStack){
        return new SessionItem(itemName, itemStack);
    }

    public SessionItem addItem(SessionItem sessionItem, boolean register) {
        System.out.println("addItem: " + sessionItem.getItemName());
        System.out.println(this.toString());
        if(sessionItem.isUniversal())
            System.out.println("isUniversal");
            universalSpecialItems.put(sessionItem.getItemStack().getItemMeta().getDisplayName(), sessionItem);
        if(register){
            specialByName.put(sessionItem.getItemName(), sessionItem);
        }
        return sessionItem;
    }

    public void getByName(String itemName){
        specialByName.get(itemName);
    }


    public boolean isUniversal(String itemName){
        return universalSpecialItems.containsKey(itemName);
    }
    public SessionItem getByDisplayName(String displayName){
        return universalSpecialItems.get(displayName);
    }
}
