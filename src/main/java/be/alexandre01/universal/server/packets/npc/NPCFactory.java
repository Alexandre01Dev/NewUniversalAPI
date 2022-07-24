package be.alexandre01.universal.server.packets.npc;

import be.alexandre01.universal.server.SpigotPlugin;
import be.alexandre01.universal.server.packets.skin.SkinData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;

public class NPCFactory {
    private HashMap<Integer,NPC> npcs = new HashMap<>();
    @Getter private boolean isInitialized = false;


    private NPCFactory load(boolean autoRead){
        isInitialized = true;
        if(autoRead) {
            Bukkit.getPluginManager().registerEvents(new NPCAutoPacketReadListener(this), SpigotPlugin.getInstance());
        }
        return this;
    }

    public NPCFactory initialize(boolean autoReadInteractions){
        return load(autoReadInteractions);
    }


    public NPC createNPC(String name, SkinData skin, Location location){
        NPC npc = new NPC(name,location);
        npc.setSkin(skin);
        npcs.put(npc.getEntityID(),npc);
        return npc;
    }

    public NPC getNPC(int entityID){
        return npcs.get(entityID);
    }

    public boolean containsNPC(int entityID){
        return npcs.containsKey(entityID);
    }

    public void removeNPC(int entityID){
        npcs.remove(entityID);
    }

    public void addNPC(NPC npc){
        npcs.put(npc.getEntityID(),npc);
    }
}
