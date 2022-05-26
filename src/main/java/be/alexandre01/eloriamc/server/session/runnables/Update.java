package be.alexandre01.eloriamc.server.session.runnables;

import jdk.nashorn.internal.objects.annotations.Setter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Update {
        String name();
        long first() default 0;
        long delay() default 1*20L;

        TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
        UpdateType type() default UpdateType.BUKKIT_SYNC;

        int threadNum() default 1;

}
