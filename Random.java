public class Random {
    public static int random(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be less than or equal to max");
        }
        java.util.Random rand = new java.util.Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
