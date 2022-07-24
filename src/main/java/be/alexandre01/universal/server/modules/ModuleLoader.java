package be.alexandre01.universal.server.modules;

import be.alexandre01.universal.config.yaml.YamlUtils;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    @Getter private final ArrayList<Module> host = new ArrayList<>();

    @Getter private final ArrayList<Module> cachedModule = new ArrayList<>();

    YamlUtils cacheYML;
    Plugin plugin;
    public ModuleLoader(Plugin plugin) {
        this.plugin = plugin;
         cacheYML = new YamlUtils(plugin,"cache.yml");

        if(cacheYML.getFile().exists()){
            System.out.println("Cache Found");
            FileConfiguration cachedConfig = cacheYML.getConfig();
            System.out.println("Cache Config Loaded");
            System.out.println(cachedConfig.getKeys(true));
            if(cachedConfig.contains("modules")){
                for (String key : cachedConfig.getConfigurationSection("modules").getKeys(false)) {
                    if (cachedConfig.contains("modules."+key)) {
                        System.out.println("Cached Config ? ");
                        String json = cachedConfig.getString("modules."+key);
                        System.out.println(""+json);
                        Module module = (Module) new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(json,Module.class);
                        System.out.println(module.getUrl());
                        cachedModule.add(module);

                    }
                }
            }
        }
        load();
    }

    public void load(){
        boolean b = cacheYML.getFile().delete();
        if(!b){
            System.out.println("Could not delete cache.yml");
        }

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

                Module cache = null;
                for (Module module : cachedModule) {
                    System.out.println(file.toURI().toURL());
                    if (module.getUrl().toString().equals(file.toURI().toURL().toString())) {
                        cache = module;
                        System.out.println("Cached >> "+ cache);
                        break;
                    }
                }

                String sessionPath = null;
                String moduleName = null;
                String[] authors = null;
                String version = null;
                Material material = null;
                String description = null;
                boolean overrideLoading = false;
                CustomClassLoader child = new CustomClassLoader(
                        file.toURI().toURL(),
                        this.getClass().getClassLoader()
                );

                if(cache != null){
                    sessionPath = cache.getSessionPath();
                    moduleName = cache.getModuleName();
                    authors = cache.getAuthors();
                    version = cache.getVersion();
                    material = cache.getMaterial();
                    description = cache.getDescription();
                    overrideLoading = cache.isOverrideLoading();
                }else {
                    YamlConfiguration yamlConfiguration = new YamlConfiguration();
                    if(child.getResource("module.yml") != null){
                        InputStream inputStream = child.getResourceAsStream("module.yml");
                        yamlConfiguration.load(inputStream);
                        for (String k : yamlConfiguration.getKeys(false)){
                            System.out.println("KEY>>"+ k);
                        }

                        sessionPath = yamlConfiguration.getString("session-path");
                        moduleName = yamlConfiguration.getString("module-name");
                        authors = yamlConfiguration.getStringList("authors").toArray(new String[0]);
                        version = yamlConfiguration.getString("version");
                        material = Material.getMaterial(yamlConfiguration.getInt("itemId"));
                        description = yamlConfiguration.getString("description");
                        overrideLoading = yamlConfiguration.getBoolean("override-loading");

                        inputStream.close();
                    }
                }



                Module module = null;




                module = new Module();
                module.setFile(file);
                module.setSessionPath(sessionPath);
                module.setModuleName(moduleName);
                module.setVersion(version);
                module.setAuthors(authors);
                module.setDescription(description);

                module.setMaterial(material);

                module.setChild(child);
                module.setOverrideLoading(overrideLoading);
                module.setUrl(file.toURI().toURL());

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
