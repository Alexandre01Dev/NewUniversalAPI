package be.alexandre01.eloriamc.data;

import be.alexandre01.eloriamc.data.impl.IPlayerData;
import be.alexandre01.eloriamc.data.mysql.Mysql;
import be.alexandre01.eloriamc.data.redis.RedisManager;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.lang.reflect.Proxy;
import java.sql.SQLException;

@AllArgsConstructor
@Getter
@Setter
public class PlayerData implements IPlayerData {

    private final String playerName;
    private final String uuid;

    private int coins;
    private int gemmes;

    private boolean mod;



    public String toJson(){
        return new Gson().toJson(this);
    }

    public static PlayerData fromJson(String playerdata) {
        return new Gson().fromJson(playerdata, PlayerData.class);
    }



    public void savePlayerCache(){
        RedisManager.set("Player:" + playerName, this.toJson());
    }

    public void deletePlayerCache(){
        RedisManager.del("Player:" + playerName);
    }

    @Override
    public void setupPlayer() {
        Mysql.query("SELECT * FROM users WHERE uuid='" + uuid + "'", rs -> {
            try {
                if(!rs.next()) {
                    Mysql.update("INSERT INTO users (uuid, name, playerData) VALUES ('" + uuid + "', '" + playerName + "', '" + this.toJson() + "')");
                    this.savePlayerCache();
                } else {
                    PlayerData playerData = PlayerData.fromJson(rs.getString("playerData"));
                    playerData.savePlayerCache();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void savePlayer() {
        Mysql.query("SELECT * FROM users WHERE uuid='" + uuid + "'", rs -> {
            try {
                if (rs.next()) {
                    Mysql.update("UPDATE users SET playerData= '" + this.toJson() + "' WHERE uuid= '" + uuid + "'");
                    deletePlayerCache();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void addCoins(int i) {
        coins = coins + i;
    }

    @Override
    public void removeCoins(int i) {
        coins = coins - i;
    }

    @Override
    public void addGemmes(int i) {
        gemmes = gemmes + i;
    }

    @Override
    public void removeGemmes(int i) {
        gemmes = gemmes - i;
    }
}
