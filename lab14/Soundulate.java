import java.util.ArrayList;

public class Soundulate {

	public static void main(String[] args) {
//		Generator generator = new SawToothGenerator(512);
//		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
//		gav.drawAndPlay(4096, 1000000);

//		Generator generator = new AcceleratingSawToothGenerator(200, 1.1);
//		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
//		gav.drawAndPlay(4096, 1000000);

		Generator g1 = new StrangeBitwiseGenerator(250);
		Generator g2 = new StrangeBitwiseGenerator(201);

		ArrayList<Generator> generators = new ArrayList<Generator>();
		generators.add(g1);
		generators.add(g2);
		MultiGenerator mg = new MultiGenerator(generators);

		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(mg);
		gav.drawAndPlay(500000, 1000000);
	}
} 