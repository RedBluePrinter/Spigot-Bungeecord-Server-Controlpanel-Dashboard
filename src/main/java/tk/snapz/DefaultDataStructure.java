package tk.snapz;

import tk.snapz.logging.ConsoleLogger;

import java.io.File;
import java.util.logging.Level;

public class DefaultDataStructure {
    public static void checkAndCreate(File directory) {
        if (directory == null) {
            directory = new File("");
        }
        String parent = directory.getPath();
        createDirectory(new File(parent, "config"));
        createDirectory(new File(parent, "public/media/"));
    }
    public static boolean createDirectory(File directory) {
        if(!directory.exists()) {
            ConsoleLogger.log(Level.INFO, "Creating folder \"" + directory.getName() + "\" ");
            directory.mkdirs();
            return true;
        }
        return false;
    }
}
