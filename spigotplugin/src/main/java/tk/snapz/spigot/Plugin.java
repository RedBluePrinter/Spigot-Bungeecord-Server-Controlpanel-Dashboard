package tk.snapz.spigot;

import hu.trigary.simplenetty.client.Client;
import hu.trigary.simplenetty.serialization.DataSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Plugin extends JavaPlugin {
    @Override
    public void onLoad() {

    }

    @Override
    public void onDisable() {

    }

    private static String uid = Bukkit.getIp() + "-" + Bukkit.getPort();

    public YamlConfiguration template() {
        YamlConfiguration config = new YamlConfiguration();
        config.set("mtype", "spigot");
        config.set("sname", Bukkit.getServerName());
        config.set("uid", uid);
        return config;
    }

    @Override
    public void onEnable() {
        YamlConfiguration config = new YamlConfiguration();
        File directory = new File(this.getDataFolder() + "");
        if(!directory.exists()) {
            directory.mkdirs();
        }
        File configFile = new File(this.getDataFolder() + "", "networking.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException ioException) {
            ioException.printStackTrace();
        }
        if(!config.isSet("uid")) {
            uid = new Random().nextInt(10000) + "-" + Bukkit.getIp() + "-" + Bukkit.getPort();
            config.set("uid",  uid);
            try {
                config.save(configFile);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else {
            uid = config.getString("uid");
        }

        createClient();

        client.onConnected(() -> {
            YamlConfiguration initChannel = template();
            initChannel.set("token", ServerToken.serverToken);
            initChannel.set("port", Bukkit.getPort());
            client.send(initChannel.saveToString());
        });

        client.onDisconnected(this::checkAndConnect);

        client.onException((ex)-> {
            try {
                client.disconnect();
                checkAndConnect();
            } catch (InterruptedException interruptedException) {interruptedException.printStackTrace();}
        });

        checkAndConnect();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            try {
                client.send(template().saveToString());
            } catch (Exception throwable) {
                checkAndConnect();
            }
        }, 0L, 100L);
    }

    private int port = 8894;
    private String host = "localhost";
    private static Client<String> client = null;

    public static DataSerializer<String> stringSerializer = new DataSerializer<String>() {
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

    public void checkAndConnect() {
        try {
            if(client.getContext() == null) {
                client.connect(host, port, 10000);
            } else {
                if (!client.getContext().channel().isOpen()) {
                    client.connect(host, port, 10000);
                }
            }
        } catch (InterruptedException e) {}
    }

    public void createClient() {
        client = new Client<>(stringSerializer);
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
    }
}
