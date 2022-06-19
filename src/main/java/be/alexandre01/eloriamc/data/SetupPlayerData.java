package be.alexandre01.eloriamc.data;

import be.alexandre01.eloriamc.data.game.Identifier;
import be.alexandre01.eloriamc.data.mysql.Mysql;
import be.alexandre01.eloriamc.utils.Tuple;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class SetupPlayerData {


    public static PlayerData setupPlayer(String playerName,String uuid) {
        AtomicReference<PlayerData> pData = new AtomicReference<>();
        Mysql.query("SELECT * FROM users WHERE uuid='" + uuid + "'", rs -> {
            try {
                if (!rs.next()) {
                    PlayerData playerData = new PlayerData(playerName, uuid, true);
                    Mysql.update("INSERT INTO users (uuid, name, playerData) VALUES ('" + uuid + "', '" + playerName + "', '" + playerData.toJson() + "')");
                    playerData.savePlayerCache();
                    pData.set(playerData);
                } else {
                    PlayerData playerData = PlayerData.fromJson(rs.getString("playerData"));
                    for (Tuple<Identifier, String> id : playerData.getIdentifiers()) {
                        //System.out.println("cc" + id.b() + " " + id.a());
                        if (id.a() == null) {
                            // System.out.println("null");
                            try {
                                try {
                                    //Fait un new manuellement aux classes (ex: Madness,KbWarrior) et l'injecte dedans  si elles n'existent pas
                                    // <!> Attention a bien mettre des valeurs par défaut+ Mettre un @NoArgsConstructor dans ces classes <!>+

                                    Field field = playerData.getClass().getDeclaredField(id.b());
                                    field.setAccessible(true);
                                    //System.out.println("Fuck 2");
                                    field.set(playerData, field.getType().newInstance());
                                    //System.out.println("TESTED");
                                    playerData.savePlayerCache();
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

                    pData.set(playerData);
                    playerData.getBan().checkBan();
                    playerData.savePlayerCache();
                    if(playerData.getBan().isBanned()) {
                        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);
                        player.disconnect(new TextComponent("§c§LBANNI !\n" + playerData.getBan().getTimeLeft()));
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return pData.get();
    }
}