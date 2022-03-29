package Task;

import Resource.Area;

public class AreaGenerate {
	/*Area[] area=new Area[3];
	public AreaGenerate(){
		area[0] = new Area();
		area[1] = new Area();
		area[2] = new Area();
		
	}*/
	
	
	Area[] area = {new Area(800,800,0,0,0,800,800,800,800,0), 
			       new Area(1000,1000,0,0,0,1000,1000,1000,1000,0),
			       new Area(1200,1200,0,0,0,1200,1200,1200,1200,0)
			};      //初始化长宽分别为800*800,1000*1000,1200*1200的区域
	
	public Area getArea(int i){
		return area[i];
	}
}