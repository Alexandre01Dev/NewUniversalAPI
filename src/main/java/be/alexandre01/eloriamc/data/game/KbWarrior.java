package be.alexandre01.eloriamc.data.game;

import be.alexandre01.eloriamc.data.PlayerData;
import be.alexandre01.eloriamc.data.TypeData;
import be.alexandre01.eloriamc.data.impl.IPlayerData;
import be.alexandre01.eloriamc.data.mysql.Mysql;
import be.alexandre01.eloriamc.data.redis.RedisManager;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.SQLException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KbWarrior {
    private int kill;
    private int death;
    private int bestKs;
    private float elo;
    private String division;
}
