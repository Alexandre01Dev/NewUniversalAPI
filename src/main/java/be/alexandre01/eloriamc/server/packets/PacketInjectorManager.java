package be.alexandre01.eloriamc.server.packets;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
@Getter
public class PacketInjectorManager {
    private final HashMap<Player,PacketInjector> packetInjectors;
    private final ArrayList<PacketInterceptor> interceptors;

    public PacketInjectorManager() {
        this.packetInjectors = new HashMap<>();
        this.interceptors = new ArrayList<>();
    }

    public void addInjector(PacketInjector packetInjector){
        packetInjectors.put(packetInjector.player,packetInjector);
    }
    public void addInterceptor(PacketInterceptor packetInterceptor){
        interceptors.add(packetInterceptor);
    }
    public boolean isInjected(Player player){
        return packetInjectors.get(player).isInjected;
    }
}
