package Resource;

import Tool.C;

public class MEC {
	private Point point;     //移动边缘计算服务器坐标
	private double FMecCpu = C.FMecCpu;   //移动边缘计算服务器的计算能力
	
	public Point getPoint() {
		return point;
	}

	public double getFMecCpu() {
		return FMecCpu;
	}

	/*public MEC(){
		point.setX(0);
		point.setY(0);
	}

	public void setPoint(Point point) {
		this.point = point;
	}*/
	public void setPoint(double x,double y){
		point = new Point();
		point.setX(x);
		point.setY(y);
	}
}
