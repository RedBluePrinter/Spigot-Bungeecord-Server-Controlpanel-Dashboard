package tk.snapz.usermanager;

import com.ericrabil.yamlconfiguration.configuration.InvalidConfigurationException;
import com.ericrabil.yamlconfiguration.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class Users {

    public static YamlConfiguration userConfiguration = new YamlConfiguration();

    public static void init() throws IOException, InvalidConfigurationException {
        File authData = new File("auth.d");
        if(!authData.exists()) {
            YamlConfiguration newData = new YamlConfiguration();
            newData.set("auth.users.admin.pass", Base64.getEncoder().encodeToString("password".getBytes()));
            authData.createNewFile();
            newData.save(authData);
        }
        userConfiguration.load(authData);
    }

    public static void save() throws IOException {
        userConfiguration.save(new File("auth.d"));
    }

    public static User getUser(String username) {
        User user = new User(username, null);
        user.username = username;
        user.base64Password = userConfiguration.getString("auth.users." + username + ".pass");
        return user;
    }

    public static void updateUser(User user) {
        User originalUser = user.getOriginal();
        userConfiguration.set("auth.users." + originalUser, null);
        userConfiguration.set("auth.users." + user.username + ".pass", user.base64Password);
    }

    public static boolean isUserRegistered(String username) {
        return userConfiguration.contains("auth.users." + username);
    }
}
