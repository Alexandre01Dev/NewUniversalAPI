package be.alexandre01.eloriamc.server.session.examples;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.player.BasePlayer;

import be.alexandre01.eloriamc.server.session.Session;
import be.alexandre01.eloriamc.server.session.runnables.Task;
import be.alexandre01.eloriamc.server.session.runnables.Update;
import be.alexandre01.eloriamc.server.session.runnables.UpdateType;
import be.alexandre01.eloriamc.server.session.runnables.Updater;
import lombok.Getter;
import lombok.Setter;

public class WaitingGameSession<T extends BasePlayer> extends Session<T> implements Updater {
    private int maxTime;
    private int minTime;

    private Session<?> defaultSession;

    @Getter @Setter
    private Session<?> sessionRedirection;

    private int playerDif = 0;

    public WaitingGameSession(String name, boolean isTemporary, int maxTime, int minTime, Session<?> defaultSession) {
        super(name, isTemporary);
        this.maxTime = maxTime;
        this.minTime = minTime;
        this.defaultSession = defaultSession;
    }
    public WaitingGameSession(String name, boolean isTemporary, int maxTime, int minTime, int playerDif, Session<?> defaultSession) {
        super(name, isTemporary);
        this.maxTime = maxTime;
        this.minTime = minTime;
        this.playerDif = playerDif;
        this.defaultSession = defaultSession;
    }



    @Override
    public void start(SpigotPlugin base) {
        super.start(base);
        base.getUpdateFactory().createUpdate(this);
        base.getUpdateFactory().callScheduler("onStart",base);
    }

    @Override
    public void stop(SpigotPlugin base) {
    }


    @Update(name = "onStart",first = 20,delay = 20,type = UpdateType.BUKKIT_ASYNC)
    public void taskOnStart(Task task, Object... o){
        SpigotPlugin base = (SpigotPlugin) o[0];

        task.onUpdate(new Task.TaskRunnable() {
            int ticks = 120;
            @Override
            public void update() {
                int maxPlayers = defaultSession.getSessionParameters().getMaxPlayers();
                int players = getPlayers().size();
                if(players >= maxPlayers){
                    getPlayers().forEach(basePlayer -> {

                        basePlayer.sendActionBar("§aTous les joueurs sont présents");
                        });
                        base.getUpdateFactory().callScheduler("onEnd",base);
                        task.cancel();
                    }
                    getPlayers().forEach(basePlayer -> {
                        basePlayer.sendActionBar("§aEn attentes des Joueurs "+ players +"/"+maxPlayers);
                    });

                    ticks --;
                    if(ticks > 20 && players >= maxPlayers-playerDif){
                        ticks = 20;
                    }

                    if(ticks <= 0){
                        task.cancel();
                        finish();
                        sessionRedirection.processStart();
                    }
            }
        });
    }

}
