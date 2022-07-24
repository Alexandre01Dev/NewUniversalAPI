package be.alexandre01.universal.server.events.players;

import be.alexandre01.universal.server.player.BasePlayer;
import be.alexandre01.universal.server.session.Session;

public class SessionListenerLoader {
    Session session;
    public SessionListenerLoader(be.alexandre01.universal.server.session.Session session) {
     this.session = session;
    }

    public void playerJoinSession(BasePlayer basePlayer){
        for (IPlayerEvent<?> iPlayerEvent  : session.getListenerManager().getPlayerEvents());

    }
}
