package net.maple3142.craft2d;

public class Vector2 {
    public double x;
    public double y;

    public Vector2() {
        x = 0;
        y = 0;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    public void plus(Vector2 vec) {
        x += vec.x;
        y += vec.y;
    }

    public void multiply(double k) {
        x *= k;
        y *= k;
    }

    public static Vector2 multiply(Vector2 vec, double k) {
        return new Vector2(vec.x * k, vec.y * k);
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
