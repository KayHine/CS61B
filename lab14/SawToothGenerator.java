public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        this.period = period;
        state = 0;
    }

    @Override
    public double next() {
        state = state + 1;
        int num = state % (period - 1);
        return normalize(num);
    }

    public double normalize(int num) {
        return 2 * ((num * 1.001) / period) - 1;
    }
}
