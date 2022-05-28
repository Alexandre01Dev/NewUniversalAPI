package be.alexandre01.eloriamc.server.listener;

import be.alexandre01.eloriamc.manager.RankManager;
import be.alexandre01.eloriamc.server.player.NameTagImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener, NameTagImpl {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        RankManager rankManager = new RankManager(player.getName());
        switch (rankManager.getGroup()) {
            case "Admin":
                removeNameTag(player, "01Admin");
                break;
            case "Responsable":
                removeNameTag(player, "02Resp");
                break;
            case "Custom":
                removeNameTag(player, "05" + player.getName());

                break;
        }
    }
}
