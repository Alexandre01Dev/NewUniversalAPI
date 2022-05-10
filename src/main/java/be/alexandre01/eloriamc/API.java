package be.alexandre01.eloriamc;

import be.alexandre01.eloriamc.data.mysql.DatabaseManager;
import be.alexandre01.eloriamc.data.redis.RedisManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class API  {
    @Getter private final static API instance;

    static {
        instance = new API();
    }

    public API(){

    }

    public void onOpen(){
        DatabaseManager.initAllDatabaseConnection();
        RedisManager.init();
    }
    public void onClose(){
        DatabaseManager.closeAllDatabaseConnection();
        RedisManager.close();
    }



}
