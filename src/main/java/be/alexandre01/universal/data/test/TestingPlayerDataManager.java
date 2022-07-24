package be.alexandre01.universal.data.test;

import be.alexandre01.universal.data.PlayerData;
import be.alexandre01.universal.data.PlayerDataManager;

public class TestingPlayerDataManager extends PlayerDataManager {
    @Override
    public PlayerData getPlayerData(String playername) {
        return new PlayerData(playername,"",true);
    }
}
