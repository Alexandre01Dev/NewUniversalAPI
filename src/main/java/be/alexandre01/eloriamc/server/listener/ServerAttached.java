package be.alexandre01.eloriamc.server.listener;

import be.alexandre01.dreamnetwork.api.request.channels.DNChannel;
import be.alexandre01.dreamnetwork.api.request.channels.DNChannelManager;
import be.alexandre01.dreamnetwork.api.request.channels.DataListener;
import be.alexandre01.dreamnetwork.api.request.channels.RegisterListener;
import be.alexandre01.dreamnetwork.gson.internal.LinkedTreeMap;
import be.alexandre01.dreamnetwork.plugins.spigot.api.DNSpigotAPI;
import be.alexandre01.dreamnetwork.plugins.spigot.api.events.server.ServerAttachedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ServerAttached implements Listener {
    public DNChannel online;
    public int count;


    @EventHandler
    public void onServerAttachement(ServerAttachedEvent e) {

        DNSpigotAPI dnSpigotAPI = (DNSpigotAPI) DNSpigotAPI.getInstance();

        DNChannelManager dnChannelManager = dnSpigotAPI.getChannelManager();

        online = new DNChannel("online");

        online.setDataListener("all", Integer.class, new DataListener<Integer>() {
            @Override
            public void onUpdateData(Integer integer) {
                count = integer;
            }
        });

        online = dnChannelManager.registerChannel(online, false, new RegisterListener() {
            @Override
            public void onNewDataReceived(LinkedTreeMap<String, Object> linkedTreeMap) {
                createInitialData("all", 0);
            }
        });


    }
}
