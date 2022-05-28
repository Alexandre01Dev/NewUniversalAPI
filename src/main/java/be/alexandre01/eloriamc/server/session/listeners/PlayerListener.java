package be.alexandre01.eloriamc.server.session.listeners;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    SpigotPlugin plugin;

    public PlayerListener(SpigotPlugin spigotPlugin) {
        this.plugin = spigotPlugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(plugin.getSessionManager().getDefaultSession() != null) {
            plugin.getSessionManager().getDefaultSession().addPlayer(plugin.getBasePlayer(event.getPlayer()));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(plugin.getSessionManager().getDefaultSession() != null) {
            plugin.getSessionManager().getDefaultSession().removePlayer(plugin.getBasePlayer(event.getPlayer()));
        }
    }


}
