package be.alexandre01.eloriamc.server.session.listeners.examples;

import be.alexandre01.eloriamc.server.events.players.IPlayerEvent;
import be.alexandre01.eloriamc.server.session.Session;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import static org.bukkit.Bukkit.getServer;

public class InstaKillOnVoid extends IPlayerEvent<PlayerMoveEvent> {
    Session session;
    Session redirection;
    double distance;
    public InstaKillOnVoid(Session session, Session redirection,double distance){
        this.session = session;
        this.redirection = redirection;
        this.distance = distance;
    }
    public InstaKillOnVoid(Session session, Session redirection){
        this.session = session;
        this.redirection = redirection;
        distance = 0.0;
    }

    @Override
    public void onPlayerEvent(PlayerMoveEvent event, Player player) {
        if(player.getLocation().getY() <= distance) {
            session.removePlayer(player);
            redirection.addPlayer(player);
        }
    }
}
