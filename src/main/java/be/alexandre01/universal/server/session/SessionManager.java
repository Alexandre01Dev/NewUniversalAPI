package be.alexandre01.universal.server.session;

import be.alexandre01.universal.server.player.BasePlayer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

public class SessionManager {

    private HashMap<String, Session> sessions = new HashMap<>();
    private HashMap<BasePlayer, Session> playersOnSession = new HashMap<>();
    @Getter
    private ArrayList<Session> defaultSessions = new ArrayList<>();
    private static SessionManager instance;

    public static SessionManager getInstance() {
        if(instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    private SessionManager() {

    }

    public void registerSession(Session session) {
        sessions.put(session.getName(), session);
    }

    public void unregisterSession(Session session) {
        sessions.remove(session.getName());
    }

    public Session getSession(String name) {
        return null;
    }

}
