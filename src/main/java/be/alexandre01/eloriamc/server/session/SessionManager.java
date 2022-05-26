package be.alexandre01.eloriamc.server.session;

import be.alexandre01.eloriamc.server.player.BasePlayer;

import java.util.HashMap;

public class SessionManager {

    private HashMap<String, Session> sessions;
    private HashMap<BasePlayer, Session> playersOnSession;
    private Session defaultSession = null;
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

    }

    public void unregisterSession(Session session) {

    }

    public Session getSession(String name) {
        return null;
    }

    public Session getDefault() {
        return defaultSession;
    }
}
