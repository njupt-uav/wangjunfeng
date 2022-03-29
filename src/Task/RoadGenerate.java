package Task;


import Resource.Area;
import Resource.Road;
import Tool.C;


public class RoadGenerate {   //生成道路
	
	public Road roadOneGenerate(Area area){       //生成一条道路
		Road road =  new Road();
		road.setWidth(C.width);    //设置道路宽度
		road.setPoint(C.west_south, 0, (area.getWidth()-road.getWidth())/2+road.getWidth());   //设置西南点
		road.setPoint(C.west_north, 0, (area.getWidth()-road.getWidth())/2);    //设置西北点
		road.setPoint(C.east_north, area.getLength(), (area.getWidth()-road.getWidth())/2);    //设置东北点
		road.setPoint(C.east_south, area.getLength(), (area.getWidth()-road.getWidth())/2+road.getWidth());     //设置东南点
		return road;
	}
	
	public Road[] roadTwoGenerate(Area area){     //生成两条道路
		Road[] road =  new Road[2];
		road[0] = new Road();                   //东西方向的道路
		road[0].setWidth(C.width);
		road[0].setPoint(C.west_south, 0, (area.getWidth()-road[0].getWidth())/2+road[0].getWidth());
		road[0].setPoint(C.west_north, 0, (area.getWidth()-road[0].getWidth())/2);
		road[0].setPoint(C.east_north, area.getLength(), (area.getWidth()-road[0].getWidth())/2);
		road[0].setPoint(C.east_south, area.getLength(), (area.getWidth()-road[0].getWidth())/2+road[0].getWidth());
		road[1] = new Road();                   //南北方向的道路
		road[1].setWidth(C.width);
		road[1].setPoint(C.west_south, (area.getLength()-road[1].getWidth())/2, area.getWidth());
		road[1].setPoint(C.west_north, (area.getLength()-road[1].getWidth())/2, 0);
		road[1].setPoint(C.east_north, (area.getLength()-road[1].getWidth())/2+road[1].getWidth(), 0);
		road[1].setPoint(C.east_south, (area.getLength()-road[1].getWidth())/2+road[1].getWidth(), area.getWidth());
		return road;
	}
	
	public Road[] roadThreeGenerate(Area area,int index){     //生成三条道路
		Road[] road =  new Road[3];
		road[0] = new Road();       //东西方向的道路，南边的一条
		road[0].setWidth(C.width);
		road[0].setPoint(C.west_south, 0, (area.getWidth()-C.fixedDistance-index*C.space)/2+C.fixedDistance+index*C.space+road[0].getWidth());
		road[0].setPoint(C.west_north, 0, (area.getWidth()-C.fixedDistance-index*C.space)/2+C.fixedDistance+index*C.space);
		road[0].setPoint(C.east_north, area.getLength(), (area.getWidth()-C.fixedDistance-index*C.space)/2+C.fixedDistance+index*C.space);
		road[0].setPoint(C.east_south, area.getLength(), (area.getWidth()-C.fixedDistance-index*C.space)/2+C.fixedDistance+index*C.space+road[0].getWidth());
		road[1] = new Road();      //东西方向的道路，北边的一条
		road[1].setWidth(C.width);
		road[1].setPoint(C.west_south, 0, (area.getWidth()-C.fixedDistance-index*C.space)/2);
		road[1].setPoint(C.west_north, 0, (area.getWidth()-C.fixedDistance-index*C.space)/2-road[1].getWidth());
		road[1].setPoint(C.east_north, area.getLength(), (area.getWidth()-C.fixedDistance-index*C.space)/2-road[1].getWidth());
		road[1].setPoint(C.east_south, area.getLength(), (area.getWidth()-C.fixedDistance-index*C.space)/2);
		road[2] = new Road();      //南北方向的道路
		road[2].setWidth(C.width);
		road[2].setPoint(C.west_south, (area.getLength()-road[2].getWidth())/2, area.getWidth());
		road[2].setPoint(C.west_north, (area.getLength()-road[2].getWidth())/2, 0);
		road[2].setPoint(C.east_north, (area.getLength()-road[2].getWidth())/2+road[2].getWidth(), 0);
		road[2].setPoint(C.east_south, (area.getLength()-road[2].getWidth())/2+road[2].getWidth(), area.getWidth());
		return road;
	}
	
	public Road[] roadFourGenerate(Area area, int index){        //生成四条道路
		Road[] road =  new Road[4];
		road[0] = new Road();       //东西方向的道路，南边的一条
		road[0].setWidth(C.width);
		road[0].setPoint(C.west_south, 0, (area.getWidth()-C.fixedDistance-index*C.space)/2+C.fixedDistance+index*C.space+road[0].getWidth());
		road[0].setPoint(C.west_north, 0, (area.getWidth()-C.fixedDistance-index*C.space)/2+C.fixedDistance+index*C.space);
		road[0].setPoint(C.east_north, area.getLength(), (area.getWidth()-C.fixedDistance-index*C.space)/2+C.fixedDistance+index*C.space);
		road[0].setPoint(C.east_south, area.getLength(), (area.getWidth()-C.fixedDistance-index*C.space)/2+C.fixedDistance+index*C.space+road[0].getWidth());
		road[1] = new Road();      //东西方向的道路，北边的一条
		road[1].setWidth(C.width);
		road[1].setPoint(C.west_south, 0, (area.getWidth()-C.fixedDistance-index*C.space)/2);
		road[1].setPoint(C.west_north, 0, (area.getWidth()-C.fixedDistance-index*C.space)/2-road[1].getWidth());
		road[1].setPoint(C.east_north, area.getLength(), (area.getWidth()-C.fixedDistance-index*C.space)/2-road[1].getWidth());
		road[1].setPoint(C.east_south, area.getLength(), (area.getWidth()-C.fixedDistance-index*C.space)/2);
		road[2] = new Road();        //南北方向的道路，西边的一条
		road[2].setWidth(C.width);
		road[2].setPoint(C.west_south, (area.getLength()-C.fixedDistance-index*C.space)/2-road[2].getWidth(), area.getWidth());
		road[2].setPoint(C.west_north, (area.getLength()-C.fixedDistance-index*C.space)/2-road[2].getWidth(), 0);
		road[2].setPoint(C.east_north, (area.getLength()-C.fixedDistance-index*C.space)/2, 0);
		road[2].setPoint(C.east_south, (area.getLength()-C.fixedDistance-index*C.space)/2, area.getWidth());
		road[3] = new Road();        //南北方向的道路，东边的一条
		road[3].setWidth(C.width);
		road[3].setPoint(C.west_south, (area.getLength()-C.fixedDistance-index*C.space)/2+C.fixedDistance+index*C.space, area.getWidth());
		road[3].setPoint(C.west_north, (area.getLength()-C.fixedDistance-index*C.space)/2+C.fixedDistance+index*C.space, 0);
		road[3].setPoint(C.east_north, (area.getLength()-C.fixedDistance-index*C.space)/2+C.fixedDistance+index*C.space+road[3].getWidth(), 0);
		road[3].setPoint(C.east_south, (area.getLength()-C.fixedDistance-index*C.space)/2+C.fixedDistance+index*C.space+road[3].getWidth(), area.getWidth());
		return road;
	}
	
	/*public static void main(String[] args) {
		AreaGenerate areaGenerate = new AreaGenerate();
		Road[] road = roadFourGenerate(areaGenerate.area[1],2);
		System.out.println(road[0].getPoint(2).getX());
	}*/
	
	/*public static void main(String[] args) {
		AreaGenerate areagenerate = new AreaGenerate();
		Road road = roadOneGenerate(areagenerate.area[1]);
		System.out.println(road.getPoint(0).getY());
		
		Road[] road1 = roadTwoGenerate(areagenerate.area[0]);
		System.out.println(road1[0].getPoint(2).getY());
		System.out.println(road1[1].getPoint(2).getY());
		
	}*/
	
	/*public Road[][] roadOneGenerate(){     //生成一条道路
		Road[][] road = new Road[3][1];
		AreaGenerate areagenerate = new AreaGenerate();
		for(int i=0;i<3;i++){
			road[i][0] = new Road();
			road[i][0].setWidth(6);
			road[i][0].setPoint(0, 0, (areagenerate.area[i].getWidth()-road[i][0].getWidth())/2);  //0号点代表西南点
			road[i][0].setPoint(1, 0, (areagenerate.area[i].getWidth()-road[i][0].getWidth())/2+road[i][0].getWidth());    //1号点代表西北点
			road[i][0].setPoint(2, areagenerate.area[i].getLength(), (areagenerate.area[i].getWidth()-road[i][0].getWidth())/2+road[i][0].getWidth());   //2号点代表东北点
			road[i][0].setPoint(3, areagenerate.area[i].getLength(), (areagenerate.area[i].getWidth()-road[i][0].getWidth())/2);     //3号点代表东南点
			
		}
		//System.out.println(road[0][0].getPoint(1).getY());
		//System.out.println(road[2][0].getPoint(2).getY());
		
		return road;
	}*/
	
	/*public double getWidth(int index){
		return 
	}*/
	
	
	
	
	/*public Road[][] roadTwoGenerate(){   //生成两条道路
		int i=0;
		Road[][] road = new Road[3][2];
		AreaGenerate areagenerate = new AreaGenerate();
		for(i=0;i<3;i++){
			//东西道路，四个点按照西南、西北、东北、东南
			road[i][0] = new Road();  //东西方向的道路
			road[i][0].setWidth(6);
			road[i][0].setPoint(0, 0, (areagenerate.area[i].getWidth()-road[i][0].getWidth())/2);
			road[i][0].setPoint(1, 0, (areagenerate.area[i].getWidth()-road[i][0].getWidth())/2+road[i][0].getWidth());
			road[i][0].setPoint(2, areagenerate.area[i].getLength(), (areagenerate.area[i].getWidth()-road[i][0].getWidth())/2+road[i][0].getWidth());   //2号点代表东北点
			road[i][0].setPoint(3, areagenerate.area[i].getLength(), (areagenerate.area[i].getWidth()-road[i][0].getWidth())/2);     //3号点代表东南点
			//南北道路，四个点按照西南、西北、东北、东南
			road[i][1] = new Road();   //南北方向的道路
			road[i][1].setWidth(6);
			road[i][1].setPoint(0, (areagenerate.area[i].getLength()-road[i][1].getWidth())/2, 0);
			road[i][1].setPoint(1, (areagenerate.area[i].getLength()-road[i][1].getWidth())/2, areagenerate.area[i].getWidth());
			road[i][1].setPoint(2, (areagenerate.area[i].getLength()-road[i][1].getWidth())/2+road[i][1].getWidth(), areagenerate.area[i].getWidth());
			road[i][1].setPoint(3, (areagenerate.area[i].getLength()-road[i][1].getWidth())/2+road[i][1].getWidth(), 0);
		}
		System.out.println(road[0][1].getPoint(2).getX());
		System.out.println(road[2][1].getPoint(2).getX());
		return road;
	}*/
	
	
	
	/*public Road[][] roadThreeGenerate(){    //生成三条道路
		int i=0;
		int increment=100;
		Road[][] road = new Road[3][3];
		AreaGenerate areagenerate = new AreaGenerate();
		for(i=0;i<3;i++){
			road[i][0] = new Road();  //东西方向的南边的一条道路
			road[i][0].setWidth(6);
			road[i][0].setPoint(0, 0, (areagenerate.area[i].getWidth()-200-i*increment)/2-road[i][0].getWidth());
			road[i][0].setPoint(1, 0, (areagenerate.area[i].getWidth()-200-i*increment)/2);
			road[i][0].setPoint(2, areagenerate.area[i].getLength(), (areagenerate.area[i].getWidth()-200-i*increment)/2);
			road[i][0].setPoint(3, areagenerate.area[i].getLength(), (areagenerate.area[i].getWidth()-200-i*increment)/2-road[i][0].getWidth());
			road[i][1] = new Road();   //东西方向的北面的一条道路
			road[i][1].setWidth(6);
			road[i][1].setPoint(0, 0, (areagenerate.area[i].getWidth()-200-i*increment)/2+200+i*increment);
			road[i][1].setPoint(1, 0, (areagenerate.area[i].getWidth()-200-i*increment)/2+200+i*increment+road[i][1].getWidth());
			road[i][1].setPoint(2, areagenerate.area[i].getLength(), (areagenerate.area[i].getWidth()-200-i*increment)/2+200+i*increment+road[i][1].getWidth());
			road[i][1].setPoint(3, areagenerate.area[i].getLength(), (areagenerate.area[i].getWidth()-200-i*increment)/2+200+i*increment);		
			road[i][2] = new Road();  //南北方向的一条道路
			road[i][2].setWidth(6);
			road[i][2].setPoint(0, (areagenerate.area[i].getLength()-road[i][2].getWidth())/2, 0);
			road[i][2].setPoint(1, (areagenerate.area[i].getLength()-road[i][2].getWidth())/2, areagenerate.area[i].getWidth());
			road[i][2].setPoint(2, (areagenerate.area[i].getLength()-road[i][2].getWidth())/2+road[i][2].getWidth(), areagenerate.area[i].getWidth());
			road[i][2].setPoint(3, (areagenerate.area[i].getLength()-road[i][2].getWidth())/2+road[i][2].getWidth(), 0);
		}
		
		System.out.println(road[2][1].getPoint(2).getX());
		System.out.println(road[1][0].getPoint(0).getY());
		return road;
	}*/
	
	
	
	/*public Road[][] roadFourGenerate(){     //生成四条道路
		int i=0;
		int increment=100;
		Road[][] road = new Road[3][4];
		AreaGenerate areagenerate = new AreaGenerate();
		for(i=0;i<3;i++){
			road[i][0] = new Road();  //东西方向的南边的一条道路
			road[i][0].setWidth(6);
			road[i][0].setPoint(0, 0, (areagenerate.area[i].getWidth()-200-i*increment)/2-road[i][0].getWidth());
			road[i][0].setPoint(1, 0, (areagenerate.area[i].getWidth()-200-i*increment)/2);
			road[i][0].setPoint(2, areagenerate.area[i].getLength(), (areagenerate.area[i].getWidth()-200-i*increment)/2);
			road[i][0].setPoint(3, areagenerate.area[i].getLength(), (areagenerate.area[i].getWidth()-200-i*increment)/2-road[i][0].getWidth());
			road[i][1] = new Road();   //东西方向的北面的一条道路
			road[i][1].setWidth(6);
			road[i][1].setPoint(0, 0, (areagenerate.area[i].getWidth()-200-i*increment)/2+200+i*increment);
			road[i][1].setPoint(1, 0, (areagenerate.area[i].getWidth()-200-i*increment)/2+200+i*increment+road[i][1].getWidth());
			road[i][1].setPoint(2, areagenerate.area[i].getLength(), (areagenerate.area[i].getWidth()-200-i*increment)/2+200+i*increment+road[i][1].getWidth());
			road[i][1].setPoint(3, areagenerate.area[i].getLength(), (areagenerate.area[i].getWidth()-200-i*increment)/2+200+i*increment);
			road[i][2] = new Road();    //南北方向的西边的一条道路
			road[i][2].setWidth(6);
			road[i][2].setPoint(0, (areagenerate.area[i].getLength()-200-i*increment)/2-road[i][2].getWidth(), 0);
			road[i][2].setPoint(1, (areagenerate.area[i].getLength()-200-i*increment)/2-road[i][2].getWidth(), areagenerate.area[i].getWidth());
			road[i][2].setPoint(2, (areagenerate.area[i].getLength()-200-i*increment)/2, areagenerate.area[i].getWidth());
			road[i][2].setPoint(3, (areagenerate.area[i].getLength()-200-i*increment)/2, 0);
			road[i][3] = new Road();    //南北方向的东边的一条路
			road[i][3].setWidth(6);
			road[i][3].setPoint(0, (areagenerate.area[i].getLength()-200-i*increment)/2+200+i*increment, 0);
			road[i][3].setPoint(1, (areagenerate.area[i].getLength()-200-i*increment)/2+200+i*increment, areagenerate.area[i].getWidth());
			road[i][3].setPoint(2, (areagenerate.area[i].getLength()-200-i*increment)/2+200+i*increment+road[i][3].getWidth(), areagenerate.area[i].getWidth());
			road[i][3].setPoint(3, (areagenerate.area[i].getLength()-200-i*increment)/2+200+i*increment+road[i][3].getWidth(), 0);
			
		}
		System.out.println(road[2][3].getPoint(2).getX());
		System.out.println(road[1][1].getPoint(1).getY());
		return road;
	}*/
	
	/*public static void main(String[] args) {
		
		RoadGenerate roadgenerate = new RoadGenerate();
		roadgenerate.roadOneGenerate();
		roadgenerate.roadTwoGenerate();
		roadgenerate.roadThreeGenerate();
		roadgenerate.roadFourGenerate();
	}*/
}
