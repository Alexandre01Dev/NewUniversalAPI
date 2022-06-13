package be.alexandre01.eloriamc.data.game;

import be.alexandre01.eloriamc.data.PlayerData;
import be.alexandre01.eloriamc.data.TypeData;
import be.alexandre01.eloriamc.data.impl.IPlayerData;
import be.alexandre01.eloriamc.data.mysql.Mysql;
import be.alexandre01.eloriamc.data.redis.RedisManager;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.SQLException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KbWarrior extends Identifier {
    @Expose private int kill = 0;
    @Expose private int death = 0;
    @Expose private int bestKs = 0;
    @Expose private float elo = 0;
    @Expose private String division = "§6§lBronze IV";
}
