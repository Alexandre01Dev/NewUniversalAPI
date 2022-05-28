package be.alexandre01.eloriamc.server.modules;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.io.File;
import java.net.URLClassLoader;

@Getter @Setter
public class Module {
     private File file;
     private String sessionPath;
     private String moduleName;
     private String[] authors;
     private String version;
     private Material material;
     private String description;
     private URLClassLoader child;
     private Class<?> defaultSession;
}
