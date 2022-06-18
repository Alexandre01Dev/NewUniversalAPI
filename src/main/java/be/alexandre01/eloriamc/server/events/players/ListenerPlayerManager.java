package be.alexandre01.eloriamc.server.events.players;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.events.nms.EventUtils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ListenerPlayerManager extends EventUtils {


    HashMap<Class<? extends Event>, IPlayerEvent> events = new HashMap<>();


    @Getter HashMap<Player, ListenerPerPlayer> listenersPerPlayer = new HashMap<>();


    public <T extends Event> IPlayerEvent<T> registerEvent(Class<T> t, String handler, Player player, IPlayerEvent<T> customEvent, EventPriority eventPriority){

        if(!events.containsKey(customEvent.getEventClass())){
            events.put(customEvent.getEventClass(), customEvent);
            Listener registerEvent = new RegisterPlayerEvent(customEvent.getEventClass(), handler, this);
            for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : SpigotPlugin.getInstance().getEventsFactory().getCustomEventLoader().createCustomRegisteredListeners(registerEvent, SpigotPlugin.getInstance(),eventPriority).entrySet())
                getEventListeners(getRegistrationClass(customEvent.getEventClass())).registerAll(entry.getValue());
        }
        ListenerPerPlayer listenerPerPlayer;

        if(!listenersPerPlayer.containsKey(player)){
            listenersPerPlayer.put(player, listenerPerPlayer= new ListenerPerPlayer(player));
        }else {
            listenerPerPlayer = listenersPerPlayer.get(player);
        }
        customEvent.setPlayer(player);
        listenerPerPlayer.getListeners().add(customEvent);
        return customEvent;
    }

    public <T extends Event> IPlayerEvent<T> registerEvent(Class<T> t, String handler, Player player, IPlayerEvent<T> customEvent){
        return registerEvent(t,handler,player,customEvent,EventPriority.NORMAL);
    }


    public void removeEvent(Player player, IPlayerEvent customEvent){
        System.out.println(listenersPerPlayer.get(player).getListeners().contains(customEvent));
        System.out.println(">> "+ listenersPerPlayer.size());
        listenersPerPlayer.get(player).getListeners().remove(customEvent);
        System.out.println(">> "+ listenersPerPlayer.size());
    }
}
