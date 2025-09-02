package andrews.table_top_craft.util;

public class Triple<X, Y, Z> {
    private final X x;
    private final Y y;
    private final Z z;

    public Triple(X x, Y y, Z z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public X getFirst() {
        return x;
    }

    public Y getSecond() {
        return y;
    }

    public Z getThird() {
        return z;
    }

    public static  <Q, K, V> Triple<Q, K, V> of(Q first, K second, V third) {
        return new Triple<>(first, second, third);
    }
}
