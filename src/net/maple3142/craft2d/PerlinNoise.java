package net.maple3142.craft2d;

import java.util.Random;

public class PerlinNoise {
    private Random random;

    public PerlinNoise(long seed) {
        random = new Random(seed);
    }

    private double interpolate(double pa, double pb, double px) {
        var ft = px * Math.PI;
        var f = (1 - Math.cos(ft)) * 0.5;
        return pa * (1 - f) + pb * f;
    }

    private double cubicInterpolate(
            double y0, double y1,
            double y2, double y3,
            double mu) {
        double a0, a1, a2, a3, mu2;
        mu2 = mu * mu;
        a0 = y3 - y2 - y0 + y1;
        a1 = y0 - y1 - a0;
        a2 = y2 - y0;
        a3 = y1;
        return (a0 * mu * mu2 + a1 * mu2 + a2 * mu + a3);
    }

    private double[] generate(int width, int amplitude, int waveLength) {
        double a = random.nextDouble();
        double b = random.nextDouble();
        var arr = new double[width];
        for (int x = 0; x < width; x++) {
            if (x % waveLength == 0) {
                a = b;
                b = random.nextDouble();
                arr[x] = a * amplitude;
            } else {
                arr[x] = interpolate(a, b, Math.floorMod(x, waveLength) / (double) waveLength) * amplitude;
            }
        }
        return arr;
    }

    public double[] generateNoise(int yOffset, int width, int amplitude, int waveLength, int octaves) {
        double[][] results = new double[octaves][width];
        for (int i = 0; i < octaves; i++) {
            results[i] = generate(width, amplitude, waveLength);
            amplitude /= 2;
            waveLength /= 2;
        }
        double[] result = new double[width];
        for (int i = 0; i < width; i++) {
            double sum = 0;
            for (int j = 0; j < octaves; j++) {
                sum += results[j][i];
            }
            result[i] = sum + yOffset;
        }
        return result;
    }

    public int[] generateNoiseInteger(int yOffset, int width, int amplitude, int waveLength, int octaves) {
        var arr = new int[width];
        var oldArr = generateNoise(yOffset, width, amplitude, waveLength, octaves);
        for (int i = 0; i < width; i++) {
            arr[i] = (int) Math.floor(oldArr[i]);
        }
        return arr;
    }
}
