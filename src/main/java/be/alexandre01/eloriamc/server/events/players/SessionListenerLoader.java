package be.alexandre01.eloriamc.server.events.players;

import be.alexandre01.eloriamc.server.player.BasePlayer;
import be.alexandre01.eloriamc.server.session.Session;

public class SessionListenerLoader {
    Session session;
    public SessionListenerLoader(be.alexandre01.eloriamc.server.session.Session session) {
     this.session = session;
    }

    public void playerJoinSession(BasePlayer basePlayer){
        for (IPlayerEvent<?> iPlayerEvent  : session.getListenerManager().getPlayerEvents());

    }
}
