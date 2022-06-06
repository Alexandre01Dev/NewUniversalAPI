package be.alexandre01.eloriamc.server.events.factories;

import be.alexandre01.eloriamc.server.events.players.ListenerPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public class RegisterEvent<T extends Event> implements Listener {


    private EventsFactory eventsFactory;



    public <T extends Class<? extends Event>> RegisterEvent(Class<? extends Event> event, EventsFactory eventsFactory) {
        System.out.println(event.getSimpleName());
        this.eventsFactory = eventsFactory;
    }



    @EventHandler
    public void onRegister(T event) {
        if(!eventsFactory.getEventHashMap().containsKey(event.getClass())) return;
        Collection<IEvent<T>> iEvent = eventsFactory.getEventHashMap().get(event.getClass());
        iEvent.forEach(iPlayerEvent -> {
            if(iPlayerEvent.getEventClass().equals(event.getClass())) {
                iPlayerEvent.onEvent(event);
            }
        });
    }
}
