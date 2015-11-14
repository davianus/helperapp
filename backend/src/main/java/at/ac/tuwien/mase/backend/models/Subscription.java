package at.ac.tuwien.mase.backend.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by lobmaier on 14.11.2015.
 */
@Entity
public class Subscription {
    @Id
    @GeneratedValue
    private long id;
    private Date start;
    private Date end;

    @ManyToOne
    private User user;
    @ManyToMany
    private List<Tag> tags;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
