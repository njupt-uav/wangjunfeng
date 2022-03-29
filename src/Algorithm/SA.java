package Algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ComputationModel.PayoffFunction;
import Resource.Vehicle;
import Task.AreaGenerate;
import TaskModel.AllVehicleTaskGenerate;
import TaskModel.SingleTask;
import Tool.C;

public class SA {
	public static double getSA(Vehicle[] vehicles,AreaGenerate areaGenerate,HashMap<Integer,Map> allTaskMap) throws IOException{
		int T = 1000;   //初始化温度
		int Tmin = 1;   //温度的下界
		//int t = 100;     //迭代的次数
		double delta = 0.98;     //温度的下降率
		double consumeSA = 0;
		double temp;
		PayoffFunction payoffFunction = new PayoffFunction();
		/*HashMap<Integer,Map> allTaskMap = allVehicleTaskGenerate.oneRoadAllVehicleTask(areaGenerate.getArea(0), vehicles);
		HashMap map = new HashMap();	
		map = allVehicleTaskGenerate.oneRoadAllVehicleTask(areaGenerate.getArea(0), vehicles);*/
		int lengthAllTaskMap = allTaskMap.size();
		List<SingleTask> mecTask = new ArrayList<SingleTask>();
		List<SingleTask> uavTask = new ArrayList<SingleTask>();
		List<SingleTask> vehicleTask = new ArrayList<SingleTask>(); 
		for(int i=0; i<lengthAllTaskMap; i++){
			vehicleTask.add(null);
		}		
		List<SingleTask> unDistributeTask = new ArrayList<>();
		int[] countSingleVehicleTask = new int[lengthAllTaskMap];
		int countSingleVehicleTaskMax = 0;
		for(int i=0; i<lengthAllTaskMap; i++){
			countSingleVehicleTask[i] = allTaskMap.get(i).size();
			if(countSingleVehicleTask[i]>countSingleVehicleTaskMax){
				countSingleVehicleTaskMax = countSingleVehicleTask[i];
			}
		}
		
		List<List> arrangeList = new ArrayList<List>();//安排结果
		
		double[][] payInitial = new double[lengthAllTaskMap][countSingleVehicleTaskMax];
		SingleTask singleTaskTemp = null;
		for(int i=0; i<countSingleVehicleTaskMax; i++){
			//System.out.println("时刻:"+i);
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
			for(int vi=vehicleTask.size()-1; vi>=0; vi--){       //车辆上任务执行完成后离开
				if(vehicleTask.get(vi)!=null && i==vehicleTask.get(vi).getTaskCi()/C.vehicleTaskUnit+vehicleTask.get(vi).getGenerateTime()+vehicleTask.get(vi).getWaitTime()){
					vehicleTask.remove(vi);
					vehicleTask.add(vi, null);
				}
			}
			double consume = Double.MAX_VALUE;
			List<SingleTask> unDistributeTaskOptimize = new ArrayList<>();
			List<SingleTask> unDistributeTaskOptimizeTemp =  new ArrayList<>();
			List<SingleTask> mecTaskTemp = new ArrayList<>();
			List<SingleTask> uavTaskTemp = new ArrayList<>();
			List<SingleTask> vehicleTaskTemp = new ArrayList<>();
			List<SingleTask> unDistributeTaskTemp = new ArrayList<>();
			int flagChange = 0;
			T=1000;
			while(T>Tmin){
				//System.out.println("退火"+i);
				flagChange = 0;
				double consumeTemp = 0;
				
				/*for(int mecTaskTempNumber=0; mecTaskTempNumber<mecTaskTemp.size(); mecTaskTempNumber++){
					mecTaskTemp.remove(mecTaskTempNumber);
				}	
				System.out.println("mec数量:"+mecTaskTemp.size());*/
				mecTaskTemp.removeAll(mecTaskTemp);
				for(int mecTaskNumber=0; mecTaskNumber<mecTask.size(); mecTaskNumber++){
					SingleTask s = new SingleTask();
					s=mecTask.get(mecTaskNumber);
					mecTaskTemp.add(s);
				}	
				
				/*for(int uavTaskTempNumber=0; uavTaskTempNumber<uavTaskTemp.size(); uavTaskTempNumber++){
					uavTaskTemp.remove(uavTaskTempNumber);
				}*/
				uavTaskTemp.removeAll(uavTaskTemp);
				for(int uavTaskNumber=0; uavTaskNumber<uavTask.size(); uavTaskNumber++){
					SingleTask s = new SingleTask();
					s=uavTask.get(uavTaskNumber);
					uavTaskTemp.add(s);
				}
				
				/*for(int vehicleTaskTempNumber=0; vehicleTaskTempNumber<vehicleTaskTemp.size(); vehicleTaskTempNumber++){
					vehicleTaskTemp.remove(vehicleTaskTempNumber);
				}*/
				vehicleTaskTemp.removeAll(vehicleTaskTemp);
				for(int vehicleTaskNumber=0; vehicleTaskNumber<vehicleTask.size(); vehicleTaskNumber++){
					SingleTask s = new SingleTask();
					s=vehicleTask.get(vehicleTaskNumber);
					vehicleTaskTemp.add(s);
				}
				
				/*for(int unDistributeTaskTempNumber=0; unDistributeTaskTempNumber<unDistributeTaskTemp.size(); unDistributeTaskTempNumber++){
					unDistributeTaskTemp.remove(unDistributeTaskTempNumber);
				}*/
				unDistributeTaskTemp.removeAll(unDistributeTaskTemp);
				for(int unDistributeTaskNumber=0; unDistributeTaskNumber<unDistributeTask.size(); unDistributeTaskNumber++){
					SingleTask s = new SingleTask();
					s = unDistributeTask.get(unDistributeTaskNumber);
					unDistributeTaskTemp.add(s);
				}
				Collections.shuffle(unDistributeTaskTemp);
				unDistributeTaskOptimizeTemp.removeAll(unDistributeTaskOptimizeTemp);
				for(int kk = 0; kk<unDistributeTaskTemp.size(); kk++){
					SingleTask s = new SingleTask();
					s = unDistributeTaskTemp.get(kk);
					unDistributeTaskOptimizeTemp.add(s);
				}
			//	System.out.println(unDistributeTaskTemp.size());
				/*int kinds = 1;
				for(int kk = 1; kk<=unDistributeTask.size(); kk++){
					kinds = kk * kinds;
				}
				int[][] unDistributeTaskSize = new int[kinds][unDistributeTask.size()];
				List<List> unDistributeTaskTotal = new ArrayList<>();
				for(int kk=0; kk<kinds; kk++){
					List<SingleTask> listTemp = new ArrayList<>();
				}*/
	
				/*List<SingleTask> l1 = new ArrayList<>(unDistributeTaskTemp);
				Collections.shuffle(l1);*/
				
				/*List<SingleTask> unDistributeTaskTempOptimize = new ArrayList<>();
				for(int unDistributeTaskTempNumber=0; unDistributeTaskTempNumber<unDistributeTask.size(); unDistributeTaskTempNumber++){
					SingleTask s = new SingleTask();
					s = unDistributeTask.get(unDistributeTaskTempNumber);
					unDistributeTaskTempOptimize.add(s);
				}
				Collections.shuffle(unDistributeTaskTempOptimize);*/
				for(int p=unDistributeTaskTemp.size()-1; p>=0; p--){
					if(((SingleTask)unDistributeTaskTemp.get(p)).getSn() == 3){
						if(mecTaskTemp.size()==C.mecMax && uavTaskTemp.size()==1 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
							vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
							vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()==C.mecMax && uavTaskTemp.size()==0 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())!=null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(1);
							uavTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()==C.mecMax && uavTaskTemp.size()==0 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(1);
							temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
								vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
								vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							uavTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && uavTask.size()==1 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())!=null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(2);
							temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(3);
							}
							mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && uavTaskTemp.size()==1 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							int flag = 0;
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(2);
							temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
								flag = 1;
							}
							temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(3);
								mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							if(flag == 1){
								vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime());
								vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(),(SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && uavTaskTemp.size()==0 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())!=null){
							int flag = 0;
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(2);
							temp = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(1);
								flag = 1;
							}
							temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(3);
								mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							if(flag == 1){
								uavTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && uavTaskTemp.size()==0 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							int flag = 0;
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(2);
							temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
								flag = 1;
							}
							temp = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(1);	
								flag = 2;
							}
							temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(3);
								mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							if(flag == 1){
								vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
								vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							if(flag == 2){
								uavTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						continue;
					}
					if(((SingleTask)unDistributeTaskTemp.get(p)).getSn() == 2){
						if(mecTaskTemp.size()==C.mecMax && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
							vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
							vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())!=null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(2);
							mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(2);
							temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
								vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
								vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						continue;
					}
					if(((SingleTask)unDistributeTaskTemp.get(p)).getSn() == 1){
						if(mecTaskTemp.size()==C.mecMax && uavTaskTemp.size()==1 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
							vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
							vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()==C.mecMax && uavTaskTemp.size()==0 && vehicleTask.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())!=null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(1);
							uavTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()==C.mecMax && uavTaskTemp.size()==0 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(1);
							temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
								vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
								vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							uavTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && uavTaskTemp.size()==1 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())!=null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(3);
							mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && uavTaskTemp.size()==1 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(3);
							temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
								vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
								vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && uavTaskTemp.size()==0 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())!=null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(1);
							temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(3);
								mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							uavTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && uavTaskTemp.size()==0 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							int flag = 0;
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(1);
							temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
								flag = 1;
							}
							temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(3);
								mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							if(flag == 1){
								vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
								vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							uavTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						continue;
					}
					if(((SingleTask)unDistributeTaskTemp.get(p)).getSn() == 0){
						if(vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
						vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
						vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
						consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
						unDistributeTaskTemp.remove(p);
						continue;
						}
					}	
					//System.out.println();
				}
				
				if(consumeTemp<consume){
					consume = consumeTemp;
					flagChange = 1;
					/*for(int kk1 = 0; kk1<unDistributeTaskOptimize.size(); kk1++){
						unDistributeTaskOptimize.remove(kk1);
					}*/
					/*unDistributeTaskOptimize.removeAll(unDistributeTaskOptimize);
					for(int kk = 0; kk<unDistributeTaskTemp.size(); kk++){
						SingleTask s = new SingleTask();
						s = unDistributeTaskTemp.get(kk);
						unDistributeTaskOptimize.add(s);
					}*/
					unDistributeTaskOptimize.removeAll(unDistributeTaskOptimize);
					for(int kk = 0; kk<unDistributeTaskOptimizeTemp.size(); kk++){
						SingleTask s = new SingleTask();
						s = unDistributeTaskOptimizeTemp.get(kk);
						unDistributeTaskOptimize.add(s);
					}
					//Collections.reverse(unDistributeTaskOptimize);
				}
				if(flagChange == 1){
					unDistributeTask.removeAll(unDistributeTask);
					for(int kk=0; kk<unDistributeTaskOptimize.size(); kk++){
						SingleTask s = new SingleTask();
						s = unDistributeTaskOptimize.get(kk);
						unDistributeTask.add(s);
					}
				}
				T = (int) (T*delta);
			}
			/*for(int kk=0; kk<unDistributeTask.size(); kk++){
				unDistributeTask.remove(kk);
			}*/
			
			for(int p=unDistributeTask.size()-1; p>=0; p--){
				if(((SingleTask)unDistributeTask.get(p)).getSn() == 3){
					if(mecTask.size()==C.mecMax && uavTask.size()==1 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						((SingleTask)unDistributeTask.get(p)).setSn(0);
						vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
						vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(1);
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 1){
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getGenerateTime());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getGenerateTime(),(SingleTask)unDistributeTask.get(p));
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 1){
							uavTask.add((SingleTask)unDistributeTask.get(p));
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 1){
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 2){
							uavTask.add((SingleTask)unDistributeTask.get(p));
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(2);
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(1);
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==1 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(3);
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 1){
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
					consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
						consumeSA = consumeSA+payInitial[j][i];
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(j)!=null){
						payInitial[j][i] = payoffFunction.uavComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)allTaskMap.get(j).get(i)).setSn(1);
						uavTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeSA = consumeSA+payInitial[j][i];
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
							consumeSA = consumeSA+payInitial[j][i];
							continue;
						}
						uavTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeSA = consumeSA+payInitial[j][i];
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
						consumeSA = consumeSA+payInitial[j][i];
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
							consumeSA = consumeSA+payInitial[j][i];
							continue;
						}
						if(flag == 1){
							vehicleTask.remove(j);
							vehicleTask.add(j,(SingleTask)allTaskMap.get(j).get(i));
							consumeSA = consumeSA+payInitial[j][i];
							continue;
						}
						mecTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeSA = consumeSA+payInitial[j][i];
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
							consumeSA = consumeSA+payInitial[j][i];
							continue;
						}
						if(flag == 1){
							uavTask.add((SingleTask)allTaskMap.get(j).get(i));
							consumeSA = consumeSA+payInitial[j][i];
							continue;
						}
						mecTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeSA = consumeSA+payInitial[j][i];
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
							consumeSA = consumeSA+payInitial[j][i];
							continue;
						}
						if(flag == 1){
							vehicleTask.remove(j);
							vehicleTask.add(j,(SingleTask)allTaskMap.get(j).get(i));
							consumeSA = consumeSA+payInitial[j][i];
							continue;
						}
						if(flag == 2){
							uavTask.add((SingleTask)allTaskMap.get(j).get(i));
							consumeSA = consumeSA+payInitial[j][i];
							continue;
						}
						mecTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeSA = consumeSA+payInitial[j][i];
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
						consumeSA = consumeSA+payInitial[j][i];
						continue;
					}
					if(mecTask.size()<C.mecMax && vehicleTask.get(j)!=null){
						payInitial[j][i]=payoffFunction.directMecComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)allTaskMap.get(j).get(i)).setSn(2);
						mecTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeSA = consumeSA+payInitial[j][i];
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
							consumeSA = consumeSA+payInitial[j][i];
							continue;
						}
						mecTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeSA = consumeSA+payInitial[j][i];
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
						consumeSA = consumeSA+payInitial[j][i];
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(j)!=null){
						payInitial[j][i] = payoffFunction.uavComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)allTaskMap.get(j).get(i)).setSn(1);
						uavTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeSA = consumeSA+payInitial[j][i];
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
							consumeSA = consumeSA+payInitial[j][i];
							continue;
						}
						uavTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeSA = consumeSA+payInitial[j][i];
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==1 && vehicleTask.get(j)!=null){
						payInitial[j][i] = payoffFunction.uavNodeMecComputing(vehicles[j], (SingleTask)allTaskMap.get(j).get(i), i, areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)allTaskMap.get(j).get(i)).setSn(3);
						mecTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeSA = consumeSA+payInitial[j][i];
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
							consumeSA = consumeSA+payInitial[j][i];
							continue;
						}
						vehicleTask.remove(j);
						vehicleTask.add(j,(SingleTask)allTaskMap.get(j).get(i));
						consumeSA = consumeSA+payInitial[j][i];
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
							consumeSA = consumeSA+payInitial[j][i];
							continue;
						}
						uavTask.add((SingleTask)allTaskMap.get(j).get(i));
						consumeSA = consumeSA+payInitial[j][i];
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
							consumeSA = consumeSA+payInitial[j][i];
							continue;
						}
						if(flag == 1){
							uavTask.add((SingleTask)allTaskMap.get(j).get(i));
							consumeSA = consumeSA+payInitial[j][i];
							continue;
						}
						vehicleTask.remove(j);
						vehicleTask.add(j,(SingleTask)allTaskMap.get(j).get(i));
						consumeSA = consumeSA+payInitial[j][i];
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
					consumeSA = consumeSA+payInitial[j][i];
					continue;
					}
				}
			}
			List listTemp = new ArrayList<>();
			listTemp.add(i);
			listTemp.add(mecTask.size());
			listTemp.add(uavTask.size());
			arrangeList.add(listTemp);
		}
		
		
		int i = countSingleVehicleTaskMax-1;
		System.out.println("未完成");
		System.out.println(unDistributeTask.size());
		List<SingleTask> unDistributeTaskOptimize = new ArrayList<>();
		List<SingleTask> unDistributeTaskOptimizeTemp =  new ArrayList<>();
		List<SingleTask> mecTaskTemp = new ArrayList<>();
		List<SingleTask> uavTaskTemp = new ArrayList<>();
		List<SingleTask> vehicleTaskTemp = new ArrayList<>();
		List<SingleTask> unDistributeTaskTemp = new ArrayList<>();
		while(unDistributeTask.size()!=0){
			i++;
			//System.out.println("时刻:"+i);
			for(int ui=0; ui<unDistributeTask.size(); ui++){
				((SingleTask)unDistributeTask.get(ui)).setWaitTime(((SingleTask)unDistributeTask.get(ui)).getWaitTime()+1);
			}
			for(int m=mecTask.size()-1; m>=0; m--){      //MEC上任务执行完成后离开
				if(i==mecTask.get(m).getTaskCi()/C.vehicleTaskUnit+mecTask.get(m).getGenerateTime()+mecTask.get(m).getWaitTime()){
					mecTask.remove(m);
				}
			}
			for(int k=uavTask.size()-1; k>=0; k--){    //无人机上任务执行完成后离开
				if(i==uavTask.get(k).getTaskCi()/C.vehicleTaskUnit+uavTask.get(k).getGenerateTime()+uavTask.get(k).getWaitTime()){
					uavTask.remove(k);
				}
			}
			for(int vi=vehicleTask.size()-1; vi>=0; vi--){
				if(vehicleTask.get(vi)!=null && i==vehicleTask.get(vi).getTaskCi()/C.vehicleTaskUnit+vehicleTask.get(vi).getGenerateTime()+vehicleTask.get(vi).getWaitTime()){
					vehicleTask.remove(vi);
					vehicleTask.add(vi, null);
				}
			}	
			double consume = Double.MAX_VALUE;
			int flagChange = 0;
			T=1000;
			while(T>Tmin){
				//System.out.println("退火"+i);
				flagChange = 0;
				double consumeTemp = 0;
				mecTaskTemp.removeAll(mecTaskTemp);
				for(int mecTaskNumber=0; mecTaskNumber<mecTask.size(); mecTaskNumber++){
					SingleTask s = new SingleTask();
					s=mecTask.get(mecTaskNumber);
					mecTaskTemp.add(s);
				}	
				uavTaskTemp.removeAll(uavTaskTemp);
				for(int uavTaskNumber=0; uavTaskNumber<uavTask.size(); uavTaskNumber++){
					SingleTask s = new SingleTask();
					s=uavTask.get(uavTaskNumber);
					uavTaskTemp.add(s);
				}
				vehicleTaskTemp.removeAll(vehicleTaskTemp);
				for(int vehicleTaskNumber=0; vehicleTaskNumber<vehicleTask.size(); vehicleTaskNumber++){
					SingleTask s = new SingleTask();
					s=vehicleTask.get(vehicleTaskNumber);
					vehicleTaskTemp.add(s);
				}
				unDistributeTaskTemp.removeAll(unDistributeTaskTemp);
				for(int unDistributeTaskNumber=0; unDistributeTaskNumber<unDistributeTask.size(); unDistributeTaskNumber++){
					SingleTask s = new SingleTask();
					s = unDistributeTask.get(unDistributeTaskNumber);
					unDistributeTaskTemp.add(s);
				}
				Collections.shuffle(unDistributeTaskTemp);
				unDistributeTaskOptimizeTemp.removeAll(unDistributeTaskOptimizeTemp);
				for(int kk = 0; kk<unDistributeTaskTemp.size(); kk++){
					SingleTask s = new SingleTask();
					s = unDistributeTaskTemp.get(kk);
					unDistributeTaskOptimizeTemp.add(s);
				}
				for(int p=unDistributeTaskTemp.size()-1; p>=0; p--){
					if(((SingleTask)unDistributeTaskTemp.get(p)).getSn() == 3){
						if(mecTaskTemp.size()==C.mecMax && uavTaskTemp.size()==1 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
							vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
							vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()==C.mecMax && uavTaskTemp.size()==0 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())!=null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(1);
							uavTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()==C.mecMax && uavTaskTemp.size()==0 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(1);
							temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
								vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
								vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							uavTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && uavTask.size()==1 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())!=null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(2);
							temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(3);
							}
							mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && uavTaskTemp.size()==1 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							int flag = 0;
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(2);
							temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
								flag = 1;
							}
							temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(3);
								mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							if(flag == 1){
								vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime());
								vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(),(SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && uavTaskTemp.size()==0 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())!=null){
							int flag = 0;
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(2);
							temp = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(1);
								flag = 1;
							}
							temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(3);
								mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							if(flag == 1){
								uavTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && uavTaskTemp.size()==0 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							int flag = 0;
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(2);
							temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
								flag = 1;
							}
							temp = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(1);	
								flag = 2;
							}
							temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(3);
								mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							if(flag == 1){
								vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
								vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							if(flag == 2){
								uavTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						continue;
					}
					if(((SingleTask)unDistributeTaskTemp.get(p)).getSn() == 2){
						if(mecTaskTemp.size()==C.mecMax && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
							vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
							vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())!=null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(2);
							mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(2);
							temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
								vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
								vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						continue;
					}
					if(((SingleTask)unDistributeTaskTemp.get(p)).getSn() == 1){
						if(mecTaskTemp.size()==C.mecMax && uavTaskTemp.size()==1 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
							vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
							vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()==C.mecMax && uavTaskTemp.size()==0 && vehicleTask.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())!=null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(1);
							uavTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()==C.mecMax && uavTaskTemp.size()==0 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(1);
							temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
								vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
								vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							uavTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && uavTaskTemp.size()==1 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())!=null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(3);
							mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && uavTaskTemp.size()==1 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(3);
							temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
								vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
								vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && uavTaskTemp.size()==0 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())!=null){
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(1);
							temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(3);
								mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							uavTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						if(mecTaskTemp.size()<C.mecMax && uavTaskTemp.size()==0 && vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
							int flag = 0;
							payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							//((SingleTask)unDistributeTaskTemp.get(p)).setSn(1);
							temp = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(0);
								flag = 1;
							}
							temp = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p), ((SingleTask)unDistributeTaskTemp.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
							if(temp<payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]){
								payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=temp;
								//((SingleTask)unDistributeTaskTemp.get(p)).setSn(3);
								mecTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							if(flag == 1){
								vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
								vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
								consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
								unDistributeTaskTemp.remove(p);
								continue;
							}
							uavTaskTemp.add((SingleTask)unDistributeTaskTemp.get(p));
							consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
							unDistributeTaskTemp.remove(p);
							continue;
						}
						continue;
					}
					if(((SingleTask)unDistributeTaskTemp.get(p)).getSn() == 0){
						if(vehicleTaskTemp.get(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()]=payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()], (SingleTask)unDistributeTaskTemp.get(p));
						vehicleTaskTemp.remove(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID());
						vehicleTaskTemp.add(((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID(),(SingleTask)unDistributeTaskTemp.get(p));
						consumeTemp = consumeTemp+payInitial[((SingleTask)unDistributeTaskTemp.get(p)).getVehicleID()][((SingleTask)unDistributeTaskTemp.get(p)).getTaskID()];
						unDistributeTaskTemp.remove(p);
						continue;
						}
					}	
				}
				
				if(consumeTemp<consume){
					consume = consumeTemp;
					flagChange = 1;
					unDistributeTaskOptimize.removeAll(unDistributeTaskOptimize);
					for(int kk = 0; kk<unDistributeTaskOptimizeTemp.size(); kk++){
						SingleTask s = new SingleTask();
						s = unDistributeTaskOptimizeTemp.get(kk);
						unDistributeTaskOptimize.add(s);
					}
				}
				if(flagChange == 1){
					unDistributeTask.removeAll(unDistributeTask);
					for(int kk=0; kk<unDistributeTaskOptimize.size(); kk++){
						SingleTask s = new SingleTask();
						s = unDistributeTaskOptimize.get(kk);
						unDistributeTask.add(s);
					}
				}
				T = (int) (T*delta);
			}
			
			
			
			
			
			for(int p=unDistributeTask.size()-1; p>=0; p--){
				if(((SingleTask)unDistributeTask.get(p)).getSn() == 3){
					if(mecTask.size()==C.mecMax && uavTask.size()==1 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())==null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.localComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p));
						((SingleTask)unDistributeTask.get(p)).setSn(0);
						vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
						vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(1);
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							flag = 0;
							continue;
						}
						if(flag == 1){
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 1){
							uavTask.add((SingleTask)unDistributeTask.get(p));
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 1){
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 2){
							uavTask.add((SingleTask)unDistributeTask.get(p));
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()]=payoffFunction.directMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(2);
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()==C.mecMax && uavTask.size()==0 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(1);
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
						unDistributeTask.remove(p);
						continue;
					}
					if(mecTask.size()<C.mecMax && uavTask.size()==1 && vehicleTask.get(((SingleTask)unDistributeTask.get(p)).getVehicleID())!=null){
						payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()] = payoffFunction.uavNodeMecComputing(vehicles[((SingleTask)unDistributeTask.get(p)).getVehicleID()], (SingleTask)unDistributeTask.get(p), ((SingleTask)unDistributeTask.get(p)).getGenerateTime(), areaGenerate.getArea(0), vehicles, allTaskMap);
						((SingleTask)unDistributeTask.get(p)).setSn(3);
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						mecTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						if(flag == 1){
							vehicleTask.remove(((SingleTask)unDistributeTask.get(p)).getVehicleID());
							vehicleTask.add(((SingleTask)unDistributeTask.get(p)).getVehicleID(),(SingleTask)unDistributeTask.get(p));
							consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
							unDistributeTask.remove(p);
							continue;
						}
						uavTask.add((SingleTask)unDistributeTask.get(p));
						consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
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
					consumeSA = consumeSA+payInitial[((SingleTask)unDistributeTask.get(p)).getVehicleID()][((SingleTask)unDistributeTask.get(p)).getTaskID()];
					unDistributeTask.remove(p);
					//consumeSA = consumeSA+payInitial[j][i];
					continue;
					}
				}
			}
			List listTemp = new ArrayList<>();
			listTemp.add(i);
			listTemp.add(mecTask.size());
			listTemp.add(uavTask.size());
			arrangeList.add(listTemp);
			
		}
		int wang = i;
		while(uavTask.size()!=0 || mecTask.size()!=0){
			List listTemp = new ArrayList<>();
			i++;
			listTemp.add(i);
			listTemp.add(mecTask.size());
			listTemp.add(uavTask.size());
			arrangeList.add(listTemp);
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
			for(int vi=vehicleTask.size()-1; vi>=0; vi--){       //车辆上任务执行完成后离开
				if(vehicleTask.get(vi)!=null && i==vehicleTask.get(vi).getTaskCi()/C.vehicleTaskUnit+vehicleTask.get(vi).getGenerateTime()+vehicleTask.get(vi).getWaitTime()){
					vehicleTask.remove(vi);
					vehicleTask.add(vi, null);
				}
			}
		}
		
		List listTemp = new ArrayList<>();
		i++;
		listTemp.add(i);
		listTemp.add(mecTask.size());
		listTemp.add(uavTask.size());
		arrangeList.add(listTemp);
		
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
		
		for(i=0; i<lengthAllTaskMap; i++){
			System.out.print("车辆:"+i+":");
			for(int j=0; j<countSingleVehicleTask[i]; j++){
				System.out.print("开始时间:"+((SingleTask)allTaskMap.get(i).get(j)).getGenerateTime()+" "+"结束时间:"+(((SingleTask)allTaskMap.get(i).get(j)).getGenerateTime()+((SingleTask)allTaskMap.get(i).get(j)).getTaskCi()/C.vehicleTaskUnit+((SingleTask)allTaskMap.get(i).get(j)).getWaitTime()));
			}
			System.out.println();
		}
		System.out.println(arrangeList);
		System.out.println(wang);
		return consumeSA;
		
	}

}
