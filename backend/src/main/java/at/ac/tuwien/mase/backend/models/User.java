package at.ac.tuwien.mase.backend.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by xvinci on 11/14/15.
 */
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String username;

    private String name;
    private String logo;
    private boolean verified;
    private String phone;
    private String password;
    private boolean creator;

    @OneToMany(mappedBy = "user")
    private List<Subscription> subscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Request> requests = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Fulfillment> fulfillments = new ArrayList<>();

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public List<Fulfillment> getFulfillments() {
        return fulfillments;
    }

    public void setFulfillments(List<Fulfillment> fulfillments) {
        this.fulfillments = fulfillments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCreator() {
        return creator;
    }

    public void setCreator(boolean creator) {
        this.creator = creator;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.creator) {
            return AuthorityUtils.createAuthorityList("USER", "CREATOR");
        } else {
            return AuthorityUtils.createAuthorityList("USER");
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
