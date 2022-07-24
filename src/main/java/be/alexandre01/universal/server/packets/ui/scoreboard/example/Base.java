package be.alexandre01.universal.server.packets.ui.scoreboard.example;

import be.alexandre01.universal.server.packets.ui.scoreboard.ScoreboardImpl;
import be.alexandre01.universal.server.player.BasePlayer;
import org.bukkit.Bukkit;

public class Base<T extends BasePlayer> extends ScoreboardImpl<T> {


    public Base() {
        super();
    }

    @Override
    public void reloadData() {
        
    }

    @Override
    public void setLines(String ip) {
        objectiveSign.setDisplayName("§eServeur");

        objectiveSign.setLine(0, "§1");
        objectiveSign.setLine(1, "§6Joueurs: §a" + Bukkit.getOnlinePlayers().size() + "/20");
        objectiveSign.setLine(2, "§6Pseudo: §b" + player.getName());
        objectiveSign.setLine(3, "§2");
        objectiveSign.setLine(4, ip);

        objectiveSign.updateLines();
    }
}
