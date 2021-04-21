package tk.snapz.server;

import com.ericrabil.yamlconfiguration.configuration.InvalidConfigurationException;
import com.ericrabil.yamlconfiguration.configuration.file.YamlConfiguration;
import tk.snapz.server.minecraft.spigot.SpigotMessageHandler;

public class SnapzServerApplication {
    public static void handleMessage(String message) {
        YamlConfiguration yaml = new YamlConfiguration();
        try {
            yaml.loadFromString(message);
        } catch (InvalidConfigurationException invalidConfigurationException) {
            invalidConfigurationException.printStackTrace();
        }
        if(yaml.contains("mtype")) {
            String messageType = yaml.getString("mtype");
            if(messageType.equals("spigot")) {
                SpigotMessageHandler.handle(yaml);
            }
        }
    }
    public static void start(String[] args) {
        SimpleNettyServer.onMessage = SnapzServerApplication::handleMessage;
        SimpleNettyServer.start(49886);
    }
}
