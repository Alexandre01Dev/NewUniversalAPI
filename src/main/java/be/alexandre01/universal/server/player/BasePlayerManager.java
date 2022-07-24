package be.alexandre01.universal.server.player;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class BasePlayerManager {

    private Class<? extends BasePlayer> defaultPlayerClass = BasePlayer.class;
    private final HashMap<UUID, BasePlayer> playerHashMap = new HashMap<>();


    public void changeBasePlayer(Class<?> o){
        this.defaultPlayerClass = (Class<? extends BasePlayer>) o;
    }


    public BasePlayer createPlayerObject(Player player){
        try {
            System.out.println(defaultPlayerClass.getName());
            BasePlayer gamePlayer = defaultPlayerClass.getConstructor(Player.class).newInstance(player);
            playerHashMap.put(player.getUniqueId(), gamePlayer);
            return gamePlayer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void initPlayer(Player player, BasePlayer basePlayer){
        playerHashMap.put(player.getUniqueId(), basePlayer);
    }

    public void removePlayer(BasePlayer basePlayer){
        playerHashMap.remove(basePlayer.getUniqueId());
    }
}
