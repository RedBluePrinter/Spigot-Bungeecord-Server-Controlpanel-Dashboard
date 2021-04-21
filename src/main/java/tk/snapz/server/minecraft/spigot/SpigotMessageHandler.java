package tk.snapz.server.minecraft.spigot;

import com.ericrabil.yamlconfiguration.configuration.file.YamlConfiguration;
import hu.trigary.simplenetty.server.Server;

import java.time.Instant;

public class SpigotMessageHandler {
    public static boolean handle(YamlConfiguration config) {
        for (SpigotServer server : SpigotServers.servers) {
            if (server.identifier.equals(config.getString("uid"))) {
                server.lastResponse = Instant.now();
                return true;
            }
        }

        SpigotServer spigotServer = new SpigotServer();
        spigotServer.serverName = config.getString("sname");
        spigotServer.identifier = config.getString("uid");
        SpigotServers.servers.add(spigotServer);

        return true;
    }
}
