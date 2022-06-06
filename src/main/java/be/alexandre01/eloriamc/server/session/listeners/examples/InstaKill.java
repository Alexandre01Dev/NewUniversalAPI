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

    IListener[] listeners;
    public InstaKill(Session session,Session redirection,IListener... listeners){
        this.session = session;
        this.redirection = redirection;
        this.listeners = listeners;
    }


    @Override
    public void onPlayerEvent(EntityDamageEvent event, Player player) {
        for (IListener listener : listeners) {
            if(!listener.before(event,player)) return;
        }
        player.sendMessage(""+(event.getDamage() - player.getHealth()));
        if( player.getHealth() - event.getDamage() <= 0){
            event.setCancelled(true);
            session.removePlayer(player);
            redirection.addPlayer(player);
            for (IListener listener : listeners) {
                listener.onKill(event,player);
            }
        }
    }

    public interface IListener extends Listener {
        boolean before(EntityDamageEvent event, Player player);

        void onKill(EntityDamageEvent event, Player player);
    }
}
