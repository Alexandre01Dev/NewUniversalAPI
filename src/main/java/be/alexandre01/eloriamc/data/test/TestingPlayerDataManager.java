package be.alexandre01.eloriamc.data.test;

import be.alexandre01.eloriamc.data.PlayerData;
import be.alexandre01.eloriamc.data.PlayerDataManager;
import be.alexandre01.eloriamc.data.Settings;
import be.alexandre01.eloriamc.data.game.KbWarrior;
import be.alexandre01.eloriamc.data.game.Madness;
import be.alexandre01.eloriamc.data.redis.RedisManager;

public class TestingPlayerDataManager extends PlayerDataManager {
    @Override
    public PlayerData getPlayerData(String playername) {
        return new PlayerData(playername,"",true);
    }
}
