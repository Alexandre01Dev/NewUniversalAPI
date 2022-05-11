package be.alexandre01.eloriamc.server;

import be.alexandre01.eloriamc.API;
import be.alexandre01.eloriamc.chat.ChatConfiguration;
import be.alexandre01.eloriamc.server.events.factories.EventsFactory;
import be.alexandre01.eloriamc.server.events.players.ListenerPlayerManager;
import be.alexandre01.eloriamc.server.packets.injector.AutoPacketInjectorJoin;
import be.alexandre01.eloriamc.server.packets.injector.PacketInjectorManager;
import be.alexandre01.eloriamc.server.packets.npc.NPCFactory;
import be.alexandre01.eloriamc.server.packets.skin.SkinData;
import be.alexandre01.eloriamc.server.packets.skin.SkinFactory;
import be.alexandre01.eloriamc.server.player.GamePlayerManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotPlugin extends JavaPlugin {

    @Getter private static SpigotPlugin instance;
    @Getter
    private PacketInjectorManager packetInjectorManager;
    @Getter private EventsFactory eventsFactory;

    @Getter private final SkinFactory skinFactory = new SkinFactory();

    @Getter private final NPCFactory npcFactory = new NPCFactory();

    @Getter private ListenerPlayerManager listenerPlayerManager = new ListenerPlayerManager();


    @Override
    public void onEnable() {
        instance = this;
        packetInjectorManager = new PacketInjectorManager();
        AutoPacketInjectorJoin.init(AutoPacketInjectorJoin.PacketInjectorType.INPUT_DECODER);
        API.getInstance().onOpen();
        eventsFactory = new EventsFactory();
        skinFactory = new SkinFactory();
    }

    @Override
    public void onDisable() {
        API.getInstance().onClose();
    }


}
