package tk.snapz.server.minecraft.spigot;

import hu.trigary.simplenetty.server.ServerClient;

import java.time.Instant;

public class SpigotServer {
    public String serverName = "SpigotServer";
    public String identifier = "Server UID Should be here...";
    public ServerClient<String> client = null;
    protected int port = -1;
    public boolean setPort(String identifier, int port) {
        if(this.identifier.equals(identifier)) {
            this.port = port;
            return true;
        }
        return false;
    }

    public int getPort() {
        return port;
    }

    public boolean isOnline() {
        if((Instant.now().getEpochSecond() - lastResponse.getEpochSecond()) < 6) {
            return true;
        }
        return false;
    }

    public boolean compare(SpigotServer server1, SpigotServer server2) {
        if(server1.identifier.equals(server2.identifier)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean wasForcedTerminated = false;
    private boolean wasAutoTerminated = false;

    public boolean isTerminated() {
        if(wasForcedTerminated) {
            return true;
        } else if((Instant.now().getEpochSecond() - lastResponse.getEpochSecond()) < 60) {
            return false;
        } else {

            return true;
        }
    }

    public void terminate() {
        wasForcedTerminated = true;
    }

    public void terminateIfTimeoutIsOver60Seconds() {

        wasForcedTerminated = true;
    }

    public Instant lastResponse = Instant.now();

    public boolean check() {
        if (wasForcedTerminated) {
            return false;
        }
        if (wasAutoTerminated) {
            return false;
        }
        if (isTerminated()) {
            return false;
        }
        for (SpigotServer s : SpigotServers.servers) {
            for (SpigotServer a : SpigotServers.servers) {
                if(s.identifier.equals(a.identifier)) {
                    if(s.isOnline()) {
                        if(!a.isOnline()) {
                            a.terminate();
                        }
                    } else {
                        if(a.isOnline()) {
                            s.terminate();
                        }
                    }
                }
            }
        }
        return true;
    }
}
