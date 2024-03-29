package be.alexandre01.universal.server.session;

import be.alexandre01.universal.server.SpigotPlugin;
import be.alexandre01.universal.server.events.players.SessionListenerManager;
import be.alexandre01.universal.server.player.BasePlayer;
import be.alexandre01.universal.server.session.inventory.item.ItemFactory;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Getter
public abstract class Session<T extends BasePlayer> {
    private boolean isStarted = false;
    private ArrayList<Class<?>> cancelledListener = new ArrayList<>();
    @Getter ArrayList<T> players = new ArrayList<>();
    private HashMap<UUID,T> uuidToGP = new HashMap<>();
    SessionParameters sessionParameters = new SessionParameters();

    @Getter
    SpigotPlugin spigotPlugin;

    public final SessionListenerManager listenerManager = new SessionListenerManager();
    public final SessionManager sessionManager = SessionManager.getInstance();

    @Getter private final ItemFactory itemFactory;

    private String name;

    public Session(String name,boolean isTemporary){
        this.name = name;

        sessionParameters.setTemporary(isTemporary);
        spigotPlugin = SpigotPlugin.getInstance();
        sessionManager.registerSession(this);
        itemFactory = new ItemFactory(this);
    }

    public void addPlayer(T player){
        System.out.println(player);
        System.out.println(player.getName());
        players.add(player);
        uuidToGP.put(player.getUniqueId(),player);
        listenerManager.registerToPlayer(player.getPlayer());

        onAddPlayer(player);
    }




    public T getCustomPlayer(Player player){
        Class<T> persistentClass = (Class<T>)
                ((ParameterizedType)getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
       return (T) spigotPlugin.getBasePlayer(player,persistentClass);
    }
    public void addPlayer(Player player){
        addPlayer(getCustomPlayer(player));
    }


    public void removePlayer(T player){
        players.remove(player);
        uuidToGP.remove(player.getUniqueId());
        listenerManager.unregisterPlayerEvents(player.getPlayer());
        onRemovePlayer(player);
    }

    public void removePlayer(Player player){
        removePlayer(getCustomPlayer(player));
    }

    protected void onAddPlayer(T player){}

    protected void onRemovePlayer(T player){}

    public void finish(){
        isStarted = false;
        System.out.println("Session"+ name+" finished");
        listenerManager.unregisterAllEvents();
        listenerManager.unregisterAllPlayerEvents();
        stop(spigotPlugin);
        if(sessionParameters.getRedirection() != null){
            if(!sessionParameters.getRedirection().isStarted)
                sessionParameters.getRedirection().start(spigotPlugin);
        }

    }


    public void processStart(){
        isStarted = true;
        System.out.println("Session"+ name+" started");
        start(spigotPlugin);

    }
    protected void start(SpigotPlugin base){}

    protected void stop(SpigotPlugin base){}



    public boolean containsPlayer(Player player){
        return uuidToGP.containsKey(player.getUniqueId());
    }


}
