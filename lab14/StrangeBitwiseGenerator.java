public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        state = 0;
    }

    @Override
    public double next() {
        state = state + 1;
        int weirdState = state & (state >> 3)  & (state >> 8) % period;
        int num = state % (period - 1);
        return normalize(weirdState);
    }

    public double normalize(int num) {
        return 2 * ((num * 1.001) / period) - 1;
    }
}
