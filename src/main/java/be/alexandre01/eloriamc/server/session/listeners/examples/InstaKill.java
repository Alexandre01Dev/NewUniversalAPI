package be.alexandre01.eloriamc.server.session.listeners.examples;

import be.alexandre01.eloriamc.server.events.players.IPlayerEvent;
import be.alexandre01.eloriamc.server.player.BasePlayer;
import be.alexandre01.eloriamc.server.session.Session;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class InstaKill extends IPlayerEvent<EntityDamageEvent> {
    Session session;
    Session redirection;
    public InstaKill(Session session,Session redirection){
        this.session = session;
        this.redirection = redirection;
    }


    @Override
    public void onPlayerEvent(EntityDamageEvent event, Player player) {
        if(event.getFinalDamage() - player.getHealth() < 0){
            event.setCancelled(true);
            session.removePlayer(player);
            redirection.addPlayer(player);
        }
    }
}
