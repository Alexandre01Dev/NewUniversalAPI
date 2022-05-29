package be.alexandre01.eloriamc.server.session.inventory.stuff;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;

@Getter
public class Stuff {
    private String name;
    private Integer ovewriteSlot = null;
    private HashMap<Integer,ItemStack> itemStacks;

    public Stuff(String name){
        this.name = name;
        itemStacks = new HashMap<>();
    }


    public void setItem(int slot, ItemStack itemStack){
        itemStacks.put(slot,itemStack);
    }

    public void removeItem(int slot){
        itemStacks.remove(slot);
    }

    public void distributeToPlayer(Player player){
        Collection<ItemStack> itemStacks = this.itemStacks.values();
        for(ItemStack itemStack : itemStacks){
            player.getInventory().addItem(itemStack);
        }

        if(ovewriteSlot != null){
            player.getInventory().setHeldItemSlot(ovewriteSlot);
        }

        player.updateInventory();
    }
}
