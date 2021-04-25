package tk.snapz.server.minecraft.spigot;

import com.ericrabil.yamlconfiguration.configuration.file.YamlConfiguration;
import hu.trigary.simplenetty.client.Client;
import hu.trigary.simplenetty.server.Server;
import hu.trigary.simplenetty.server.ServerClient;
import tk.snapz.ServerToken;

import java.time.Instant;

public class SpigotMessageHandler {
    public static boolean handle(ServerClient<String> client, YamlConfiguration config) {
//        System.out.println(config.saveToString());
        for (SpigotServer server : SpigotServers.servers) {
            if (server.identifier.equals(config.getString("uid"))) {
                server.client = client;
                server.lastResponse = Instant.now();
                server.packetsSent++;
                return true;
            }
        }

        if(config.contains("token")) {
            if(config.getString("token").equals(ServerToken.serverToken)) {
                SpigotServer spigotServer = new SpigotServer();
                spigotServer.serverName = config.getString("sname");
                spigotServer.identifier = config.getString("uid");
                spigotServer.setPort(spigotServer.identifier, config.getInt("port"));
                spigotServer.packetsSent = 1;
                SpigotServers.servers.add(spigotServer);
            } else {
                YamlConfiguration response = new YamlConfiguration();
                response.set("err", "Not Authorized!");
                client.sendAndClose(response.saveToString());
            }
        } else {
            client.sendAndClose("Not Authorized!");
        }

        return true;
    }
}
