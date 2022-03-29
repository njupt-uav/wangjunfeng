package ComputationModel;

import java.io.IOException;
import java.util.HashMap;

import ChannelModel.TransmissionRate;
import Resource.Area;
import Resource.Vehicle;
import TaskModel.SingleTask;
import Tool.C;

public class PayoffFunction {
	TransmissionRate transmissionRate = new TransmissionRate();
	public double localComputing(Vehicle vehicle, SingleTask singleTask){
		double time = (double)singleTask.getTaskCi()/vehicle.getCalculation();
		double energy = (double)singleTask.getTaskCi()*C.eVCPU;
		double payOff = C.proportion_t * (time+singleTask.getWaitTime()) + C.proportion_e * energy;
		return payOff;
	}
	public double directMecComputing(Vehicle vehicle, SingleTask singleTask, int t, Area area, Vehicle[] vehicles, HashMap map) throws IOException{
		double time1 = (double)singleTask.getTaskOi()/transmissionRate.rateVehicleToMec(vehicle,singleTask, t, area, vehicles, map);
		double time2 = (double)singleTask.getTaskCi()/C.FMecCpu;
		double time3 = (double)singleTask.getTaskDi()/C.RLTE;
		double time = time1 + time2 + time3;
		double energy = (double)singleTask.getTaskOi()/1024*C.eVSEND;
		double payOff = C.proportion_t * (time+singleTask.getWaitTime()) + C.proportion_e * energy;
		return payOff;
	}
	public double uavComputing(Vehicle vehicle, SingleTask singleTask, int t, Area area, Vehicle[] vehicles, HashMap map) throws IOException{
		double time1 = (double)singleTask.getTaskOi()/transmissionRate.rateVehicleToUav(vehicle, singleTask, t, area, vehicles, map);
		double time2 = (double)singleTask.getTaskCi()/C.FUavCpu;
		double time3 = (double)singleTask.getTaskDi()/C.RWIFI;
		double time = time1+time2+time3;
		double energy1 = (double)singleTask.getTaskOi()/1024*C.eVSEND;
		double energy2 = (double)singleTask.getTaskCi()*C.eUCPU;
		double energy3 = (double)singleTask.getTaskDi()/1024*C.eUSEND;
		double energy = energy1+energy2+energy3;
		double payOff = C.proportion_t * (time+singleTask.getWaitTime()) + C.proportion_e * energy;
		//System.out.println("payoff="+payOff);
		return payOff;
	}
	public double uavNodeMecComputing(Vehicle vehicle, SingleTask singleTask, int t, Area area, Vehicle[] vehicles, HashMap map) throws IOException{
		double time1 = (double)singleTask.getTaskOi()/transmissionRate.rateVehicleToUav(vehicle,singleTask, t, area, vehicles, map);
		double time2 = (double)singleTask.getTaskOi()/C.RWIFI;
		double time3 = (double)singleTask.getTaskCi()/C.FMecCpu;
		double time4 = (double)singleTask.getTaskDi()/C.RLTE;
		double time5 = (double)singleTask.getTaskDi()/C.RWIFI;
		double time = time1+time2+time3+time4+time5;
		double energy1 = (double)singleTask.getTaskOi()/1024*C.eVSEND;
		double energy2 = (double)singleTask.getTaskOi()/1024*C.eUSEND;
		double energy3 = (double)singleTask.getTaskDi()/1024*C.eUSEND;
		double energy = energy1+energy2+energy3;
		double payOff = C.proportion_t * (time+singleTask.getWaitTime()) + C.proportion_e * energy;
		return payOff;
	}
}
