package be.alexandre01.eloriamc.server.session.listeners.examples;

import be.alexandre01.eloriamc.server.events.factories.IEvent;
import be.alexandre01.eloriamc.server.session.Session;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Arrays;
import java.util.List;

public class CancelBlockPlace extends IEvent<BlockPlaceEvent> {
    Session session;
    List<Material> materials;
    public CancelBlockPlace(Session session, Material... materials){
        this.session = session;
        this.materials = Arrays.asList(materials);
    }

    @Override
    public void onEvent(BlockPlaceEvent event) {
        if(session.containsPlayer(event.getPlayer())){
            if(materials.isEmpty()){
                event.setCancelled(true);
                return;
            }

            if(materials.contains(event.getBlock().getType()))
                event.setCancelled(true);
        }
    }
}
