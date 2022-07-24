package be.alexandre01.universal.server.session.listeners.examples;

import be.alexandre01.universal.server.events.factories.IEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CancelEntitySpawn extends IEvent<CreatureSpawnEvent> {

    @Override
    public void onEvent(CreatureSpawnEvent event) {
        if(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.EGG) {
            event.setCancelled(true);
        }
    }
}
