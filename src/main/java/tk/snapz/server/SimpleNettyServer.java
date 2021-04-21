package tk.snapz.server;

import java.util.function.Consumer;

public class SimpleNettyServer {
    public static Consumer<String> onMessage = (message) -> System.out.println("DEBUG-Message: " + message);
    public static void start(int port) {

    }
}
