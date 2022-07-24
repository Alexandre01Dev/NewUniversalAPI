package be.alexandre01.universal.server.events.players;

import be.alexandre01.universal.server.SpigotPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.lang.reflect.ParameterizedType;

public abstract class IPlayerEvent<T extends Event> {

    @Getter @Setter
    private Player player;
    @Getter
    private Class<T> eventClass;

    @Getter @Setter private String playerCall = null;
    @Getter @Setter private String cancelCall = null;


    public IPlayerEvent(){
        eventClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void registerToPlayer(Player player){
        this.player = player;
        System.out.println("Registering to player "+ this.getEventClass().getSimpleName() + " for " + player.getName());
        SpigotPlugin.getInstance().getListenerPlayerManager().registerEvent(getEventClass(), getPlayerCall(), player, this);

    }
    public abstract void onPlayerEvent(T event, Player player);

}
