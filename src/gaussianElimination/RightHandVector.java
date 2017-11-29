package gaussianElimination;

public class RightHandVector {
	private double[] vector;

	RightHandVector(double[] vector){
		this.vector = vector;
	}

	void elimination(double scale, int rowNumber, int pivotCoordinate){
		this.vector[rowNumber] -= scale*this.vector[pivotCoordinate];
	}

	double getValue(int row){
		return this.vector[row];
	}
}
