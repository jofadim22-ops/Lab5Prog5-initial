package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class Route implements Comparable<Route>, Serializable {

    public static Route createEmpty() {
        return new Route();
    }

    private static final long serialVersionUID = 3L;
    private static int nextId = 1;
    private static final Object ID_LOCK = new Object();

    private int id;
    private String name;
    private Coordinates coordinates;
    private Date creationDate;
    private Location from;
    private Location to;
    private Float distance;

    public Route() {}

    public Route(String name, Coordinates coordinates, Location from, Location to, Float distance) {
        setName(name);
        setCoordinates(coordinates);
        this.from = from;
        this.to = to;
        setDistance(distance);
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate != null ? new Date(creationDate.getTime()) : null;
    }
    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public Float getDistance() {
        return distance;
    }

    public void setId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Route ID must be non-null");
        }

        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Route name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("coordinates cannot be null");
        }
        this.coordinates = coordinates;
    }

    public void setCreationDate(Date creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("Creation date cannot be null");
        }
        this.creationDate = new Date(creationDate.getTime());
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public void setDistance(Float distance) {
        if (distance == null || distance <= 1) {
            throw new IllegalArgumentException("Distance must be non-null and > 1");
        }
        this.distance = distance;
    }

    public void assignNewId() {
        synchronized (ID_LOCK) {
            this.id = nextId++;

        }
    }

    public static void updateNextId(int loadedId) {
        synchronized (ID_LOCK) {
            if (loadedId >= nextId) {
                nextId = loadedId + 1;
            }
        }
    }


    public static void resetNextId(int startId) {
        synchronized (ID_LOCK) {
            nextId = Math.max(1, startId);
        }
    }

    public static int getCurrrentNextId() {
        synchronized (ID_LOCK) {
            return nextId;
        }
    }


    @Override
    public int compareTo(Route other) {
        if (other == null) return 1;

        int distanceCmp = this.distance.compareTo(other.distance);
        if (distanceCmp != 0) return distanceCmp;

        int nameCmp = this.name.compareToIgnoreCase(other.name);
        if (nameCmp != 0) return nameCmp;

        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", name=' " + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", from=" + from +
                ", to=" + to +
                ", distance=" + distance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = new Route();
        return Objects.equals(id, route.id) &&
                Objects.equals(name, route.name) &&
                Objects.equals(coordinates, route.coordinates) &&
                Objects.equals(creationDate, route.creationDate) &&
                Objects.equals(from, route.from) &&
                Objects.equals(to, route.to) &&
                Objects.equals(distance, route.distance);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, from, to, distance);
    }

    public static Route create(String name, Coordinates coordinates, Location from,
                               Location to, Float distance) {
        Route route = new Route();
        route.assignNewId();
        route.setCreationDate(new Date());
        route.setName(name);
        route.setCoordinates(coordinates);
        route.setFrom(from);
        route.setTo(to);
        route.setDistance(distance);
        return route;
    }


    public static Route fromData (Integer id, String name, Coordinates coordinates,
                                 Date creationDate, Location from, Location to, Float distance) {
        Route route = new Route();
        route.setId(id);
        updateNextId(id);
        route.setName(name);
        route.setCoordinates(coordinates);
        route.setCreationDate(creationDate);
        route.setFrom(from);
        route.setTo(to);
        route.setDistance(distance);
        return route;
    }
}











