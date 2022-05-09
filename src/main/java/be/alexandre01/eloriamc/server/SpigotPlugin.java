package be.alexandre01.eloriamc.server;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotPlugin extends JavaPlugin {

    @Getter private static SpigotPlugin instance;

    public SpigotPlugin() {
        instance = this;
    }

}
