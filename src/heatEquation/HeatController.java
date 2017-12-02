package heatEquation;

public class HeatController {
	public static void main(String[] args){
		// 欲しい時間に欲しい温度分布を得るために必要な現在の温度分布を調べる機能。ポンコツ。
		//
		// 必ず偶数サイズの配列にすること。
		// わずかでも設定値を変えると結果が大幅に変わり、カオスを実感できる。
		// また、解が波形を描くことがあり、フーリエの研究成果を何となく感じることができる。
		double[] finalDistributionValue = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
		int endTime = 10;
		SquareMatrix laplaceOperator =
				new SquareMatrix(
						pivotChange(
								makeLineHeatTransitionMatrix(finalDistributionValue.length)
								)
						);
		Vector finalDistribution = new Vector(pivotChange(finalDistributionValue));

		double[] boundaryConditionValue ={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,21};

		Vector boundaryCondition = new Vector(getHalf(pivotChange(boundaryConditionValue)));


		ReversedHeatEquation reversedHeat =
				new ReversedHeatEquation(finalDistribution, boundaryCondition, laplaceOperator);

		long withLUStart = System.nanoTime();
		double[] heat = reversePivotChange(reversedHeat.solveByLU(endTime));
		long withLUEnd = System.nanoTime();
		System.out.println("The solution by LUPartProgram is");
		String camma = "[ ";
		for(double element:heat){
			System.out.print(camma);
			System.out.print(element);
			camma = " , ";
		}
		System.out.println(" ].");


		ReversedHeatEquation reversedHeatWithoutLU =
				new ReversedHeatEquation(finalDistribution, boundaryCondition, new SquareMatrix(
						pivotChange(
								makeLineHeatTransitionMatrix(finalDistributionValue.length)
								)
						)
					);
		long withoutLUStart = System.nanoTime();
		double[] heatWithoutLU = reversePivotChange(reversedHeatWithoutLU.solveWithoutLU(endTime));
		long withoutLUEnd = System.nanoTime();
		System.out.println("The solution withby withoutLU-Program is");
		camma = "[ ";
		for(double element:heatWithoutLU){
			System.out.print(camma);
			System.out.print(element);
			camma = " , ";
		}
		System.out.println(" ].");

		System.out.println("--------time--------");
		long withLUTime = withLUEnd - withLUStart;
		long withoutLUTime = withoutLUEnd - withoutLUStart;
		System.out.println("Gausian Elimination with LUPart:" + withLUTime);
		System.out.println("Gausian Elimination without LU :" + withoutLUTime);
		System.out.println("---------------------------------");

	}

	public static double[][] makeLineHeatTransitionMatrix(int areaLength){
		double[][] coefficientMatrix = new double[areaLength][areaLength];
		double heatPartitiontion = 0.5;
		for(int row = 0; row< areaLength ;row++){
			for(int column = 0; column < areaLength;column++){
				coefficientMatrix[row][column] = 0;
			}

			if(row == 0){
				coefficientMatrix[row][row + 1] = heatPartitiontion;
			} else if(row == areaLength - 1){
				coefficientMatrix[row][row - 1] = heatPartitiontion;
			} else {
				coefficientMatrix[row][row + 1] = heatPartitiontion;
				coefficientMatrix[row][row - 1] = heatPartitiontion;
			}
		}

		return coefficientMatrix;
	}

	// TODO :LU分解の中でできるようにする
	public static double[][] pivotChange(double[][] matrixValue){
		double[][] newMatrixValue =  new double[matrixValue.length][];
		for(int row = 0; row <matrixValue.length-1; row++){
			newMatrixValue[row] = matrixValue[row+1];
		}
		newMatrixValue[matrixValue.length-1] = matrixValue[0];

		return newMatrixValue;
	}

	// TODO :LU分解の中でできるようにする
	public static double[] pivotChange(double[] vectorValue){
		double[] newMatrixValue =  new double[vectorValue.length];
		for(int row = 0; row <vectorValue.length-1; row++){
			newMatrixValue[row] = vectorValue[row+1];
		}
		newMatrixValue[vectorValue.length-1] = vectorValue[0];

		return newMatrixValue;
	}

	// TODO :LU分解の中でできるようにする
	public static double[] reversePivotChange(double[] vectorValue){
		double[] newMatrixValue =  new double[vectorValue.length];
		for(int row = 0; row <vectorValue.length-1; row++){
			newMatrixValue[row+1] = vectorValue[row];
		}
		newMatrixValue[0] = vectorValue[vectorValue.length-1];

		return newMatrixValue;
	}


	// TODO :LU分解の中でできるようにする
	public static double[] getHalf(double[] vectorValue){
		for(int row = 0; row <vectorValue.length; row++){
			vectorValue[row] = vectorValue[row]/2;
		}

		return vectorValue;
	}

}
