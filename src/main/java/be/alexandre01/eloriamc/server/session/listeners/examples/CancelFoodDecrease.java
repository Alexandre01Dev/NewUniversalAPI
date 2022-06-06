package be.alexandre01.eloriamc.server.session.listeners.examples;

import be.alexandre01.eloriamc.server.events.factories.IEvent;
import be.alexandre01.eloriamc.server.session.Session;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class CancelFoodDecrease extends IEvent<FoodLevelChangeEvent> {

    private Session session;
    public CancelFoodDecrease(Session session){
        this.session = session;
    }

    @Override
    public void onEvent(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();

        if(session.containsPlayer(player)){
            event.setFoodLevel(20);
        }
    }
}
