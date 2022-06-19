package be.alexandre01.eloriamc.proxy.listener;

import be.alexandre01.eloriamc.API;
import be.alexandre01.eloriamc.data.PlayerData;
import be.alexandre01.eloriamc.data.SetupPlayerData;
import be.alexandre01.eloriamc.proxy.BungeePlugin;
import com.jakub.premium.JPremium;
import com.jakub.premium.api.User;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Optional;

public class ProxyJoin implements Listener {
    API api;
    public ProxyJoin(){
        api = API.getInstance();
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();

        //PlayerData playerData = new PlayerData(player.getName(), player.getUniqueId().toString(), 0,0, 0, 1, false, new Settings(true, true, true, true), new KbWarrior(), new Madness());
        PlayerData playerData = SetupPlayerData.setupPlayer(player.getName(), player.getUniqueId().toString());
        api.getPlayerDataManager().getPlayerDataHashMap().put(player.getName(), playerData);
        BungeePlugin.getInstance().getOnline().setData("all", ProxyServer.getInstance().getOnlineCount());

        Optional<User> user = JPremium.getApplication().getUserProfileByNickname(player.getName());
        player.sendMessage("Fuck");
        System.out.println(user);
        if(user.isPresent()) {
            if(user.get().isPremium()) {
                System.out.println("Premium");
                player.sendMessage("§aTu as été enregistré comme un Premium");
            } else {
                System.out.println("Non-Premium");
                player.sendMessage("§aTu as été enregistré comme un Non-Premium");
            }
        }
    }
}
