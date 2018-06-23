package gaussianElimination;

public class SolvingController {
	public static void main(String[] args){
		// 去年のアドベントカレンダー
		// http://interprism.hatenablog.com/entry/dirichlet_problem_by_java
		// のディリクレ問題
		double[][] simultaneousEquationCoefficients = makeSimpleDirichtetCoefficients(3);
//			{
//				{4, -1, 0, -1, 0, 0, 0, 0, 0},
//				{-1, 4, -1, 0, -1, 0, 0, 0, 0},
//				{0, -1, 4, 0, 0, -1, 0, 0, 0},
//				{-1, 0, 0, 4, -1, 0, -1, 0, 0},
//				{0, -1, 0, -1, 4, -1, 0, -1, 0},
//				{0, 0, -1, 0, -1, 4, 0, 0, -1},
//				{0, 0, 0, -1, 0, 0, 4, -1, 0},
//				{0, 0, 0, 0, -1, 0, -1, 4, -1},
//				{0, 0, 0, 0, 0, -1, 0, -1, 4}
//				};

		double[] constants = makeConstants(3);
//			{5, 5, 5, 0, 0, 0, 0, 0, 0};

		Simultaneousness simultaneousness =
				new Simultaneousness(
						Simultaneousness.twoDimensionalArrayAdapter(simultaneousEquationCoefficients, constants)
						);

		long simpleGEStart = System.nanoTime();
		simultaneousness.solve();
		long simpleGEEnd = System.nanoTime();

		cramer.Equation plateHeat = new cramer.Equation(simultaneousEquationCoefficients, constants);

		System.out.println("Next, let us see the Cramer's Rule program");

		long withLUStart = System.nanoTime();
		double[] heat = plateHeat.solveByCofactorExpansion();
		long withLUEnd = System.nanoTime();
		System.out.println("Finally, the solution is");
		String camma = "[ ";
		for(double element:heat){
			System.out.print(camma);
			System.out.print(element);
			camma = " , ";
		}

		System.out.println(" ].");

		System.out.println("---------------------------------");
		// ヤコビ法
		double[] firstSolution = {0, 0, 0, 0, 0, 0, 0, 0, 0};
		jacobiMethod.Equation jacobiPlate =
				new jacobiMethod.Equation(simultaneousEquationCoefficients, constants, firstSolution);

		System.out.println("Finally, let us see the Jacobi method");
		long jacobiStart = System.nanoTime();
		double[] jacobiHeat = jacobiPlate.solveByNumberOfIterations(1000);
		long jacobiEnd = System.nanoTime();
		System.out.println("Finally, the solution is");
		camma = "[ ";
		for(double element:jacobiHeat){
			System.out.print(camma);
			System.out.print(element);
			camma = " , ";
		}

		System.out.println(" ].");

		System.out.println("--------time--------");
		long simpleGETime = simpleGEEnd - simpleGEStart;
		long withLUTime = withLUEnd - withLUStart;
		long jacobiTime = jacobiEnd - jacobiStart;
		System.out.println("First Gausian Elimination:" + simpleGETime);
		System.out.println("Cramer's Rule with cofactor:" + withLUTime);
		System.out.println("The Jacobi method:" + jacobiTime);
				System.out.println("---------------------------------");

		//何も考えずに消去法で解くと計算機が計算ミスをする例･･･のはずだった。
		//double型は浮動小数点型として優秀すぎて誤差が大きくなってくれない。4桁で計算すると誤差が大きくなるらしい。
//		double[][] stewart = {{0.001, 2, 3, 5.001},{-1, 3.712, 4.623, 7.335},{-2, 1.072, 5.643, 4.715}};
//
//		Simultaneousness stewartSimultaneousness =
//				new Simultaneousness(
//						Simultaneousness.twoDimensionalArrayAdapter(stewart)
//						);
//
//		stewartSimultaneousness.solve();
	}

	private static double[][] makeSimpleDirichtetCoefficients(int areaLength){
		int squareMeasure = areaLength*areaLength;
		double[][] coefficientMatrix = new double[squareMeasure][squareMeasure];
		for(int point = 0; point< squareMeasure ;point++){
			coefficientMatrix[point][point] = 4;

			if(point%areaLength == 0){
				coefficientMatrix[point][point + 1] = -1;
			} else if(point%areaLength == areaLength - 1){
				coefficientMatrix[point][point - 1] = -1;
			} else {
				coefficientMatrix[point][point + 1] = -1;
				coefficientMatrix[point][point - 1] = -1;
			}

			if(point < areaLength){
				coefficientMatrix[point][point + areaLength] = -1;
			} else if(point + areaLength > squareMeasure -1 )
			{
				coefficientMatrix[point][point - areaLength] = -1;
			} else {
				coefficientMatrix[point][point + areaLength] = -1;
				coefficientMatrix[point][point - areaLength] = -1;
			}
		}

		return coefficientMatrix;
	}

	private static double[] makeConstants(int areaLength){
		int squareMeasure = areaLength*areaLength;
		double[] constants = new double[squareMeasure];
		for(int point = 0;point<areaLength; point++){
			constants[point] = 5;
		}
		for(int point = areaLength; point < squareMeasure; point++){
			constants[point] = 0;
		}

		return constants;
	}
}
