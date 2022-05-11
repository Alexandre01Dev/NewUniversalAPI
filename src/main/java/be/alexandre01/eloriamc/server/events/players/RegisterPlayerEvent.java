package be.alexandre01.eloriamc.server.events.players;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.InvocationTargetException;

public class RegisterPlayerEvent<T extends Event> implements Listener {

    String handler;

    ListenerPlayerManager listenerPlayerManager;

    public <T extends Class<? extends Event>> RegisterPlayerEvent(Class<? extends Event> event, String handler, ListenerPlayerManager listenerPlayerManager) {
        this.handler = handler;
        System.out.println(event.getSimpleName());
        this.listenerPlayerManager = listenerPlayerManager;

    }



    @EventHandler
    public void onRegister(T event) {
        try {
            Object o = event.getClass().getMethod(handler).invoke(event);
            if(o != null) {
                if(o instanceof Player) {
                    Player p = (Player) o;
                    listenerPlayerManager.listeners.get(p).forEach(iPlayerEvent -> {
                        if(iPlayerEvent.getEventClass().equals(event.getClass())) {
                            iPlayerEvent.onPlayerEvent(event,p);
                        }
                        System.out.println(event);
                    });
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
