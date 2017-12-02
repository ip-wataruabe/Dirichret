package heatEquation;

public class SquareMatrix {
	private final int size;
	private double[][] values;

	SquareMatrix(double[][] values){
		this.size = values.length;
		this.values = values;
	}

	public int size(){
		return this.size;
	}

	public double getValue(int row, int column){
		return this.values[row][column];
	}

	public double[][] getValue(){
		return this.values;
	}


	/**
	 * 主にLU分解で用いる
	 * 係数行列の要素の消去をしつつ、フロベニウス行列の素となる数を出力する
	 *
	 * @param rowNumber
	 * @param columnNumber
	 * @param pivotCoordinate
	 * @return
	 */
	public double eliminateThePoint(int rowNumber, int pivotCoordinate){
		double scale =
				this.values[rowNumber][pivotCoordinate]/this.values[pivotCoordinate][pivotCoordinate];

		for(int coordinate = 0; coordinate<this.values.length; coordinate++){
			this.values[rowNumber][coordinate] -= scale*this.values[pivotCoordinate][coordinate];
		}
		return scale;
	}

	/**
	 * 行列をLU分解する。Lを返し、this.coefficientMatrixがUとなる。
	 *
	 * @return
	 */
	public SquareMatrix splitLU(){
		double[][] lowerTriangleMatrix =
				new double[this.values.length][this.values.length];

		//Lを求めるときはいいが、Lの逆行列を求める場合はこのロジックは使えないことに注意
		for(int columnNumber = 0; columnNumber < this.values.length; columnNumber++){
			double[] FrobeniusColumn = this.eliminateOnTheColumn(columnNumber);
			for(int rowCoordinate = 0; rowCoordinate < this.values.length; rowCoordinate++){
				lowerTriangleMatrix[rowCoordinate][columnNumber] = FrobeniusColumn[rowCoordinate];
			}
		}

		return new SquareMatrix(lowerTriangleMatrix);
	}

	/**
	 * 係数行列の列消去をしつつ、フロベニウス行列の素となる列を出力する。LU分解専用。
	 *
	 * @param columnNumber
	 * @return
	 */
	private double[] eliminateOnTheColumn(int columnNumber){
		double[] FrobeniusColumn = new double[this.values.length];

		for(int rowNumber = 0; rowNumber < this.values.length; rowNumber++){
			if(rowNumber < columnNumber){
				FrobeniusColumn[rowNumber] = 0;
			} else if (rowNumber == columnNumber){
				FrobeniusColumn[rowNumber] = 1;
			} else {
				FrobeniusColumn[rowNumber]
						= this.eliminateThePoint(rowNumber, columnNumber);
			}
		}

		return FrobeniusColumn;
	}
}
