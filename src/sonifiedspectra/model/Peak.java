package sonifiedspectra.model;

/**
 * Created by Hvandenberg on 5/28/15.
 */
public class Peak {

    private int id;

    // doubles
    private double value;
    private double x1;
    private double x2;

    public Peak(int id, double value, double x1, double x2) {
        this.id = id;
        this.value = value;
        this.x1 = x1;
        this.x2 = x2;
    }

    public double getWidth() {
        return x1 - x2;
    }

    @Override
    public String toString() {
        return "Peak{" +
                "id=" + id +
                ", value=" + value +
                ", x1=" + x1 +
                ", x2=" + x2 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Peak peak = (Peak) o;

        if (id != peak.id) return false;
        if (Double.compare(peak.value, value) != 0) return false;
        if (Double.compare(peak.x1, x1) != 0) return false;
        if (Double.compare(peak.x2, x2) != 0) return false;

        return true;
    }

    // Getters and Setters
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
