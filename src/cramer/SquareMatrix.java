package cramer;

public class SquareMatrix {
	private double[][] elements;
	private Permutation[] allPermutations;

	SquareMatrix(double[][] elements){
		this.elements = elements;
		this.allPermutations = new Permutation[this.getAllPermutationNumbers()];
		setAllPermutations();
	}

	public int size(){
		return this.elements.length;
	}

	public double getDeterminant(){
		double determinant = 0;
		for(Permutation permutation:this.allPermutations){
			determinant += permutation.getSigunature()*this.getElementProductBy(permutation);
		}
		return determinant;
	}

	private void setAllPermutations(){
		int[] replacementArray = new int[0];
		this.getPermutations(replacementArray, 0);
	}

	private int getPermutations(int[] usedNumbers, int permutationIndex){
		for(int unusedNumber:this.getUnusedNumbers(usedNumbers)){
			int[] updatedUsedNumbers = new int[usedNumbers.length+1];
			System.arraycopy(usedNumbers,0,updatedUsedNumbers,0,usedNumbers.length);
			updatedUsedNumbers[usedNumbers.length] = unusedNumber;
			permutationIndex = getPermutations(updatedUsedNumbers, permutationIndex);
		}

		if(usedNumbers.length == this.size()){
			this.allPermutations[permutationIndex] = new Permutation(usedNumbers);
			permutationIndex++;
		}

		return permutationIndex;
	}

	private int[] getUnusedNumbers(int[] usedNumbers){
		int[] unusedNumbers = new int[this.size()-usedNumbers.length];
		int index = 0;
		for(int newNumber = 0; newNumber < this.size(); newNumber++){
			if(!this.inArray(newNumber, usedNumbers)){
				unusedNumbers[index] = newNumber;
				index++;
			}
		}

		return unusedNumbers;
	}

	private boolean inArray(int needle, int[] Array){
		for(int element: Array){
			if(needle == element){
				return true;
			}
		}
		return false;
	}

	private int getAllPermutationNumbers(){
		int amount = 1;
		for(int i=0;i<this.size();i++){
			amount *= i+1;
		}
		return amount;
	}

	private double getElementProductBy(Permutation permutation){
		double product = 1;
		for(int row = 0; row < this.size(); row++){
			product *=elements[row][permutation.getValue(row)];
		}
		return product;
	}
}
