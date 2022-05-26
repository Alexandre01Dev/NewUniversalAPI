package be.alexandre01.eloriamc.data.game;

import be.alexandre01.eloriamc.data.PlayerData;
import be.alexandre01.eloriamc.data.TypeData;
import be.alexandre01.eloriamc.data.impl.IPlayerData;
import be.alexandre01.eloriamc.data.mysql.Mysql;
import be.alexandre01.eloriamc.data.redis.RedisManager;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;

@AllArgsConstructor
@Getter
@Setter
public class KbWarrior implements IPlayerData {

    private final String playerName;
    private final String uuid;

    private int kill;
    private int death;
    private int bestKs;



    public String toJson(){
        return new Gson().toJson(this);
    }

    public static PlayerData fromJson(String playerdata) {
        return new Gson().fromJson(playerdata, PlayerData.class);
    }



    public void savePlayerCache(){
        RedisManager.set("KbWarrior:" + playerName, this.toJson());
    }

    public void deletePlayerCache(){
        RedisManager.del("KbWarrior:" + playerName);
    }

    @Override
    public void setupPlayer() {
        Mysql.query("SELECT * FROM kbwarrior WHERE uuid='" + uuid + "'", rs -> {
            try {
                if(!rs.next()) {
                    Mysql.update("INSERT INTO users (uuid, name, kbData) VALUES ('" + uuid + "', '" + playerName + "', '" + this.toJson() + "')");
                    this.savePlayerCache();
                } else {
                    PlayerData playerData = PlayerData.fromJson(rs.getString("kbData"));
                    playerData.savePlayerCache();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void savePlayer() {
        Mysql.query("SELECT * FROM kbwarrior WHERE uuid='" + uuid + "'", rs -> {
            try {
                if (rs.next()) {
                    Mysql.update("UPDATE users SET kbData= '" + this.toJson() + "' WHERE uuid= '" + uuid + "'");
                    deletePlayerCache();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void add(TypeData typeData, int i) {
        switch (typeData) {
            case KillKbW:
                kill = kill + i;
                break;
            case DeathKbW:
                death = death + i;
                break;
            case BestKSKbw:
                bestKs = bestKs + i;
        }
    }

    @Override
    public void remove(TypeData typeData, int i) {
        switch (typeData) {
            case KillKbW:
                kill = kill - i;
                break;
            case DeathKbW:
                death = death - i;
                break;
            case BestKSKbw:
                bestKs = bestKs - i;
        }
    }

    @Override
    public void set(TypeData typeData, int i) {
        switch (typeData) {
            case KillKbW:
                kill = i;
                break;
            case DeathKbW:
                death = i;
                break;
            case BestKSKbw:
                bestKs = i;
        }

    }
}
