package tk.snapz.server;

import com.ericrabil.yamlconfiguration.configuration.InvalidConfigurationException;
import com.ericrabil.yamlconfiguration.configuration.file.YamlConfiguration;

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

            }
        }
    }
    public static void start(String[] args) {
        SimpleNettyServer.onMessage = (message) -> {handleMessage(message);};
        SimpleNettyServer.start(49886);
    }
}
