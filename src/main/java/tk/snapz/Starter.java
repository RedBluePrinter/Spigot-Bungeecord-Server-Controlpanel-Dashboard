package tk.snapz;

import tk.snapz.server.SnapzServerApplication;
import tk.snapz.server.minecraft.spigot.SpigotServer;
import tk.snapz.server.minecraft.spigot.SpigotServers;
import tk.snapz.web.SpringApplication;

public class Starter {
    public static void main(String[] args) {
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
        Thread t = new Thread(() -> SnapzServerApplication.start(args));
        t.start();
        Thread s = new Thread(() -> SpringApplication.start(args));
        s.start();
    }
}
