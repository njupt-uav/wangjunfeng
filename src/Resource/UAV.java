package Resource;
import Tool.C;
public class UAV{
	private int avgSpeed = C.uavSpeed;    //飞行速度
	private int fixedHight = C.fixedHight;     //飞行固定高度
	private Point CenterPoint ;          //圆心坐标
	private int radius = C.radius;        //飞行半径
	private Point[] points;         //无人机位置
	private double FUavCpu = C.FUavCpu;   //无人机的计算能力
	public int getAvgSpeed() {
		return avgSpeed;
	}
	public void setAvgSpeed(int avgSpeed) {
		this.avgSpeed = avgSpeed;
	}
	public int getFixedHight() {
		return fixedHight;
	}
	public void setFixedHight(int fixedHight) {
		this.fixedHight = fixedHight;
	}
	public Point getCenterPoint() {
		return CenterPoint;
	}
	public void setCenterPoint(Point centerPoint) {
		CenterPoint = centerPoint;
	}	
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public Point[] getPoints() {
		return points;
	}
	public void setPoints(Point[] points) {
		this.points = points;
	}
	public double getFUavCpu() {
		return FUavCpu;
	}
	public void setFUavCpu(int fUavCpu) {
		FUavCpu = fUavCpu;
	}
	public Point centerCoordinatesGenerate(Area area){    //圆心坐标
		Point point = new Point();
		point.setX(area.getLength()/2);
		point.setY(area.getWidth()/2);
		return point;
	}
	public Point[] realPosition(int avgSpeed, int radius, Area area){      //顺时针飞行，实时位置
		Point centerPoint = centerCoordinatesGenerate(area);
		double circumference = 2*C.PI*radius;
		int num = (int) (circumference/avgSpeed);
		Point[] point = new Point[num];
		point[0] = new Point();
		point[0].setX(area.getLength()/2);
		point[0].setY(area.getWidth()/2-radius);              //初始点为最北边的点
		double L,N,sinn,cosn;
		for(int i=1; i<num; i++){
			point[i] = new Point();
			L = avgSpeed*i;
			N = L/radius*180/C.PI;
			sinn = (double) Math.sin(Math.toRadians(N));    //该圆心角的正弦值
			cosn = (double) Math.cos(Math.toRadians(N));    //该圆心角的余弦值
			point[i].setX( (point[0].getX()-centerPoint.getX())*cosn - (point[0].getY()-centerPoint.getY())*sinn + centerPoint.getX() );   //x坐标
			point[i].setY( (point[0].getX()-centerPoint.getX())*sinn + (point[0].getY()-centerPoint.getY())*cosn + centerPoint.getY() );   //y坐标
		}
		return point;
	}
	
/*	public static void main(String[] args) {
		UAV uav = new UAV();
		AreaGenerate areaGenerate = new AreaGenerate();
		Point[] point = uav.realPosition(uav.avgSpeed, uav.radius, areaGenerate.getArea(0));
		for(int i=0; i<point.length; i++){
			System.out.println(i+": "+point[i].getX()+"   "+point[i].getY());
		}
	}*/
	
}
/*class UAV{              //声明无人机类
	private float avgSpeed;     //飞行速度
	private float fixedHight;    //飞行固定高度
	private float coordinates_x;      //无人机圆心x坐标
	private float coordinates_y;      //无人机圆心y坐标
	private float position_x;       //无人机初始位置x坐标
	private float position_y;       //无人机初始位置y坐标
	private float radius;                 //飞行半径     计算能力的一个方法F，能耗
	private double realtimelocation_x;   //无人机实时位置的x坐标
	private double realtimelocation_y;   //无人机实时位置的y坐标
	
	
	public UAV(){     //无参构造函数
		
	}
	
	//有参构造函数
	public UAV(float avgSpeed, float fixedHight, float coordinates_x, float coordinates_y, float position_x,float position_y, float radius){
		this.avgSpeed = avgSpeed;
		this.fixedHight = fixedHight;
		this.coordinates_x = coordinates_x;
		this.coordinates_y = coordinates_y;
		this.position_x = position_x;
		this.position_y = position_y;
		this.radius = radius;
	}

	public float getAvgSpeed() {
		return avgSpeed;
	}

	public void setAvgSpeed(float avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

	public float getFixedHight() {
		return fixedHight;
	}

	public void setFixedHight(float fixedHight) {
		this.fixedHight = fixedHight;
	}

	public float getCoordinates_x() {
		return coordinates_x;
	}

	public void setCoordinates_x(float coordinates_x) {
		this.coordinates_x = coordinates_x;
	}

	public float getCoordinates_y() {
		return coordinates_y;
	}

	public void setCoordinates_y(float coordinates_y) {
		this.coordinates_y = coordinates_y;
	}

	public float getPosition_x() {
		return position_x;
	}

	public void setPosition_x(float position_x) {
		this.position_x = position_x;
	}

	public float getPosition_y() {
		return position_y;
	}

	public void setPosition_y(float position_y) {
		this.position_y = position_y;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public double getRealtimelocation_x() {
		return realtimelocation_x;
	}

	public void setRealtimelocation_x(float realtimelocation_x) {
		this.realtimelocation_x = realtimelocation_x;
	}

	public double getRealtimelocation_y() {
		return realtimelocation_y;
	}

	public void setRealtimelocation_y(float realtimelocation_y) {
		this.realtimelocation_y = realtimelocation_y;
	}
	
	public void setRealtimelocation_x_y(float avgSpeed, float coordinates_x, float coordinates_y, float position_x, float position_y, float radius, float time){
		//假设无人机做逆时针旋转计算
		//Constant constant = new Constant();
		C c = new C();
		double L = avgSpeed * time;      //计算所经过的弧长
		L=L%(2*(c.PI)*radius);   //在一圈内所经过的弧长
		double N = L/radius*180/c.PI;     //一圈内所经过的圆心角
		double sinn = (double) Math.sin(Math.toRadians(N));    //该圆心角的正弦值
		double cosn = (double) Math.cos(Math.toRadians(N));    //该圆心角的余弦值
		//计算实时位置的x，y坐标
		this.realtimelocation_x = (position_x-coordinates_x)*cosn - (position_y-coordinates_y)*sinn+coordinates_x;
		this.realtimelocation_y = (position_x-coordinates_x)*sinn + (position_y-coordinates_y)*cosn+coordinates_y;
		
	}
	
}*/
