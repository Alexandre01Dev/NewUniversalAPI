package be.alexandre01.eloriamc.server.session.examples;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.player.GamePlayer;
import be.alexandre01.eloriamc.server.session.Session;
import be.alexandre01.eloriamc.server.session.runnables.Task;
import be.alexandre01.eloriamc.server.session.runnables.Update;
import be.alexandre01.eloriamc.server.session.runnables.Updater;

public class SpecSession extends Session<GamePlayer> implements Updater {
    public SpecSession(String name, boolean isTemporary) {
        super(name, isTemporary);
    }

    @Override
    protected void start(SpigotPlugin base) {

    }

    @Override
    protected void stop(SpigotPlugin base) {

    }



}
