package at.ac.tuwien.mase.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Created by xvinci on 11/14/15.
 */
@Document
public class Request {
    @Id
    private String id;
    private Date startdate;
    private Date enddate;
    private String description;
    private int amount;
    private List<String> tags;

    @DBRef
    private List<Fullfillment> fullfillments;

    @DBRef
    private User user;

    @DBRef
    private Location location;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
