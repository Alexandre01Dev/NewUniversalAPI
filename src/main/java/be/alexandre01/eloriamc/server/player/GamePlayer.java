package be.alexandre01.eloriamc.server.player;

import be.alexandre01.gameapi.GameAPI;
import org.bukkit.entity.Player;

public class GamePlayer extends BasePlayer {
    public GamePlayer(Player player) {
        super(player);
        GameAPI.get.getGamePlayerManager().initPlayer(player,this);
    }
    public static GamePlayer toGamePlayer(Player player){
        GameAPI gameAPI = GameAPI.get;
        if(gameAPI.getGamePlayerManager().getGamePlayerIndex().containsKey(player)){
            return gameAPI.getGamePlayerManager().getGamePlayerIndex().get(player);
        }
        return new GamePlayer(player);
    }

}
