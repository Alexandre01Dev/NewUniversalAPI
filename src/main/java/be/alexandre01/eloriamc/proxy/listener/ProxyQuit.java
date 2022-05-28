package be.alexandre01.eloriamc.proxy.listener;

import be.alexandre01.eloriamc.data.PlayerData;
import be.alexandre01.eloriamc.data.PlayerDataManager;
import be.alexandre01.eloriamc.proxy.BungeePlugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {
        ProxiedPlayer player = e.getPlayer();

        PlayerData playerData = PlayerDataManager.getPlayerData(player.getName());
        playerData.savePlayer();

        BungeePlugin.getInstance().getOnline().setData("all", ProxyServer.getInstance().getOnlineCount() - 1);

    }
}
