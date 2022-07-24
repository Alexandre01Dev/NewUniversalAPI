package be.alexandre01.universal.server.session.inventory.stuff;

import java.util.HashMap;

public class StuffFactory {
    private HashMap<String, Stuff> stuffHashMap = new HashMap<>();

    public void createStuff(Stuff stuff){
        stuffHashMap.put(stuff.getName(), stuff);
    }

    public Stuff getStuff(String name){
        return stuffHashMap.get(name);
    }
}
