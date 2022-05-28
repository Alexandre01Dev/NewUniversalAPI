package be.alexandre01.eloriamc.server.session.listeners.examples;

import be.alexandre01.eloriamc.server.events.factories.IEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class CancelWeatherChange extends IEvent<WeatherChangeEvent> {

    @Override
    public void onEvent(WeatherChangeEvent event) {
        event.getWorld().setStorm(false);
        event.getWorld().setWeatherDuration(100000);
    }
}
