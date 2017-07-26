package gaussianElimination;

public class Simultaneousness {
	Equation[] simultaneousEquation;

	Simultaneousness(Equation[] simultaneousEquation) {
		this.simultaneousEquation = simultaneousEquation;
	}

	static Equation[] twoDimensionalArrayAdapter(double[][] equations){
		Equation[] adaptedEquation = new Equation[equations.length];
		for(int i = 0; i < equations.length; i++){
			double[] coefficients = new double[equations[i].length - 1];
			for(int j = 0; j < equations[i].length-1; j++){
				coefficients[j] = equations[i][j];
			}
			adaptedEquation[i] = new Equation(coefficients, equations[i][equations[i].length-1]);
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

	void solve(){
		forwardElimination();
		for(int i = 0; i < this.simultaneousEquation.length; i++){
			for(int j = 0; j < this.simultaneousEquation[i].coefficients.length; j++){
				System.out.print(this.simultaneousEquation[i].coefficients[j] + " ");
			}
			System.out.println("| " + this.simultaneousEquation[i].rightHandSide);
		}
	}
}
