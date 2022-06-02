package be.alexandre01.eloriamc.data;

import be.alexandre01.eloriamc.data.mysql.Mysql;
import be.alexandre01.eloriamc.data.redis.RedisManager;
import lombok.Getter;
import org.bukkit.entity.Player;

import javax.persistence.GeneratedValue;
import java.sql.SQLException;
import java.util.HashMap;

public class PlayerDataManager {
    @Getter
    private HashMap<String, PlayerData> playerDataHashMap = new HashMap<>();

    public PlayerData getLocalPlayerData(Player player){
        return playerDataHashMap.get(player.getName());
    }

    public PlayerData getPlayerData(String playername) {
        return PlayerData.fromJson(RedisManager.get("Player:" + playername));
    }
}
