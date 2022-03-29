package TaskModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Random;

import Resource.Area;
import Resource.MEC;
import Resource.Point;
import Resource.UAV;
import Resource.Vehicle;
import Task.VehicleSetGenerate;
import Tool.C;

public class VehicleTaskGenerate {
	MEC mec = new MEC();
	
	UAV uav = new UAV();
	public HashMap<Integer, SingleTask> taskGenerate(Vehicle vehicle, int time, Area area) throws IOException{
		mec.setPoint(0, 0);
		HashMap<Integer,SingleTask> map = new HashMap<Integer,SingleTask>();	
		Random random = new Random();
		int rand;
		double[] kind;
		for(int i=0; i<=time; i++){
			SingleTask singleTask = new SingleTask();
			singleTask.setTaskID(i); 
			singleTask.setVehicleID(vehicle.getID()); 
			singleTask.setGenerateTime(i); 
			singleTask.setDeadTime(i+5);
			singleTask.setWaitTime(0);
			rand = random.nextInt(C.numVehicleTask);
			kind = taskSize(rand);
			singleTask.setTaskCi(kind[0]);
			singleTask.setTaskOi(kind[1]);
			singleTask.setTaskDi(kind[2]);
			if(vehicle.getDir() == 0){
				singleTask.setPoint(vehicle.getPoint().getX()+i*vehicle.speed, vehicle.getPoint().getY());
			}
			else if(vehicle.getDir() == 1){
				singleTask.setPoint(vehicle.getPoint().getX()-i*vehicle.speed, vehicle.getPoint().getY());
			}
			else if(vehicle.getDir() == 2){
				singleTask.setPoint(vehicle.getPoint().getX(), vehicle.getPoint().getY()+i*vehicle.speed);
			}
			else{
				singleTask.setPoint(vehicle.getPoint().getX(), vehicle.getPoint().getY()-i*vehicle.speed);
			}
			singleTask.setSn(snPrime(singleTask.getPoint().getX(),singleTask.getPoint().getY(), area, i));
			//System.out.println(i);
			map.put(i, singleTask);
		}
		return map;
	}
	
	public int snPrime(double x, double y, Area area, int t) throws IOException{
		//String txtName = "MapSource\\1200_1200_1_road_0.1_0_random.txt";     //一条道路
		//String txtName = "MapSource\\1200_1200_2_road_0.1_0_random.txt";     //两条道路
		String txtName = "MapSource\\1200_1200_3_road_0.1_0_random.txt";     //三条道路
		//String txtName = "MapSource\\1200_1200_4_road_0.1_0_random.txt";     //四条道路
		FileReader fr;
		BufferedReader br;
		int count = 0;
		fr = new FileReader(txtName);
		br = new BufferedReader(fr);
		RandomAccessFile file = new RandomAccessFile(new File(txtName),"r");
		while(file.readLine()!=null){ 
			count++;
		}
		file.close();
		//int buildingNum = count - 2;     //一条道路
		//int buildingNum = count - 3;     //两条道路
		int buildingNum = count - 4;     //三条道路
		//int buildingNum = count - 5;     //四条道路
		String s = null;
		s = br.readLine();
		s = br.readLine();
		String[] strs;
		double distanceVTUMax = Math.sqrt(Math.pow(area.getLength()/2+C.radius, 2) + Math.pow(C.fixedHight, 2));
		Point[] points = uav.realPosition(C.uavSpeed, C.radius, area);
		int i = 0;
		mec.setPoint(0, 0);
		int flag = 1;
		for(i=0; i<buildingNum; i++){
			s = br.readLine();
			strs = s.split("\t");
			for(int j=0; j<4; j++){
				if(Math.max(Double.parseDouble(strs[(2*j)%8]),Double.parseDouble(strs[(2*j+2)%8])) < Math.min(mec.getPoint().getX(), x) || Math.max(mec.getPoint().getX(), x) < Math.min(Double.parseDouble(strs[(2*j)%8]),Double.parseDouble(strs[(2*j+2)%8])) || Math.max(Double.parseDouble(strs[(2*j+1)%8]),Double.parseDouble(strs[(2*j+3)%8])) < Math.min(mec.getPoint().getY(), y) || Math.max(mec.getPoint().getY(), y) < Math.min(Double.parseDouble(strs[(2*j+1)%8]),Double.parseDouble(strs[(2*j+3)%8]))){
					flag=1;      //不相交
				}else{
					flag=0;
					break;
				}
				if(((Double.parseDouble(strs[(2*j+2)%8])-mec.getPoint().getX()) * (Double.parseDouble(strs[(2*j+3)%8])-Double.parseDouble(strs[(2*j+1)%8]))-(Double.parseDouble(strs[(2*j+3)%8])-mec.getPoint().getY())*(Double.parseDouble(strs[(2*j+2)%8])-Double.parseDouble(strs[(2*j)%8])))*((Double.parseDouble(strs[(2*j+2)%8])-x)*(Double.parseDouble(strs[(2*j+3)%8])-Double.parseDouble(strs[(2*j+1)%8]))-(Double.parseDouble(strs[(2*j+3)%8])-y)*(Double.parseDouble(strs[(2*j+2)%8])-Double.parseDouble(strs[(2*j)%8])))>0.0000001){
					flag=1;      //不相交
				}else{
					flag=0;
					break;
				}
				if(((Double.parseDouble(strs[(2*j)%8])-mec.getPoint().getX())*(y-mec.getPoint().getY())-(Double.parseDouble(strs[(2*j+1)%8])-mec.getPoint().getY())*(x-mec.getPoint().getX()))*((Double.parseDouble(strs[(2*j+2)%8])-mec.getPoint().getX())*(y-mec.getPoint().getY())-(Double.parseDouble(strs[(2*j+3)%8])-mec.getPoint().getY())*(x-mec.getPoint().getX()))>0.0000001){
					flag=1;;      //不相交
				}else{
					flag=0;
					break;
				}
			}
			if(flag==0){
				break;
			}
		}
		t = t % points.length;
		double distanceUTVReal =Math.sqrt(Math.pow(x-points[t].getX(), 2) + Math.pow(y-points[t].getY(), 2) + Math.pow(C.fixedHight, 2));
		if(i == buildingNum){       //和MEC可以建立连接
			if(distanceUTVReal < distanceVTUMax*0.9){        //车辆产生的任务与无人机的距离小于最远距离的90%就可以连接上,同时可以连接上无人机
				return 3;
			}
			return 2;
		}
		else{               //是否可以连接UAV
			//t = t % points.length;
			//double distanceUTVReal =Math.sqrt(Math.pow(x-points[t].getX(), 2) + Math.pow(y-points[t].getY(), 2) + Math.pow(C.fixedHight, 2));
			if(distanceUTVReal < distanceVTUMax*0.9){        //车辆产生的任务与无人机的距离小于最远距离的90%就可以连接上
				return 1;
			}
		}
		return 0;                //在本地
	}
	
	public double[] taskSize(int rand){
		double[] kind = new double[3];
		if(rand == 0){          //1秒完成
			/*kind[0] = 1;
			kind[1] = 0.2;
			kind[2] = 0.1;*/
			kind[0] = C.vehicleTaskUnit*1;
			kind[1] = 0.76;
			kind[2] = 0.2;
		}
		else if(rand == 1){   //2秒完成
			/*kind[0] = 2;
			kind[1] = 0.3;
			kind[2] = 0.1;*/
			kind[0] = C.vehicleTaskUnit*2;
			kind[1] = 0.76;
			kind[2] = 0.2;
			/*kind[1] = 480;
			kind[2] = 40;*/
		}
		else if(rand == 2){    //3秒完成
			/*kind[0] = 3;
			kind[1] = 0.3;
			kind[2] = 0.2;*/
			kind[0] = C.vehicleTaskUnit*3;
			kind[1] = 0.76;
			kind[2] = 0.2;
			/*kind[1] = 1000;
			kind[2] = 100;*/
		}
		else if(rand == 3){
			/*kind[0] = 4;
			kind[1] = 0.3;
			kind[2] = 0.05;*/
			kind[0] = C.vehicleTaskUnit*4;
			kind[1] = 0.76;
			kind[2] = 0.2;
			/*kind[1] = 2000;
			kind[2] = 150;*/
		}
		else{
			/*kind[0] = 5;
			kind[1] = 0.2;
			kind[2] = 0.05;*/
			kind[0] = C.vehicleTaskUnit*5;
			kind[1] = 0.76;
			kind[2] = 0.2;
			/*kind[1] = 200;
			kind[2] = 10;*/
		}
		return kind;
	}

}
