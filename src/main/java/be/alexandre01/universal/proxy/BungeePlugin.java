package be.alexandre01.universal.proxy;

import be.alexandre01.dnplugin.api.request.channels.DNChannel;
import be.alexandre01.dnplugin.api.request.channels.DNChannelManager;
import be.alexandre01.dnplugin.api.request.channels.RegisterListener;
import be.alexandre01.dnplugin.plugins.bungeecord.api.DNBungeeAPI;
import be.alexandre01.universal.API;
import be.alexandre01.universal.proxy.listener.ProxyJoin;
import be.alexandre01.universal.proxy.listener.ProxyQuit;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

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

       // API.getInstance().onOpen();

        this.getProxy().getPluginManager().registerListener(this, new ProxyJoin());
        this.getProxy().getPluginManager().registerListener(this, new ProxyQuit());



        online = new DNChannel("online");

        dnBungeeAPI = (DNBungeeAPI) DNBungeeAPI.getInstance();
        DNChannelManager dnChannelManager = BungeePlugin.getInstance().getDnBungeeAPI().getChannelManager();
       /* online = dnChannelManager.registerChannel(online, false, new RegisterListener() {
            @Override
            public void onNewDataReceived(LinkedTreeMap<String, Object> linkedTreeMap) {
                createInitialData("all", 0);
            }
        });*/




    }

    @Override
    public void onDisable() {
     //   API.getInstance().onClose();
    }
}
