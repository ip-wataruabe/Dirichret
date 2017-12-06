package gaussianElimination;

public class VectorEquation {
	private CoefficientMatrix coefficientMatrix;
	private RightHandVector rightHandVector;

	private final static double[] boundaryCondition = {5,5,5,0,0,0,0,0,0};

	VectorEquation(CoefficientMatrix coefficientMatrix, RightHandVector rightHandVector){
		this.coefficientMatrix = coefficientMatrix;
		this.rightHandVector = rightHandVector;
	}

	static VectorEquation createByDoubleNumbers(double[][] matrix, double[] vector){
		CoefficientMatrix coefficientMatrix = new CoefficientMatrix(matrix);
		RightHandVector rightHandVector = new RightHandVector(vector);

		return new VectorEquation(coefficientMatrix, rightHandVector);
	}

	void solveByLUPartition() {
		CoefficientMatrix LowerTriangleMatrix = splitLU();
//		System.out.println("The lower triangle matrix is");
//		System.out.println();
//		for(int i = 0; i < LowerTriangleMatrix.size(); i++){
//			for(int j = 0; j < LowerTriangleMatrix.size(); j++){
//				System.out.print(LowerTriangleMatrix.getValue(i,j) + " ");
//			}
//			System.out.println("");
//		}
//		System.out.println(".");

		VectorEquation mediateProbrem = new VectorEquation(LowerTriangleMatrix, this.rightHandVector);

		double[] mediateSolution = mediateProbrem.forwardSubstitutionForLowerTriangle();

		System.out.println("Additively, the mediate solution is");
		String camma = "[ ";
		for(double element:mediateSolution){
			System.out.print(camma);
			System.out.print(element);
			camma = " , ";
		}
		System.out.println(" ].");
		this.rightHandVector = new RightHandVector(mediateSolution);
		double[] solution = this.backwordSubstitutionForUpperTriangle();
//		System.out.println("Also, The upper triangle matrix is");
//		System.out.println();
//		for(int i = 0; i < this.coefficientMatrix.size(); i++){
//			for(int j = 0; j < this.coefficientMatrix.size(); j++){
//				System.out.print(this.coefficientMatrix.getValue(i,j) + " ");
//			}
//			System.out.println("");
//		}
//		System.out.println(".");
		System.out.println("Finally, the solution is");
		camma = "[ ";
		for(double element:solution){
			System.out.print(camma);
			System.out.print(element);
			camma = " , ";
		}

		System.out.println(" ].");
		System.out.println("---------------------------------");
	}

	/**
	 *
	 * @param time
	 * @return
	 */
	public double[] solveAsHeatEquation(int TargetTime){
		//LU分解。Uはこれ以降固定したい
		CoefficientMatrix LowerTriangle = this.splitLU();

		RightHandVector solution = this.rightHandVector;

		for(int time = 0; time < TargetTime; time++){
			VectorEquation mediateEquation = new VectorEquation(LowerTriangle, solution);
			double[] mediateSolution = mediateEquation.forwardSubstitutionForLowerTriangle();
			this.resetRightHand(mediateSolution);
			solution.reset(
				addVector(
					this.backwordSubstitutionForUpperTriangle(),
					boundaryCondition)
				);
		}

		return solution.getValue();
	}

	private double[] forwardSubstitutionForLowerTriangle(){
		double[] solution = new double[this.coefficientMatrix.size()];

		for(int row = 0; row < this.coefficientMatrix.size(); row++){
			solution[row] = this.rightHandVector.getValue(row);

			for(int column = 0; column < row; column++){
				solution[row] -= this.coefficientMatrix.getValue(row, column)*solution[column];
			}

			solution[row] = solution[row]/this.coefficientMatrix.getValue(row, row);
		}

		return solution;

	}

	private double[] backwordSubstitutionForUpperTriangle(){
		double[] solution = new double[this.coefficientMatrix.size()];

		for(int row = this.coefficientMatrix.size()-1; row >= 0; row--){
			solution[row] = this.rightHandVector.getValue(row);

			for(int column = row + 1; column < this.coefficientMatrix.size(); column++){
				solution[row] -= this.coefficientMatrix.getValue(row, column)*solution[column];
			}

			solution[row] = solution[row]/this.coefficientMatrix.getValue(row, row);
		}

		return solution;
	}

	/**
	 * 行列をLU分解する。Lを返し、this.coefficientMatrixがUとなる。
	 *
	 * @return
	 */
	private CoefficientMatrix splitLU(){
		double[][] lowerTriangleMatrix =
				new double[this.coefficientMatrix.size()][this.coefficientMatrix.size()];

		//Lを求めるときはいいが、Lの逆行列を求める場合はこのロジックは使えないことに注意
		for(int columnNumber = 0; columnNumber < this.coefficientMatrix.size(); columnNumber++){
			double[] FrobeniusColumn = this.eliminateOnTheColumn(columnNumber);
			for(int rowCoordinate = 0; rowCoordinate < this.coefficientMatrix.size(); rowCoordinate++){
				lowerTriangleMatrix[rowCoordinate][columnNumber] = FrobeniusColumn[rowCoordinate];
			}
		}

		return new CoefficientMatrix(lowerTriangleMatrix);
	}

	/**
	 * 係数行列の列消去をしつつ、フロベニウス行列の素となる列を出力する。LU分解専用。
	 *
	 * @param columnNumber
	 * @return
	 */
	private double[] eliminateOnTheColumn(int columnNumber){
		double[] FrobeniusColumn = new double[this.coefficientMatrix.size()];

		for(int rowNumber = 0; rowNumber < this.coefficientMatrix.size(); rowNumber++){
			if(rowNumber < columnNumber){
				FrobeniusColumn[rowNumber] = 0;
			} else if (rowNumber == columnNumber){
				FrobeniusColumn[rowNumber] = 1;
			} else {
				FrobeniusColumn[rowNumber]
						= this.coefficientMatrix.eliminateThePoint(rowNumber, columnNumber);
			}
		}

		return FrobeniusColumn;
	}

	private void forwardElimination(){
		for(int columnNumber = 0; columnNumber < this.coefficientMatrix.size(); columnNumber++){
			this.eliminateOnTheColumnWithoutLU(columnNumber);
		}
	}

	/**
	 * 係数行列・定数ベクトルの列消去をする。LU分解では使用しない。
	 *
	 * @param columnNumber
	 * @return
	 */
	private void eliminateOnTheColumnWithoutLU(int columnNumber){
		for(int rowNumber = columnNumber+1; rowNumber < this.coefficientMatrix.size(); rowNumber++){
			this.eliminateThePointWithoutLU(rowNumber, columnNumber);
		}
	}

	/**
	 * 係数行列の一点に対する消去を行う。LU分解を使う際は決して使用しないこと。
	 *
	 * @param rowNumber
	 * @param columnNumber
	 * @return
	 */
	private void eliminateThePointWithoutLU(int rowNumber, int columnNumber){
		this.rightHandVector.elimination(
			this.coefficientMatrix.eliminateThePoint(rowNumber, columnNumber),
			rowNumber,
			columnNumber);
	}

	private void resetRightHand(double[] newVector){
		this.rightHandVector.reset(newVector);
	}

	static double[] addVector(double[] vector1, double[] vector2){
		for(int i = 0; i < vector1.length; i++){
			vector1[i] += vector2[i];
		}

		return vector1;
	}
}