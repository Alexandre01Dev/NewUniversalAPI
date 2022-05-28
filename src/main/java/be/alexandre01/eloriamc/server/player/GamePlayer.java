package be.alexandre01.eloriamc.server.player;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import org.bukkit.entity.Player;

public class GamePlayer extends BasePlayer {
    private static SpigotPlugin spigotPlugin;




    public GamePlayer(Player player) {
        super(player);
    }


    public static GamePlayer get(Player player){
        if(spigotPlugin.getBasePlayerManager().getPlayerHashMap().containsKey(player)){
            return (GamePlayer) spigotPlugin.getBasePlayerManager().getPlayerHashMap().get(player);
        }
        return new GamePlayer(player);
    }

}
