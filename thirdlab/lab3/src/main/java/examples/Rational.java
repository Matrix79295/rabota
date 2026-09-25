package examples;
public class Rational {
    final long num, den;

    public Rational(long num, long den) {
        if (den == 0) throw new IllegalArgumentException("den=0");
        if (den < 0) { num = -num; den = -den; }
        long g = gcd(Math.abs(num), den);
        this.num = num / g;
        this.den = den / g;
    }

    public Rational(long num) { this(num, 1); }

    private static long gcd(long a, long b) {
        return b == 0 ? (a == 0 ? 1 : a) : gcd(b, a % b);
    }

    public double toDouble() { return (double) num / den; }

    @Override public boolean equals(Object o) {
        if (!(o instanceof Rational)) return false;
        Rational r = (Rational) o;
        return num == r.num && den == r.den;
    }

    @Override public int hashCode() { return Long.hashCode(num * 31 + den); }
    @Override public String toString() { return den == 1 ? "" + num : num + "/" + den; }
}