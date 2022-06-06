package be.alexandre01.eloriamc.server.events.players;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.events.factories.IEvent;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

public class SessionListenerManager {

    @Getter private ArrayList<IPlayerEvent<?>> playerEvents;
    @Getter private Multimap<Player,IPlayerEvent<?>> listenersPerPlayer;
    @Getter private ArrayList<IEvent<?>> events = new ArrayList<>();
    @Getter private SpigotPlugin spigotPlugin;

    public SessionListenerManager() {
        playerEvents = new ArrayList<>();
        spigotPlugin = SpigotPlugin.getInstance();
        listenersPerPlayer = ArrayListMultimap.create();
    }

    public <T extends Event> void registerPlayerEvent(IPlayerEvent<T> playerEvent, String callPlayer) {
        playerEvent.setPlayerCall(callPlayer);
        playerEvents.add(playerEvent);
    }

    public <T extends Event> void registerToPlayer(Player player) {
        for (IPlayerEvent<?> playerEvent : getPlayerEvents()) {
            System.out.println(player.getName() + " registered to " + playerEvent.getPlayerCall());
            playerEvent.registerToPlayer(player);
            listenersPerPlayer.put(player, playerEvent);
        }
    }

    public void registerEvent(IEvent<? extends Event> event) {
        events.add(event);
        spigotPlugin.getEventsFactory().fastRegisterEvent(event.getEventClass(), event);
    }


    public void unregisterPlayerEvents(Player player) {
        System.out.println("Unregistering player " + player.getName());
        if(!listenersPerPlayer.containsKey(player)) return;
        System.out.println("?");
        for(IPlayerEvent<? extends Event> playerEvent : listenersPerPlayer.get(player)) {
            System.out.println(player.getName() + " unregistered to " + playerEvent.getPlayerCall());
            SpigotPlugin.getInstance().getListenerPlayerManager().removeEvent(player,playerEvent);
        }
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
