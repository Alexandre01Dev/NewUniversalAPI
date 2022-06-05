package be.alexandre01.eloriamc.server.session.listeners.examples;

import be.alexandre01.eloriamc.server.events.factories.IEvent;
import be.alexandre01.eloriamc.server.session.Session;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class CancelEntitySpawn extends IEvent<CreatureSpawnEvent> {

    @Override
    public void onEvent(CreatureSpawnEvent event) {
        if(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.EGG) {
            event.setCancelled(true);
        }
    }
}
