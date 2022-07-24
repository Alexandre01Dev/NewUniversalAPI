package be.alexandre01.universal;

import be.alexandre01.universal.chat.ChatConfiguration;
import be.alexandre01.universal.chat.ChatOptions;
import be.alexandre01.universal.config.APIConfig;
import be.alexandre01.universal.data.PlayerDataManager;
import be.alexandre01.universal.data.mysql.DatabaseManager;
import be.alexandre01.universal.data.redis.RedisManager;
import be.alexandre01.universal.data.test.TestingPlayerDataManager;
import lombok.Getter;

public class API  {
    @Getter private final static API instance;

    @Getter
    PlayerDataManager playerDataManager;

    @Getter
    boolean noDB = true;
    @Getter
    boolean noDream = true;



    @Getter private final ChatConfiguration chatConfiguration = new ChatConfiguration();

    @Getter private final APIConfig configuration = new APIConfig();

    static {
        instance = new API();
    }

    public API(){
        chatConfiguration.createSimpleTextBuilder(ChatOptions.PREFIX, ChatConfiguration.ChatTextType.PREFIX,configuration.getDefaultPrefix());
        chatConfiguration.createSimpleTextBuilder(ChatOptions.ERROR, ChatConfiguration.ChatTextType.PREFIX,configuration.getDefaultError());
        chatConfiguration.createSimpleTextBuilder(ChatOptions.WARNING, ChatConfiguration.ChatTextType.PREFIX,configuration.getDefaultWarn());

    }

    public void onOpen(){

        String s = System.getProperty("B");
        if(s != null && s.equalsIgnoreCase("false")){
            noDB = true;
            playerDataManager = new TestingPlayerDataManager();
        }

        String s2 = System.getProperty("N");
        if(s2 != null && s2.equalsIgnoreCase("false")){
            noDream = true;
        }
        if(!noDB){
            System.out.println("DATA LOAD");
            DatabaseManager.initAllDatabaseConnection();
            RedisManager.init();
            playerDataManager = new PlayerDataManager();
        }
    }


    public void onClose(){
        if(!noDB){
            DatabaseManager.closeAllDatabaseConnection();
            RedisManager.close();
            System.out.println("DATA UNLOAD");
        }

    }



}
