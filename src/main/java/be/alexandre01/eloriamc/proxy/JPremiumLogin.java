package be.alexandre01.eloriamc.proxy;

import com.jakub.premium.api.event.UserEvent;
import com.jakub.premium.api.event.UserEvent$Login;
import com.jakub.premium.api.event.UserEvent$StartSession;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JPremiumLogin implements Listener {
    @EventHandler
    public void onJoin(UserEvent$StartSession event){
        String v;
        if(event.getUserProfile().isPremium()) {
            v = "Premium";
        } else {
            v = "Non-Premium";
        }
        event.getUserProfile().getProxiedPlayer().sendMessage("§aTu as été enregistré comme un " + v);
    }
}
