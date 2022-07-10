package be.alexandre01.eloriamc.server.listener;

import be.alexandre01.eloriamc.API;
import be.alexandre01.eloriamc.data.PlayerData;
import be.alexandre01.eloriamc.data.PlayerDataManager;
import be.alexandre01.eloriamc.manager.RankManager;
import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.packets.npc.MirrorNPC;
import be.alexandre01.eloriamc.server.packets.skin.MojangUtils;
import be.alexandre01.eloriamc.server.packets.skin.SkinData;
import be.alexandre01.eloriamc.server.packets.skin.SkinPlayer;
import be.alexandre01.eloriamc.server.player.NameTagImpl;
import be.alexandre01.eloriamc.server.session.Session;
import be.alexandre01.eloriamc.server.utils.locations.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
        if(playerData != null) {
            if(playerData.getProfile().getSkinUUID() != null) {
                SkinPlayer skinPlayer = new SkinPlayer(player);
                skinPlayer.applySkin(MojangUtils.getSkinDataFromUUID(playerData.getProfile().getSkinUUID()));
            }
        }

        //new MirrorNPC(new Location(player.getWorld(), 346.5D,74,1623.5D), new Cuboid(new Location(player.getWorld(),346,74,1622.3D),new Location(player.getWorld(),346.5D,74,1623.5D))).run(player);


        for(Session<?> defaultSession : plugin.getSessionManager().getDefaultSessions()){
            defaultSession.addPlayer(player);
        }



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
                case "Développer":
                    setNameTag(player, "03Dev", rankManager.getRankPrefix(), " §8┃ §a✔");
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
                case "Famous":
                    setNameTag(player, "07Famous", rankManager.getRankPrefix(), " §8┃ §b✪");
                    break;
                case "Youtuber":
                    setNameTag(player, "08Youtuber", rankManager.getRankPrefix(), " §8┃ §b✪");
                    break;
                case "Tiktok":
                    setNameTag(player, "09Tiktok", rankManager.getRankPrefix(), " §8┃ §b✪");
                    break;
                case "Custom":
                    setNameTag(player, "07" + player.getName(), rankManager.getPlayerPrefix(), "");
                    break;
                case "Joueur":
                    setNameTag(player, "99Joueur", "§7", "");
                    break;
            }
        }catch (Exception ignored){
            System.out.println("[EloriaMC] Erreur lors de la création du nametag du joueur " + player.getName());
        }


    }
}
