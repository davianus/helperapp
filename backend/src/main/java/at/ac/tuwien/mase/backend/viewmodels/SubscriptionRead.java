package at.ac.tuwien.mase.backend.viewmodels;

import at.ac.tuwien.mase.backend.models.Subscription;
import at.ac.tuwien.mase.backend.models.Tag;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lobmaier on 14.11.2015.
 */
public class SubscriptionRead {
    private long id;
    private Date start;
    private Date end;
    private List<String> tags;

    public SubscriptionRead(Subscription subscription) {
        this.id = subscription.getId();
        this.start = subscription.getStart();
        this.end = subscription.getEnd();
        this.tags = subscription.getTags().stream().map(Tag::getName).collect(Collectors.toList());
    }

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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
