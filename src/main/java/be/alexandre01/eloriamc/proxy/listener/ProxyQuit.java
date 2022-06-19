package be.alexandre01.eloriamc.proxy.listener;

import be.alexandre01.eloriamc.API;
import be.alexandre01.eloriamc.data.PlayerData;
import be.alexandre01.eloriamc.data.PlayerDataManager;
import be.alexandre01.eloriamc.proxy.BungeePlugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyQuit implements Listener {
    API api;
    BungeePlugin plugin;
    public ProxyQuit(){
        api = API.getInstance();
        plugin = BungeePlugin.getInstance();
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {
        ProxiedPlayer player = e.getPlayer();

        PlayerData playerData = api.getPlayerDataManager().getPlayerData(player.getName());
        long dif = System.currentTimeMillis()-plugin.getTimePlayed().get(player.getUniqueId());
        playerData.setTimePlayed(playerData.getTimePlayed()+dif);
        playerData.savePlayer();
        api.getPlayerDataManager().getPlayerDataHashMap().remove(player.getName());

        plugin.getOnline().setData("all", ProxyServer.getInstance().getOnlineCount() - 1);
    }
}
