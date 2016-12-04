package randomWalk;

class D2Inner {
	static boolean isInbounds(D2MovingPoint point){
		if(point.getXCoodinate() < -2 || point.getXCoodinate() > 2){
			return false;
		}

		if(point.getYCoodinate() < -2 || point.getYCoodinate() > 2){
			return false;
		}

		return true;
	}

	static double getScore(D2MovingPoint point){
		if(point.getYCoodinate() >2){
			return 5;
		}

		return 0;
	}
}
