package be.alexandre01.eloriamc.data;

import be.alexandre01.eloriamc.data.game.Identifier;
import be.alexandre01.eloriamc.data.game.KbWarrior;
import be.alexandre01.eloriamc.data.game.Madness;
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
import net.minecraft.server.v1_8_R3.EntityEnderman;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.entity.Enderman;
import org.bukkit.event.Listener;

import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PlayerData implements IPlayerData {

    private final String playerName;
    private final String uuid;

    private float coins;
    private float ecu;

    private boolean mod;

    private Settings settings;

    private KbWarrior kbWarrior;

    private Madness madness;




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
                    PlayerData playerData = fromJson(rs.getString("playerData"));
                    for( Tuple<Identifier,String> id : playerData.getIdentifiers()){
                        if(id.a() == null){
                            try {
                                try {
                                    //Fait un new manuellement aux classes (ex: Madness,KbWarrior) et l'injecte dedans  si elles n'existent pas
                                    // <!> Attention a bien mettre des valeurs par défaut+ Mettre un @NoArgsConstructor dans ces classes <!>
                                    playerData.getClass().getField(id.b()).set(playerData,id.a().getClass().newInstance());
                                } catch (InstantiationException e) {
                                    throw new RuntimeException(e);
                                }
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            } catch (NoSuchFieldException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }


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
    public void add(TypeData typeData, int i) {
        switch (typeData) {
            case Coins:
                coins = coins + i;
                break;
            case Ecu:
                ecu = ecu + i;
                break;
        }
    }

    @Override
    public void remove(TypeData typeData, int i) {
        switch (typeData) {
            case Coins:
                coins = coins - i;
                break;
            case Ecu:
                ecu = ecu - i;
                break;
        }
    }

    @Override
    public void set(TypeData typeData, int i) {
        switch (typeData) {
            case Coins:
                coins = i;
                break;
            case Ecu:
                ecu =  i;
                break;
        }

    }

}
