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
import org.bukkit.Material;
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
    @Expose private float exp = 0;
    @Expose private int[] killMsg = {0};
    @Expose private int[] killAnim = {0};
    @Expose private int[] blockList = {0};

    @Expose private int blockChoose = 159;


    public void addBlock(Material block){
        addBlock(block.getId());
    }

    private void addBlock(int b){
        int[] newBlockList = new int[blockList.length + 1];
        for(int i = 0; i < blockList.length; i++){
            newBlockList[i] = blockList[i];
        }
        newBlockList[blockList.length] = b;
        blockList = newBlockList;
    }

    public void removeBlock(Material block){
        removeBlock(block.getId());
    }

    private void removeBlock(int b){
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
