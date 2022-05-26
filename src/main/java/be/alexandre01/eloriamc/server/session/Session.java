package be.alexandre01.eloriamc.server.session;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.events.players.SessionListenerManager;
import be.alexandre01.eloriamc.server.player.BasePlayer;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class Session {
    private boolean isStarted = false;
    private ArrayList<Class<?>> cancelledListener = new ArrayList<>();
    @Getter ArrayList<BasePlayer> players = new ArrayList<>();

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
    }

    public void addPlayer(BasePlayer player){
        players.add(player);
    }

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


}
