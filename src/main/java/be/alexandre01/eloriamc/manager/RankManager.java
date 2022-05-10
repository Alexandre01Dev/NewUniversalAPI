package be.alexandre01.eloriamc.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.TechsCode.UltraPermissions.UltraPermissions;
import me.TechsCode.UltraPermissions.UltraPermissionsAPI;
import me.TechsCode.UltraPermissions.storage.collection.GroupList;
import me.TechsCode.UltraPermissions.storage.objects.Group;
import me.TechsCode.UltraPermissions.storage.objects.User;
import org.bukkit.entity.Player;

import java.util.Optional;

@AllArgsConstructor
public class RankManager {

    private final UltraPermissionsAPI ultraPermissionsAPI = UltraPermissions.getAPI();

    private Player player;

    public String getRankPrefix() {
        Optional<User> user = ultraPermissionsAPI.getUsers().name(player.getName());
        if(!user.isPresent()) return null;
        GroupList it = user.get().getActiveGroups().bestToWorst();
        if(it.isEmpty()) return null;
        Optional<String> prefix = it.get(0).getPrefix();
        return prefix.map(s -> s + " ").orElse(null);
    }

    public String getRankSuffix() {
        Optional<User> user = ultraPermissionsAPI.getUsers().name(player.getName());
        if(!user.isPresent()) return null;
        GroupList it = user.get().getActiveGroups().bestToWorst();
        if(it.isEmpty()) return null;
        Optional<String> prefix = it.get(0).getSuffix();
        return prefix.map(s -> s + " ").orElse(null);
    }

    public String getPlayerPrefix() {
        Optional<User> user = ultraPermissionsAPI.getUsers().name(player.getName());
        return user.map(value -> value.getPrefix().orElse(null) + " ").orElse(null);
    }

    public String getPlayerSuffix() {
        Optional<User> user = ultraPermissionsAPI.getUsers().name(player.getName());
        return user.map(value -> value.getSuffix().orElse(null) + " ").orElse(null);
    }
}
