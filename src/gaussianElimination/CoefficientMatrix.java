package gaussianElimination;

public class CoefficientMatrix {
	private double[][] coefficients;

	CoefficientMatrix(double[][] coefficients){
		this.coefficients = coefficients;
	}
	/**
	 * 係数行列の要素の消去をしつつ、フロベニウス行列の素となる数を出力する
	 *
	 * @param rowNumber
	 * @param columnNumber
	 * @param pivotCoordinate
	 * @return
	 */
	public double eliminateThePoint(int rowNumber, int pivotCoordinate){
		double scale =
				this.coefficients[rowNumber][pivotCoordinate]/this.coefficients[pivotCoordinate][pivotCoordinate];

		for(int coordinate = 0; coordinate<this.coefficients.length; coordinate++){
			this.coefficients[rowNumber][coordinate] -= scale*this.coefficients[pivotCoordinate][coordinate];
		}
		return scale;
	}

	public int size(){
		return this.coefficients.length;
	}

	public double getValue(int row, int column){
		return this.coefficients[row][column];
	}
}
