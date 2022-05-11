package be.alexandre01.eloriamc.server.packets.npc;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.events.factories.IEvent;
import be.alexandre01.eloriamc.server.events.players.IPlayerEvent;
import be.alexandre01.eloriamc.server.packets.Reflections;
import be.alexandre01.eloriamc.server.packets.skin.SkinData;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class NPC extends Reflections {

    @Getter
    private int entityID;
    @Getter
    private String name;
    @Getter
    private Location location;

    @Getter
    private SkinData skinData;

    @Getter
    private HashMap<Player,NPCPlayerInstance> playersInstance = new HashMap<>();

    @Getter @Setter private NPCInteract interaction;

    @Getter
    private GameProfile gameProfile;

    private SpigotPlugin plugin;



    public NPC(String name, Location location) {
        this.name = name;
        this.location = location;
        entityID = (int) Math.ceil(Math.random() * 1000) + 2000;
        gameProfile = new GameProfile(UUID.randomUUID(), name);
        this.plugin = SpigotPlugin.getInstance();
        if(plugin.getNpcFactory().isInitialized()) {
            plugin.getNpcFactory().addNPC(this);
        }
    }

    public void setSkin(String texture, String signature) {
        this.skinData = new SkinData(texture, signature);
    }

    public void setSkin(SkinData skinData) {
        this.skinData = skinData;
    }

    public NPCPlayerInstance get(Player player) {
        return playersInstance.get(player);
    }

    public NPCPlayerInstance getOrCreate(Player player) {
        if(!playersInstance.containsKey(player)) {
            return initAndShow(player);
        }
        return playersInstance.get(player);
    }
    public NPCPlayerInstance initAndShow(Player player) {
        if(!playersInstance.containsKey(player)) {
            SpigotPlugin spigotPlugin = SpigotPlugin.getInstance();
            spigotPlugin.getListenerPlayerManager().registerEvent(PlayerQuitEvent.class,"getPlayer",player, new IPlayerEvent<PlayerQuitEvent>() {
                @Override
                public void onPlayerEvent(PlayerQuitEvent event, Player player) {
                    playersInstance.remove(player);
                    spigotPlugin.getListenerPlayerManager().removeEvent(player,this);
                }
            });
            NPCPlayerInstance instance = new NPCPlayerInstance(this, player);
             playersInstance.put(player,instance);
             instance.show();
            return instance;
    }
        return null;
    }


    public interface NPCInteract {
         public void action(Player player,InteractClick click);
    }

    public enum InteractClick {
        RIGHT,LEFT;
    }

}
