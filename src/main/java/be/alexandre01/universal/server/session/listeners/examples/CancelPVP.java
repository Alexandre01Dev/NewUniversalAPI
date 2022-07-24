package be.alexandre01.universal.server.session.listeners.examples;

import be.alexandre01.universal.server.events.factories.IEvent;
import be.alexandre01.universal.server.session.Session;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CancelPVP extends IEvent<EntityDamageByEntityEvent> {

    private Session session;

    private boolean withOtherEntity;

    public CancelPVP(Session session, boolean withOtherEntity) {
        this.session = session;
        this.withOtherEntity = withOtherEntity;
    }


    @Override
    public void onEvent(EntityDamageByEntityEvent event) {
        if((!withOtherEntity && event.getEntity() instanceof Player && event.getDamager() instanceof Player) ||
                (withOtherEntity && event.getEntity() instanceof Player)){
                if(session.containsPlayer((Player) event.getEntity())){
                    event.setCancelled(true);
                }
        }
    }
}
