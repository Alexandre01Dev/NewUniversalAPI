package be.alexandre01.eloriamc.server.session.examples.ffa;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.session.Session;

public class FFAGameSession extends Session {
    public FFAGameSession(String name, boolean isTemporary) {
        super(name, isTemporary);
    }

    @Override
    protected void start(SpigotPlugin base) {

    }

    @Override
    protected void stop(SpigotPlugin base) {

    }

}

