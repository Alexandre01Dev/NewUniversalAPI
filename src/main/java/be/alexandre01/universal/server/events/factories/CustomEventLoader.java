package be.alexandre01.universal.server.events.factories;


import java.util.Map;
import java.util.Set;

import be.alexandre01.universal.server.SpigotPlugin;
import org.bukkit.event.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

public abstract class CustomEventLoader {



    protected SpigotPlugin spigotPlugin;

    public CustomEventLoader(){
        spigotPlugin = SpigotPlugin.getInstance();
    }

    public abstract Map<Class<? extends Event>, Set<RegisteredListener>> createCustomRegisteredListeners(Listener listener, Plugin plugin, EventPriority eventPriority);


}
