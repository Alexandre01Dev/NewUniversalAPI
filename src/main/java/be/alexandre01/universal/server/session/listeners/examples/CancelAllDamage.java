package be.alexandre01.universal.server.session.listeners.examples;

import be.alexandre01.universal.server.events.factories.IEvent;
import be.alexandre01.universal.server.session.Session;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class CancelAllDamage extends IEvent<EntityDamageEvent> {

    private Session session;

    public CancelAllDamage(Session session) {
        this.session = session;
    }
    @Override
    public void onEvent(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player){
            if(session.containsPlayer((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
    }
}
