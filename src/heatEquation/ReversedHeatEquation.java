package heatEquation;

public class ReversedHeatEquation {
	private final Vector finalCondition;
	private final Vector boundaryCondition; //まずは熱源の様子が時間によって変化しない問題を考える
	private final SquareMatrix laplaceOperator; //熱の拡散を表す行列

	ReversedHeatEquation(Vector finalCondition, Vector boundaryCondition, SquareMatrix laplaceOperator){
		this.finalCondition = finalCondition;
		this.boundaryCondition = boundaryCondition;
		this.laplaceOperator = laplaceOperator;
	}

	public double[] solveByLU(int endTime){
		Vector diffusion = new Vector(this.boundaryCondition.getValue());
		SquareMatrix coefficientMatrix = new SquareMatrix(this.laplaceOperator.getValue());
		SquareMatrix lowerTriangle = coefficientMatrix.splitLU();

		Vector solution = new Vector(this.finalCondition.getValue());

		for(int time = endTime; time>0;time--){
			solution.setValue(solution.getDifferenceValueWith(diffusion));

			solution.setValue(
				ReversedHeatEquation.forwardSubstitutionForLowerTriangle(
						lowerTriangle, solution
					)
				);

			solution.setValue(
				ReversedHeatEquation.backwordSubstitutionForUpperTriangle(
					coefficientMatrix, solution
					)
			);

			//方程式を解くたびに行列のピボット選択を反映する必要があるみたい。←TODO もっと勉強
			solution.setValue(HeatController.pivotChange(solution.getValue()));
		}

		return solution.getValue();
	}

	private static double[] forwardSubstitutionForLowerTriangle(SquareMatrix lowerTriangle, Vector rightHandVector){
		double[] solution = new double[rightHandVector.size()];

		for(int row = 0; row < rightHandVector.size(); row++){
			solution[row] = rightHandVector.getValue(row);

			for(int column = 0; column < row; column++){
				solution[row] -= lowerTriangle.getValue(row, column)*solution[column];
			}

			solution[row] = solution[row]/lowerTriangle.getValue(row, row);
		}

		return solution;

	}

	private static double[] backwordSubstitutionForUpperTriangle(SquareMatrix upperTriangle, Vector rightHandVector){
		double[] solution = new double[rightHandVector.size()];

		for(int row = rightHandVector.size()-1; row >= 0; row--){
			solution[row] = rightHandVector.getValue(row);

			for(int column = row + 1; column < rightHandVector.size(); column++){
				solution[row] -= upperTriangle.getValue(row, column)*solution[column];
			}

			solution[row] = solution[row]/upperTriangle.getValue(row, row);
		}

		return solution;
	}

	public double[] solveWithoutLU(int endTime){
		Vector diffusion = new Vector(this.boundaryCondition.getValue());

		Vector solution = new Vector(this.finalCondition.getValue());

		for(int time = endTime; time>0;time--){
			SquareMatrix coefficientMatrix = new SquareMatrix(
					HeatController.pivotChange(
							HeatController.makeLineHeatTransitionMatrix(finalCondition.size())
							)
					);
			solution.setValue(solution.getDifferenceValueWith(diffusion));			solution.setValue(
					simpleGausianElimination(coefficientMatrix,solution)
				);
			//方程式を解くたびに行列のピボット選択を反映する必要があるみたい。←TODO もっと勉強
			solution.setValue(HeatController.pivotChange(solution.getValue()));
		}

		return solution.getValue();
	}

	private static double[] simpleGausianElimination(SquareMatrix matrix, Vector rightHandVector){
		double scale;
		double pivotLevelRightHand;
		double originalRightHand;
		for(int column = 0; column < rightHandVector.size(); column++){
			for(int row = column+1; row < rightHandVector.size(); row++){
				scale = matrix.eliminateThePoint(row,column);
			    pivotLevelRightHand = rightHandVector.getValue(column);
				originalRightHand = rightHandVector.getValue(row);
				rightHandVector.setValue(originalRightHand-scale*pivotLevelRightHand, row);
			}
		}

		return backwordSubstitutionForUpperTriangle(matrix, rightHandVector);

	}
}
