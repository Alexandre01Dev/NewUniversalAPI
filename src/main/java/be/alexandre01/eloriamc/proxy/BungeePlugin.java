package be.alexandre01.eloriamc.proxy;

import be.alexandre01.dreamnetwork.api.request.channels.DNChannel;
import be.alexandre01.dreamnetwork.api.request.channels.DNChannelManager;
import be.alexandre01.dreamnetwork.api.request.channels.RegisterListener;
import be.alexandre01.dreamnetwork.gson.internal.LinkedTreeMap;
import be.alexandre01.dreamnetwork.plugins.bungeecord.api.DNBungeeAPI;
import be.alexandre01.eloriamc.API;
import be.alexandre01.eloriamc.data.mysql.DatabaseManager;
import be.alexandre01.eloriamc.data.redis.RedisManager;
import be.alexandre01.eloriamc.proxy.listener.ProxyJoin;
import be.alexandre01.eloriamc.proxy.listener.ProxyQuit;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class BungeePlugin extends Plugin {

    @Getter
    private static BungeePlugin instance;


    @Getter
    private DNBungeeAPI dnBungeeAPI;

    @Getter
    private DNChannel online;

    @Getter
    private HashMap<UUID,Long> timePlayed = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        API.getInstance().onOpen();

        this.getProxy().getPluginManager().registerListener(this, new ProxyJoin());
        this.getProxy().getPluginManager().registerListener(this, new ProxyQuit());
        this.getProxy().getPluginManager().registerListener(this, new JPremiumLogin());


        online = new DNChannel("online");

        dnBungeeAPI = (DNBungeeAPI) DNBungeeAPI.getInstance();
        DNChannelManager dnChannelManager = BungeePlugin.getInstance().getDnBungeeAPI().getChannelManager();
        online = dnChannelManager.registerChannel(online, false, new RegisterListener() {
            @Override
            public void onNewDataReceived(LinkedTreeMap<String, Object> linkedTreeMap) {
                createInitialData("all", 0);
            }
        });



    }

    @Override
    public void onDisable() {
        API.getInstance().onClose();
    }
}
