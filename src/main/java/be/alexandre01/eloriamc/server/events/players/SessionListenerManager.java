package be.alexandre01.eloriamc.server.events.players;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.events.factories.IEvent;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

public class SessionListenerManager {

    @Getter private ArrayList<IPlayerEvent<?>> playerEvents = new ArrayList<>();
    @Getter private ArrayList<IEvent<?>> events = new ArrayList<>();

    public SessionListenerManager() {
        playerEvents = new ArrayList<>();
    }

    public void registerPlayerEvent(IPlayerEvent<? extends Event> playerEvent,String callPlayer) {
        playerEvent.setPlayerCall(callPlayer);
        playerEvents.add(playerEvent);
    }

    public void registerEvent(IEvent<? extends Event> event) {
        events.add(event);
    }

    public void unregisterAllEvents() {
        for(IEvent<? extends Event> event : events) {
            SpigotPlugin.getInstance().getEventsFactory().unregisterEvent(event);
        }
        events.clear();
    }

    public void unregisterAllPlayerEvents() {
        for(IPlayerEvent<? extends Event> playerEvent : playerEvents) {
            SpigotPlugin.getInstance().getListenerPlayerManager().removeEvent(playerEvent.getPlayer(),playerEvent);
        }
        playerEvents.clear();
    }
}
