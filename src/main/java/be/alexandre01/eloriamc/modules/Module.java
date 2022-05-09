package be.alexandre01.eloriamc.modules;


import lombok.Data;
import org.bukkit.Material;

import java.io.File;
import java.net.URLClassLoader;

@Data
public class Module {
     private File file;
     private String presetPath;
     private String moduleName;
     private String[] authors;
     private String version;
     private Material material;
     private String description;
     private URLClassLoader child;
     private Class<?> presetClass;
}
