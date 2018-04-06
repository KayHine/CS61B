public class SawToothGenerator implements Generator {
    int period;
    int state;

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
        return ((1 / period) * num * 2) - 1;
    }
}
