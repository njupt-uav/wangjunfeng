package TaskModel;

import Resource.Point;

public class SingleTask {
	private int taskID;    //任务ID
	private int vehicleID;   //车辆ID
	private int Sn;      //决定任务在本地或者无人机或者边缘计算服务器上执行
	private int generateTime;     //任务生成时间
	private double taskCi;       // 完成一个任务的cpu周期数
	private double taskOi;       // 一个任务的规模大小
	private double taskDi;       //任务执行结果的大小
	private int deadTime;     //任务的执行截止时间   5秒
	private int waitTime;      //任务的等待时间
	private Point point;     //产生的任务的位置
	public SingleTask(){
		
	}
	public SingleTask(int taskID, int vehicleID, int Sn, int generateTime, int taskCi, int taskOi, int taskDi, int deadTime){
		this.taskID = taskID;
		this.vehicleID = vehicleID;
		this.Sn = Sn;
		this.generateTime = generateTime;
		this.taskCi = taskCi;
		this.taskOi = taskOi;
		this.taskDi = taskDi;
		this.deadTime = deadTime;
	}
	
	public int getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	public int getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}
	public int getSn() {
		return Sn;
	}
	public void setSn(int sn) {
		Sn = sn;
	}
	public int getGenerateTime() {
		return generateTime;
	}
	public void setGenerateTime(int generateTime) {
		this.generateTime = generateTime;
	}
	public double getTaskCi() {
		return taskCi;
	}
	public void setTaskCi(double taskCi) {
		this.taskCi = taskCi;
	}
	public double getTaskOi() {
		return taskOi;
	}
	public void setTaskOi(double taskOi) {
		this.taskOi = taskOi;
	}
	public double getTaskDi() {
		return taskDi;
	}
	public void setTaskDi(double taskDi) {
		this.taskDi = taskDi;
	}
	public int getDeadTime() {
		return deadTime;
	}
	public void setDeadTime(int deadTime) {
		this.deadTime = deadTime;
	}
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	public void setPoint(double x,double y){
		point = new Point();
		point.setX(x);
		point.setY(y);
	}
}
