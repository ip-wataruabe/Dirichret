package randomWalk;

import java.util.Random;

public class WalkingController {
	private final long numberOfTrials = 10000;

	public double getAverageScore(int x, int y){
		double score = 0;

		for(int round = 0; round<numberOfTrials; round++){
			 score += walk(x, y);
		}

		return score/numberOfTrials;
	}

	private double walk(int x, int y){
		Random random = new Random();
		D2MovingPoint point = new D2MovingPoint(x, y);
		int direction;

		while(D2Inner.isInbounds(point)){
			direction = random.nextInt(4);
			point.step(direction);
		}

		return D2Inner.getScore(point);
	}

	public long getNumberOfTrials(){
		return numberOfTrials;
	}
}
