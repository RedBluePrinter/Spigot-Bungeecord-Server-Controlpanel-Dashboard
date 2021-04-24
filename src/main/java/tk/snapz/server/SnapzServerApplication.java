package tk.snapz.server;

import com.ericrabil.yamlconfiguration.configuration.InvalidConfigurationException;
import com.ericrabil.yamlconfiguration.configuration.file.YamlConfiguration;
import hu.trigary.simplenetty.client.Client;
import hu.trigary.simplenetty.server.ServerClient;
import tk.snapz.server.minecraft.spigot.SpigotMessageHandler;

public class SnapzServerApplication {
    public static void handleMessage(ServerClient<String> client, String message) {
        YamlConfiguration yaml = new YamlConfiguration();
        try {
            yaml.loadFromString(message);
        } catch (InvalidConfigurationException invalidConfigurationException) {
            invalidConfigurationException.printStackTrace();
        }
        if(yaml.contains("mtype")) {
            String messageType = yaml.getString("mtype");
            if(messageType.equals("spigot")) {
                SpigotMessageHandler.handle(client, yaml);
            }
        }
    }
    private static boolean isRunning = false;
    public static void start(String[] args) {
        System.out.println("hi");
        if(!isRunning) {
            SimpleNettyServer.onMessage = SnapzServerApplication::handleMessage;
            SimpleNettyServer.start(8894);
            System.out.println("Server started!");
            isRunning = true;
        }
    }
}
