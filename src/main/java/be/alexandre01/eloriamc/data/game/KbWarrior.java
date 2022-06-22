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
import org.bukkit.block.Block;

import java.sql.SQLException;
import java.util.List;

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
    @Expose private boolean ranked = false;
    @Expose private int[] blockList = {0};


    public void addBlock(Block block){
        addBlock(block.getTypeId());
    }
    public void addBlock(int b){
        int[] newBlockList = new int[blockList.length + 1];
        for(int i = 0; i < blockList.length; i++){
            newBlockList[i] = blockList[i];
        }
        newBlockList[blockList.length] = b;
        blockList = newBlockList;
    }

    public void removeBlock(Block block){
        removeBlock(block.getTypeId());
    }

    public void removeBlock(int b){
        int[] newBlockList = new int[blockList.length - 1];
        int j = 0;
        for(int i = 0; i < blockList.length; i++){
            if(blockList[i] != b){
                newBlockList[j] = blockList[i];
                j++;
            }
        }
        blockList = newBlockList;
    }


}
