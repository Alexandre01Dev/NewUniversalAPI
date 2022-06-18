package be.alexandre01.eloriamc.server.packets.ui.scoreboard;

import be.alexandre01.eloriamc.server.player.BasePlayer;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class ScoreboardImpl<T extends BasePlayer> {
    protected ObjectiveSign objectiveSign;
    protected T player;
    public ScoreboardImpl() {
    }


    protected abstract void reloadData();
    protected abstract void setLines(String ip);


}
