package tk.snapz.server.minecraft.spigot;

import java.time.Instant;

public class SpigotServer {
    public String serverName = "SpigotServer";
    public String identifier = "Server UID Should be here...";
    public boolean isOnline() {
        if((Instant.now().getEpochSecond() - lastResponse.getEpochSecond()) < 10) {
            return true;
        }
        return false;
    }
    public Instant lastResponse = Instant.now();
}
