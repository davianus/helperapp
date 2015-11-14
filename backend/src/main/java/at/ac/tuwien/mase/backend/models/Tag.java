package at.ac.tuwien.mase.backend.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by xvinci on 11/14/15.
 */
@Entity
public class Tag {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private int count;
    @ManyToMany
    private List<Request> requests;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
