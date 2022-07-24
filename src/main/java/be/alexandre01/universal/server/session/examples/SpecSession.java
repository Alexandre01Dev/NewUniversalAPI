package be.alexandre01.universal.server.session.examples;

import be.alexandre01.universal.server.SpigotPlugin;
import be.alexandre01.universal.server.player.BasePlayer;

import be.alexandre01.universal.server.session.Session;
import be.alexandre01.universal.server.session.runnables.Updater;

public class SpecSession<T extends BasePlayer> extends Session<T> implements Updater {
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
