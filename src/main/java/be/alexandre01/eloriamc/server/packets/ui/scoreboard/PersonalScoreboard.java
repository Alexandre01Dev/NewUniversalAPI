package be.alexandre01.eloriamc.server.packets.ui.scoreboard;

import be.alexandre01.eloriamc.server.player.BasePlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class PersonalScoreboard<T extends BasePlayer> {
    private T player;
    private final UUID uuid;
    private final ObjectiveSign objectiveSign;

    @Getter
    private ScoreboardImpl<T> scoreboardImpl;

    public PersonalScoreboard(T player){
        this.player = player;
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "DevPlugin");

        //reloadData();
        //objectiveSign.addReceiver(player);
    }

    public void setScoreboardImpl(ScoreboardImpl<T> scoreboardImpl){
        this.scoreboardImpl = scoreboardImpl;
        scoreboardImpl.setPlayer(player);
        scoreboardImpl.setObjectiveSign(objectiveSign);
        reloadData();
        if(!objectiveSign.receivers.contains(player))
            objectiveSign.addReceiver(player);
    }

    public void reloadData(){
        scoreboardImpl.reloadData();
    }

    public void setLines(String ip){
        scoreboardImpl.setLines(ip);
    }

    public void onLogout(){
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }
}