package model;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Serializable {

    private static final long serialVersionUID = 2L;
    public static final int MAX_NAME_LENGTH = 241;

    private Double x;
    private float y;
    private int z;
    private String name;

    public Location(Double x, Float y,Integer z, String name) {
        if (x == null) {
            throw new IllegalArgumentException("Location x cannot be null.");
        }
        if (name != null && name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Location name cannot exceed " + MAX_NAME_LENGTH + " characters");
        }
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public Double getX() { return x; }
    public float getY() { return y; }
    public int getZ() { return z; }
    public String getName() { return name; }

    public void setX(Double x) {
        if (x == null) throw new IllegalArgumentException("Location x cannot be null");
        this.x = x;
    }

    public void setY(float y) { this.y = y; }
    public void setZ(int z) { this.z = z; }

    public void setName(String name) {
        if (name != null && name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Location name cannot exceed " + MAX_NAME_LENGTH + " characters");
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                (name != null ? ", name='" + name + '\'' : "") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return Float.compare(location.y, y) == 0 &&
                z == location.z &&
                Objects.equals(x, location.x) &&
                Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }
}