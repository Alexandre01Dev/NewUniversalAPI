package be.alexandre01.universal.utils;

public class ClassUtils {
    public static boolean classExist(String c) {
        try {
            Class.forName(c);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
