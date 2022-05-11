package be.alexandre01.eloriamc.server.packets.skin;

import lombok.Getter;

import java.util.HashMap;

public class SkinFactory {
    @Getter private HashMap<String,SkinData> skinsData = new HashMap<String,SkinData>();

    public SkinFactory() {
        //DO SOME THINGS
    }

    public void registerSkinData(String name, SkinData skinData) {
        skinsData.put(name, skinData);
    }

    public SkinData getSkinData(String name) {
        return skinsData.get(name);
    }
}
