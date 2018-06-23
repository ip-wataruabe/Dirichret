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

	public double[] solveByPricision(double pricision){
		double[] oldSolution;
		do {
			oldSolution = this.mediateSolution.clone();
			fleshMediateSolution();
		} while(!efficientPricision(pricision, oldSolution));

		return this.mediateSolution;
	}

	private boolean efficientPricision(double pricision, double[] oldSolution){
		for(int index = 0; index < oldSolution.length; index++){
			double difference = this.mediateSolution[index] - oldSolution[index];
			if(difference > pricision || -pricision > difference){
				return false;
			}
		}

		return true;
	}
}
