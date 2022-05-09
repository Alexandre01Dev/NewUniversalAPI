package be.alexandre01.eloriamc.presets.impl;

import be.alexandre01.eloriamc.modules.Module;
import be.alexandre01.eloriamc.presets.DefaultPresetData;
import be.alexandre01.eloriamc.presets.DefaultPresetLoader;
import lombok.Getter;
import lombok.SneakyThrows;

public abstract class PresetLoaderImpl {
    PresetLoaderImpl p;

    @Getter
    private static PresetLoaderImpl instance;

    public PresetLoaderImpl() {
        instance = this;
    }
    protected void onSet(Module module) {
        //DO NOTHING AND OVERRIDE IN SUBCLASS
    }

    @SneakyThrows
    private void set(Module module) {
        p = (PresetLoaderImpl) module.getPresetClass().getConstructor().newInstance();
        pData = (DefaultPresetData) p;
        modules.put(module.getPresetClass(),module);
        this.m = module;
        onSet(module);
    }
}
