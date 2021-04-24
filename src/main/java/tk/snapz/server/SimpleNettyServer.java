package tk.snapz.server;

import hu.trigary.simplenetty.server.Server;
import hu.trigary.simplenetty.server.ServerClient;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SimpleNettyServer {
    public static BiConsumer<ServerClient<String>, String> onMessage = (client, message) -> System.out.println("DEBUG-Message: " + message);
    public static void start(int port) {
        Server<ServerClient<String>, String> server = new Server<>(StaticThings.stringSerializer, ServerClient::new);
        server.onReceived((client, data) -> {onMessage.accept(client, data);});
        try {
            server.start(null, port);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
