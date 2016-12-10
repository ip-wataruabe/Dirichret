package randomWalk;

class D2MovingPoint {
	int x;
	int y;

	D2MovingPoint(int x, int y){
		this.x = x;
		this.y = y;
	}

	void moveUpward(){
		this.x++;
	}

	void moveDownward(){
		this.x--;
	}

	void moveRight(){
		this.y++;
	}

	void moveLeft(){
		this.y--;
	}

	int getXCoodinate(){
		return this.x;
	}

	int getYCoodinate(){
		return this.y;
	}

	void step(int direction){
		switch(direction){
			case 0:
				this.moveRight();
				break;
			case 1:
				this.moveLeft();
				break;
			case 2:
				this.moveUpward();
				break;
			default:
				this.moveDownward();
		}
	}
}
