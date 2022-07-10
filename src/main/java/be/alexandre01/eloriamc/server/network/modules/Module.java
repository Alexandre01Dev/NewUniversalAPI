package be.alexandre01.eloriamc.server.network.modules;



import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

@Getter @Setter
public class Module implements Serializable {
     private File file;
     @Expose
     private String sessionPath;
     @Expose
     private String moduleName;
     @Expose
     private String[] authors;
     @Expose
     private String version;
     @Expose
     private Material material;
     @Expose
     private String description;
     private URLClassLoader child;
     @Expose
     private URL url;
     private Class<?> defaultSession;
     private boolean overrideLoading;
}