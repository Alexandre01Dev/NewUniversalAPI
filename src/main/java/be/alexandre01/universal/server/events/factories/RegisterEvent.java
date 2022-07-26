package be.alexandre01.universal.server.events.factories;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.Collection;

public class RegisterEvent<T extends Event> implements Listener {


    private EventsFactory eventsFactory;



    public <T extends Class<? extends Event>> RegisterEvent(Class<? extends Event> event, EventsFactory eventsFactory) {
        System.out.println(event.getSimpleName());
        this.eventsFactory = eventsFactory;
    }



    @EventHandler
    public void onRegister(T event) {
        if(!eventsFactory.getEventHashMap().containsKey(event.getClass().getSimpleName())) return;


        Collection<IEvent<T>> iEvent = eventsFactory.getEventHashMap().get(event.getClass().getSimpleName());

       // System.out.println(newList.size());
        iEvent.forEach(e -> {
            if(e.getEventClass().getSimpleName().equals(event.getClass().getSimpleName())) {
                //System.out.println(e.getEventClass().getSimpleName() + " > " + event.getClass().getSimpleName());
                //System.out.println("Executing event: " + event.getClass().getSimpleName() + this);
                e.onEvent(event);
            }
        });
    }
}
