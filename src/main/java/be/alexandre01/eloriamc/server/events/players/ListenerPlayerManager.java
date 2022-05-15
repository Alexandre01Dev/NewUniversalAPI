package be.alexandre01.eloriamc.server.events.players;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.events.nms.EventUtils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ListenerPlayerManager extends EventUtils {

    @Getter Multimap<Player, IPlayerEvent> listeners = ArrayListMultimap.create();
    HashMap<Class<? extends Event>, IPlayerEvent> events = new HashMap<>();

    public <T extends Event> IPlayerEvent<T> registerEvent(Class<T> t, String handler, Player player, IPlayerEvent<T> customEvent){

        if(!listeners.containsKey(customEvent.getEventClass())){
            events.put(customEvent.getEventClass(), customEvent);
            Listener registerEvent = new RegisterPlayerEvent<T>(customEvent.getEventClass(), handler, this);
            for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : SpigotPlugin.getInstance().getPluginLoader().createRegisteredListeners(registerEvent, SpigotPlugin.getInstance()).entrySet())
                getEventListeners(getRegistrationClass(customEvent.getEventClass())).registerAll(entry.getValue());
        }
        customEvent.setPlayer(player);
        listeners.put(player,customEvent);
        return customEvent;
    }


    public void removeEvent(Player player, IPlayerEvent customEvent){
        listeners.remove(player, customEvent);
    }
}
