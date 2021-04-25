package tk.snapz;

import com.ericrabil.yamlconfiguration.configuration.InvalidConfigurationException;
import tk.snapz.server.SnapzServerApplication;
import tk.snapz.server.minecraft.spigot.SpigotServer;
import tk.snapz.server.minecraft.spigot.SpigotServers;
import tk.snapz.usermanager.Users;
import tk.snapz.web.SpringApplication;

import java.io.File;
import java.io.IOException;

public class Starter {
    public static void main(String[] args) {
        DefaultDataStructure.checkAndCreate(new File(""));
        new Thread(() -> {
            while (true) {
                //Debug Thread!
                for (SpigotServer server : SpigotServers.servers) {
                    System.out.println(server.serverName + " | isOnline=" + server.isOnline());
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }).start();

        try {
            Users.init();
        } catch (IOException | InvalidConfigurationException ioException) {
            ioException.printStackTrace();
        }

        Thread t = new Thread(() -> SnapzServerApplication.start(args));
        t.start();
        Thread s = new Thread(() -> SpringApplication.start(args));
        s.start();
    }
}
