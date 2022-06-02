package be.alexandre01.eloriamc.data;

import be.alexandre01.eloriamc.data.mysql.Mysql;
import be.alexandre01.eloriamc.data.redis.RedisManager;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class PlayerDataManager {

    public PlayerData getPlayerData(String playername) {
        return PlayerData.fromJson(RedisManager.get("Player:" + playername));
    }
}
