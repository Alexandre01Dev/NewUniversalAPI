package be.alexandre01.eloriamc.server.session.inventory.item;

import be.alexandre01.eloriamc.server.events.factories.IEvent;
import be.alexandre01.eloriamc.server.session.Session;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemInteractEvent extends IEvent<PlayerInteractEvent> {
    Session session;
    ItemFactory itemFactory;
    public ItemInteractEvent(Session<?> session,ItemFactory itemFactory) {
        this.session = session;
        this.itemFactory = itemFactory;
    }
    @Override
    public void onEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(session.containsPlayer(player)){
            ItemStack item = event.getItem();
            if(item == null) return;

            ItemMeta itemMeta = item.getItemMeta();
            if(itemMeta.hasDisplayName()){
            if(session.getItemFactory().isUniversal(itemMeta.getDisplayName())){
                SessionItem sessionItem = session.getItemFactory().getByDisplayName(itemMeta.getDisplayName());
                sessionItem.getItemInteractEvent().onEvent(event);
            }
            }
        }

    }
}
