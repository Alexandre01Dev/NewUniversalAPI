package be.alexandre01.universal.server.session.runnables;

import be.alexandre01.universal.server.SpigotPlugin;
import lombok.Getter;

public abstract class Task {
    private SpigotPlugin spigotPlugin;
    @Getter private TaskRunnable taskRunnable = null;
    public Task(){
        spigotPlugin = SpigotPlugin.getInstance();
    }
    public Integer id;
    public abstract void cancel();

    public void delayed(Runnable runnable){
        spigotPlugin.getServer().getScheduler().scheduleSyncDelayedTask(spigotPlugin, runnable, id);
    }




    public void onUpdate(TaskRunnable taskRunnable){
        this.taskRunnable = taskRunnable;
    }
    public interface TaskRunnable{
        void update();
    }

}
