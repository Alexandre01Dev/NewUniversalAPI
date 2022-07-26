package be.alexandre01.universal.server.events.factories;

import com.google.common.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class IEvent<T extends Event> {
    private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) { };

    @Getter
    private Class<T> eventClass;

    @Getter @Setter private String playerCall = null;
    @Getter @Setter private String cancelCall = null;


    public IEvent(){
        //eventClass = (Class<T>) ((ParameterizedType) getClass()
          //.getGenericSuperclass()).getActualTypeArguments()[0];
        eventClass = (Class<T>) typeToken.getRawType();
       // System.out.println("New IEvent > "+ eventClass.getSimpleName() + this);
    }
    public  abstract void onEvent(T event);


}
