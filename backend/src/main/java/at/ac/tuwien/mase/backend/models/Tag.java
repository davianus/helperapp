package at.ac.tuwien.mase.backend.models;


import javax.persistence.*;
import java.util.List;

/**
 * Created by xvinci on 11/14/15.
 */
@Entity
public class Tag {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
