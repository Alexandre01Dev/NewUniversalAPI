package be.alexandre01.eloriamc.server.session.listeners.examples;

import be.alexandre01.eloriamc.server.events.factories.IEvent;
import be.alexandre01.eloriamc.server.session.Session;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CancelClickInventory extends IEvent<InventoryClickEvent> {

    private Session session;
    public CancelClickInventory(Session session){
        this.session = session;
    }
    @Override
    public void onEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(session.containsPlayer(player)){
            event.setCancelled(true);
        }
    }
}
