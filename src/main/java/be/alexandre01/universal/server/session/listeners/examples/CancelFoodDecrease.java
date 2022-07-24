package be.alexandre01.universal.server.session.listeners.examples;

import be.alexandre01.universal.server.events.factories.IEvent;
import be.alexandre01.universal.server.session.Session;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;

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
