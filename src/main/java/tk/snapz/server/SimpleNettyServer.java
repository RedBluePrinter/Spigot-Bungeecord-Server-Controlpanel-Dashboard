package tk.snapz.server;

import java.util.function.Consumer;

public class SimpleNettyServer {
    public Consumer<String> onMessage = (message) -> System.out.println("DEBUG-Message: " + message);
}
