package be.alexandre01.eloriamc.server.events.factories;

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

    public EventsFactory() {
        eventHashMap = new HashMap<>();
    }

    public <T extends Event> IEvent<T> fastRegisterEvent(Class<T> event, IEvent<T> iEvent) {
        if(!eventHashMap.containsKey(iEvent.getEventClass())){
            eventHashMap.put(iEvent.getEventClass(), iEvent);
            Listener registerEvent = new RegisterEvent<>(iEvent.getEventClass(), this);
            for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : GameAPI.get.getPluginLoader().createRegisteredListeners(registerEvent, GameAPI.get).entrySet())
                getEventListeners(getRegistrationClass(iEvent.getEventClass())).registerAll(entry.getValue());
        }
        return iEvent;
    }
}
