package be.alexandre01.universal.server.session.listeners.examples.damages;

import be.alexandre01.universal.server.events.players.IPlayerEvent;
import be.alexandre01.universal.server.session.Session;
import be.alexandre01.universal.server.session.listeners.examples.PlayerDamageListener;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class InstaKillByEntity extends IPlayerEvent<EntityDamageByEntityEvent> {
    Session session;
    Session redirection;

    PlayerDamageListener.IListener[] listeners;
    @Setter
    PlayerDamageListener playerDamageListener = null;
    public InstaKillByEntity(Session session, Session redirection, PlayerDamageListener.IListener<EntityDamageByEntityEvent>... listeners){
        this.session = session;
        this.redirection = redirection;
        this.listeners = listeners;
    }


    @Override
    public void onPlayerEvent(EntityDamageByEntityEvent event, Player player) {
        if(playerDamageListener != null){
            playerDamageListener.getGlobalListener().before(player);
        }
        for (PlayerDamageListener.IListener listener : listeners) {
            if(!listener.before(event,player)) return;
        }
        player.sendMessage(""+(event.getDamage() - player.getHealth()));
        if( player.getHealth() - event.getDamage() <= 0){
            event.setCancelled(true);
            if(redirection != null){
                session.removePlayer(player);
                redirection.addPlayer(player);
            }
            for (PlayerDamageListener.IListener listener : listeners) {
                listener.onKill(event,player);
            }
            if(playerDamageListener != null){
                playerDamageListener.getGlobalListener().onKill(player);
            }
        }
    }


}
