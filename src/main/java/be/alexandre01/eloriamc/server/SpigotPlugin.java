package be.alexandre01.eloriamc.server;

import be.alexandre01.eloriamc.API;
import be.alexandre01.eloriamc.server.events.factories.EventsFactory;
import be.alexandre01.eloriamc.server.events.players.ListenerPlayerManager;
import be.alexandre01.eloriamc.server.packets.injector.AutoPacketInjectorJoin;
import be.alexandre01.eloriamc.server.packets.injector.PacketInjectorManager;
import be.alexandre01.eloriamc.server.packets.npc.NPC;
import be.alexandre01.eloriamc.server.packets.npc.NPCFactory;
import be.alexandre01.eloriamc.server.packets.npc.type.NPCUniversalEntity;
import be.alexandre01.eloriamc.server.packets.skin.MojangUtils;
import be.alexandre01.eloriamc.server.packets.skin.SkinData;
import be.alexandre01.eloriamc.server.packets.skin.SkinFactory;
import be.alexandre01.eloriamc.server.packets.skin.SkinPlayer;
import be.alexandre01.eloriamc.server.packets.ui.bossbar.BossBar;
import be.alexandre01.eloriamc.server.packets.ui.bossbar.BossBarManagerTask;
import be.alexandre01.eloriamc.server.player.GamePlayer;
import be.alexandre01.eloriamc.server.player.GamePlayerManager;
import be.alexandre01.eloriamc.server.session.runnables.UpdateFactory;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.UUID;

public class SpigotPlugin extends JavaPlugin implements Listener {

    @Getter private static SpigotPlugin instance;
    @Getter
    private PacketInjectorManager packetInjectorManager;
    @Getter private EventsFactory eventsFactory;

    @Getter private final SkinFactory skinFactory = new SkinFactory();

    @Getter private final NPCFactory npcFactory = new NPCFactory();

    @Getter private ListenerPlayerManager listenerPlayerManager = new ListenerPlayerManager();

    @Getter private BossBarManagerTask bossBarManagerTask = new BossBarManagerTask();

    @Getter private UpdateFactory updateFactory = new UpdateFactory();

    @Getter private GamePlayerManager gamePlayerManager= new GamePlayerManager();


    @Override
    public void onEnable() {
        instance = this;
        packetInjectorManager = new PacketInjectorManager();
        AutoPacketInjectorJoin.init(AutoPacketInjectorJoin.PacketInjectorType.INPUT_DECODER);

        API.getInstance().onOpen();
        eventsFactory = new EventsFactory();
        npcFactory.initialize(true);

        getServer().getPluginManager().registerEvents(this, this);
        registerCommand("skin", new Command("Skin") {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] strings) {
                Player player = (Player) commandSender;
                if(strings.length == 0){

                    SkinData skinData = skinFactory.getSkinData("Boug1");

                    SkinPlayer skinPlayer = new SkinPlayer(player);
                    skinPlayer.applySkin(skinData);
                    return true;
                }

                String uuid = MojangUtils.getUUID(strings[0]);
                if(uuid == null){
                    player.sendMessage("§cPlayer not found");
                    return false;
                }

                SkinData skinData =  MojangUtils.getSkinDataFromUUID(uuid);
                if(skinData == null){
                    player.sendMessage("§cPlayerSkin not found");
                    return false;
                }

                SkinPlayer skinPlayer = new SkinPlayer(player);
                skinPlayer.applySkin(skinData);




                return false;
            }
        });

        skinFactory.registerSkinData("Boug1",new SkinData("ewogICJ0aW1lc3RhbXAiIDogMTY1MTI0OTM1NTkxOCwKICAicHJvZmlsZUlkIiA6ICIxMGZhZDhhOWVmZTQ0NzEzYmYxMThjY2MzODRkZTU3NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJDb2ZmZWVCdW5ueSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80NTNkM2I2MmFkOTVlMjFkZDJiOTFiZGFhMDI5OWViNWJhYWZkYzEyYmYxN2UxMjAwZGI5OWMwNTQwZWJjYjI2IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0sCiAgICAiQ0FQRSIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjBjYzA4ODQwNzAwNDQ3MzIyZDk1M2EwMmI5NjVmMWQ2NWExM2E2MDNiZjY0YjE3YzgwM2MyMTQ0NmZlMTYzNSIKICAgIH0KICB9Cn0=","fUy+nnSryBoMJQiTOqfuCTlxErYexvhseHEDqimCNfnQ8nRSfGIH8PCXv6mTq7p+3hJ0ZjE9n5bDXsEGPhLz9MJyU2QHfK1k2mdqHEopPUSsy88b/E1+xib2ZS5Lbv/lP843Al1ABupnaOgboOxknt4tiO5QgKowIqJDzffF0DYMbAKe2QBKxDKZiZeFFoSkmvJnd0fLf6AK5je7KzBxebWYzOrPdkbSANO5tNtZ79rJzAYb6lOe6osZ3yycJ7HRZVicB3WJA9znGSr/CjQQLw1XU0vKbC26MoG8sTHAy1GJxEWe7eMBkWyo7IPbJj6+lD7DQJ6PZUfMRfQ+NMRRja0UqMgC5jQmQQCgvAWEclmZSmbv9V9cPUa/kifIZVI9ySqx395Dl6ghQdh7pnk3+weHxyE44vVH9qnisPF/TS2LeD4I/ZLPhcgZpZ/98oavbznckdARcAwHZNWtSYP7KNGde6qBkN7LnjKfM1B5bqrAcSF8RWQJ26aUespULN9thpuA6lvsNJKMa39up0qeaaqI25Sp9eH+qsT5qHA6SYhm2Hs+tttnvPlN2LZQ6poobJ4ALYbkAR7JR8bwxQvfppBHz1z3WLsTu6JvVa3CVJn+2i4Fi9iMcpNZFaiA58PFNXI4sZR3DEmRMyt8cbb536Vzr3/1L791366V8V++dh0="));

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

        getGamePlayer(player);

    }



    public GamePlayer getGamePlayer(Player player){

        if(getGamePlayerManager().getPlayerHashMap().containsKey(player)){
            return getGamePlayerManager().getPlayerHashMap().get(player);
        }
        return new GamePlayer(player);
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
