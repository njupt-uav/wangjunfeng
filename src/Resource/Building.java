package Resource;

import java.util.Arrays;

public class Building {   //大楼
	public static double length = 40;    //大楼的长度 静态变量
	public static double width = 10;     //大楼的宽度 静态变量
	private double height;               //大楼的高度
	private Point[] points = new Point[4];       //四个点确定一个大楼的位置
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
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
	
	
	/*@Override
	public String toString() {
		return "Building [height=" + height + ", points=" + points[0].toString() +points[1].toString() +points[2].toString() +points[3].toString() + "]";
	}*/
	
	
	
	
	
	
	
}
