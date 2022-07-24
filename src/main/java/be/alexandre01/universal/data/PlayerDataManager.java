package be.alexandre01.universal.data;

import be.alexandre01.universal.data.redis.RedisManager;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class PlayerDataManager {
    @Getter
    private HashMap<String, PlayerData> playerDataHashMap = new HashMap<>();

    public PlayerData getLocalPlayerData(String playerName){
        return playerDataHashMap.get(playerName);
    }

    public PlayerData getLocalPlayerData(Object player){
        //reflection
        try {
            //sout all fields
            Method method = player.getClass().getMethod("getName");

            method.setAccessible(true);
            String name = (String) method.invoke(player);
            if(playerDataHashMap.containsKey(name)){
                return playerDataHashMap.get(name);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public PlayerData getPlayerData(String playername) {
        return PlayerData.fromJson(RedisManager.get("Player:" + playername));
    }


}
