package be.alexandre01.universal.server.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;


public interface NameTagImpl {

    default void setNameTag(Player player, String teamName,String prefix,String suffix){
        Scoreboard score = Bukkit.getScoreboardManager().getMainScoreboard();
        Team t = score.getTeam(teamName);

        if(t == null){
            t = score.registerNewTeam(teamName);
        }
        if(prefix != null){
            t.setPrefix(prefix);
        }

        if(suffix != null){
            t.setSuffix(suffix);
        }

        t.addPlayer(player);

        for(Player players : Bukkit.getOnlinePlayers()){
            players.setScoreboard(score);
        }
    }

    default void removeNameTag(Player player,String teamName){
        Scoreboard score = Bukkit.getScoreboardManager().getMainScoreboard();
        Team t = score.getTeam(teamName);
        if(t == null){
            t = score.registerNewTeam(teamName);
        }
        t.removePlayer(player);

        for(Player players : Bukkit.getOnlinePlayers()){

            players.setScoreboard(score);
        }
    }

}
