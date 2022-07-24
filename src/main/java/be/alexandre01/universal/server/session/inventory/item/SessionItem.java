package be.alexandre01.universal.server.session.inventory.item;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@Getter @Setter
public class SessionItem {

    private final String itemName;
    private final ItemStack itemStack;

    private boolean isUniversal = true;

    private ItemInteractEvent itemInteractEvent;

    public SessionItem(String itemName, ItemStack itemStack) {
        this.itemName = itemName;
        this.itemStack = itemStack;
    }
    boolean isDroppable;

    public  boolean isDroppable() {
        return isDroppable;
    }

    public interface ItemInteractEvent {
        void onEvent(PlayerInteractEvent event);
    }

}
