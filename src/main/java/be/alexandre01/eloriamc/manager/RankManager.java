package be.alexandre01.eloriamc.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.TechsCode.UltraPermissions.UltraPermissions;
import me.TechsCode.UltraPermissions.UltraPermissionsAPI;
import me.TechsCode.UltraPermissions.storage.collection.GroupList;
import me.TechsCode.UltraPermissions.storage.objects.User;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class RankManager {

    private final UltraPermissionsAPI ultraPermissionsAPI = UltraPermissions.getAPI();

    private Player player;

    public String getRankPrefix() {
        User user = ultraPermissionsAPI.getUsers().name(player.getName()).get();
        GroupList it = user.getActiveGroups().bestToWorst();
        if(it.isEmpty()) return null;
        return it.get(0).getPrefix().get() + " ";
    }

    public String getRankSuffix() {
        User user = ultraPermissionsAPI.getUsers().name(player.getName()).get();
        GroupList it = user.getActiveGroups().bestToWorst();
        if(it.isEmpty()) return null;
        return it.get(0).getSuffix().get() + " ";
    }

    public String getPlayerPrefix() {
        User user = ultraPermissionsAPI.getUsers().name(player.getName()).get();
        return user.getPrefix().get() + " ";
    }

    public String getPlayerSuffix() {
        User user = ultraPermissionsAPI.getUsers().name(player.getName()).get();
        return user.getSuffix().get() + " ";
    }
}
