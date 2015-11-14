package at.ac.tuwien.mase.backend.models;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by xvinci on 11/14/15.
 */
@Entity
public class Request {
    @Id
    @GeneratedValue
    private long id;
    private Date startdate;
    private Date enddate;
    private String description;
    private int amount;

    @ManyToMany
    private List<Tag> tags;

    @OneToMany(mappedBy = "request")
    private List<Fullfillment> fullfillments;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Location.class)
    private Location location;

    @ManyToMany
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Fullfillment> getFullfillments() {
        return fullfillments;
    }

    public void setFullfillments(List<Fullfillment> fullfillments) {
        this.fullfillments = fullfillments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
