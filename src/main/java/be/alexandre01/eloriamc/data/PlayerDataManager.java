package be.alexandre01.eloriamc.data;

import be.alexandre01.eloriamc.data.redis.RedisManager;

public class PlayerDataManager {

    public static PlayerData getPlayerData(String playername) {
        return PlayerData.fromJson(RedisManager.get("Player:" + playername));
    }
}
