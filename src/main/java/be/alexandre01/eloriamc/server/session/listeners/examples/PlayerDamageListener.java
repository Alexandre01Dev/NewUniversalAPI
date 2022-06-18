package be.alexandre01.eloriamc.server.session.listeners.examples;

import be.alexandre01.eloriamc.server.events.players.IPlayerEvent;
import be.alexandre01.eloriamc.server.session.Session;
import be.alexandre01.eloriamc.server.session.listeners.examples.damages.InstaKill;
import be.alexandre01.eloriamc.server.session.listeners.examples.damages.InstaKillByBlock;
import be.alexandre01.eloriamc.server.session.listeners.examples.damages.InstaKillByEntity;
import be.alexandre01.eloriamc.server.session.listeners.examples.damages.InstaKillOnVoid;
import be.alexandre01.eloriamc.utils.Tuple;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PlayerDamageListener {
    private List<Tuple<IPlayerEvent<?>,String>> iPlayerEvents = new ArrayList<>();
    @Setter private GlobalListener globalListener;



    public PlayerDamageListener(){

    }

    @SafeVarargs
    public final InstaKill addInstantKill(Session<?> session, Session<?> redirection, IListener<EntityDamageEvent>... iListener){
        InstaKill instaKill;
        iPlayerEvents.add(new Tuple<>(instaKill = new InstaKill(session,redirection,iListener),"getEntity"));
        instaKill.setPlayerDamageListener(this);
        return instaKill;
    }

    @SafeVarargs
    public final InstaKillOnVoid addInstantKillOnVoid(Session<?> session, Session<?> redirection, int height, IListener<PlayerMoveEvent>... iListener){
        InstaKillOnVoid instaKillOnVoid;
        iPlayerEvents.add(new Tuple<>(instaKillOnVoid = new InstaKillOnVoid(session,redirection,height,iListener),"getPlayer"));
        instaKillOnVoid.setPlayerDamageListener(this);
        return instaKillOnVoid;
    }

    @SafeVarargs
    public final InstaKillByEntity addInstantKillByEntity(Session<?> session, Session<?> redirection, IListener<EntityDamageByEntityEvent>... iListener){
        InstaKillByEntity instaKillByEntity;
        iPlayerEvents.add(new Tuple<>(instaKillByEntity = new InstaKillByEntity(session,redirection,iListener),"getEntity"));
        instaKillByEntity.setPlayerDamageListener(this);
        return instaKillByEntity;
    }
    @SafeVarargs
    public final InstaKillByBlock addInstantKillByBlock(Session<?> session, Session<?> redirection, IListener<EntityDamageByBlockEvent>... iListener){
        InstaKillByBlock instaKillByBlock;
        iPlayerEvents.add(new Tuple<>(instaKillByBlock = new InstaKillByBlock(session,redirection,iListener),"getEntity"));
        instaKillByBlock.setPlayerDamageListener(this);
        return instaKillByBlock;
    }


    public void build(Session<?> session){
        iPlayerEvents.forEach(iPlayerEvent -> {
            session.getListenerManager().registerPlayerEvent(iPlayerEvent.a(),iPlayerEvent.b());
        });
    }




    public interface  IListener<T extends Event> {
        boolean before(T event, Player player);
        void onKill(T event, Player player);
    }

    public interface GlobalListener{
        boolean before(Player player);
        void onKill(Player player);
    }
}
