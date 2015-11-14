package at.ac.tuwien.mase.backend.viewmodels;

import at.ac.tuwien.mase.backend.models.User;

/**
 * Created by lobmaier on 14.11.2015.
 */
public class UserRead {

    public UserRead(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.logo = user.getLogo();
        this.verified = user.isVerified();
        this.creator = user.isCreator();
    }

    private String username;
    private String name;
    private String phone;
    private String logo;
    private boolean verified;
    private boolean creator;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isCreator() {
        return creator;
    }

    public void setCreator(boolean creator) {
        this.creator = creator;
    }
}
