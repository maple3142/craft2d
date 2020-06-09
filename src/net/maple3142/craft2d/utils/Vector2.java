package net.maple3142.craft2d.utils;

import com.google.gson.annotations.Expose;

public class Vector2 {
    @Expose
    public double x;
    @Expose
    public double y;

    public Vector2() {
        x = 0;
        y = 0;
    }

    public Vector2(Vector2 vec) {
        x = vec.x;
        y = vec.y;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2 plus(Vector2 a, Vector2 b) {
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    public static Vector2 subtract(Vector2 a, Vector2 b) {
        return new Vector2(a.x - b.x, a.y - b.y);
    }

    public static Vector2 multiply(Vector2 vec, double k) {
        return new Vector2(vec.x * k, vec.y * k);
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

    public double norm() {
        return Math.sqrt(x * x + y * y);
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
