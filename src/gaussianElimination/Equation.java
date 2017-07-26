package gaussianElimination;

public class Equation {
	double[] coefficients;
	double rightHandSide;

	Equation(double[] coefficients, double rightHandSide){
		this.coefficients = coefficients;
		this.rightHandSide = rightHandSide;
	}

	public void eliminatedBy(Equation fixedEquation, int pivotCoordinate){
		double scale = this.coefficients[pivotCoordinate]/fixedEquation.coefficients[pivotCoordinate];
		for(int coordinate = 0; coordinate<this.coefficients.length; coordinate++){
			this.coefficients[coordinate] -= scale*fixedEquation.coefficients[coordinate];
		}

		this.rightHandSide -= scale*fixedEquation.rightHandSide;
	}
}
