package be.alexandre01.eloriamc.server.packets.ui.scoreboard;

import be.alexandre01.eloriamc.server.player.BasePlayer;

public abstract class ScoreboardImpl<T extends BasePlayer> {
    protected ObjectiveSign objectiveSign;
    protected T player;
    public ScoreboardImpl(ObjectiveSign objectiveSign, T player) {
        this.objectiveSign = objectiveSign;
        this.player = player;
    }
    protected abstract void reloadData();
    protected abstract void setLines(String ip);


}
