package be.alexandre01.eloriamc.server;

import be.alexandre01.eloriamc.API;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotPlugin extends JavaPlugin {

    @Getter private static SpigotPlugin instance;




    @Override
    public void onEnable() {
        instance = this;
        API.getInstance().onOpen();
    }

    @Override
    public void onDisable() {
        API.getInstance().onClose();
    }


}
