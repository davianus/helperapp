package at.ac.tuwien.mase.backend.viewmodels;

import at.ac.tuwien.mase.backend.models.Location;

/**
 * Created by lobmaier on 14.11.2015.
 */
public class LocationRead {
    private long id;
    private String name;
    private double[] location;

    public LocationRead(Location location) {
        this.id = location.getId();
        this.name = location.getName();
        this.location = location.getLocation();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
