package TaskModel;
import java.io.IOException;
import java.util.*;

import Resource.Area;
import Resource.Vehicle;
import Task.VehicleSetGenerate;

public class AllVehicleTaskGenerate {
	VehicleSetGenerate vehicleSetGenerate = new VehicleSetGenerate();
	VehicleTaskGenerate vehicleTaskGenerate = new VehicleTaskGenerate();
	public HashMap<Integer,Map> oneRoadAllVehicleTask(Area area, Vehicle[] vehicles) throws IOException{
		HashMap<Integer,Map> map = new HashMap<Integer,Map>();
		//Vehicle[] vehicles = vehicleSetGenerate.oneRoadVehicleSet(area);
		int time = 0;
		Map maptemp = new HashMap();
		for(int i=0; i<vehicles.length; i++){
			if(vehicles[i].getDir()==0){
				time = (int) ((area.getLength() - vehicles[i].getPoint().getX())/vehicles[i].speed);
			}
			if(vehicles[i].getDir()==1){
				time = (int) (vehicles[i].getPoint().getX()/vehicles[i].speed);
			}
			maptemp = vehicleTaskGenerate.taskGenerate(vehicles[i], time, area);
			map.put(vehicles[i].getID(), maptemp);
		}
		return map;
	}
	public HashMap<Integer,Map> twoRoadAllVehicleTask(Area area, Vehicle[] vehicles) throws IOException{
		HashMap<Integer,Map> map = new HashMap<Integer,Map>();
		//Vehicle[] vehicles = vehicleSetGenerate.twoRoadVehicleSet(area);
		int time = 0;
		Map maptemp = new HashMap();
		for(int i=0; i<vehicles.length; i++){
			if(vehicles[i].getDir()==0){
				time = (int) ((area.getLength() - vehicles[i].getPoint().getX())/vehicles[i].speed);
			}
			if(vehicles[i].getDir()==1){
				time = (int) (vehicles[i].getPoint().getX()/vehicles[i].speed);
			}
			if(vehicles[i].getDir()==2){
				time = (int) ((area.getWidth() - vehicles[i].getPoint().getY())/vehicles[i].speed);
			}
			if(vehicles[i].getDir()==3){
				time = (int) (vehicles[i].getPoint().getY()/vehicles[i].speed);
			}
			maptemp = vehicleTaskGenerate.taskGenerate(vehicles[i], time, area);
			map.put(vehicles[i].getID(), maptemp);
		}
		return map;
	}
	public HashMap<Integer,Map> threeRoadAllVehicleTask(Area area, int index, Vehicle[] vehicles) throws IOException{
		HashMap<Integer,Map> map = new HashMap<Integer,Map>();
		//Vehicle[] vehicles = vehicleSetGenerate.threeRoadVehicleSet(area, index);
		int time = 0;
		Map maptemp = new HashMap();
		for(int i=0; i<vehicles.length; i++){
			if(vehicles[i].getDir()==0){
				time = (int) ((area.getLength() - vehicles[i].getPoint().getX())/vehicles[i].speed);
			}
			if(vehicles[i].getDir()==1){
				time = (int) (vehicles[i].getPoint().getX()/vehicles[i].speed);
			}
			if(vehicles[i].getDir()==2){
				time = (int) ((area.getWidth() - vehicles[i].getPoint().getY())/vehicles[i].speed);
			}
			if(vehicles[i].getDir()==3){
				time = (int) (vehicles[i].getPoint().getY()/vehicles[i].speed);
			}
			maptemp = vehicleTaskGenerate.taskGenerate(vehicles[i], time, area);
			map.put(vehicles[i].getID(), maptemp);
		}
		return map;
	}
	public HashMap<Integer,Map> fourRoadAllVehicleTask(Area area, int index, Vehicle[] vehicles) throws IOException{
		HashMap<Integer,Map> map = new HashMap<Integer,Map>();
		//Vehicle[] vehicles = vehicleSetGenerate.fourRoadVehicleSet(area, index);
		int time = 0;
		Map maptemp = new HashMap();
		for(int i=0; i<vehicles.length; i++){
			if(vehicles[i].getDir()==0){
				time = (int) ((area.getLength() - vehicles[i].getPoint().getX())/vehicles[i].speed);
			}
			if(vehicles[i].getDir()==1){
				time = (int) (vehicles[i].getPoint().getX()/vehicles[i].speed);
			}
			if(vehicles[i].getDir()==2){
				time = (int) ((area.getWidth() - vehicles[i].getPoint().getY())/vehicles[i].speed);
			}
			if(vehicles[i].getDir()==3){
				time = (int) (vehicles[i].getPoint().getY()/vehicles[i].speed);
			}
			maptemp = vehicleTaskGenerate.taskGenerate(vehicles[i], time, area);
			map.put(vehicles[i].getID(), maptemp);
		}
		return map;
	}
}
