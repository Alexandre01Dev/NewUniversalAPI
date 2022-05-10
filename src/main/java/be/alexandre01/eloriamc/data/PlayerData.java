package be.alexandre01.eloriamc.data;

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
public class PlayerData {

    private final String playerName;
    private final String uuid;

    private String rank;
    private int coins;
    private int gemmes;
    private String server;
    private boolean ptp;
    private String ptpname;
    private boolean mod;

    public String toJson(){
        return new Gson().toJson(this);
    }

    public static PlayerData fromJson(String playerdata) {
        return new Gson().fromJson(playerdata, PlayerData.class);
    }

    public void setupPlayer() {
        Mysql.query("SELECT * FROM users WHERE uuid='" + uuid + "'", rs -> {
            try {
                if(!rs.next()) {
                    for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                        if (all.hasPermission("MODERATOR")) {
                            all.sendMessage(new TextComponent(TextComponent.fromLegacyText("§6§LMOD§8│ §7Première connexion de §b" + playerName)));
                            Mysql.update("INSERT INTO users (uuid, name, coins, gemmes) VALUES ('" + uuid + "', '" + playerName + "', '" + coins + "', '" + gemmes + "')");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void savePlayerCache(){
        RedisManager.set("Player:" + playerName, this.toJson());
    }

    public void deletePlayerCache(){
        RedisManager.del("Player:" + playerName);
    }
}
