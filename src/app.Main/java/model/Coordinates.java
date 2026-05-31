package model;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int MAX_X = 849;
    public static final int MIN_Y = -880;

    private Integer x;
    private Integer y;

    public Coordinates(Integer x, Integer y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("X cannot be null");
        }
        if (y == null) {
            throw new IllegalArgumentException("Y cannot be null");
        }
        if (x > MAX_X) {
            throw new IllegalArgumentException("X cannot exceed " + MAX_X + ", got: " + x);
        }
        if (y <= MIN_Y) {
            throw new IllegalArgumentException("Y must be > " + MIN_Y + ", got: " + y);
        }
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void setX(Integer x) {
        if (x == null) {
            throw new IllegalArgumentException("X cannot be null");
        }
        if (x > MAX_X) {
            throw new IllegalArgumentException("X cannot exceed " + MAX_X);
        }
        this.x = x;
    }

    public void setY(Integer y) {
        if (y == null) {
            throw new IllegalArgumentException("Y cannot be null");
        }
        if (y <= MIN_Y) {
            throw new IllegalArgumentException("Y must be > " + MIN_Y);
        }
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{x=" + x + ", y=" + y +"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}