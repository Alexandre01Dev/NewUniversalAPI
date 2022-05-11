package be.alexandre01.eloriamc.server.events.players;

import lombok.Getter;
import org.bukkit.event.Event;

import java.util.ArrayList;

public class SessionListenerManager {

    @Getter private ArrayList<IPlayerEvent<?>> playerEvents = new ArrayList<>();

    public SessionListenerManager() {
        playerEvents = new ArrayList<>();
    }

    public void registerPlayerEvent(IPlayerEvent<? extends Event> playerEvent,String callPlayer) {
        playerEvent.setPlayerCall(callPlayer);
        playerEvents.add(playerEvent);
    }
}
