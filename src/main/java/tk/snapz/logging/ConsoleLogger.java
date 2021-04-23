package tk.snapz.logging;

import java.util.logging.Level;

public class ConsoleLogger {
    public static void log(Level level, String message) {
        System.out.println(level.getName() + " -> " + message);
    }
}
