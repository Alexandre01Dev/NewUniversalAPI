package be.alexandre01.eloriamc.server.session.runnables;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import lombok.Getter;
import lombok.Setter;

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
