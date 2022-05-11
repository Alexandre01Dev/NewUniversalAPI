package be.alexandre01.eloriamc.server.session;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.events.players.SessionListenerManager;
import be.alexandre01.eloriamc.server.player.BasePlayer;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class Session {
    private ArrayList<Class<?>> cancelledListener = new ArrayList<>();
    @Getter ArrayList<BasePlayer> players = new ArrayList<>();
    SessionParameters sessionParameters = new SessionParameters();

    @Getter
    SpigotPlugin spigotPlugin;

    public final SessionListenerManager sessionManager = new SessionListenerManager();
    private String name;

    public Session(String name,boolean isTemporary){
        this.name = name;
        sessionParameters.setTemporary(isTemporary);
        spigotPlugin = SpigotPlugin.getInstance();
    }

    public void addPlayer(BasePlayer player){
        players.add(player);
    }

    protected abstract void start(SpigotPlugin gameAPI);

    protected abstract void stop(SpigotPlugin gameAPI);
}
