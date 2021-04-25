package tk.snapz.spigot;

import hu.trigary.simplenetty.client.Client;
import hu.trigary.simplenetty.serialization.DataSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.charset.StandardCharsets;

public class Plugin extends JavaPlugin {
    @Override
    public void onLoad() {

    }

    @Override
    public void onDisable() {

    }

    public static YamlConfiguration template() {
        YamlConfiguration config = new YamlConfiguration();
        config.set("mtype", "spigot");
        config.set("sname", Bukkit.getServerName());
        config.set("uid", Bukkit.getServerName() + Bukkit.getServerId() + Bukkit.getPort());
        return config;
    }

    @Override
    public void onEnable() {
        DataSerializer<String> stringSerializer = new DataSerializer<String>() {
            @Override
            public byte[] serialize(String data) {
                return data.getBytes(StandardCharsets.US_ASCII);
            }

            @Override
            public String deserialize(byte[] bytes) {
                return new String(bytes, StandardCharsets.US_ASCII);
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        };
        YamlConfiguration yaml = template();
        yaml.set("port", Bukkit.getPort());
        Client<String> client = new Client<>(stringSerializer);
        client.onConnected(() -> client.send(yaml.saveToString()));
        client.onDisconnected(() -> {
            try {
                client.connect("localhost", 8894, 0);
            } catch (InterruptedException interruptedException) {

            }
        });
        client.onException((ex)-> {
            try {
                client.disconnect();
                client.connect("localhost", 8894, 0);
            } catch (InterruptedException interruptedException) {}
        });
        client.onReceived((message) -> {
            YamlConfiguration command = new YamlConfiguration();
            try {
                command.loadFromString(message);
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
            if(command.getString("cmd").equals("reload")) {
                new Thread(() -> {
                    System.out.println("Reload Executed remotely!");
                    Bukkit.reload();
                }).start();
            }
            if(command.getString("cmd").equals("stop")) {
                System.out.println("Shutdown Executed remotely!");
//                System.out.println("Saving Players...");
                Server server = Bukkit.getServer();
//                server.savePlayers();
//                System.out.println("Saving Worlds...");
//                for (World world : server.getWorlds()) {
//                    world.save();
//                    server.unloadWorld(world, true);
//                }
//                System.out.println("Saved!");
//                System.out.println("Disabling Plugins...");
//                for(org.bukkit.plugin.Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
//                    System.out.println("Disabling " + plugin.getName() + "...");
//                    plugin.onDisable();
//                    System.out.println("Disabled " + plugin.getName() + "!");
//                }
                System.out.println("Shutting Down...");
                server.shutdown();
            }
        });

        try {
            client.connect("localhost", 8894, 0);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            try {
                client.send(yaml.saveToString());
            } catch (Exception interruptedException) {
                try {
                    client.connect("localhost", 8894, 0);
                } catch (InterruptedException e) {}
            }
        }, 20L, 20L);
    }
}
