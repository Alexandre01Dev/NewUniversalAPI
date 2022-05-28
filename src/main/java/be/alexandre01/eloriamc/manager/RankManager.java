package be.alexandre01.eloriamc.manager;

import be.alexandre01.eloriamc.data.PlayerData;
import be.alexandre01.eloriamc.data.PlayerDataManager;
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

    private String playerName;

    public String getRankPrefix() {
        Optional<User> user = ultraPermissionsAPI.getUsers().name(playerName);
        if(!user.isPresent()) return null;
        GroupList it = user.get().getActiveGroups().bestToWorst();
        if(it.isEmpty()) return null;
        Optional<String> prefix = it.get(0).getPrefix();
        return prefix.map(s -> s + " ").orElse(null);
    }

    public String getRankSuffix() {
        Optional<User> user = ultraPermissionsAPI.getUsers().name(playerName);
        if(!user.isPresent()) return null;
        GroupList it = user.get().getActiveGroups().bestToWorst();
        if(it.isEmpty()) return null;
        Optional<String> prefix = it.get(0).getSuffix();
        return prefix.map(s -> s + " ").orElse(null);
    }

    public String getGroup() {
        Optional<User> user = ultraPermissionsAPI.getUsers().name(playerName);
        if(!user.isPresent()) return null;
        GroupList it = user.get().getActiveGroups().bestToWorst();
        if(it.isEmpty()) return null;
        String groupName = it.get(0).getName();
        return groupName;
    }


    public String getPlayerPrefix() {
        Optional<User> user = ultraPermissionsAPI.getUsers().name(playerName);
        return user.map(value -> value.getPrefix().orElse(null) + " ").orElse(null);
    }

    public String getPlayerSuffix() {
        Optional<User> user = ultraPermissionsAPI.getUsers().name(playerName);
        return user.map(value -> value.getSuffix().orElse(null) + " ").orElse(null);
    }
}
