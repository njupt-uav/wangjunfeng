package ChannelModel;

import java.io.IOException;
import java.util.HashMap;

import Resource.Area;
import Resource.MEC;
import Resource.Point;
import Resource.UAV;
import Resource.Vehicle;
import Task.VehicleSetGenerate;
import TaskModel.*;
import Tool.C;

public class TransmissionRate {
	AllVehicleTaskGenerate allVehicleTaskGenerate = new AllVehicleTaskGenerate();
	VehicleSetGenerate vehicleSetGenerate = new VehicleSetGenerate();
	UAV uav = new UAV();
	public double rateVehicleToUav(Vehicle vehicle,SingleTask singleTaskTarget, int time, Area area, Vehicle[] vehicles, HashMap map) throws IOException{
		double rateNU = 0;
		/*HashMap map = new HashMap();
		
		map = allVehicleTaskGenerate.oneRoadAllVehicleTask(area, vehicles);*/
		//Vehicle[] vehicles = vehicleSetGenerate.oneRoadVehicleSet(area);
		Point[] uavPoints = uav.realPosition(C.uavSpeed, C.radius, area);
		double circumference = 2*C.PI*C.radius;
		int numReal = (int) (circumference/C.uavSpeed);
		int num = time%numReal;
		double sum = 0;
	//	int count = 0;
	//	SingleTask taskTaregt = (SingleTask)((HashMap) map.get(vehicle.getID())).get(time);
		//System.err.print(vehicle.getP()+" ");
	//	System.err.print(+uavPoints[num].getX()+" ");
	//	System.err.println(singleTaskTarget.getPoint().getX());
		//double disTarget = vehicle.getP()*(1/( Math.pow(Math.sqrt(Math.pow(uavPoints[num].getX()-taskTaregt.getPoint().getX(), 2) + Math.pow(uavPoints[num].getY()-taskTaregt.getPoint().getY(), 2) + Math.pow(C.fixedHight, 2)), C.V2I)));
		double disTarget = vehicle.getP()*(1/( Math.pow(Math.sqrt(Math.pow(uavPoints[num].getX()-singleTaskTarget.getPoint().getX(), 2) + Math.pow(uavPoints[num].getY()-singleTaskTarget.getPoint().getY(), 2) + Math.pow(C.fixedHight, 2)), C.V2I)));
		for(int i=0; i<map.size(); i++){
			SingleTask singleTask = (SingleTask) ((HashMap) map.get(i)).get(time);
			//System.out.println("Trans");
			if(singleTask == null){
				continue;
			}
			if(singleTask.getSn() == 1 || singleTask.getSn() == 3){
				//System.out.println("nei");
				sum = sum + vehicles[i].getP() * (1/ (Math.pow(Math.sqrt(Math.pow(singleTask.getPoint().getX()-uavPoints[num].getX(), 2)+Math.pow(singleTask.getPoint().getY()-uavPoints[num].getY(), 2) +Math.pow(C.fixedHight,  2)), C.V2I)));
			}
		}
		if( singleTaskTarget.getSn() == 1 || singleTaskTarget.getSn() == 3){
			sum = sum - disTarget;
		}
		if(sum == 0 || sum==-C.N0){
			rateNU = C.W * (Math.log(1+(disTarget/(-C.N0)))/Math.log(2));
		}else{
			rateNU = C.W * (Math.log(1+(disTarget/(C.N0+sum)))/Math.log(2));
		}
		
		//rateNU = C.W * (Math.log(1+(disTarget/(sum)))/Math.log(2));
		
		/*if(sum!=-C.N0){
			rateNU = C.W * (Math.log(1+(disTarget/(C.N0+sum)))/Math.log(2));
		}else{
			rateNU = C.W * (Math.log(1+(disTarget/(sum)))/Math.log(2));
		}*/
		
		//System.out.println("rateNU="+rateNU);
		return rateNU;
	}
	
	
	public double rateVehicleToMec(Vehicle vehicle,SingleTask singleTaskTarget, int time, Area area, Vehicle[] vehicles, HashMap map) throws IOException{
		MEC mec = new MEC();
		mec.setPoint(0, 0);
		double rateNM = 0;
		double sum = 0;
		/*HashMap map = new HashMap();
		map = allVehicleTaskGenerate.oneRoadAllVehicleTask(area, vehicles);*/
		//Vehicle[] vehicles = vehicleSetGenerate.oneRoadVehicleSet(area);
		//SingleTask taskTaregt = (SingleTask)((HashMap) map.get(vehicle.getID())).get(time);
		//double disTarget = vehicle.getP() * (1/(Math.pow(Math.sqrt(Math.pow(taskTaregt.getPoint().getX()-mec.getPoint().getX(), 2)+Math.pow(taskTaregt.getPoint().getY()-mec.getPoint().getY(), 2)), C.V2I)));
		double disTarget = vehicle.getP() * (1/(Math.pow(Math.sqrt(Math.pow(singleTaskTarget.getPoint().getX()-mec.getPoint().getX(), 2)+Math.pow(singleTaskTarget.getPoint().getY()-mec.getPoint().getY(), 2)), C.V2I)));
		for(int i=0; i<map.size(); i++){
			SingleTask singleTask = (SingleTask) ((HashMap) map.get(i)).get(time);
			if(singleTask == null){
				continue;
			}
			if(singleTask.getSn() == 2){
				sum = sum + vehicles[i].getP() * (1/(Math.pow(Math.sqrt(Math.pow(singleTask.getPoint().getX()-mec.getPoint().getX(), 2)+Math.pow(singleTask.getPoint().getY()-mec.getPoint().getY(), 2)), C.V2I)));
			}
		}
		if(singleTaskTarget.getSn() == 2){
			sum = sum - disTarget;
		}
		
		if(sum == 0 || sum==-C.N0){
			rateNM = C.W * (Math.log(1+(disTarget/(-C.N0)))/Math.log(2));
		}else{
			rateNM = C.W * (Math.log(1+(disTarget/(C.N0+sum)))/Math.log(2));
		}
		
		//rateNM = C.W * (Math.log(1+(disTarget/(C.N0+sum)))/Math.log(2));
		//System.out.println("rateNM="+rateNM);
		return rateNM;
	}

}
