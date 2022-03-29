package Algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import ComputationModel.PayoffFunction;
import Resource.Vehicle;
import Task.AreaGenerate;
import Task.VehicleSetGenerate;
import TaskModel.AllVehicleTaskGenerate;
import TaskModel.SingleTask;
import Tool.C;

public class UVCO {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		SingleTask singleTask= new SingleTask();
		AreaGenerate areaGenerate = new AreaGenerate();
		VehicleSetGenerate vehicleSetGenerate = new VehicleSetGenerate();
		PayoffFunction payoffFunction = new PayoffFunction();
		AllVehicleTaskGenerate allVehicleTaskGenerate = new AllVehicleTaskGenerate();
		
		/*Vehicle[] vehicles = vehicleSetGenerate.oneRoadVehicleSet(areaGenerate.getArea(2));  //一条道路   kkk
		HashMap<Integer,Map> allTaskMap = allVehicleTaskGenerate.oneRoadAllVehicleTask(areaGenerate.getArea(2), vehicles);   //一条道路
*/		
		/*Vehicle[] vehicles = vehicleSetGenerate.twoRoadVehicleSet(areaGenerate.getArea(2));   //两条道路
		HashMap<Integer,Map> allTaskMap = allVehicleTaskGenerate.twoRoadAllVehicleTask(areaGenerate.getArea(2), vehicles);    //两条道路
*/		
		Vehicle[] vehicles = vehicleSetGenerate.threeRoadVehicleSet(areaGenerate.getArea(2),2);   //三条道路
		HashMap<Integer,Map> allTaskMap = allVehicleTaskGenerate.threeRoadAllVehicleTask(areaGenerate.getArea(2),2, vehicles);    //三条道路
		
	/*	Vehicle[] vehicles = vehicleSetGenerate.fourRoadVehicleSet(areaGenerate.getArea(2),2);   //四条道路
		HashMap<Integer,Map> allTaskMap = allVehicleTaskGenerate.fourRoadAllVehicleTask(areaGenerate.getArea(2), 2, vehicles);    //四条道路
		*/
		int taskAllNumber = 0;
		for(int taskAllNumberId=0;taskAllNumberId<allTaskMap.size();taskAllNumberId++){
			taskAllNumber = taskAllNumber+allTaskMap.get(taskAllNumberId).size();
		}
		
		HashMap<Integer,Map> allTaskMap1 = new HashMap<Integer,Map>();	
		/*HashMap map = new HashMap();	
		map = allVehicleTaskGenerate.oneRoadAllVehicleTask(areaGenerate.getArea(0), vehicles);
		
		HashMap map1 = new HashMap();
		map1.putAll(map);;*/
		
		Vehicle[] vehicles1 = new Vehicle[vehicles.length];
		for(int i=0; i<vehicles1.length; i++){
			vehicles1[i] = new Vehicle();
			vehicles1[i].setPoint(vehicles[i].getPoint().getX(), vehicles[i].getPoint().getY());
			vehicles1[i].setID(vehicles[i].getID());
			vehicles1[i].setDir(vehicles[i].getDir());
			vehicles1[i].setTime(vehicles[i].getTime());
			vehicles1[i].setCalculation(vehicles[i].getCalculation());
			vehicles1[i].setP(vehicles[i].getP());
		}
		int lengthAllTaskMap = allTaskMap.size();
		int[] countSingleVehicleTask = new int[lengthAllTaskMap];
		int countSingleVehicleTaskMax = 0;
		for(int i=0; i<lengthAllTaskMap; i++){
			countSingleVehicleTask[i] = allTaskMap.get(i).size();
			if(countSingleVehicleTask[i]>countSingleVehicleTaskMax){
				countSingleVehicleTaskMax = countSingleVehicleTask[i];
			}
		}
		
		
	/*	for(int i=0; i<allTaskMap.size(); i++){
			for(int j=0; j<countSingleVehicleTask[i]; j++){	
				System.out.print(((SingleTask)allTaskMap.get(i).get(j)).getSn());
			}
			System.out.println();
		}
		for(int i=0; i<vehicles.length; i++){
			System.out.print(vehicles[i].getPoint().getX()+"  ");
		}
		System.out.println();*/
		
		
		
		for(int i=0; i<allTaskMap.size(); i++){
			HashMap<Integer, SingleTask> hs = new HashMap<>();
			for(int j=0; j<countSingleVehicleTask[i]; j++){	
				SingleTask singleTask1 = new SingleTask();
				singleTask1.setTaskID(((SingleTask)allTaskMap.get(i).get(j)).getTaskID()); 
				singleTask1.setVehicleID(((SingleTask)allTaskMap.get(i).get(j)).getVehicleID()); 
				singleTask1.setGenerateTime(((SingleTask)allTaskMap.get(i).get(j)).getGenerateTime()); 
				singleTask1.setDeadTime(((SingleTask)allTaskMap.get(i).get(j)).getDeadTime());
				singleTask1.setWaitTime(((SingleTask)allTaskMap.get(i).get(j)).getWaitTime());
				singleTask1.setTaskCi(((SingleTask)allTaskMap.get(i).get(j)).getTaskCi());
				singleTask1.setTaskOi(((SingleTask)allTaskMap.get(i).get(j)).getTaskOi());
				singleTask1.setTaskDi(((SingleTask)allTaskMap.get(i).get(j)).getTaskDi());
				singleTask1.setPoint(((SingleTask)allTaskMap.get(i).get(j)).getPoint().getX(), ((SingleTask)allTaskMap.get(i).get(j)).getPoint().getY());
				singleTask1.setSn(((SingleTask)allTaskMap.get(i).get(j)).getSn());
				hs.put(j, singleTask1);
			}
			allTaskMap1.put(i, hs);
		}
		double[][] payInitial = new double[lengthAllTaskMap][countSingleVehicleTaskMax];
		double consumeLocal = 0;
		double consumeBoYi = 0;
		for(int i=0; i<lengthAllTaskMap; i++){
			for(int j=0; j<countSingleVehicleTask[i]; j++){
				payInitial[i][j] = payoffFunction.localComputing(vehicles[i], (SingleTask)allTaskMap.get(i).get(j));
				consumeLocal = consumeLocal + payInitial[i][j];
			}
		}
		for(int i=0; i<lengthAllTaskMap; i++){
			System.out.print("i="+i+":");
			for(int j=0; j<countSingleVehicleTask[i]; j++){
				System.out.print(payInitial[i][j]+"  ");
			}
			System.out.println();
		}
		double temp;
		
		List<SingleTask> mecTask = new ArrayList<SingleTask>();
		List<SingleTask> uavTask = new ArrayList<SingleTask>();
		List<SingleTask> vehicleTask = new ArrayList<SingleTask>(); 
		for(int i=0; i<lengthAllTaskMap; i++){
			vehicleTask.add(null);
		}
		List unDistributeTask = new ArrayList<>();
		SingleTask singleTaskTemp = null;
		
		for(int i=0; i<countSingleVehicleTaskMax; i++){
			System.out.println("时刻:"+i);
			for(int ui=0; ui<unDistributeTask.size(); ui++){
				((SingleTask)unDistributeTask.get(ui)).setWaitTime(((SingleTask)unDistributeTask.get(ui)).getWaitTime()+1);
			}
			for(int m=mecTask.size()-1; m>=0; m--){      //MEC上任务执行完成后离开
				if(i==mecTask.get(m).getTaskCi()/C.vehicleTaskUnit+mecTask.get(m).getGenerateTime()+mecTask.get(m).getWaitTime()){//修改
					mecTask.remove(m);
				}
			}
			for(int k=uavTask.size()-1; k>=0; k--){    //无人机上任务执行完成后离开
				if(i==uavTask.get(k).getTaskCi()/C.vehicleTaskUnit+uavTask.get(k).getGenerateTime()+uavTask.get(k).getWaitTime()){//修改
					uavTask.remove(k);
				}
			}
			for(int vi=vehicleTask.size()-1; vi>=0; vi--){
				if(vehicleTask.get(vi)!=null && i==vehicleTask.get(vi).getTaskCi()/C.vehicleTaskUnit+vehicleTask.get(vi).getGenerateTime()+vehicleTask.get(vi).getWaitTime()){//修改
					vehicleTask.remove(vi);
					vehicleTask.add(vi, null);
				}
			}
			for(int p=unDistributeTask.size()-1; p>=0; p--){
				if(((SingleTask)unDistributeTask.get(p)).getSn() == 3){
					if(mecTask.size()==C.mecMax && uavTask.size()==1 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						((SingleTask)unDistributeTask.get(p)).setSn(0);
						vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
						vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(1);
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(1);
						temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(0);
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==1 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(2);
						temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(3);
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==1 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						int flag = 0;
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(2);
						temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(0);
							flag = 1;
						}
						temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(3);
							mecTask.add((SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 1){
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getGenerateTime());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getGenerateTime(),(SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						int flag = 0;
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(2);
						temp = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(1);
							flag = 1;
						}
						temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(3);
							mecTask.add((SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 1){
							uavTask.add((SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						int flag = 0;
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(2);
						temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(0);
							flag = 1;
						}
						temp = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(1);	
							flag = 2;
						}
						temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(3);
							mecTask.add((SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 1){
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 2){
							uavTask.add((SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					continue;
				}
				if(((SingleTask)unDistributeTask.get(p)).getSn() == 2){
					if(mecTask.size()==C.mecMax && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						((SingleTask)unDistributeTask.get(p)).setSn(0);
						vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
						vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(2);
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(2);
						temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(0);
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
					}
					continue;
				}
				if(((SingleTask)unDistributeTask.get(p)).getSn() == 1){
					if(mecTask.size()==C.mecMax && uavTask.size()==1 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						((SingleTask)unDistributeTask.get(p)).setSn(0);
						vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
						vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(1);
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(1);
						temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(0);
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==1 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(3);
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==1 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(3);
						temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(0);
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(1);
						temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(3);
							mecTask.add((SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						int flag = 0;
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(1);
						temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(0);
							flag = 1;
						}
						temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(3);
							mecTask.add((SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 1){
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					/*consumeBoYi = consumeBoYi+payInitial[j][i];
					System.out.print(payInitial[j][i]+"  ");*/
					continue;
				}
				if(((SingleTask)unDistributeTask.get(p)).getSn() == 0){
					if(vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
					payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
					vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
					vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
					consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
					unDistributeTask.remove(p);
					continue;
					}
				}
			}
			for(int j=0; j<lengthAllTaskMap; j++){
				singleTaskTemp=(SingleTask) allTaskMap.get(j).get(i);
				if(singleTaskTemp == null){
					continue;
				}
				if(((SingleTask)allTaskMap.get(j).get(i)).getSn() == 3){
					if(mecTask.size()==C.mecMax && uavTask.size()==1 && vehicleTask.get(j)!=null){
						//((SingleTask) allTaskMap.get(j).get(i)).setWaitTime(((SingleTask) allTaskMap.get(j).get(i)).getWaitTime()+1);
						unDistributeTask.add((SingleTask) allTaskMap.get(j).get(i));
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==1 && vehicleTask.get(j)==null){
						payInitial[j][i] = payoffFunction.localComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i));
						((SingleTask)allTaskMap.get(j).get(i)).setSn(0);
						vehicleTask.remove(j);
						vehicleTask.add(j,(SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(j)!=null){
						payInitial[j][i] = payoffFunction.uavComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)allTaskMap.get(j).get(i)).setSn(1);
						uavTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(j)==null){
						payInitial[j][i] = payoffFunction.uavComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)allTaskMap.get(j).get(i)).setSn(1);
						temp = payoffFunction.localComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i));
						if(temp<payInitial[j][i]){
							payInitial[j][i]=temp;
							((SingleTask)allTaskMap.get(j).get(i)).setSn(0);
							vehicleTask.remove(j);
							vehicleTask.add(j,(SingleTask)allTaskMap.get(j).get(i));
							consumeBoYi = consumeBoYi+payInitial[j][i];
							continue;
						}
						uavTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==1 && vehicleTask.get(j)!=null){
						payInitial[j][i]=payoffFunction.directMecComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)allTaskMap.get(j).get(i)).setSn(2);
						temp = payoffFunction.uavNodeMecComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[j][i]){
							payInitial[j][i]=temp;
							((SingleTask)allTaskMap.get(j).get(i)).setSn(3);
						}
						mecTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==1 && vehicleTask.get(j)==null){
						int flag = 0;
						payInitial[j][i]=payoffFunction.directMecComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)allTaskMap.get(j).get(i)).setSn(2);
						temp = payoffFunction.localComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i));
						if(temp<payInitial[j][i]){
							payInitial[j][i]=temp;
							((SingleTask)allTaskMap.get(j).get(i)).setSn(0);
							flag = 1;
						}
						temp = payoffFunction.uavNodeMecComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[j][i]){
							payInitial[j][i]=temp;
							((SingleTask)allTaskMap.get(j).get(i)).setSn(3);
							mecTask.add((SingleTask)allTaskMap.get(j).get(i));
							consumeBoYi = consumeBoYi+payInitial[j][i];
							continue;
						}
						if(flag == 1){
							vehicleTask.remove(j);
							vehicleTask.add(j,(SingleTask)allTaskMap.get(j).get(i));
							consumeBoYi = consumeBoYi+payInitial[j][i];
							continue;
						}
						mecTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==0 && vehicleTask.get(j)!=null){
						int flag = 0;
						payInitial[j][i]=payoffFunction.directMecComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)allTaskMap.get(j).get(i)).setSn(2);
						temp = payoffFunction.uavComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[j][i]){
							payInitial[j][i]=temp;
							((SingleTask)allTaskMap.get(j).get(i)).setSn(1);
							flag = 1;
						}
						temp = payoffFunction.uavNodeMecComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[j][i]){
							payInitial[j][i]=temp;
							((SingleTask)allTaskMap.get(j).get(i)).setSn(3);
							mecTask.add((SingleTask)allTaskMap.get(j).get(i));
							consumeBoYi = consumeBoYi+payInitial[j][i];
							continue;
						}
						if(flag == 1){
							uavTask.add((SingleTask)allTaskMap.get(j).get(i));
							consumeBoYi = consumeBoYi+payInitial[j][i];
							continue;
						}
						mecTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==0 && vehicleTask.get(j)==null){
						int flag = 0;
						payInitial[j][i]=payoffFunction.directMecComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)allTaskMap.get(j).get(i)).setSn(2);
						temp = payoffFunction.localComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i));
						if(temp<payInitial[j][i]){
							payInitial[j][i]=temp;
							((SingleTask)allTaskMap.get(j).get(i)).setSn(0);
							flag = 1;
						}
						temp = payoffFunction.uavComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[j][i]){
							payInitial[j][i]=temp;
							((SingleTask)allTaskMap.get(j).get(i)).setSn(1);
							flag = 2;
						}
						temp = payoffFunction.uavNodeMecComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[j][i]){
							payInitial[j][i]=temp;
							((SingleTask)allTaskMap.get(j).get(i)).setSn(3);
							mecTask.add((SingleTask)allTaskMap.get(j).get(i));
							consumeBoYi = consumeBoYi+payInitial[j][i];
							continue;
						}
						if(flag == 1){
							vehicleTask.remove(j);
							vehicleTask.add(j,(SingleTask)allTaskMap.get(j).get(i));
							consumeBoYi = consumeBoYi+payInitial[j][i];
							continue;
						}
						if(flag == 2){
							uavTask.add((SingleTask)allTaskMap.get(j).get(i));
							consumeBoYi = consumeBoYi+payInitial[j][i];
							continue;
						}
						mecTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					continue;
				}
				if(((SingleTask)allTaskMap.get(j).get(i)).getSn() == 2){
					if(mecTask.size()==C.mecMax && vehicleTask.get(j)!=null){
						//((SingleTask) allTaskMap.get(j).get(i)).setWaitTime(((SingleTask) allTaskMap.get(j).get(i)).getWaitTime()+1);
						unDistributeTask.add((SingleTask) allTaskMap.get(j).get(i));
					}
					if(mecTask.size()==C.mecMax && vehicleTask.get(j)==null){
						payInitial[j][i] = payoffFunction.localComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i));
						((SingleTask)allTaskMap.get(j).get(i)).setSn(0);
						vehicleTask.remove(j);
						vehicleTask.add(j,(SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					if(mecTask.size()<C.mecMax && vehicleTask.get(j)!=null){
						payInitial[j][i]=payoffFunction.directMecComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)allTaskMap.get(j).get(i)).setSn(2);
						mecTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					if(mecTask.size()<C.mecMax && vehicleTask.get(j)==null){
						payInitial[j][i]=payoffFunction.directMecComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)allTaskMap.get(j).get(i)).setSn(2);
						temp = payoffFunction.localComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i));
						if(temp<payInitial[j][i]){
							payInitial[j][i]=temp;
							((SingleTask)allTaskMap.get(j).get(i)).setSn(0);
							vehicleTask.remove(j);
							vehicleTask.add(j,(SingleTask)allTaskMap.get(j).get(i));
							consumeBoYi = consumeBoYi+payInitial[j][i];
							continue;
						}
						mecTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					continue;
				}
				if(((SingleTask)allTaskMap.get(j).get(i)).getSn() == 1){
					if(mecTask.size()==C.mecMax && uavTask.size()==1 && vehicleTask.get(j)!=null){
						//((SingleTask) allTaskMap.get(j).get(i)).setWaitTime(((SingleTask) allTaskMap.get(j).get(i)).getWaitTime()+1);
						unDistributeTask.add((SingleTask) allTaskMap.get(j).get(i));
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==1 && vehicleTask.get(j)==null){
						payInitial[j][i] = payoffFunction.localComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i));
						((SingleTask)allTaskMap.get(j).get(i)).setSn(0);
						vehicleTask.remove(j);
						vehicleTask.add(j,(SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(j)!=null){
						payInitial[j][i] = payoffFunction.uavComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)allTaskMap.get(j).get(i)).setSn(1);
						uavTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(j)==null){
						payInitial[j][i] = payoffFunction.uavComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)allTaskMap.get(j).get(i)).setSn(1);
						temp = payoffFunction.localComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i));
						if(temp<payInitial[j][i]){
							payInitial[j][i]=temp;
							((SingleTask)allTaskMap.get(j).get(i)).setSn(0);
							vehicleTask.remove(j);
							vehicleTask.add(j,(SingleTask)allTaskMap.get(j).get(i));
							consumeBoYi = consumeBoYi+payInitial[j][i];
							continue;
						}
						uavTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==1 && vehicleTask.get(j)!=null){
						payInitial[j][i] = payoffFunction.uavNodeMecComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)allTaskMap.get(j).get(i)).setSn(3);
						mecTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==1 && vehicleTask.get(j)==null){
						payInitial[j][i] = payoffFunction.localComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i));
						((SingleTask)allTaskMap.get(j).get(i)).setSn(0);
						temp = payoffFunction.uavNodeMecComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[j][i]){
							payInitial[j][i]=temp;
							((SingleTask)allTaskMap.get(j).get(i)).setSn(3);
							mecTask.add((SingleTask)allTaskMap.get(j).get(i));
							consumeBoYi = consumeBoYi+payInitial[j][i];
							continue;
						}
						vehicleTask.remove(j);
						vehicleTask.add(j,(SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==0 && vehicleTask.get(j)!=null){
						payInitial[j][i] = payoffFunction.uavComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)allTaskMap.get(j).get(i)).setSn(1);
						temp = payoffFunction.uavNodeMecComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[j][i]){
							payInitial[j][i]=temp;
							((SingleTask)allTaskMap.get(j).get(i)).setSn(3);
							mecTask.add((SingleTask)allTaskMap.get(j).get(i));
							consumeBoYi = consumeBoYi+payInitial[j][i];
							continue;
						}
						uavTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==0 && vehicleTask.get(j)==null){
						int flag = 0;
						payInitial[j][i] = payoffFunction.localComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i));
						((SingleTask)allTaskMap.get(j).get(i)).setSn(0);
						temp = payoffFunction.uavComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[j][i]){
							payInitial[j][i]=temp;
							((SingleTask)allTaskMap.get(j).get(i)).setSn(1);
							flag = 1;
						}
						temp = payoffFunction.uavNodeMecComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[j][i]){
							payInitial[j][i]=temp;
							((SingleTask)allTaskMap.get(j).get(i)).setSn(3);
							mecTask.add((SingleTask)allTaskMap.get(j).get(i));
							consumeBoYi = consumeBoYi+payInitial[j][i];
							continue;
						}
						if(flag == 1){
							uavTask.add((SingleTask)allTaskMap.get(j).get(i));
							consumeBoYi = consumeBoYi+payInitial[j][i];
							continue;
						}
						vehicleTask.remove(j);
						vehicleTask.add(j,(SingleTask)allTaskMap.get(j).get(i));
						consumeBoYi = consumeBoYi+payInitial[j][i];
						continue;
					}
					continue;
				}
				if(((SingleTask)allTaskMap.get(j).get(i)).getSn() == 0){
					if(vehicleTask.get(j)!=null){
						//((SingleTask) allTaskMap.get(j).get(i)).setWaitTime(((SingleTask) allTaskMap.get(j).get(i)).getWaitTime()+1);
						unDistributeTask.add((SingleTask) allTaskMap.get(j).get(i));
					}else{
					payInitial[j][i]=payoffFunction.localComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i));
					vehicleTask.remove(j);
					vehicleTask.add(j,(SingleTask)allTaskMap.get(j).get(i));
					consumeBoYi = consumeBoYi+payInitial[j][i];
					continue;
					}
				}
			}
		}
		int i = countSingleVehicleTaskMax-1;
		System.out.println("未完成");
		System.out.println(unDistributeTask.size());
		while(unDistributeTask.size()!=0){
			i++;
			System.out.println("时刻:"+i);
			for(int ui=0; ui<unDistributeTask.size(); ui++){
				((SingleTask)unDistributeTask.get(ui)).setWaitTime(((SingleTask)unDistributeTask.get(ui)).getWaitTime()+1);
			}
			for(int m=mecTask.size()-1; m>=0; m--){      //MEC上任务执行完成后离开
				if(i==mecTask.get(m).getTaskCi()/C.vehicleTaskUnit+mecTask.get(m).getGenerateTime()+mecTask.get(m).getWaitTime()){//修改
					mecTask.remove(m);
				}
			}
			for(int k=uavTask.size()-1; k>=0; k--){    //无人机上任务执行完成后离开
				if(i==uavTask.get(k).getTaskCi()/C.vehicleTaskUnit+uavTask.get(k).getGenerateTime()+uavTask.get(k).getWaitTime()){//修改
					uavTask.remove(k);
				}
			}
			for(int vi=vehicleTask.size()-1; vi>=0; vi--){
				if(vehicleTask.get(vi)!=null && i==vehicleTask.get(vi).getTaskCi()/C.vehicleTaskUnit+vehicleTask.get(vi).getGenerateTime()+vehicleTask.get(vi).getWaitTime()){
					vehicleTask.remove(vi);
					vehicleTask.add(vi, null);
				}
			}
			for(int p=unDistributeTask.size()-1; p>=0; p--){
				if(((SingleTask)unDistributeTask.get(p)).getSn() == 3){
					if(mecTask.size()==C.mecMax && uavTask.size()==1 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						((SingleTask)unDistributeTask.get(p)).setSn(0);
						vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
						vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(1);
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(1);
						temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(0);
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==1 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(2);
						temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(3);
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==1 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						int flag = 0;
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID()), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(2);
						temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(0);
							flag = 1;
						}
						temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(3);
							mecTask.add((SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							flag = 0;
							continue;
						}
						if(flag == 1){
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						int flag = 0;
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(2);
						temp = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(1);
							flag = 1;
						}
						temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(3);
							mecTask.add((SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 1){
							uavTask.add((SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						int flag = 0;
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(2);
						temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(0);
							flag = 1;
						}
						temp = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(1);
							flag = 2;
						}
						temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(3);
							mecTask.add((SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 1){
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 2){
							uavTask.add((SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					continue;
				}
				if(((SingleTask)unDistributeTask.get(p)).getSn() == 2){
					if(mecTask.size()==C.mecMax && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						((SingleTask)unDistributeTask.get(p)).setSn(0);
						vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
						vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(2);
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(2);
						temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(0);
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
					}
					continue;
				}
				if(((SingleTask)unDistributeTask.get(p)).getSn() == 1){
					if(mecTask.size()==C.mecMax && uavTask.size()==1 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						((SingleTask)unDistributeTask.get(p)).setSn(0);
						vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
						vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(1);
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(1);
						temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(0);
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==1 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(3);
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==1 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(3);
						temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(0);
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(1);
						temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(3);
							mecTask.add((SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						int flag = 0;
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(1);
						temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(0);
							flag = 1;
						}
						temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						if(temp<payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]){
							payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=temp;
							((SingleTask)unDistributeTask.get(p)).setSn(3);
							mecTask.add((SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 1){
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					continue;
				}
				if(((SingleTask)unDistributeTask.get(p)).getSn() == 0){
					if(vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
					payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
					vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
					vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
					consumeBoYi = consumeBoYi+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
					unDistributeTask.remove(p);
					//consumeBoYi = consumeBoYi+payInitial[j][i];
					continue;
					}
				}
			}
		}
		for(i=0; i<lengthAllTaskMap; i++){
			System.out.print("车辆"+i+":");
			for(int j=0; j<countSingleVehicleTask[i];j++){
				System.out.print(payInitial[i][j]+"  ");
			}
			System.out.println();
		}
		for(i=0; i<lengthAllTaskMap; i++){
			System.out.print("车辆"+i+":");
			for(int j=0; j<countSingleVehicleTask[i];j++){
				System.out.print(((SingleTask)allTaskMap.get(i).get(j)).getSn()+"  ");
			}
			System.out.println();
		}
		
		System.out.println(consumeBoYi/consumeLocal);
		for(i=0; i<lengthAllTaskMap; i++){
			System.out.print("车辆:"+i+":");
			for(int j=0; j<countSingleVehicleTask[i]; j++){
				System.out.print("开始时间:"+((SingleTask)allTaskMap.get(i).get(j)).getGenerateTime()+" "+"结束时间:"+(((SingleTask)allTaskMap.get(i).get(j)).getGenerateTime()+((SingleTask)allTaskMap.get(i).get(j)).getTaskCi()/C.vehicleTaskUnit+((SingleTask)allTaskMap.get(i).get(j)).getWaitTime()));
			}
			System.out.println();
		}
		
		SA sa = new SA();
		double consumeSA = sa.getSA(vehicles1,areaGenerate,allTaskMap1);
		double pro = consumeSA/consumeBoYi;
		System.out.println(pro);
		System.out.println(1-pro);
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间:"+(endTime-startTime)+"ms");
		System.out.println("任务总数:"+taskAllNumber);
		//System.out.println(consumeSA/consumeLocal);
		//String ss = JSONObject.toJSONString(allTaskMap);
      //  JSONObject.toJavaObject(ss,HashMap.class);
		
	}
	
	/*for(int i=0; i<lengthAllTaskMap; i++){
	System.out.print("i="+i+":");
	for(int j=0; j<countSingleVehicleTask[i]; j++){
		if(((SingleTask)allTaskMap.get(i).get(j)).getSn() == 3){
			payInitial[i][j]=payoffFunction.directMecComputing(vehicles[i], (SingleTask)allTaskMap.get(i).get(j), j, areaGenerate.getArea(0), vehicles);
			((SingleTask)allTaskMap.get(i).get(j)).setSn(2);
			temp = payoffFunction.localComputing(vehicles[i], (SingleTask)allTaskMap.get(i).get(j));
			if(temp<payInitial[i][j]){
				payInitial[i][j]=temp;
				((SingleTask)allTaskMap.get(i).get(j)).setSn(0);
			}
			temp = payoffFunction.uavComputing(vehicles[i], (SingleTask)allTaskMap.get(i).get(j), j, areaGenerate.getArea(0), vehicles);
			if(temp<payInitial[i][j]){
				payInitial[i][j]=temp;
				((SingleTask)allTaskMap.get(i).get(j)).setSn(1);
			}
			temp = payoffFunction.uavNodeMecComputing(vehicles[i], (SingleTask)allTaskMap.get(i).get(j), j, areaGenerate.getArea(0), vehicles);
			if(temp<payInitial[i][j]){
				payInitial[i][j]=temp;
				((SingleTask)allTaskMap.get(i).get(j)).setSn(3);
			}
			consumeBoYi = consumeBoYi+payInitial[i][j];
			System.out.print(payInitial[i][j]+"  ");
			continue;
		}
		if(((SingleTask)allTaskMap.get(i).get(j)).getSn() == 2){
			payInitial[i][j]=payoffFunction.directMecComputing(vehicles[i], (SingleTask)allTaskMap.get(i).get(j), j, areaGenerate.getArea(0), vehicles);
			((SingleTask)allTaskMap.get(i).get(j)).setSn(2);
			temp = payoffFunction.localComputing(vehicles[i], (SingleTask)allTaskMap.get(i).get(j));
			if(temp<payInitial[i][j]){
				payInitial[i][j]=temp;
				((SingleTask)allTaskMap.get(i).get(j)).setSn(0);
			}
			consumeBoYi = consumeBoYi+payInitial[i][j];
			System.out.print(payInitial[i][j]+"  ");
			continue;
		}
		if(((SingleTask)allTaskMap.get(i).get(j)).getSn() == 1){
			payInitial[i][j]=payoffFunction.uavComputing(vehicles[i], (SingleTask)allTaskMap.get(i).get(j), j, areaGenerate.getArea(0), vehicles);
			((SingleTask)allTaskMap.get(i).get(j)).setSn(1);
			temp = payoffFunction.localComputing(vehicles[i], (SingleTask)allTaskMap.get(i).get(j));
			if(temp<payInitial[i][j]){
				payInitial[i][j]=temp;
				((SingleTask)allTaskMap.get(i).get(j)).setSn(0);
			}
			temp = payoffFunction.uavNodeMecComputing(vehicles[i], (SingleTask)allTaskMap.get(i).get(j), j, areaGenerate.getArea(0), vehicles);
			if(temp<payInitial[i][j]){
				payInitial[i][j]=temp;
				((SingleTask)allTaskMap.get(i).get(j)).setSn(3);
			}
			consumeBoYi = consumeBoYi+payInitial[i][j];
			System.out.print(payInitial[i][j]+"  ");
			continue;
		}
		if(((SingleTask)allTaskMap.get(i).get(j)).getSn() == 0){
			payInitial[i][j]=payoffFunction.localComputing(vehicles[i], (SingleTask)allTaskMap.get(i).get(j));
			consumeBoYi = consumeBoYi+payInitial[i][j];
			System.out.print(payInitial[i][j]+"  ");
			continue;
		}
	}
	System.out.println();
}
for(int i=0; i<lengthAllTaskMap; i++){
	System.out.print("i="+i+":");
	for(int j=0; j<countSingleVehicleTask[i]; j++){
		System.out.print(((SingleTask)allTaskMap.get(i).get(j)).getSn()+"  ");
	}
	System.out.println();
}
System.out.println(consumeBoYi/consumeLocal);*/

}
