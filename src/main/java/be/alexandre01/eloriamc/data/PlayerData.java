package be.alexandre01.eloriamc.data;

import be.alexandre01.eloriamc.data.game.Identifier;
import be.alexandre01.eloriamc.data.game.KbWarrior;
import be.alexandre01.eloriamc.data.game.Madness;
import be.alexandre01.eloriamc.data.impl.IPlayerData;
import be.alexandre01.eloriamc.data.mysql.Mysql;
import be.alexandre01.eloriamc.data.redis.RedisManager;
import be.alexandre01.eloriamc.utils.Tuple;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PlayerData implements IPlayerData {

    private final String playerName;
    private final String uuid;

    private int coins;
    private int ecu;

    private float exp;
    private int level;

    private boolean mod;


    private Settings settings;

    private KbWarrior kbWarrior;

    private Madness madness;


    public PlayerData(String playerName, String uuid, boolean initDefault){
        this.playerName = playerName;
        this.uuid = uuid;
        if(!initDefault) return;
        this.coins = 0;
        this.ecu = 0;
        this.exp = 0;
        this.level = 1;
        this.mod = false;
        this.settings = new Settings(true, true, true, true);
        this.kbWarrior = new KbWarrior();
        this.madness = new Madness();
    }

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
        System.out.println("LIST USED");
        for(Field field : this.getClass().getDeclaredFields()){
            System.out.println("FOR USED "+ field.getName());
            System.out.println("FOR TYPE "+ field.getType());
            System.out.println("FOR ANNOTED"+ field.getAnnotatedType());
            System.out.println("FOR GENERIC"+ field.getGenericType());
            field.setAccessible(true);
            System.out.println();

            System.out.println(isInheritedClass(Identifier.class,field.getType()));
            if(   isInheritedClass(Identifier.class,field.getType())){
                System.out.println("SHEESH");
                try {
                    ids.add(new Tuple<>((Identifier) field.get(this),field.getName()));
                    System.out.println(ids);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        for(Field field : this.getClass().getFields()){
            field.setAccessible(true);
            if(field.getType().isAssignableFrom(Identifier.class)){
                System.out.println("SHEESH");
                try {
                    ids.add(new Tuple<>((Identifier) field.get(this),field.getName()));
                    System.out.println(ids);
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
