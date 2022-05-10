package be.alexandre01.eloriamc.server;

import be.alexandre01.eloriamc.API;
import be.alexandre01.eloriamc.server.packets.injector.AutoPacketInjectorJoin;
import be.alexandre01.eloriamc.server.packets.injector.PacketInjectorManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotPlugin extends JavaPlugin {

    @Getter private static SpigotPlugin instance;
    @Getter
    private PacketInjectorManager packetInjectorManager;



    @Override
    public void onEnable() {
        instance = this;
        packetInjectorManager = new PacketInjectorManager();
        AutoPacketInjectorJoin.init(AutoPacketInjectorJoin.PacketInjectorType.INPUT_DECODER);
        API.getInstance().onOpen();
    }

    @Override
    public void onDisable() {
        API.getInstance().onClose();
    }


}
