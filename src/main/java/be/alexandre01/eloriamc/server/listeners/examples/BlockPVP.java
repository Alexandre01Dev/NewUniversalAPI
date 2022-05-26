package be.alexandre01.eloriamc.server.listeners.examples;

import be.alexandre01.eloriamc.server.events.players.IPlayerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class BlockPVP extends IPlayerEvent<EntityDamageEvent> {
    @Override
    public void onPlayerEvent(EntityDamageEvent event, Player player) {


    }
}
