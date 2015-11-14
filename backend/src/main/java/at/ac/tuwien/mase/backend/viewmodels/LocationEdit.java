package at.ac.tuwien.mase.backend.viewmodels;

/**
 * Created by lobmaier on 14.11.2015.
 */
public class LocationEdit {
    private String id;
    private String name;
    private double[] location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
