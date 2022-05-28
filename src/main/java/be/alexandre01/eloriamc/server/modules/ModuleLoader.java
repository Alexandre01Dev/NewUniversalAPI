package be.alexandre01.eloriamc.server.modules;

import be.alexandre01.eloriamc.server.session.Session;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ModuleLoader {
    private File dir;
    @Getter private final ArrayList<Module> modules = new ArrayList<>();


    public ModuleLoader(Plugin plugin) {
        dir = new File(plugin.getDataFolder().getAbsolutePath()+"/modules");

        try {
             if(!dir.exists()) {
                dir.mkdirs();
            }
            if(isDirEmpty(dir.toPath())) {
               return;
            }
            for(File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.isDirectory())
                    continue;
                CustomClassLoader child = new CustomClassLoader(
                        file.toURI().toURL(),
                        this.getClass().getClassLoader()
                );

                Module module = null;
                YamlConfiguration yamlConfiguration = new YamlConfiguration();
                if(child.getResource("module.yml") != null){

                    yamlConfiguration.load(child.getResourceAsStream("module.yml"));
                    for (String k : yamlConfiguration.getKeys(false)){
                        System.out.println("KEY>>"+ k);
                    }

                    module = new Module();
                    module.setFile(file);
                    module.setSessionPath(yamlConfiguration.getString("session-path"));
                    module.setModuleName(yamlConfiguration.getString("module-name"));

                    module.setVersion(yamlConfiguration.getString("version"));
                    module.setAuthors(yamlConfiguration.getStringList("authors").toArray(new String[0]));

                    module.setDescription(yamlConfiguration.getString("description"));

                    module.setMaterial(Material.getMaterial(yamlConfiguration.getInt("itemId")));

                    module.setChild(child);
                }
                if(module == null)
                    return;

                System.out.println(module.getSessionPath());
                Class<?> classToLoad = Class.forName(module.getSessionPath(), true, child);
                module.setDefaultSession(classToLoad);

                System.out.println(module.getModuleName());
                //Preset.instance.add(module.getModuleName(), module);

                this.modules.add(module);

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }


    }



    public void searchAllObject(Object object){
        for(Field field : object.getClass().getFields()){
            searchForField(field,object);
        }
        for(Field field : object.getClass().getDeclaredFields()){
            searchForField(field,object);
        }
        for( Method method : object.getClass().getMethods()){
            try {
                searchAllObject(method.invoke(object));
            }catch (Exception ignored){

            }
        }

        for( Method method : object.getClass().getMethods()){
            try {
                searchAllObject(method.invoke(object));
            }catch (Exception ignored){

            }
        }
    }

    public void searchForField(Field field,Object object){
        if(field.getType() == List.class){
            try {
                List<?> l = (List<?>) field.get(object);
                for(Object o : l){
                    searchAllObject(o);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(field.getType() == ArrayList.class){
            try {
                ArrayList<?> l = (ArrayList<?>) field.get(object);
                for(Object o : l){
                    searchAllObject(o);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(field.getType() == HashMap.class){
            try {
                HashMap<?,?> l = (HashMap<?,?>) field.get(object);
                for(Object o : l.keySet()){
                    searchAllObject(o);
                }
                for(Object o : l.values()){
                    searchAllObject(o);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        /*if(field.getType() == IPreset.class){
            try {
                field.setAccessible(true);
                field.set(object,Preset.instance.p);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(field.getType() == PresetData.class){
            try {
                field.setAccessible(true);
                field.set(object,Preset.instance.pData);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }*/
    }

    private boolean isDirEmpty(final Path directory) throws IOException {
        try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
            return !dirStream.iterator().hasNext();
        }
    }
}
