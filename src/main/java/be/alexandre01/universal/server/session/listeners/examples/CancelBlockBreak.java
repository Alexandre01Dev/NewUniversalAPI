package be.alexandre01.universal.server.session.listeners.examples;

import be.alexandre01.universal.server.events.factories.IEvent;
import be.alexandre01.universal.server.session.Session;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Arrays;
import java.util.List;

public class CancelBlockBreak extends IEvent<BlockBreakEvent> {
    Session session;
    List<Material> materials;
    public CancelBlockBreak(Session session, Material... materials){
        this.session = session;
        this.materials = Arrays.asList(materials);
    }

    @Override
    public void onEvent(BlockBreakEvent event) {
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
