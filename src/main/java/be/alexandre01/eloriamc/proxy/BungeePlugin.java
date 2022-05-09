package be.alexandre01.eloriamc.proxy;

import be.alexandre01.eloriamc.API;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeePlugin extends Plugin {

    @Getter private static BungeePlugin instance;

    @Override
    public void onEnable() {
        API api = API.getInstance();
    }

    @Override
    public void onDisable() {

    }
}
