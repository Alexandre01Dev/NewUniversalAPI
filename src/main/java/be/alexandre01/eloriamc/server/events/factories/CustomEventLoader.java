package be.alexandre01.eloriamc.server.events.factories;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import org.apache.commons.lang.Validate;
import org.bukkit.Warning;
import org.bukkit.event.*;
import org.bukkit.plugin.AuthorNagException;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.spigotmc.CustomTimingsHandler;

public abstract class CustomEventLoader {



    protected SpigotPlugin spigotPlugin;

    public CustomEventLoader(){
        spigotPlugin = SpigotPlugin.getInstance();
    }

    public abstract Map<Class<? extends Event>, Set<RegisteredListener>> createCustomRegisteredListeners(Listener listener, Plugin plugin, EventPriority eventPriority);


}
