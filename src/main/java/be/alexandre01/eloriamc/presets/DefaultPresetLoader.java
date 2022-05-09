package be.alexandre01.eloriamc.presets;

import be.alexandre01.eloriamc.modules.Module;
import be.alexandre01.eloriamc.presets.impl.PresetLoaderImpl;
import lombok.Getter;
import lombok.SneakyThrows;

public class DefaultPresetLoader extends PresetLoaderImpl {

    @SneakyThrows
    public void set(Module module){
        onSet(module);
    }

    @Override
    public void onSet(Module module) {
        // Do nothing
    }
}
