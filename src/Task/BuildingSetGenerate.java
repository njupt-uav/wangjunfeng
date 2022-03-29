package Task;

import java.util.*;
import Resource.Area;
import Resource.Building;
import Resource.Road;

public class BuildingSetGenerate {//大楼集合生成类
	public List oneRoadBuildingSet(Area area, int index, double proportion){   //一条道路的大楼集合函数
		BuildingGenerate buildingGenerate = new BuildingGenerate();
		RoadGenerate roadGenerate = new RoadGenerate(); 
		Building building = new Building();
		Building buildingTemp = new Building();
		Road road = roadGenerate.roadOneGenerate(area);
		List list = new ArrayList();
		//大楼的数量number
		int number =  (int) ((area.getLength()*area.getWidth() - road.getWidth()*area.getLength())*proportion/(building.length*building.width));
		for(int i=0;i<number;i++){
			//System.out.println("i="+i);
			building = buildingGenerate.oneRoadBuilding(area, index);
			//System.err.println(building.toString());	
			boolean flag = true;
			for(int j=0;j<list.size();j++){
				//System.out.println("j="+j);
				buildingTemp = (Building) list.get(j);
				//如果有重叠，则不添加
				if((building.getPoint(0).getX()>=buildingTemp.getPoint(0).getX() && building.getPoint(0).getX()<=buildingTemp.getPoint(3).getX() && building.getPoint(0).getY()>=buildingTemp.getPoint(0).getY() && building.getPoint(0).getY()<=buildingTemp.getPoint(1).getY()) || (building.getPoint(1).getX()>=buildingTemp.getPoint(0).getX() && building.getPoint(1).getX()<=buildingTemp.getPoint(3).getX() && building.getPoint(1).getY()>=buildingTemp.getPoint(0).getY() && building.getPoint(1).getY()<=buildingTemp.getPoint(1).getY()) ||(building.getPoint(2).getX()>=buildingTemp.getPoint(0).getX() && building.getPoint(2).getX()<=buildingTemp.getPoint(3).getX() && building.getPoint(2).getY()>=buildingTemp.getPoint(0).getY() && building.getPoint(2).getY()<=buildingTemp.getPoint(1).getY()) ||(building.getPoint(3).getX()>=buildingTemp.getPoint(0).getX() && building.getPoint(3).getX()<=buildingTemp.getPoint(3).getX() && building.getPoint(3).getY()>=buildingTemp.getPoint(0).getY() && building.getPoint(3).getY()<=buildingTemp.getPoint(1).getY())){
					flag = false;
					i--;
					break;
				}
				/*if((buildingTemp.getPoint(1).getY()>=building.getPoint(3).getY()) && (building.getPoint(1).getY()>=buildingTemp.getPoint(3).getY()) && (buildingTemp.getPoint(1).getX()<=building.getPoint(3).getX()) && (buildingTemp.getPoint(3).getX()>=building.getPoint(1).getX())) {
					flag = false;
					i--;
					break;
				}     */      
			}
			if(flag){
				list.add(building);
			}
		}
		return list;
	}
	
	
	public List twoRoadBuildingSet(Area area, int index, double proportion){        //两条道路的大楼集合函数
		BuildingGenerate buildingGenerate = new BuildingGenerate();
		RoadGenerate roadGenerate = new RoadGenerate(); 
		Building building = new Building();
		Building buildingTemp = new Building();
		Road[] road;
		road = roadGenerate.roadTwoGenerate(area);
		List list = new ArrayList();
		int number = (int) ((area.getLength()*area.getWidth()-road[0].getWidth()*area.getLength()-road[1].getWidth()*area.getWidth()+road[0].getWidth()*road[1].getWidth())*proportion/(building.length*building.width));
		for(int i=0;i<number;i++){
			building = buildingGenerate.twoRoadBuilding(area, index);
			boolean flag = true;
			for(int j=0;j<list.size();j++){
				buildingTemp = (Building) list.get(j);
				//如果有重叠，则不添加
				if((building.getPoint(0).getX()>=buildingTemp.getPoint(0).getX() && building.getPoint(0).getX()<=buildingTemp.getPoint(3).getX() && building.getPoint(0).getY()>=buildingTemp.getPoint(0).getY() && building.getPoint(0).getY()<=buildingTemp.getPoint(1).getY()) || (building.getPoint(1).getX()>=buildingTemp.getPoint(0).getX() && building.getPoint(1).getX()<=buildingTemp.getPoint(3).getX() && building.getPoint(1).getY()>=buildingTemp.getPoint(0).getY() && building.getPoint(1).getY()<=buildingTemp.getPoint(1).getY()) ||(building.getPoint(2).getX()>=buildingTemp.getPoint(0).getX() && building.getPoint(2).getX()<=buildingTemp.getPoint(3).getX() && building.getPoint(2).getY()>=buildingTemp.getPoint(0).getY() && building.getPoint(2).getY()<=buildingTemp.getPoint(1).getY()) ||(building.getPoint(3).getX()>=buildingTemp.getPoint(0).getX() && building.getPoint(3).getX()<=buildingTemp.getPoint(3).getX() && building.getPoint(3).getY()>=buildingTemp.getPoint(0).getY() && building.getPoint(3).getY()<=buildingTemp.getPoint(1).getY())){
					flag = false;
					i--;
					break;
				}
				/*if((buildingTemp.getPoint(1).getY()>=building.getPoint(3).getY()) && (building.getPoint(1).getY()>=buildingTemp.getPoint(3).getY()) && (buildingTemp.getPoint(1).getX()<=building.getPoint(3).getX()) && (buildingTemp.getPoint(3).getX()>=building.getPoint(1).getX())) {
					flag = false;
					i--;
					break;
				}  */         
			}
			if(flag){
				list.add(building);
			}
		}
		return list;
	}
	
	public List threeRoadBuildingSet(Area area, int index, double proportion){     //三条道路的大楼集合函数
		BuildingGenerate buildingGenerate = new BuildingGenerate();
		RoadGenerate roadGenerate = new RoadGenerate(); 
		Building building = new Building();
		Building buildingTemp = new Building();
		Road[] road;
		road = roadGenerate.roadThreeGenerate(area,index);
		List list = new ArrayList();
		int number = (int) ((area.getLength()*area.getWidth()-road[0].getWidth()*area.getLength()-road[1].getWidth()*area.getLength()-road[2].getWidth()*area.getWidth()+road[0].getWidth()*road[2].getWidth()+road[1].getWidth()*road[2].getWidth())*proportion/(building.length*building.width));
		for(int i=0;i<number;i++){
			building = buildingGenerate.threeRoadBuilding(area, index);
			boolean flag = true;
			for(int j=0;j<list.size();j++){
				buildingTemp = (Building) list.get(j);
				//如果有重叠，则不添加
				if((building.getPoint(0).getX()>=buildingTemp.getPoint(0).getX() && building.getPoint(0).getX()<=buildingTemp.getPoint(3).getX() && building.getPoint(0).getY()>=buildingTemp.getPoint(0).getY() && building.getPoint(0).getY()<=buildingTemp.getPoint(1).getY()) || (building.getPoint(1).getX()>=buildingTemp.getPoint(0).getX() && building.getPoint(1).getX()<=buildingTemp.getPoint(3).getX() && building.getPoint(1).getY()>=buildingTemp.getPoint(0).getY() && building.getPoint(1).getY()<=buildingTemp.getPoint(1).getY()) ||(building.getPoint(2).getX()>=buildingTemp.getPoint(0).getX() && building.getPoint(2).getX()<=buildingTemp.getPoint(3).getX() && building.getPoint(2).getY()>=buildingTemp.getPoint(0).getY() && building.getPoint(2).getY()<=buildingTemp.getPoint(1).getY()) ||(building.getPoint(3).getX()>=buildingTemp.getPoint(0).getX() && building.getPoint(3).getX()<=buildingTemp.getPoint(3).getX() && building.getPoint(3).getY()>=buildingTemp.getPoint(0).getY() && building.getPoint(3).getY()<=buildingTemp.getPoint(1).getY())){
					flag = false;
					i--;
					break;
				}
				/*if((buildingTemp.getPoint(1).getY()>=building.getPoint(3).getY()) && (building.getPoint(1).getY()>=buildingTemp.getPoint(3).getY()) && (buildingTemp.getPoint(1).getX()<=building.getPoint(3).getX()) && (buildingTemp.getPoint(3).getX()>=building.getPoint(1).getX())) {
					flag = false;
					i--;
					break;
				}      */     
			}
			if(flag){
				list.add(building);
			}
		}
		return list;
	}
	
	public List fourRoadBuildingSet(Area area, int index, double proportion){    //四条道路的大楼集合函数
		BuildingGenerate buildingGenerate = new BuildingGenerate();
		RoadGenerate roadGenerate = new RoadGenerate(); 
		Building building = new Building();
		Building buildingTemp = new Building();
		Road[] road ;
		road = roadGenerate.roadFourGenerate(area,index);
		List list = new ArrayList();
		int number = (int) ((area.getLength()*area.getWidth()-road[0].getWidth()*area.getLength()-road[1].getWidth()*area.getLength()-road[2].getWidth()*area.getWidth()-road[3].getWidth()*area.getWidth()+road[0].getWidth()*road[2].getWidth()+road[1].getWidth()*road[2].getWidth()+road[0].getWidth()*road[3].getWidth()+road[1].getWidth()*road[3].getWidth())*proportion/(building.length*building.width));
		for(int i=0;i<number;i++){
			building = buildingGenerate.fourRoadBuilding(area, index);
			boolean flag = true;
			for(int j=0;j<list.size();j++){
				buildingTemp = (Building) list.get(j);
				//如果有重叠，则不添加
				if((building.getPoint(0).getX()>=buildingTemp.getPoint(0).getX() && building.getPoint(0).getX()<=buildingTemp.getPoint(3).getX() && building.getPoint(0).getY()>=buildingTemp.getPoint(0).getY() && building.getPoint(0).getY()<=buildingTemp.getPoint(1).getY()) || (building.getPoint(1).getX()>=buildingTemp.getPoint(0).getX() && building.getPoint(1).getX()<=buildingTemp.getPoint(3).getX() && building.getPoint(1).getY()>=buildingTemp.getPoint(0).getY() && building.getPoint(1).getY()<=buildingTemp.getPoint(1).getY()) ||(building.getPoint(2).getX()>=buildingTemp.getPoint(0).getX() && building.getPoint(2).getX()<=buildingTemp.getPoint(3).getX() && building.getPoint(2).getY()>=buildingTemp.getPoint(0).getY() && building.getPoint(2).getY()<=buildingTemp.getPoint(1).getY()) ||(building.getPoint(3).getX()>=buildingTemp.getPoint(0).getX() && building.getPoint(3).getX()<=buildingTemp.getPoint(3).getX() && building.getPoint(3).getY()>=buildingTemp.getPoint(0).getY() && building.getPoint(3).getY()<=buildingTemp.getPoint(1).getY())){
					flag = false;
					i--;
					break;
				}
				/*if((buildingTemp.getPoint(1).getY()>=building.getPoint(3).getY()) && (building.getPoint(1).getY()>=buildingTemp.getPoint(3).getY()) && (buildingTemp.getPoint(1).getX()<=building.getPoint(3).getX()) && (buildingTemp.getPoint(3).getX()>=building.getPoint(1).getX())) {
					flag = false;
					i--;
					break;
				}         */  
			}
			if(flag){
				list.add(building);
			}
		}
		return list;
	}
	
	
	/*public static void main(String[] args) {
		List list = new ArrayList();
		BuildingSetGenerate buildingSetGenerate = new BuildingSetGenerate();
		AreaGenerate areaGenerate = new AreaGenerate();
		list = buildingSetGenerate.fourRoadBuildingSet(areaGenerate.area[1], 1, 0.5);
		Building building = new Building();
		for(int i=0;i<list.size();i++){
			building = (Building) list.get(i);
			System.out.println(building.getPoint(0).getY());
		}
	}*/
	
	
}
