package gaussianElimination;

public class SolvingController {
	public static void main(String[] args){
		// 去年のアドベントカレンダー
		// http://interprism.hatenablog.com/entry/dirichlet_problem_by_java
		// のディリクレ問題
		double[][] simultaneousEquationCoefficients = makeSimpleDirichtelCoefficients(2);
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

		double[] constants = makeConstants(2);
//			{5, 5, 5, 0, 0, 0, 0, 0, 0};

		Simultaneousness simultaneousness =
				new Simultaneousness(
						Simultaneousness.twoDimensionalArrayAdapter(simultaneousEquationCoefficients, constants)
						);

		long simpleGEStart = System.nanoTime();
		simultaneousness.solve();
		long simpleGEEnd = System.nanoTime();

		double[] initialCondition = {5,5,5,5,5,5,5,5,5};

		VectorEquation vectorEquation =
				VectorEquation.createByDoubleNumbers(simultaneousEquationCoefficients, initialCondition);

		System.out.println("Next, let us see the LU program");

		long withLUStart = System.nanoTime();
		double[] heat = vectorEquation.solveAsHeatEquation(100);
		long withLUEnd = System.nanoTime();
		System.out.println("Finally, the solution is");
		String camma = "[ ";
		for(double element:heat){
			System.out.print(camma);
			System.out.print(element);
			camma = " , ";
		}

		System.out.println(" ].");

		System.out.println("--------time--------");
		long simpleGETime = simpleGEEnd - simpleGEStart;
		long withLUTime = withLUEnd - withLUStart;
		System.out.println("First Gausian Elimination:" + simpleGETime);
		System.out.println("Gausian Elimination with LU:" + withLUTime);
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

	private static double[][] makeSimpleDirichtelCoefficients(int areaLength){
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
