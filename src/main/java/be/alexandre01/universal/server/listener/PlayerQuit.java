package be.alexandre01.universal.server.listener;

import be.alexandre01.universal.API;
import be.alexandre01.universal.manager.RankManager;
import be.alexandre01.universal.server.SpigotPlugin;
import be.alexandre01.universal.server.player.NameTagImpl;
import be.alexandre01.universal.server.session.Session;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener, NameTagImpl {
    API api;
    SpigotPlugin plugin;
    public PlayerQuit(){
        api = API.getInstance();
        plugin = SpigotPlugin.getInstance();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        e.setQuitMessage(null);
         api.getPlayerDataManager().getPlayerDataHashMap().remove(player.getName());
        for(Session<?> defaultSession : plugin.getSessionManager().getDefaultSessions()){
            defaultSession.removePlayer(player);
        }
        plugin.getBasePlayerManager().getPlayerHashMap().remove(player.getUniqueId());
        try {
           /* RankManager rankManager = new RankManager(player.getName());
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
            }*/
        }catch (Exception ignored){
            System.out.println("[EloriaMC] Erreur lors de la suppression du nametag");
        }

    }
}
