package be.alexandre01.eloriamc.server.session.inventory.item;

import be.alexandre01.eloriamc.server.events.factories.IEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemInteractEvent extends IEvent<PlayerInteractEvent> {
    @Override
    public void onEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

    }
}
