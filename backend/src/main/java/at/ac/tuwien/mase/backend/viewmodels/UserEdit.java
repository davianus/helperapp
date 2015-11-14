package at.ac.tuwien.mase.backend.viewmodels;

import at.ac.tuwien.mase.backend.models.User;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by lobmaier on 14.11.2015.
 */
public class UserEdit {
    private String username;
    private String name;
    private String password;
    private String phone;
    private String logo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
