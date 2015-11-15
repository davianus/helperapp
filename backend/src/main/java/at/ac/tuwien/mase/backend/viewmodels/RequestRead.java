package at.ac.tuwien.mase.backend.viewmodels;

import at.ac.tuwien.mase.backend.models.Fulfillment;
import at.ac.tuwien.mase.backend.models.Request;
import at.ac.tuwien.mase.backend.models.Tag;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lobmaier on 14.11.2015.
 */
public class RequestRead {
    private long id;
    private List<String> tags;
    private int amount;
    private int amountDone;
    private Date startDate;
    private Date endDate;
    private String description;
    private UserRead user;
    private LocationRead location;
    private List<FulfillmentRead> fulfillments;

    public RequestRead(Request request) {
        this.id = request.getId();
        this.tags = request.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        this.amount = request.getAmount();
        if (request.getFulfillments() != null) this.amountDone = request.getFulfillments().stream().mapToInt(Fulfillment::getAmount).sum();
        else this.amountDone = 0;
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.user = new UserRead(request.getUser());
    }

    public RequestRead(Request request, boolean deep) {
        this(request);
        if (deep) {
            this.description = request.getDescription();
            this.location = new LocationRead(request.getLocation());
            if (request.getFulfillments() != null) this.fulfillments = request.getFulfillments().stream().map(FulfillmentRead::new).collect(Collectors.toList());
        }
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getAmountDone() {
        return amountDone;
    }

    public void setAmountDone(int amountDone) {
        this.amountDone = amountDone;
    }

    public UserRead getUser() {
        return user;
    }

    public void setUser(UserRead user) {
        this.user = user;
    }

    public LocationRead getLocation() {
        return location;
    }

    public void setLocation(LocationRead location) {
        this.location = location;
    }

    public List<FulfillmentRead> getFulfillments() {
        return fulfillments;
    }

    public void setFulfillments(List<FulfillmentRead> fulfillments) {
        this.fulfillments = fulfillments;
    }
}
