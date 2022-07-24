package be.alexandre01.universal.server.session.listeners.examples;

import be.alexandre01.universal.server.events.factories.IEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class CancelWeatherChange extends IEvent<WeatherChangeEvent> {

    @Override
    public void onEvent(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
