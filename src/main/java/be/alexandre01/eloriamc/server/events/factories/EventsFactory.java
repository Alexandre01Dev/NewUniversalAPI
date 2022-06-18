package be.alexandre01.eloriamc.server.events.factories;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.events.factories.eventloaders.PaperEventLoader;
import be.alexandre01.eloriamc.server.events.factories.eventloaders.SpigotEventLoader;
import be.alexandre01.eloriamc.server.events.nms.EventUtils;
import be.alexandre01.eloriamc.server.events.players.RegisterPlayerEvent;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EventsFactory<T extends Event> extends EventUtils {

    @Getter private Multimap<Class<T>, IEvent<T>> eventHashMap;
    private SpigotPlugin spigotPlugin;
    @Getter private CustomEventLoader customEventLoader;
    public EventsFactory() {
        eventHashMap = ArrayListMultimap.create();
        spigotPlugin = SpigotPlugin.getInstance();
        try {
            Class aikar =  Class.forName("co.aikar.timings.TimedEventExecutor");
            if(aikar != null){
                customEventLoader = new PaperEventLoader();
            }
        } catch (ClassNotFoundException e) {
            customEventLoader = new SpigotEventLoader();
        }
    }

    public IEvent<T> fastRegisterEvent(Class<T> event, IEvent<T> iEvent, EventPriority eventPriority) {
        if(!eventHashMap.containsKey(iEvent.getEventClass())){
            eventHashMap.put(iEvent.getEventClass(), iEvent);
            Listener registerEvent = new RegisterEvent<>(iEvent.getEventClass(), this);
            for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : spigotPlugin.getEventsFactory().getCustomEventLoader().createCustomRegisteredListeners(registerEvent, spigotPlugin,eventPriority).entrySet())
                getEventListeners(getRegistrationClass(iEvent.getEventClass())).registerAll(entry.getValue());
        }

        eventHashMap.put(iEvent.getEventClass(), iEvent);

        return (IEvent<T>) iEvent;
    }
    public IEvent<T> fastRegisterEvent(Class<T> event, IEvent<T> iEvent) {
        return fastRegisterEvent(event,iEvent,EventPriority.NORMAL);
    }
    public void unregisterEvent(IEvent<? extends Event> iEvent){
        eventHashMap.remove(iEvent.getEventClass(),iEvent);
    }

    public void  unregisterEvent(Class<? extends Event> event){
        eventHashMap.removeAll(event);
    }
}
