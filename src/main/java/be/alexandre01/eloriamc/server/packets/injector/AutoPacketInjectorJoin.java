package be.alexandre01.eloriamc.server.packets.injector;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AutoPacketInjectorJoin implements Listener {
    SpigotPlugin spigotPlugin;
    PacketInjectorType type;
    public AutoPacketInjectorJoin(SpigotPlugin plugin, PacketInjectorType type) {
        this.type = type;
        spigotPlugin = plugin;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        System.out.println("[EloRiaMC] Injecting packets for " + e.getPlayer().getName());
        PacketInjector packetInjector = new PacketInjector(e.getPlayer());
        if(type == PacketInjectorType.ALL || type == PacketInjectorType.INPUT_DECODER)
            packetInjector.injectInputDecoder();
        if(type == PacketInjectorType.ALL || type == PacketInjectorType.OUTPUT_ENCODER)
            packetInjector.injectOutputEncoder();
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        spigotPlugin.getPacketInjectorManager().getPacketInjectors().get(event.getPlayer()).uninjectAll();
    }

    public static void init(PacketInjectorType type) {
        SpigotPlugin s = SpigotPlugin.getInstance();
        Bukkit.getPluginManager().registerEvents(new AutoPacketInjectorJoin(s,type), s);
    }

    public enum PacketInjectorType {
        INPUT_DECODER, OUTPUT_ENCODER, ALL;
    }

}


