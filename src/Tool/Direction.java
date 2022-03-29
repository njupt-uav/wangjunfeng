package Tool;

public enum Direction {
	EAST(0,"东"),WEST(1,"西"),SOUTH(2,"南"),NORTH(3,"北");
	private int num;
	private String direction;
	
	Direction(int num, String direction){
		this.num = num;
		this.direction=direction;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
}
