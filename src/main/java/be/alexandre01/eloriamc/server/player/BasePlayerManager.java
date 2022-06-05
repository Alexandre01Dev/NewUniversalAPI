package be.alexandre01.eloriamc.server.player;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Getter
public class BasePlayerManager {

    private Class<? extends BasePlayer> defaultPlayerClass = GamePlayer.class;
    private final HashMap<Player, BasePlayer> playerHashMap = new HashMap<>();


    public void changeBasePlayer(Class<?> o){
        this.defaultPlayerClass = (Class<? extends BasePlayer>) o;
    }


    public BasePlayer createPlayerObject(Player player){
        try {
            System.out.println(defaultPlayerClass.getName());
            BasePlayer gamePlayer = defaultPlayerClass.getConstructor(Player.class).newInstance(player);
            playerHashMap.put(player, gamePlayer);
            return gamePlayer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void initPlayer(Player player, GamePlayer gamePlayer){
        playerHashMap.put(player, gamePlayer);
    }

    public void removePlayer(BasePlayer gamePlayer){
        playerHashMap.remove(gamePlayer.getPlayer());
    }
}
