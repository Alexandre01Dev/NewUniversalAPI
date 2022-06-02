package be.alexandre01.eloriamc.proxy.listener;

import be.alexandre01.eloriamc.API;
import be.alexandre01.eloriamc.data.PlayerData;
import be.alexandre01.eloriamc.data.Settings;
import be.alexandre01.eloriamc.data.game.KbWarrior;
import be.alexandre01.eloriamc.data.game.Madness;
import be.alexandre01.eloriamc.proxy.BungeePlugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.bukkit.craftbukkit.Main;

import java.util.ArrayList;
import java.util.List;

public class ProxyJoin implements Listener {
    API api;
    public ProxyJoin(){
        api = API.getInstance();
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();

        PlayerData playerData = new PlayerData(player.getName(), player.getUniqueId().toString(), 0,0, 0, 1, false, new Settings(true, true, true, true), new KbWarrior(), new Madness());
        playerData.setupPlayer();
        api.getPlayerDataManager().getPlayerDataHashMap().put(player.getName(), playerData);

        BungeePlugin.getInstance().getOnline().setData("all", ProxyServer.getInstance().getOnlineCount());
    }
}
