package heatEquation;

public class Vector {
	private final int size;
	private double[] values;

	Vector(double[] values){
		this.size = values.length;
		this.values = values;
	}

	int size(){
		return this.size;
	}

	void elimination(double scale, int rowNumber, int pivotCoordinate){
		this.values[rowNumber] -= scale*this.values[pivotCoordinate];
	}

	double getValue(int row){
		return this.values[row];
	}

	double[] getValue(){
		return this.values;
	}

	void setValue(double[] values){
		this.values = values;
	}

	void setValue(double value,int index){
		this.values[index] = value;
	}

	double[] getSumValueWith(Vector vector){
		double[] sum = new double[this.size];
		for(int index = 0;index < this.size; index++){
			sum[index] = this.values[index] + vector.values[index];
		}
		return sum;
	}

	double[] getDifferenceValueWith(Vector vector){
		double[] difference = new double[this.size];
		for(int index = 0;index < this.size; index++){
			difference[index] = this.values[index] - vector.values[index];
		}
		return difference;
	}

	double[] getValuesOperatedBy(SquareMatrix operator){
		double[] operatedValues = new double[this.size];
		for(int index = 0;index < this.size; index++){
			operatedValues[index] = 0;
			for(int column = 0; column < this.size; column++){
				this.values[index] += operator.getValue(index,column)*this.values[index];
			}
		}

		return operatedValues;
	}
}
