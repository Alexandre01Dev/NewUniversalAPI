package be.alexandre01.eloriamc.server.session.listeners.examples.damages;

import be.alexandre01.eloriamc.server.events.players.IPlayerEvent;
import be.alexandre01.eloriamc.server.session.Session;
import be.alexandre01.eloriamc.server.session.listeners.examples.PlayerDamageListener;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class InstaKillOnVoid extends IPlayerEvent<PlayerMoveEvent> {
    Session session;
    Session redirection;
    double height;

    @Setter
    PlayerDamageListener playerDamageListener = null;
    PlayerDamageListener.IListener[] listeners;
    public InstaKillOnVoid(Session session, Session redirection, double height, PlayerDamageListener.IListener<PlayerMoveEvent>... iListener){
        this.session = session;
        this.redirection = redirection;
        this.height = height;
        this.listeners = iListener;
    }
    public InstaKillOnVoid(Session session, Session redirection){
        this.session = session;
        this.redirection = redirection;
        height = 0.0;
        this.listeners = new PlayerDamageListener.IListener[0];
    }

    @Override
    public void onPlayerEvent(PlayerMoveEvent event, Player player) {
        for (PlayerDamageListener.IListener listener : listeners) {
            if(!listener.before(event,player)) return;
        }

        if(player.getLocation().getY() <= height) {
            session.removePlayer(player);
            redirection.addPlayer(player);
            for (PlayerDamageListener.IListener listener : listeners) {
                listener.onKill(event,player);
            }
            if(playerDamageListener != null){
                playerDamageListener.getGlobalListener().onKill(player);
            }
        }
    }
}
