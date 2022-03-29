package Resource;

public class Area {      //区域面积
	private double length;     //区域的长度
	private double width;      //区域的宽度
	private Point[] points = new Point[4];   //四个点坐标
	public Area(){   //无参构造函数
		
	}
	
	//有参构造函数
	public Area(double length, double width, double x1,double y1, double x2, double y2, double x3, double y3, double x4, double y4){
		this.length = length;
		this.width  = width;
		points[0] = new Point();
		points[1] = new Point();
		points[2] = new Point();
		points[3] = new Point();
		points[0].setX(x1);
		points[0].setY(y1);
		points[1].setX(x2);
		points[1].setY(y2);
		points[2].setX(x3);
		points[2].setY(y3);
		points[3].setX(x4);
		points[3].setY(y4);
		
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

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
	
	public Point getPoint(int index){
		if(index>=0&&index<this.points.length)
		return this.points[index];
		else
			try {
				throw new Exception("数组越界了");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("你请求的长度为"+index);
				System.out.println("本数组长度为"+this.points.length);
				return null;
			}
		   
	}
}
