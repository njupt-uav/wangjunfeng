package Resource;

import Tool.C;

public class Vehicle {
	public static double speed = C.vehicleSpeed;   //车辆的运行速度 54km/h
	private Point point;   //车辆的位置
	private int ID;              //车辆的编号
	private int dir;            //车辆的方向
	private int time;          //车辆在某时刻位置的时间
	private double calculation;//计算速度，无人机也加上    看看那些是同构的
	private double P;     //车辆的传输功率
	
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	
	public void setPoint(double x,double y){
		point = new Point();
		point.setX(x);
		point.setY(y);
	}
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	
	
	public int getDir() {
		return dir;
	}
	public void setDir(int dir) {
		this.dir = dir;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public double getCalculation() {
		return calculation;
	}
	public void setCalculation(double calculation) {
		this.calculation = calculation;
	}
	public double getP() {
		return P;
	}
	public void setP(double p) {
		P = p;
	}
	
	
}
