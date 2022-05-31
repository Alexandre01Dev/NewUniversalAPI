package be.alexandre01.eloriamc.proxy.listener;

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

    @EventHandler
    public void onPostLogin(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();

        PlayerData playerData = new PlayerData(player.getName(), player.getUniqueId().toString(), 0, (float) 0.00, false, new Settings(true, true, true, true), new KbWarrior(0, 0, 0, 0, "§6§lBronze IV"), new Madness(0, 0, 0, 0, "§6§lBronze IV"));
        playerData.setupPlayer();

        BungeePlugin.getInstance().getOnline().setData("all", ProxyServer.getInstance().getOnlineCount());
    }
}
