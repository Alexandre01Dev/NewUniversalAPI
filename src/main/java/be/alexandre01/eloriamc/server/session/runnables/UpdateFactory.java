package be.alexandre01.eloriamc.server.session.runnables;

import be.alexandre01.eloriamc.server.SpigotPlugin;
import be.alexandre01.eloriamc.utils.Tuple;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
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
    @Getter private Multimap<String,Task> bukkitTasks = HashMultimap.create();

    @Getter private HashMap<Update,Integer> taskIds = new HashMap<>();

    public UpdateFactory(SpigotPlugin spigotPlugin){
        this.spigotPlugin = spigotPlugin;
    }

    @Getter private HashMap<String, Tuple<Method,Updater>> methods = new HashMap<>();
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
                System.out.println("public >> "+ method.getName());
                methods.add(method);
                b++;
            }
            for (i = (arrayOfMethod1 = privateMethods).length, b = 0; b < i; ) {
                final Method method = arrayOfMethod1[b];
                System.out.println("private >> "+ method.getName());
                methods.add(method);
                b++;
            }

        for (Method method : methods) {
            Update up = method.<Update>getAnnotation(Update.class);
            System.out.println("Meth >> "+ method.getName());
            System.out.println(up);
            if (up == null)
                continue;
            System.out.println("AH OUE FUCK");
            this.methods.put(up.name(),new Tuple<>(method,updater));
        }





    }

    public Task callScheduler(String scheduler,Object... objects){
        if(!methods.containsKey(scheduler)) return null;
        Tuple<Method,Updater> tuple = methods.get(scheduler);
        Method method = tuple.a();
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
                method.setAccessible(true);
                System.out.println(method.getParameterTypes());
                method.invoke(tuple.b(),task,objects);
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.out.println(e.getCause());
                System.out.println(e.getCause().getMessage());
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
