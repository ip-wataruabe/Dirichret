package totalControllPanel;

import java.util.Scanner;

import randomWalk.D2Inner;
import randomWalk.WalkingController;

public class SolvingController {
	public static void main(String[] args){
		WalkingController dirichlet = new WalkingController();
		int startXCoordinate;
		int startYCoordinate;

		Scanner scanner = new Scanner(System.in);

		do{
			System.out.println("Input x coodination");
			startXCoordinate = scanner.nextInt();

			System.out.println("Input y coodination");
			startYCoordinate = scanner.nextInt();

			if(D2Inner.isInbounds(startXCoordinate, startYCoordinate)){
				break;
			}

			System.out.println("Sorry, Try again.");
			}while(true);

		scanner.close();

		double score = dirichlet.getAverageScore(startXCoordinate, startYCoordinate);

		System.out.println("The score is "+ score);
		System.out.println("(The number of trials is " + dirichlet.getNumberOfTrials() +").");
	}
}
