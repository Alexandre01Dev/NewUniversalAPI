package be.alexandre01.eloriamc.server.session;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.events.players.SessionListenerManager;
import be.alexandre01.eloriamc.server.player.BasePlayer;
import be.alexandre01.eloriamc.server.session.examples.WaitingGameSession;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.entity.Player;

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

    private String name;

    public Session(String name,boolean isTemporary){
        this.name = name;

        sessionParameters.setTemporary(isTemporary);
        spigotPlugin = SpigotPlugin.getInstance();
        sessionManager.registerSession(this);
    }

    public void addPlayer(T player){
        players.add(player);
        uuidToGP.put(player.getUniqueId(),player);

        onAddPlayer(player);
    }




    public T getCustomPlayer(Player player){
       return (T) spigotPlugin.getBasePlayer(player);
    }
    public void addPlayer(Player player){
        addPlayer(spigotPlugin.getBasePlayer(player));
    }


    public void removePlayer(BasePlayer player){
        players.remove(player);
        uuidToGP.remove(player.getUniqueId());
        onRemovePlayer(player);
    }

    public void removePlayer(Player player){
        removePlayer(spigotPlugin.getBasePlayer(player));
    }

    protected void onAddPlayer(BasePlayer player){}

    protected void onRemovePlayer(BasePlayer player){}

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
