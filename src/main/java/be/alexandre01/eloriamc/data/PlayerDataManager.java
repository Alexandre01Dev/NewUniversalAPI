package be.alexandre01.eloriamc.data;

import be.alexandre01.eloriamc.data.mysql.Mysql;
import be.alexandre01.eloriamc.data.redis.RedisManager;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class PlayerDataManager {

    public static PlayerData getPlayerData(String playername) {
        return PlayerData.fromJson(RedisManager.get("Player:" + playername));
    }

    public static void setPlayerData(String playername) {
        Mysql.query("SELECT * FROM users WHERE uuid='" + playername + "'", rs -> {
            try {
                if(rs.next()) {
                    PlayerData playerData = PlayerData.fromJson(rs.getString("playerData"));
                    playerData.savePlayerCache();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
