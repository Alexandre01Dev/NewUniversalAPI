package be.alexandre01.universal.server.session.listeners.examples;

import be.alexandre01.universal.server.events.factories.IEvent;
import be.alexandre01.universal.server.session.Session;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;

public class CancelDropInventory extends IEvent<PlayerDropItemEvent> {

    private Session session;
    public CancelDropInventory(Session session){
        this.session = session;
    }
    @Override
    public void onEvent(PlayerDropItemEvent event) {
        Player player = (Player) event.getPlayer();

        if(session.containsPlayer(player)){
            event.setCancelled(true);
        }
    }
}
