package be.alexandre01.eloriamc.data.success.list;

import be.alexandre01.eloriamc.API;
import be.alexandre01.eloriamc.data.success.Success;
import be.alexandre01.eloriamc.server.player.BasePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.concurrent.TimeUnit;

public class TimePlayed extends Success {

    public TimePlayed(int hours,int moneyGive,int expGive) {
        super("TimePlayed", CheckType.ON_JOIN);
        this.setMoneyGive(moneyGive);
        this.setExpGive(expGive);
        this.setCheckOnEvent(new CheckOnEvent<PlayerJoinEvent>() {
            @Override
            public boolean checkOnEvent(PlayerJoinEvent event) {
                BasePlayer player = getSpigotPlugin().getBasePlayer(event.getPlayer());
                long l = player.getData().getTimePlayed();
                long h = TimeUnit.MILLISECONDS.toHours(l);

                if(h >= hours){
                    return true;
                }
                return false;
            }
        });
    }

}
