package be.alexandre01.eloriamc.data;

import be.alexandre01.eloriamc.data.game.Identifier;
import be.alexandre01.eloriamc.data.game.KbWarrior;
import be.alexandre01.eloriamc.data.game.Madness;
import be.alexandre01.eloriamc.data.impl.IPlayerData;
import be.alexandre01.eloriamc.data.mysql.Mysql;
import be.alexandre01.eloriamc.data.profile.Profile;
import be.alexandre01.eloriamc.data.punishement.Ban;
import be.alexandre01.eloriamc.data.redis.RedisManager;
import be.alexandre01.eloriamc.server.packets.skin.SkinData;
import be.alexandre01.eloriamc.utils.Tuple;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter

public class PlayerData implements IPlayerData {

    @Expose private final String playerName;
    @Expose private final String uuid;
    @Expose private int coins;
    @Expose private int ecu;

    @Expose private float exp;
    @Expose private int level;

    @Expose private long timePlayed;

    @Expose private boolean mod;


    @Expose private Settings settings;

    @Expose private KbWarrior kbWarrior;

    @Expose private Madness madness;

    @Expose private Ban ban;

    @Expose private Profile profile;


    public PlayerData(String playerName, String uuid, boolean initDefault){
        this.playerName = playerName;
        this.uuid = uuid;
        if(!initDefault) return;
        this.coins = 0;
        this.ecu = 0;
        this.exp = 0;
        this.level = 1;
        this.timePlayed = 0L;
        this.mod = false;
        this.settings = new Settings();
        this.kbWarrior = new KbWarrior();
        this.madness = new Madness();
        this.ban = new Ban();
    }

    public String toJson(){
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(this);
        //return new Gson().toJson(this);
    }

    public static PlayerData fromJson(String playerdata) {
        return  new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(playerdata, PlayerData.class);
    }



    public void savePlayerCache(){
        RedisManager.set("Player:" + playerName, this.toJson());
    }

    public void deletePlayerCache(){
        RedisManager.del("Player:" + playerName);
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

    public List<Tuple<Identifier,String>> getIdentifiers(){
        List<Tuple<Identifier,String>> ids = new ArrayList<>();
        for(Field field : this.getClass().getDeclaredFields()){
            field.setAccessible(true);

            if(   isInheritedClass(Identifier.class,field.getType())){
                try {
                    ids.add(new Tuple<>((Identifier) field.get(this),field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        for(Field field : this.getClass().getFields()){
            field.setAccessible(true);
            if(field.getType().isAssignableFrom(Identifier.class)){
                try {
                    ids.add(new Tuple<>((Identifier) field.get(this),field.getName()));

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return ids;
    }

    public boolean isInheritedClass(Class<?> parent, Class<?> child) {
        if (parent.isAssignableFrom(child)) {
            // is child or same class
            return parent.isAssignableFrom(child.getSuperclass());
        } else {
            return false;
        }
    }

}
