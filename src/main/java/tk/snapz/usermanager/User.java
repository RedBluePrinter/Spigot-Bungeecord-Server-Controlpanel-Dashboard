package tk.snapz.usermanager;

public class User {

    User(String username, User original) {
        if (original != null) {
            this.original = original;
        }
    }

    private User original = null;

    public User getOriginal() {
        return original;
    }

    public String username = "";
    public String base64Password = "";

    public void setPassword(String username, String password) {

    }
}
