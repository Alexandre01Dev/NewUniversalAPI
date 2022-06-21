package be.alexandre01.eloriamc.server.session.players;

import be.alexandre01.eloriamc.server.player.BasePlayer;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerDamager {
    private HashMap<BasePlayer,Long> damagerTime = new HashMap<>();
    @Getter private Tuple<BasePlayer,Long> lastDamager = null;

    private Integer assist;
    public PlayerDamager() {
    }

    public void addDamager(BasePlayer player){
        damagerTime.put(player, System.currentTimeMillis());
        lastDamager = new Tuple<>(player, System.currentTimeMillis());
    }

    public void setTotalAssist(Integer assist){
        this.assist = assist;
    }

    public ArrayList<BasePlayer> getDamagers(){
        ArrayList<BasePlayer> players = new ArrayList<>();
        if(lastDamager != null && System.currentTimeMillis() - lastDamager.b() < 7000){
            players.add(lastDamager.a());
        }
        players.addAll(getAssists());
        return players;
    }

    public ArrayList<BasePlayer> getAssists(){
        ArrayList<BasePlayer> players = new ArrayList<>();
        for(BasePlayer player : damagerTime.keySet()){
            if(System.currentTimeMillis() - damagerTime.get(player) < 7000){
                players.add(player);
            }
        }
        return players;
    }
}
