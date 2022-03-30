public class Perceptron {

	public static void main(String[] args) {

		int alpha = 1;
		int threshold = 1;

		int fYent = 0;
		int generation = 0;
		String operation = "and";
		int[] WEIGHT = { 0, 0, 0, 0 };
		boolean hasWeightVariation = true;

		int[][] TRUTH_TABLE = { { 1, 1, 1, 1 }, { 1, 1, -1, 1 }, { 1, -1, 1, 1 }, { 1, -1, -1, 1 }, { -1, 1, 1, 1 },
				{ -1, 1, -1, 1 }, { -1, -1, 1, 1 }, { -1, -1, -1, 1 } };

		int[] targetAnd = { 1, -1, -1, -1, -1, -1, -1, -1 };
		int[] targetOr = { 1, 1, 1, 1, 1, 1, 1, -1 };
		int[] targetXor = { 0, 1, 1, 0, 1, 0, 0, 0 };

		int[] TARGET = targetAnd;

		while (hasWeightVariation) {
			int[] allYent = {};
			int[] allFYent = {};
			int[] allWeights = {};
			int[][] weightVariation = { {}, {}, {}, {}, {}, {}, {}, {} };

			generation += 1;

			for (int line = 0; line < 8; line++) {
				int yent = 0;

				// Cálculo do Yent
				for (int column = 0; column < 4; column++) {
					yent += TRUTH_TABLE[line][column] * WEIGHT[column];
				}

				// Captura do (f)Yent
				fYent = getFYent(yent, threshold);

				int wVariationFirstPart = alpha * (TARGET[line] - fYent);

				// Mapeamento das variações dos pesos (matriz(3,4))
				weightVariation[line][0] = wVariationFirstPart * TRUTH_TABLE[line][0];
				weightVariation[line][1] = wVariationFirstPart * TRUTH_TABLE[line][1];
				weightVariation[line][2] = wVariationFirstPart * TRUTH_TABLE[line][2];
				weightVariation[line][3] = wVariationFirstPart * TRUTH_TABLE[line][3];

				// Atualização dos valores dos pesos
				WEIGHT[0] += weightVariation[line][0];
				WEIGHT[1] += weightVariation[line][1];
				WEIGHT[2] += weightVariation[line][2];
				WEIGHT[3] += weightVariation[line][3];

				System.out.println("Epoca: " + generation);
				System.out.println("Pesos: ");
				System.out.println(WEIGHT);
				System.out.println("Yent: ");
				System.out.println(yent);
				System.out.println("FYent: ");
				System.out.println(fYent);
				System.out.println();

//			      allYent.push(yent);
//			      allFYent.push(fYent);
//			      allWeights.push(WEIGHT);
			}

			boolean done = noWeightVariation(weightVariation);

			if (generation == 100 && operation == "xor") {
				hasWeightVariation = false;
				System.out.println(
						"A operação XOR é um problema não linear, por isso esse algoritmo não consegue resolver. Foi limitado em 100 épocas para demostração!");
				continue;
			}

			if (done) {
				hasWeightVariation = false;
				continue;
			}
		}

	}

	public static int getFYent(int yent, int threshold) {
		if (yent > threshold) {
			return 1;
		} else if (yent <= threshold && yent >= -threshold) {
			return 0;
		} else {
			return -1;
		}
	}

	public static boolean noWeightVariation(int[][] weightVariation) {
		boolean done = true;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 4; j++) {
				if (weightVariation[i][j] != 0) {
					done = false;
					break;
				}
			}

			if (!done)
				break;
		}

		return done;
	}

}
