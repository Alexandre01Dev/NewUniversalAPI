package be.alexandre01.eloriamc.server;

import be.alexandre01.eloriamc.API;
import be.alexandre01.eloriamc.config.yaml.YamlUtils;
import be.alexandre01.eloriamc.data.PlayerData;
import be.alexandre01.eloriamc.data.commands.PlayerDataModifier;
import be.alexandre01.eloriamc.server.commands.RcList;
import be.alexandre01.eloriamc.server.commands.ReportChat;
import be.alexandre01.eloriamc.server.events.factories.EventsFactory;
import be.alexandre01.eloriamc.server.events.players.ListenerPlayerManager;
import be.alexandre01.eloriamc.server.listener.PlayerJoin;
import be.alexandre01.eloriamc.server.listener.PlayerQuit;
import be.alexandre01.eloriamc.server.listener.ReloadListener;
import be.alexandre01.eloriamc.server.listener.ServerAttached;

import be.alexandre01.eloriamc.server.manager.MessageData;
import be.alexandre01.eloriamc.server.modules.CustomClassLoader;
import be.alexandre01.eloriamc.server.modules.Module;
import be.alexandre01.eloriamc.server.modules.ModuleLoader;
import be.alexandre01.eloriamc.server.packets.injector.AutoPacketInjectorJoin;
import be.alexandre01.eloriamc.server.packets.injector.PacketInjectorManager;
import be.alexandre01.eloriamc.server.packets.npc.NPCFactory;
import be.alexandre01.eloriamc.server.packets.skin.*;
import be.alexandre01.eloriamc.server.packets.ui.bossbar.BossBarManagerTask;
import be.alexandre01.eloriamc.server.player.BasePlayer;
import be.alexandre01.eloriamc.server.player.BasePlayerManager;
import be.alexandre01.eloriamc.server.session.Session;
import be.alexandre01.eloriamc.server.session.SessionManager;
import be.alexandre01.eloriamc.server.session.runnables.Task;
import be.alexandre01.eloriamc.server.session.runnables.UpdateFactory;
import be.alexandre01.eloriamc.server.session.runnables.Updater;
import be.alexandre01.eloriamc.utils.Tuple;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class SpigotPlugin extends JavaPlugin implements Listener {

    @Getter private static SpigotPlugin instance;
    @Getter
    private PacketInjectorManager packetInjectorManager;
    @Getter private EventsFactory eventsFactory;

    @Getter private final SkinFactory skinFactory = new SkinFactory();

    @Getter private final NPCFactory npcFactory = new NPCFactory();

    @Getter private final ListenerPlayerManager listenerPlayerManager = new ListenerPlayerManager();

    @Getter private final BossBarManagerTask bossBarManagerTask = new BossBarManagerTask();

    @Getter private final UpdateFactory updateFactory = new UpdateFactory(this);

    @Getter private final BasePlayerManager basePlayerManager = new BasePlayerManager();

    @Getter private final ModuleLoader moduleLoader = new ModuleLoader(this);

    @Getter private final SessionManager sessionManager = SessionManager.getInstance();
    @Getter private ServerAttached serverAttached;

    @Getter private HashMap<Integer, MessageData> messageData = new HashMap<>();



    public boolean isReloading = false;

    @Override
    public void onEnable() {
        instance = this;
        saveConfig();
        saveDefaultConfig();


        packetInjectorManager = new PacketInjectorManager();
        AutoPacketInjectorJoin.init(AutoPacketInjectorJoin.PacketInjectorType.INPUT_DECODER);

        API.getInstance().onOpen();
        eventsFactory = new EventsFactory();
        npcFactory.initialize(true);
        String s = System.getProperty("B");
        boolean b = false;

        if(!API.getInstance().isNoDream()){
            this.getServer().getPluginManager().registerEvents(serverAttached = new ServerAttached(), this);
        }
        this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        this.getServer().getPluginManager().registerEvents(new ReloadListener(), this);

        //getServer().getPluginManager().registerEvents(new PlayerListener(this), this);


        //getServer().getPluginManager().registerEvents(this, this);

        registerCommand("reportchat", new ReportChat("reportchat"));
        registerCommand("rclist", new RcList("rclist"));
        registerCommand("pd", new PlayerDataModifier("pd"));

        registerCommand("skin", new Command("Skin") {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] strings) {
                Player player = (Player) commandSender;

                new Thread(){
                    @Override
                    public void run() {
                        String uuid = MojangUtils.getUUID(strings[0]);
                        if(uuid == null){
                            player.sendMessage("§cPlayer not found");
                            return;
                        }

                        SkinData skinData =  MojangUtils.getSkinDataFromUUID(uuid);
                        if(skinData == null){
                            player.sendMessage("§cPlayerSkin not found");
                            return;
                        }

                        Bukkit.getScheduler().scheduleSyncDelayedTask(SpigotPlugin.this,() -> {
                            SkinPlayer skinPlayer = new SkinPlayer(player);
                            skinPlayer.applySkin(skinData);
                        });


                    }
                }.start();


                return false;
            }
        });
        registerCommand("cskin", new Command("CSkin") {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] strings) {
                Player player = (Player) commandSender;

                new Thread(){
                    @Override
                    public void run() {
                        if(SpigotPlugin.getInstance().getSkinFactory().getSkinsFile().containsKey(strings[0])){
                            SkinFile skinFile = SpigotPlugin.getInstance().getSkinFactory().getSkinsFile().get(strings[0]);
                            skinFile.readFile();
                            Bukkit.getScheduler().scheduleSyncDelayedTask(SpigotPlugin.this,() -> {
                                SkinPlayer skinPlayer = new SkinPlayer(player);
                                skinPlayer.applySkin(skinFile.getSkinData());
                            });
                        }
                    }
                }.start();


                return false;
            }
        });
        registerCommand("rskin", new Command("RSkin") {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] strings) {
                Player player = (Player) commandSender;
                SpigotPlugin.getInstance().getSkinFactory().readFiles();

                commandSender.sendMessage("§aSkin reloaded !");


                return false;
            }
        });

        registerCommand("erl", new Command("ERl") {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] strings) {
                for(Session session : getSessionManager().getDefaultSessions()){
                    session.finish();
                }
                getSessionManager().getDefaultSessions().clear();
                moduleLoader.getCachedModule().clear();
                moduleLoader.getModules().clear();
                moduleLoader.getCachedModule().addAll(moduleLoader.getModules());
                moduleLoader.load();
                moduleLoader.getCachedModule().clear();
                for(Task tuple : updateFactory.getBukkitTasks().values()){
                   tuple.cancel();
                }

                moduleLoader.getModules().forEach(module -> {
                    Class<?> c = module.getDefaultSession();
                    Session session = null;
                    try {
                        session = (Session<?>) c.newInstance();
                        session.processStart();
                        for(Player player : getServer().getOnlinePlayers()){
                            session.addPlayer(player);
                        }

                        sessionManager.getDefaultSessions().add(session);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(session.getName() + " is not supported");
                    }
                });
                commandSender.sendMessage("§eEloriaAPI has been §lreloaded !");

                return false;
            }
        });
        skinFactory.registerSkinData("Boug1",new SkinData("ewogICJ0aW1lc3RhbXAiIDogMTY1MTI0OTM1NTkxOCwKICAicHJvZmlsZUlkIiA6ICIxMGZhZDhhOWVmZTQ0NzEzYmYxMThjY2MzODRkZTU3NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJDb2ZmZWVCdW5ueSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80NTNkM2I2MmFkOTVlMjFkZDJiOTFiZGFhMDI5OWViNWJhYWZkYzEyYmYxN2UxMjAwZGI5OWMwNTQwZWJjYjI2IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0sCiAgICAiQ0FQRSIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjBjYzA4ODQwNzAwNDQ3MzIyZDk1M2EwMmI5NjVmMWQ2NWExM2E2MDNiZjY0YjE3YzgwM2MyMTQ0NmZlMTYzNSIKICAgIH0KICB9Cn0=","fUy+nnSryBoMJQiTOqfuCTlxErYexvhseHEDqimCNfnQ8nRSfGIH8PCXv6mTq7p+3hJ0ZjE9n5bDXsEGPhLz9MJyU2QHfK1k2mdqHEopPUSsy88b/E1+xib2ZS5Lbv/lP843Al1ABupnaOgboOxknt4tiO5QgKowIqJDzffF0DYMbAKe2QBKxDKZiZeFFoSkmvJnd0fLf6AK5je7KzBxebWYzOrPdkbSANO5tNtZ79rJzAYb6lOe6osZ3yycJ7HRZVicB3WJA9znGSr/CjQQLw1XU0vKbC26MoG8sTHAy1GJxEWe7eMBkWyo7IPbJj6+lD7DQJ6PZUfMRfQ+NMRRja0UqMgC5jQmQQCgvAWEclmZSmbv9V9cPUa/kifIZVI9ySqx395Dl6ghQdh7pnk3+weHxyE44vVH9qnisPF/TS2LeD4I/ZLPhcgZpZ/98oavbznckdARcAwHZNWtSYP7KNGde6qBkN7LnjKfM1B5bqrAcSF8RWQJ26aUespULN9thpuA6lvsNJKMa39up0qeaaqI25Sp9eH+qsT5qHA6SYhm2Hs+tttnvPlN2LZQ6poobJ4ALYbkAR7JR8bwxQvfppBHz1z3WLsTu6JvVa3CVJn+2i4Fi9iMcpNZFaiA58PFNXI4sZR3DEmRMyt8cbb536Vzr3/1L791366V8V++dh0="));
        moduleLoader.getModules().forEach(module -> {
            Class<?> c = module.getDefaultSession();
                Session session = null;
                try {
                    session = (Session<?>) c.newInstance();
                    session.processStart();
                    for(Player player : getServer().getOnlinePlayers()){
                        session.addPlayer(player);
                    }
                    sessionManager.getDefaultSessions().add(session);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(session.getName() + " is not supported");
                }
        });

        skinFactory.readFiles();

        for(Player player : Bukkit.getOnlinePlayers()){
            for(Session<?> session : sessionManager.getDefaultSessions()){
                session.addPlayer(player);
            }
        }

    }

    @Override
    public void onDisable() {
        API.getInstance().onClose();
        for(CustomClassLoader c : CustomClassLoader.customClassLoaders){
            c.close();
        }

    }


    /*
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        NPC npc = new NPC("§a§lCreepah",player.getLocation());
        npc.setSkin(skinFactory.getSkinData("Boug1"));
        npc.setInteraction(new NPC.NPCInteract() {
            @Override
            public void action(Player player, NPC.InteractClick click) {
                player.sendMessage("Ksss there, tu viens de "+ click.name());
            }
        });
        npc.initAndShow(player);

        BossBar bossBar = new BossBar("§fTheFuckingBossBar",100);
        bossBarManagerTask.addBossBar("TheFuckingBossBar",bossBar);
        //send bossbar
        bossBarManagerTask.setBossBar(player,"TheFuckingBossBar");

        getBasePlayer(player);



    }

     */



    public BasePlayer getBasePlayer(Player player){

        if(getBasePlayerManager().getPlayerHashMap().containsKey(player)){
            return getBasePlayerManager().getPlayerHashMap().get(player);
        }
        return new BasePlayer(player);
    }
    public <T> T getBasePlayer(Player player, Class<T> b){

        if(getBasePlayerManager().getPlayerHashMap().containsKey(player)){
            return (T) getBasePlayerManager().getPlayerHashMap().get(player);
        }
        return (T) getBasePlayerManager().createPlayerObject(player);
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
