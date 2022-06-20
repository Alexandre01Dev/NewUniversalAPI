package be.alexandre01.eloriamc.data.success;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;


@Setter @Getter
public class Success {

    public Success(){
        spigotPlugin = SpigotPlugin.getInstance();
    }
    private SpigotPlugin spigotPlugin;
    private String name;
    private CheckType checkType;
    private int expGive;
    private int moneyGive;
    CheckOnEvent<?> checkOnEvent;

    private Reward reward;
    private Check check;


    public Success(String name, CheckType checkType) {
        this.name = name;
    }


    public void giveReward(Player player) {
        reward.reward(player);
    }
    public enum CheckType {
        ON_JOIN,
        PLAYER_KILLS,
        CUSTOM_CHECK;
    }

    public interface Check {
        boolean check();
    }
    public interface CheckOnEvent<T extends Event> {
        boolean checkOnEvent(T event);
    }

    public interface Reward {
        void reward(Player player);
    }
}
