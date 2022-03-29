package Task;

import java.util.Random;

import Resource.Area;
import Resource.Building;
import Resource.Road;
import Tool.C;
/*
 * 大楼生成类*/
public class BuildingGenerate {
	public Building oneRoadBuilding(Area area,int index){     //一条道路的场景中生成大楼
		Building building = new Building();
		Random random = new Random();
		int rand = random.nextInt(C.HeightType);     //随机生成一个0~HeightType-1的整数
		building.setHeight(C.heightSet[rand]);
		//System.out.println(building.getHeight());
		RoadGenerate roadgenerate = new RoadGenerate();
		Road road = roadgenerate.roadOneGenerate(area);
		int randx=-100,randy=-100;
		randx = random.nextInt((int) (area.getLength()-building.length));       //生成大楼西南点的x坐标
			//System.out.print("第"+i+"个x:"+randx+"  ");
		do{
			randy = random.nextInt((int) (area.getWidth()-building.width));
		}while(randy>area.getWidth()/2-road.getWidth()/2-building.width && randy<area.getWidth()/2+road.getWidth()/2);   //生成大楼西南点的y坐标
			//System.out.println("第"+i+"个y:"+randy);
		building.setPoint(C.west_south, randx, randy);
		building.setPoint(C.west_north, randx, randy+building.width);
		building.setPoint(C.east_north, randx+building.length, randy+building.width);
		building.setPoint(C.east_south, randx+building.length, randy);
		return building;
	}
	
	public Building twoRoadBuilding(Area area,int index){    //两条道路的场景中生成大楼
		Building building = new Building();
		Random random = new Random();
		int rand = random.nextInt(C.HeightType);
		building.setHeight(C.heightSet[rand]);
		RoadGenerate roadgenerate = new RoadGenerate();
		Road[] road = roadgenerate.roadTwoGenerate(area);
		int randx=-100,randy=-100;
		do{
			randx = random.nextInt((int) (area.getLength()-building.length));
		}while(randx>area.getLength()/2-road[1].getWidth()/2-building.length && randx<area.getLength()/2+road[1].getWidth()/2);
		
		do{
			randy = random.nextInt((int) (area.getWidth()-building.width));
		}while(randy>area.getWidth()/2-road[0].getWidth()/2-building.width && randy<area.getWidth()/2+road[0].getWidth()/2);
		building.setPoint(C.west_south, randx, randy);
		building.setPoint(C.west_north, randx, randy+building.width);
		building.setPoint(C.east_north, randx+building.length, randy+building.width);
		building.setPoint(C.east_south, randx+building.length, randy);
		return building;
	}
	
	public Building threeRoadBuilding(Area area,int index){    //三条道路的场景中生成大楼
		Building building = new Building();
		Random random = new Random();
		int rand = random.nextInt(C.HeightType);
		building.setHeight(C.heightSet[rand]);
		RoadGenerate roadgenerate = new RoadGenerate();
		Road[] road = roadgenerate.roadThreeGenerate(area,index);
		int randx=-100,randy=-100;
		
		do{
			randx = random.nextInt((int) (area.getLength()-building.length));
		}while(randx>area.getLength()/2-road[2].getWidth()/2-building.length && randx<area.getLength()/2+road[2].getWidth()/2);
		
		
		do{
			randy = random.nextInt((int) (area.getWidth()-building.width));
		}while( (randy>(area.getWidth()-C.fixedDistance-index*C.space)/2-road[0].getWidth()-building.width && randy<(area.getWidth()-C.fixedDistance-index*C.space)/2) || ( (randy>area.getWidth()-(area.getWidth()-C.fixedDistance-index*C.space)/2-building.width) &&(randy<area.getWidth()-(area.getWidth()-C.fixedDistance-index*C.space)/2+road[1].getWidth()) )  );
		
		building.setPoint(C.west_south, randx, randy);
		building.setPoint(C.west_north, randx, randy+building.width);
		building.setPoint(C.east_north, randx+building.length, randy+building.width);
		building.setPoint(C.east_south, randx+building.length, randy);
		
		return building;
	}
	
	
	public Building fourRoadBuilding(Area area,int index){       //四条道路的场景中生成大楼
		Building building = new Building();
		Random random = new Random();
		int rand = random.nextInt(C.HeightType);
		building.setHeight(C.heightSet[rand]);
		RoadGenerate roadgenerate = new RoadGenerate();
		Road[] road = roadgenerate.roadFourGenerate(area,index);
		int randx=-100,randy=-100;
		do{
			randx = random.nextInt((int) (area.getLength()-building.length));
		}while( ( (randx>(area.getLength()-C.fixedDistance-index*C.space)/2-road[2].getWidth()-building.length) && (randx<(area.getLength()-C.fixedDistance-index*C.space)/2) ) || ( (randx>area.getLength()-(area.getLength()-C.fixedDistance-index*C.space)/2-building.length) && (randx<area.getLength()-(area.getLength()-C.fixedDistance-index*C.space)/2+road[3].getWidth()) ) );
		
		do{
			randy = random.nextInt((int) (area.getWidth()-building.width));
		}while( (randy>(area.getWidth()-C.fixedDistance-index*C.space)/2-road[0].getWidth()-building.width && randy<(area.getWidth()-C.fixedDistance-index*C.space)/2) || ( (randy>area.getWidth()-(area.getWidth()-C.fixedDistance-index*C.space)/2-building.width) &&(randy<area.getWidth()-(area.getWidth()-C.fixedDistance-index*C.space)/2+road[1].getWidth()) )  );
		
		building.setPoint(C.west_south, randx, randy);
		building.setPoint(C.west_north, randx, randy+building.width);
		building.setPoint(C.east_north, randx+building.length, randy+building.width);
		building.setPoint(C.east_south, randx+building.length, randy);
		
		return building;
	}
	
	/*public static void main(String[] args) {
		BuildingGenerate buildinggenerate = new BuildingGenerate();
		AreaGenerate areagenerate = new AreaGenerate();
		Building building = buildinggenerate.oneRoadBuilding(areagenerate.area[0], 0);
		System.out.println(building.getHeight());
		System.out.println(building.getPoint(0).getX());
		System.out.println(building.getPoint(0).getY());
		System.out.println(building.getPoint(2).getX());
		System.out.println(building.getPoint(2).getY());
		System.out.println();
		
		Building building3 = buildinggenerate.fourRoadBuilding(areagenerate.area[2], 2);
		System.out.println(building3.getHeight());
		System.out.println(building3.getPoint(0).getX());
		System.out.println(building3.getPoint(0).getY());
		System.out.println(building3.getPoint(2).getX());
		System.out.println(building3.getPoint(2).getY());
		
		
	}*/
}
