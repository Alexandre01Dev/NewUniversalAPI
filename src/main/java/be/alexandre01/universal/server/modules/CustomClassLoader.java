package be.alexandre01.universal.server.modules;

import be.alexandre01.universal.server.SpigotPlugin;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.jar.JarFile;

public class CustomClassLoader extends URLClassLoader {

    public static ArrayList<CustomClassLoader> customClassLoaders = new ArrayList<>();
        public CustomClassLoader(URL url, ClassLoader parent) {
            super(new URL[] { url }, parent);
            customClassLoaders.add(this);
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve)
                throws ClassNotFoundException {
            try {
                return super.loadClass(name, resolve);
            } catch (ClassNotFoundException e) {
                return Class.forName(name, resolve, SpigotPlugin.class.getClassLoader());
            }
        }
        @Override
        public void close() {
            try {
                System.out.println("Custom close");
                Class clazz = java.net.URLClassLoader.class;
                Field ucp = clazz.getDeclaredField("ucp");
                ucp.setAccessible(true);
                Object sunMiscURLClassPath = ucp.get(this);
                Field loaders = sunMiscURLClassPath.getClass().getDeclaredField("loaders");
                loaders.setAccessible(true);
                Object collection = loaders.get(sunMiscURLClassPath);
                for (Object sunMiscURLClassPathJarLoader : ((Collection) collection).toArray()) {
                    try {
                        Field loader = sunMiscURLClassPathJarLoader.getClass().getDeclaredField("jar");
                        loader.setAccessible(true);
                        Object jarFile = loader.get(sunMiscURLClassPathJarLoader);
                        ((JarFile) jarFile).close();
                    } catch (Throwable t) {
                        // if we got this far, this is probably not a JAR loader so skip it
                    }
                }
            } catch (Throwable t) {
                // probably not a SUN VM
            }
            return;
        }
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println(this.toString() + " - CL Finalized.");
        }
    }
