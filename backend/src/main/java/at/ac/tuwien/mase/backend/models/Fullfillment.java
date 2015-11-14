package at.ac.tuwien.mase.backend.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by xvinci on 11/14/15.
 */
@Document
public class Fullfillment {
    private Date until;
    private int amount;
    private boolean done;

    @DBRef
    private Request request;

    @DBRef
    private User user;

    public Date getUntil() {
        return until;
    }

    public void setUntil(Date until) {
        this.until = until;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
