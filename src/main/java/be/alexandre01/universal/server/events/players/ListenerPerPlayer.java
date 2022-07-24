package be.alexandre01.universal.server.events.players;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ListenerPerPlayer {
    @Getter
    ArrayList<IPlayerEvent> listeners = new ArrayList<>();

    @Getter private Player player;

    public ListenerPerPlayer(Player player){
        this.player = player;
    }
}
