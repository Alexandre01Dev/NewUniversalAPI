package be.alexandre01.eloriamc.server.listener;

import be.alexandre01.eloriamc.API;
import be.alexandre01.eloriamc.data.PlayerData;
import be.alexandre01.eloriamc.data.PlayerDataManager;
import be.alexandre01.eloriamc.manager.RankManager;
import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.player.NameTagImpl;
import be.alexandre01.eloriamc.server.session.Session;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener, NameTagImpl {
    API api;
    SpigotPlugin plugin;
    public PlayerJoin(){
     api = API.getInstance();
        plugin = SpigotPlugin.getInstance();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PlayerData playerData = api.getPlayerDataManager().getPlayerData(player.getName());

        for(Session<?> defaultSession : plugin.getSessionManager().getDefaultSessions()){
            defaultSession.addPlayer(player);
        }
        playerData.getSettings().setNotifFriend(false);
        if(!api.isNoDB())
            playerData.savePlayerCache();
        api.getPlayerDataManager().getPlayerDataHashMap().put(player.getName(), playerData);
        try {
            RankManager rankManager = new RankManager(player.getName());
            switch (rankManager.getGroup()) {
                case "Admin":
                    setNameTag(player, "01Admin", rankManager.getRankPrefix(), " §8┃ §a✔");
                    break;
                case "Responsable":
                    setNameTag(player, "02Resp", rankManager.getRankPrefix(), " §8┃ §a✔");
                    break;
                case "SMod":
                    setNameTag(player, "03SMod", rankManager.getRankPrefix(), " §8┃ §a✔");
                    break;
                case "Mod":
                    setNameTag(player, "04Mod", rankManager.getRankPrefix(), " §8┃ §a✔");
                    break;
                case "Helper":
                    setNameTag(player, "05Helper", rankManager.getRankPrefix(), " §8┃ §a✔");
                    break;
                case "Builder":
                    setNameTag(player, "06Builder", rankManager.getRankPrefix(), " §8┃ §a✔");
                    break;
                case "Custom":
                    setNameTag(player, "07" + player.getName(), rankManager.getPlayerPrefix(), "");
                    break;
                case "Joueur":
                    setNameTag(player, "99Joueur", rankManager.getRankPrefix(), "");
                    break;
            }
        }catch (Exception ignored){
            System.out.println("[EloriaMC] Erreur lors de la création du nametag du joueur " + player.getName());
        }


    }
}
