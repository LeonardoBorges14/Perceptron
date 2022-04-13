import java.util.Scanner;

public class Perceptron {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		boolean rodarNovamente = true;

		while (rodarNovamente) {

			int alpha = 1;
			int limite = 1;

			int fYent = 0;
			int geracao = 0;
			int[] pesos = { 0, 0, 0, 0 };
			boolean variaPeso = true;

			int[][] tabelaVerdade = { { 1, 1, 1, 1 }, { 1, 1, -1, 1 }, { 1, -1, 1, 1 }, { 1, -1, -1, 1 },
					{ -1, 1, 1, 1 }, { -1, 1, -1, 1 }, { -1, -1, 1, 1 }, { -1, -1, -1, 1 } };

			int[] targetAnd = { 1, -1, -1, -1, -1, -1, -1, -1 };
			int[] targetOr = { 1, 1, 1, 1, 1, 1, 1, -1 };
			int[] targetXor = { 0, 1, 1, 0, 1, 0, 0, 0 };

			int[] target = new int[8];
			int[] todosPesos = new int[8];;
			String operation;
			
			boolean operacaoValida = false;
			System.out.println();
			System.out.print("------------------ PERCEPTRON ------------------");
			System.out.println();
			System.out.println();
			System.out.println("Θ = 1");
			System.out.println();
			System.out.println("α = 1");
			System.out.println();

			do {
				System.out.println();
				System.out.print("Informe a Operação: ");
				operation = sc.nextLine();
				System.out.println();

				if (operation.equalsIgnoreCase("AND")) {
					target = targetAnd;
					operacaoValida = true;

				} else if (operation.equalsIgnoreCase("OR")) {
					target = targetOr;
					operacaoValida = true;

				} else if (operation.equalsIgnoreCase("XOR")) {
					target = targetXor;
					operacaoValida = true;
				}
				else {
					System.out.print("Operação Inválida! Tente Novamente.");
				}
			} while(!operacaoValida);


			while (variaPeso) {
				int[] allYent = new int[8];
				int[] allFYent = new int[8];
				
				int[][] variacaoPeso = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
						{ 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

				geracao += 1;

				for (int linha = 0; linha < 8; linha++) {
					int yent = 0;

					for (int coluna = 0; coluna < 4; coluna++) {
						yent += tabelaVerdade[linha][coluna] * pesos[coluna];
					}

					if (yent > limite) {
						fYent = 1;
					} else if (yent <= limite && yent >= -limite) {
						fYent = 0;
					} else {
						fYent = -1;
					}

					int variacao = alpha * (target[linha] - fYent);

					variacaoPeso[linha][0] = variacao * tabelaVerdade[linha][0];
					variacaoPeso[linha][1] = variacao * tabelaVerdade[linha][1];
					variacaoPeso[linha][2] = variacao * tabelaVerdade[linha][2];
					variacaoPeso[linha][3] = variacao * tabelaVerdade[linha][3];

					pesos[0] += variacaoPeso[linha][0];
					pesos[1] += variacaoPeso[linha][1];
					pesos[2] += variacaoPeso[linha][2];
					pesos[3] += variacaoPeso[linha][3];

					allYent[linha] = yent;
					allFYent[linha] = fYent;
					todosPesos = pesos;
				}

				System.out.println("Epoca: " + geracao);
				System.out.println();
				System.out.println();
				int count = 1;
				System.out.print("Yent: [");
				count = 1;
				for (int Yent : allYent) {
					if (count == 8) {
						System.out.print(Yent);
					}
					else {
						System.out.print(Yent + ", ");
					}
					count++;
				}
				System.out.print("] ");
				System.out.println();
				System.out.println();
				System.out.print("FYent: [");
				count = 1;
				for (int FYent : allFYent) {
					if (count == 8) {
						System.out.print(FYent);
					} else {
						System.out.print(FYent + ", ");
					}
					count++;
				}
				System.out.print("] ");
				System.out.println();
				System.out.println();
				System.out.print("Pesos: ");
				System.out.print("[");
				count = 1;
				for (int peso : todosPesos) {
					if (count == 4) {
						System.out.print(peso);
					} else {
						System.out.print(peso + ", ");
					}
					count++;
				}
				System.out.print("] ");
				System.out.println();
				System.out.println("------------------------------------------------");

				boolean achouResultado = pesoVaria(variacaoPeso);

				if (geracao == 50 && operation.equalsIgnoreCase("XOR")) {
					variaPeso = false;
					System.out.println("");
					System.out.println("Utilizando a operação XOR o looping se tornou infinito!");
				}

				if (achouResultado) {
					variaPeso = false;
				}
			}

			boolean opcaoValida = false;
			
			if(!operation.equalsIgnoreCase("XOR")) {
				System.out.println("");
				System.out.print("Pesos Finais: ");
				System.out.print("[");
				int count = 1;
				for (int peso : todosPesos) {
					if (count == 4) {
						System.out.print(peso);
					} else {
						System.out.print(peso + ", ");
					}
					count++;
				}
				System.out.print("] ");
				System.out.println();
			}

			System.out.println();
			System.out.println("Épocas Geradas: " + geracao);
			System.out.println();
			System.out.println("------------------------------------------------");

			do {
				System.out.println();
				System.out.print("Deseja Rodar Novamente(S / N)? ");
				String rodar = sc.nextLine();
				System.out.println();

				if (rodar.equalsIgnoreCase("S") || rodar.equalsIgnoreCase("N")) {
					opcaoValida = true;
				}
				else {
					System.out.println();
					System.out.println("Erro! Informe uma opção válida (S / N).");
					System.out.println();
				}

				if (rodar.equalsIgnoreCase("N")) {
					rodarNovamente = false;
				}
				
			} while (!opcaoValida);

		}
		sc.close();
	}

	public static boolean pesoVaria(int[][] variacaoPeso) {
		boolean done = true;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 4; j++) {
				if (variacaoPeso[i][j] != 0) {
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
