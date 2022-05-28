package be.alexandre01.eloriamc.server.session.examples;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.server.player.BasePlayer;
import be.alexandre01.eloriamc.server.player.GamePlayer;
import be.alexandre01.eloriamc.server.session.Session;
import be.alexandre01.eloriamc.server.session.runnables.Task;
import be.alexandre01.eloriamc.server.session.runnables.Update;
import be.alexandre01.eloriamc.server.session.runnables.Updater;

public class WaitingGameSession extends Session<GamePlayer> implements Updater {
    private int maxTime;
    private int minTime;

    private int playerDif = 0;

    public WaitingGameSession(String name, boolean isTemporary, int maxTime, int minTime) {
        super(name, isTemporary);
        this.maxTime = maxTime;
        this.minTime = minTime;

    }
    public WaitingGameSession(String name, boolean isTemporary, int maxTime, int minTime, int playerDif) {
        super(name, isTemporary);
        this.maxTime = maxTime;
        this.minTime = minTime;
        this.playerDif = playerDif;
    }



    @Override
    protected void start(SpigotPlugin base) {
        base.getUpdateFactory().createUpdate(this);
        base.getUpdateFactory().callScheduler("onStart",base);

    }

    @Override
    protected void stop(SpigotPlugin base) {
    }


    @Update(name = "onStart",first = 20,delay = 20)
    public void taskOnStart(Task task, Object... o){
        SpigotPlugin base = (SpigotPlugin) o[0];

        task.onUpdate(new Task.TaskRunnable() {
            int ticks = 120;
            @Override
            public void update() {
                int maxPlayers = getSessionManager().getDefault().getSessionParameters().getMaxPlayers();
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
                    }
            }
        });
    }

}
