package cramer;

public class Permutation implements Comparable<Permutation>{
	private final int[] replacementArray;
	private final int signature; //1 or -1

	Permutation(int[] replacementArray){
		this.replacementArray = replacementArray;
		this.signature = this.calculateSigunature();
	}

	public int getSigunature(){
		return this.signature;
	}

	public int getValue(int originalNumber){
		return replacementArray[originalNumber];
	}



	private int calculateSigunature(){
		int[] replicationArray = this.replacementArray.clone();

		int signature = 1;
		for(int originalNumber = 0; originalNumber<replicationArray.length;originalNumber++){
			signature *= this.reverseExhange(originalNumber, replicationArray);
		}

		return signature;
	}

	private int reverseExhange(int originalNumber, int[] replicationArray){
		if(replicationArray[originalNumber] == originalNumber){
			return 1;
		}

		int replacedPlace = originalNumber + 1;
		while(replicationArray[replacedPlace] != originalNumber){
			replacedPlace++;
		}

		replicationArray[replacedPlace] = replicationArray[originalNumber];
		replicationArray[originalNumber] = originalNumber;

		return -1;
	}

	@Override
	public int compareTo(Permutation otherPermutation) {
		for(int index = 0;index < this.replacementArray.length; index++){
			if(otherPermutation.replacementArray[index] > this.replacementArray[index]){
				return -1;
			}
		}

		return 1;
	}
}
