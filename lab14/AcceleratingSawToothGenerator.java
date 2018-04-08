public class AcceleratingSawToothGenerator implements Generator {

    private int period;
    private double factor;
    private int state;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.factor = factor;
        state = 0;
    }

    @Override
    public double next() {
        state = state + 1;
        int num = state % (period - 1);
        if (state > period - 1) {
            period = (int) (period * factor);
            state = 0;
        }
        return normalize(num);
    }

    public double normalize(int num) {
        return 2 * ((num * 1.001) / period) - 1;
    }
}
