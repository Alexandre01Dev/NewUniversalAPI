package be.alexandre01.eloriamc.server.listener;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.player.BasePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerDamage implements Listener {
    SpigotPlugin spigotPlugin;
    public PlayerDamage() {
        spigotPlugin = SpigotPlugin.getInstance();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDamage(org.bukkit.event.entity.EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            BasePlayer player = spigotPlugin.getBasePlayer((org.bukkit.entity.Player)event.getEntity());
            BasePlayer damager = spigotPlugin.getBasePlayer((org.bukkit.entity.Player)event.getDamager());
            player.getDamagerUtils().addDamager(damager);
        }
    }
}
