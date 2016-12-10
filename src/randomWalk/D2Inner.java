package randomWalk;

public class D2Inner {

	public static boolean isInbounds(int x, int y){
		if(x < -1 || x > 1){
			return false;
		}

		if(y < -1 || y > 1){
			return false;
		}

		return true;
	}

	public static boolean isInbounds(D2MovingPoint point){
		return isInbounds(point.getXCoodinate(), point.getYCoodinate());
	}

	public static double getScore(D2MovingPoint point){
		if(point.getYCoodinate() >1){
			return 5;
		}

		return 0;
	}
}
