package cramer;

public class Equation {
	private double[][] coefficients;
	private double[] rightHand;

	Equation(double[][] coefficients, double[] rightHand){
		this.coefficients = coefficients;
		this.rightHand = rightHand;
	}

	private double[][] columnReplacement(int column){
		double[][] replacedArray = new double[this.coefficients.length][this.coefficients.length];
		for(int row = 0; row < replacedArray.length; row++){
			replacedArray[row] = this.coefficients[row].clone();
			replacedArray[row][column] = this.rightHand[row];
		}System.out.println(replacedArray[0][4]);

		return replacedArray;
	}

	private double getDeterminantOfColumnReplacedArray(int column){
		SquareMatrix columnReplacedArray = new SquareMatrix(this.columnReplacement(column));
		return columnReplacedArray.getDeterminant();
	}

	public double[] solveBySimpleCramersRule(){
		double[] solution = new double[this.rightHand.length];
		SquareMatrix coefficientMatrix = new SquareMatrix(this.coefficients);
		double denominator = coefficientMatrix.getDeterminant();

		for(int coordinate = 0; coordinate < this.rightHand.length ; coordinate++){
			solution[coordinate] =
					this.getDeterminantOfColumnReplacedArray(coordinate)/denominator;
		}

		return solution;
	}
}
