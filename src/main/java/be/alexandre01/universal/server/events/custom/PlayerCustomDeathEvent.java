package be.alexandre01.universal.server.events.custom;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerCustomDeathEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    @Getter
    private Player player;
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public PlayerCustomDeathEvent(Player player){
        this.player = player;
    }
}
