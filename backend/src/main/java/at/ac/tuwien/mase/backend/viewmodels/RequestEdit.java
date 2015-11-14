package at.ac.tuwien.mase.backend.viewmodels;

import java.util.Date;
import java.util.List;

/**
 * Created by lobmaier on 14.11.2015.
 */
public class RequestEdit {
    private List<String> tags;
    private Integer amount;
    private Date startDate;
    private Date endDate;
    private String description;
    private LocationEdit location;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocationEdit getLocation() {
        return location;
    }

    public void setLocation(LocationEdit location) {
        this.location = location;
    }
}
