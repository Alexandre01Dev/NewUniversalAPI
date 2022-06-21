package be.alexandre01.eloriamc.server.session.listeners.examples;

import be.alexandre01.eloriamc.server.events.factories.IEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.weather.WeatherChangeEvent;

public class CancelWeatherChange extends IEvent<WeatherChangeEvent> {

    @Override
    public void onEvent(WeatherChangeEvent event) {
        if(event.getWorld().getWeatherDuration() != 0){
            event.getWorld().setStorm(false);
        }
    }
}
