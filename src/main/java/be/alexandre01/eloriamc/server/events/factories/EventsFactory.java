package be.alexandre01.eloriamc.server.events.factories;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.events.nms.EventUtils;
import be.alexandre01.eloriamc.server.events.players.RegisterPlayerEvent;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EventsFactory extends EventUtils {

    @Getter private HashMap<Class<? extends Event>, IEvent<?>> eventHashMap;
    private SpigotPlugin spigotPlugin;
    public EventsFactory() {
        eventHashMap = new HashMap<>();
        spigotPlugin = SpigotPlugin.getInstance();
    }

    public <T extends Event> IEvent<T> fastRegisterEvent(Class<?> event, IEvent<? extends Event> iEvent) {
        if(!eventHashMap.containsKey(iEvent.getEventClass())){
            eventHashMap.put(iEvent.getEventClass(), iEvent);
            Listener registerEvent = new RegisterEvent<>(iEvent.getEventClass(), this);
            for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : spigotPlugin.getPluginLoader().createRegisteredListeners(registerEvent, spigotPlugin).entrySet())
                getEventListeners(getRegistrationClass(iEvent.getEventClass())).registerAll(entry.getValue());
        }
        return (IEvent<T>) iEvent;
    }

    public void unregisterEvent(IEvent<? extends Event> iEvent){
        eventHashMap.remove(iEvent.getEventClass());
    }
}
