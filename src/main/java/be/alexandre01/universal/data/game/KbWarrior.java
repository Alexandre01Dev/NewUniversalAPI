package be.alexandre01.universal.data.game;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;

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
