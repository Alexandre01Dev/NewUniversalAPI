package be.alexandre01.eloriamc.server.session.listeners.examples;

import be.alexandre01.eloriamc.server.events.factories.IEvent;
import be.alexandre01.eloriamc.server.session.Session;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class CancelEntityInteract extends IEvent<EntityInteractEvent> {

    private Session session;
    public CancelEntityInteract(Session session){
        this.session = session;
    }


    @Override
    public void onEvent(EntityInteractEvent event) {
        if(!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();

        if(session.containsPlayer(player)){
            event.setCancelled(true);
        }
    }
}
