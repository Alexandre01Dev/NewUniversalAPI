package be.alexandre01.eloriamc.server;

import be.alexandre01.eloriamc.API;
import be.alexandre01.eloriamc.server.events.factories.EventsFactory;
import be.alexandre01.eloriamc.server.events.players.ListenerPlayerManager;
import be.alexandre01.eloriamc.server.packets.injector.AutoPacketInjectorJoin;
import be.alexandre01.eloriamc.server.packets.injector.PacketInjectorManager;
import be.alexandre01.eloriamc.server.packets.npc.NPC;
import be.alexandre01.eloriamc.server.packets.npc.NPCFactory;
import be.alexandre01.eloriamc.server.packets.npc.type.NPCUniversalEntity;
import be.alexandre01.eloriamc.server.packets.skin.SkinFactory;
import be.alexandre01.eloriamc.server.packets.ui.bossbar.BossBar;
import be.alexandre01.eloriamc.server.packets.ui.bossbar.BossBarManagerTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class SpigotPlugin extends JavaPlugin implements Listener {

    @Getter private static SpigotPlugin instance;
    @Getter
    private PacketInjectorManager packetInjectorManager;
    @Getter private EventsFactory eventsFactory;

    @Getter private final SkinFactory skinFactory = new SkinFactory();

    @Getter private final NPCFactory npcFactory = new NPCFactory();

    @Getter private ListenerPlayerManager listenerPlayerManager = new ListenerPlayerManager();

    @Getter private BossBarManagerTask bossBarManagerTask = new BossBarManagerTask();

    @Override
    public void onEnable() {
        instance = this;
        packetInjectorManager = new PacketInjectorManager();
        AutoPacketInjectorJoin.init(AutoPacketInjectorJoin.PacketInjectorType.INPUT_DECODER);

        API.getInstance().onOpen();
        eventsFactory = new EventsFactory();


        npcFactory.initialize(true);



        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        API.getInstance().onClose();
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        NPC npc = new NPC("§a§lCreepah",player.getLocation(),EntityType.CREEPER);
        npc.setInteraction(new NPC.NPCInteract() {
            @Override
            public void action(Player player, NPC.InteractClick click) {
                player.sendMessage("Ksss there, tu viens de "+ click.name());
            }
        });
        npc.get(player, NPCUniversalEntity.class);
        npc.initAndShow(player);



        BossBar bossBar = new BossBar("§fTheFuckingBossBar",100);
        bossBarManagerTask.addBossBar("TheFuckingBossBar",bossBar);

        //send bossbar
        bossBarManagerTask.setBossBar(player,"TheFuckingBossBar");
    }

    public void registerCommand(String commandName, Command commandClass){
        try{
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(commandName, commandClass);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
