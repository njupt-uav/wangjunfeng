package Resource;

public class Road {      //道路
	private double width;     //道路的宽度
	private Point[] points = new Point[4];      //每条道路4个点坐标
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public Point[] getPoints() {
		return points;
	}
	public void setPoints(Point[] points) {
		this.points = points;
	}
	public void setPoint(int index,double x,double y){
		points[index] = new Point();
		points[index].setX(x);
		points[index].setY(y);
	}
	
	public Point getPoint(int index){
		return this.points[index];
	}
}
