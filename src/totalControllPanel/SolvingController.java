package totalControllPanel;

import java.util.Scanner;

import randomWalk.WalkingController;

public class SolvingController {
	public static void main(String[] args){
		WalkingController dirichlet = new WalkingController();

		Scanner scanner = new Scanner(System.in);

		System.out.println("Input x coodination");
		double startXCoordination = scanner.nextDouble();

		while(startXCoordination > 1 || startXCoordination < -1){
			System.out.println("Sorry, try again");
			startXCoordination = scanner.nextDouble();
		}

		System.out.println("Input y coodination");
		double startYCoordination = scanner.nextDouble();

		while(startYCoordination > 1 || startYCoordination < -1){
			System.out.println("Sorry, try again");
			startYCoordination = scanner.nextDouble();
		}

		scanner.close();

		double score = dirichlet.getAverageScore(startXCoordination, startYCoordination);

		System.out.println("The score is "+ score+ ".");
		System.out.println("(The number of trials is " + dirichlet.getNumberOfTrials() +").");
	}
}
