package be.alexandre01.eloriamc.server.session.runnables;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class UpdateFactory {
    private SpigotPlugin spigotPlugin;
    private Multimap<String,Task> bukkitTasks = HashMultimap.create();

    private HashMap<Update,Integer> taskIds = new HashMap<>();

    public UpdateFactory(){
        spigotPlugin = SpigotPlugin.getInstance();
    }
    private HashMap<String,Method> methods = new HashMap<>();
    public void createUpdate(Updater updater){
            Set<Method> methods;
            Method[] publicMethods = updater.getClass().getMethods();
            Method[] privateMethods = updater.getClass().getDeclaredMethods();
            methods = new HashSet<Method>(publicMethods.length + privateMethods.length, 1.0F);
            Method[] arrayOfMethod1;
            int i;
            byte b;
            for (i = (arrayOfMethod1 = publicMethods).length, b = 0; b < i; ) {
                final Method method = arrayOfMethod1[b];
                methods.add(method);
                b++;
            }
            for (i = (arrayOfMethod1 = privateMethods).length, b = 0; b < i; ) {
                final Method method = arrayOfMethod1[b];
                methods.add(method);
                b++;
            }

        for (Method method : methods) {
            Update up = method.<Update>getAnnotation(Update.class);
            if (up == null)
                continue;
            this.methods.put(up.name(),method);
        }





    }

    public Task callScheduler(String scheduler,Object... objects){
        if(!methods.containsKey(scheduler)) return null;
        Method method = methods.get(scheduler);
        Update up = method.<Update>getAnnotation(Update.class);

        if(up.type().name().contains("BUKKIT")){
            BukkitScheduler bukkitScheduler = Bukkit.getScheduler();
        Task task = new Task() {
            @Override
            public void cancel() {
                if(id != null)
                    bukkitScheduler.cancelTask(id);
            }
        };
            try {
                method.invoke(task,objects);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            if(task.getTaskRunnable() == null) return null;

            if(up.type() == UpdateType.BUKKIT_ASYNC){
            int id = bukkitScheduler.runTaskTimerAsynchronously(spigotPlugin,() -> {
                task.getTaskRunnable().update();
            },up.first(),up.delay()).getTaskId();
            task.id = id;
        }
        if(up.type() == UpdateType.BUKKIT_SYNC){
            int id = bukkitScheduler.runTaskTimer(spigotPlugin,() -> {
                task.getTaskRunnable().update();
            },up.first(),up.delay()).getTaskId();
            task.id = id;
        }
        if(up.type() == UpdateType.BUKKIT_DELAY){
            int id = bukkitScheduler.runTaskLater(spigotPlugin,() -> {
                task.getTaskRunnable().update();
            },up.first()).getTaskId();
            task.id = id;
        }
            return task;
        }

        if(up.type() == UpdateType.EXECUTOR_ASYNC){
            ScheduledExecutorService executors = Executors.newScheduledThreadPool(up.threadNum());
            Task task = new Task() {
                @Override
                public void cancel() {
                    executors.shutdown();
                }
            };
            executors.scheduleAtFixedRate(() ->  {
                task.getTaskRunnable().update();
            },up.first(),up.delay(),up.timeUnit());
            return task;
        }
        return null;
    }



}
