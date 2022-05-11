package be.alexandre01.eloriamc.server.session;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Update {



        /**
         * Define the priority of the event.
         * <p>
         * First priority to the last priority executed:
         * <ol>
         * <li>LOWEST
         * <li>LOW
         * <li>NORMAL
         * <li>HIGH
         * <li>HIGHEST
         * <li>MONITOR
         * </ol>
         *
         * @return the priority
         */
        /**
         * Define the priority of the event.
         * <p>
         * First priority to the last priority executed:
         * <ol>
         * <li>LOWEST
         * <li>LOW
         * <li>NORMAL
         * <li>HIGH
         * <li>HIGHEST
         * <li>MONITOR
         * </ol>
         *
         * @return the priority
         */
        String name();
        long first() default 0;
        long delay() default 1*20L;
        /**
         * Define if the handler ignores a cancelled event.
         * <p>
         * If ignoreCancelled is true and the event is cancelled, the method is
         * not called. Otherwise, the method is always called.
         *
         * @return whether cancelled events should be ignored
         */
        boolean ignoreCancelled() default false;
}
