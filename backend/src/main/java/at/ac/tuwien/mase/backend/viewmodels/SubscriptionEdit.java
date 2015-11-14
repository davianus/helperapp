package at.ac.tuwien.mase.backend.viewmodels;

import java.util.Date;
import java.util.List;

/**
 * Created by lobmaier on 14.11.2015.
 */
public class SubscriptionEdit {
    private Date start;
    private Date end;
    private List<String> tags;

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
