package at.ac.tuwien.mase.backend.viewmodels;

/**
 * Created by lobmaier on 14.11.2015.
 */
public class LocationEdit {
    private Long id;
    private String name;
    private double[] location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }
}
