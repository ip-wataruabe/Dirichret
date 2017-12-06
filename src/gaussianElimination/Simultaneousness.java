package gaussianElimination;

public class Simultaneousness {
	Equation[] simultaneousEquation;

	Simultaneousness(Equation[] simultaneousEquation) {
		this.simultaneousEquation = simultaneousEquation;
	}

	static Equation[] twoDimensionalArrayAdapter(double[][] coefficientMatrix, double[] constants){
		Equation[] adaptedEquation = new Equation[coefficientMatrix.length];
		for(int i = 0; i < coefficientMatrix.length; i++){
			double[] coefficients = new double[coefficientMatrix[i].length];
			for(int j = 0; j < coefficientMatrix[i].length; j++){
				coefficients[j] = coefficientMatrix[i][j];
			}
			adaptedEquation[i] = new Equation(coefficients, constants[i]);
		}

		return adaptedEquation;
	}

	private void forwardElimination() {
		for(int i = 0; i < this.simultaneousEquation.length; i++){
			elimination(i);
		}
	}

	private void elimination(int i){
		for(int j = i+1; j < this.simultaneousEquation.length; j++){
			this.simultaneousEquation[j].eliminatedBy(this.simultaneousEquation[i], i);
		}
	}

	private double[] backwordSubstitution(){
		double[] solution = new double[this.simultaneousEquation.length];
		for(int i = this.simultaneousEquation.length - 1; i >= 0; i--){
			double elementalSolution = this.simultaneousEquation[i].rightHandSide;

			for(int j = this.simultaneousEquation.length - 1; j > i; j--){
				elementalSolution -= this.simultaneousEquation[i].coefficients[j]*solution[j];
			}

			solution[i] = elementalSolution/this.simultaneousEquation[i].coefficients[i];
		}

		return solution;
	}

	void solve(){
		forwardElimination();
//		System.out.println("The coefficient matrix is changed to ");
//		System.out.println();
//		for(int i = 0; i < this.simultaneousEquation.length; i++){
//			for(int j = 0; j < this.simultaneousEquation[i].coefficients.length; j++){
//				System.out.print(this.simultaneousEquation[i].coefficients[j] + " ");
//			}
//			System.out.println("| " + this.simultaneousEquation[i].rightHandSide);
//		}
//		System.out.println(".");

		System.out.println("The solution by my first Gausian Elimination Program is");
		double[] solution = backwordSubstitution();
		String camma = "[ ";
		for(double element:solution){
			System.out.print(camma);
			System.out.print(element);
			camma = " , ";
		}
		System.out.println(" ].");
		System.out.println("---------------------------------");
	}
}
