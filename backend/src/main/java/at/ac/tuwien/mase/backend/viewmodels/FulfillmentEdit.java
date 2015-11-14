package at.ac.tuwien.mase.backend.viewmodels;

import java.util.Date;

/**
 * Created by lobmaier on 14.11.2015.
 */
public class FulfillmentEdit {
    private Date until;
    private Integer amount;
    private Long requestId;

    public Date getUntil() {
        return until;
    }

    public void setUntil(Date until) {
        this.until = until;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }
}
