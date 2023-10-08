package be.alexandre01.universal.server.listener;


import be.alexandre01.dnplugin.api.request.channels.DNChannel;
import be.alexandre01.dnplugin.api.request.channels.DNChannelManager;
import be.alexandre01.dnplugin.api.request.channels.DataListener;
import be.alexandre01.dnplugin.api.request.channels.RegisterListener;
import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import be.alexandre01.dnplugin.plugins.spigot.api.events.server.ServerAttachedEvent;
import com.google.gson.internal.LinkedTreeMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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

        /*online = dnChannelManager.registerChannel(online, false, new RegisterListener() {
            @Override
            public void onNewDataReceived(LinkedTreeMap<String, Object> linkedTreeMap) {
                createInitialData("all", 0);
            }
        });*/
        dnSpigotAPI.autoRefreshPlayers();
    }
}
