package be.alexandre01.eloriamc.server.player;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Getter
public class GamePlayerManager {
    private final HashMap<Player, GamePlayer> gamePlayerIndex = new HashMap<>();


    public void initPlayer(Player player, GamePlayer gamePlayer){
        gamePlayerIndex.put(player, gamePlayer);
    }

    public void removePlayer(BasePlayer gamePlayer){
        gamePlayerIndex.remove(gamePlayer.getPlayer());
    }
}
