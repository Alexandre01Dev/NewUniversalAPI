package be.alexandre01.eloriamc.server.listener;

import be.alexandre01.eloriamc.manager.RankManager;
import be.alexandre01.eloriamc.server.player.NameTagImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener, NameTagImpl {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        RankManager rankManager = new RankManager(player.getName());
        switch (rankManager.getGroup()) {
            case "Admin":
                setNameTag(player, "01Admin", rankManager.getRankPrefix(), " §8┃ §a✔");
                break;
            case "Responsable":
                setNameTag(player, "02Resp", rankManager.getRankPrefix(), " §8┃ §a✔");
                break;
            case "Custom":
                setNameTag(player, "05" + player.getName(), rankManager.getPlayerPrefix(), "");
                break;
        }

    }
}
