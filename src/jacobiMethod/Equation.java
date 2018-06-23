package jacobiMethod;

public class Equation {
	double[][] coefficients;
	double[] rightHandSide;
	double[] mediateSolution;

	public Equation(double[][] coefficients, double[] rightHandSide, double[] startPoint){
		this.coefficients = coefficients;
		this.rightHandSide = rightHandSide;
		this.mediateSolution = startPoint;
	}

	public void fleshMediateSolution(){
		for(int index = 0; index < mediateSolution.length; index++){
			double iterativeRightHand = this.rightHandSide[index];
			for(int jndex = 0; jndex < mediateSolution.length; jndex++){
				if(index != jndex){
					iterativeRightHand -= this.coefficients[index][jndex] * mediateSolution[jndex];
				}
			}
			mediateSolution[index] = iterativeRightHand/coefficients[index][index];
		}
	}

	public double[] solveByNumberOfIterations(int number){
		for(int i=0; i < number; i++){
			this.fleshMediateSolution();
		}

		return this.mediateSolution;
	}
}
