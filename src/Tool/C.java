package Tool;

public class C {
	
	/*building generation*/
	public static int west_south = 0;    //西南点
	public static int west_north = 1;    //西北点
	public static int east_north = 2;    //东北点
	public static int east_south = 3;    //东南点
	
	public static int HeightType = 6;    //高度数量
	public static int[] heightSet = {30,50,100,150,200,250};     //高度集合
	
	
	/*road width*/
	public static int width = 14;   //道路的宽度
	public static int fixedDistance = 200;     //同一方向有两条道路，基础固定距离200m
	public static int space = 100;   //按照距离，同一方向的的道路距离，在基础距离之上再增加的间距
	
	/*面积种类的数量*/
	public static int areaNum = 3;
	
	
	/*道路种类数量*/
	public static int roadNum = 4;
	
	
	/*建筑物密度三种*/
	public static int buildingDensity = 3;
	/*建筑物的三种密度集合*/
	public static double[] buildingProportion = {0.1,0.15,0.2};
	
	/*建筑物的不同分布集合*/
	public static int buildingRandomDistribute = 5;
	
	public static int W = 20;  //信道带宽为20MHz
	public static int V2I = 4;   //车辆到基础设施V2I信道的通过损失指数
	public static double N0 = -0.0000001;    //背景噪声为-100dB
	public static int FU = 10;     //无人机的计算能力10GHz
	
	public static int eVCPU = 1;   //在本地执行的车辆的每个CPU周期中消耗的能量1u
	public static int eUCPU = 1;   //在无人机上执行时，每个CPU周期中消耗的能量1u
	public static double eVSEND = 25;   //一个车辆发送一个数据单元给其他设备消耗的能量25u
	public static double eUSEND = 50;   //一个无人机发送一个数据单元给其他设备消耗的能量50u
	public static double PI = 3.14159;
	
	public static int numFV = 4;   //车辆的计算能力有4个取值
	public static double[] FV = {0.5,0.7,0.8,1.0};   //车辆的计算能力    0.5,0.7,0.8,1.0GHz
	public static double[] P = {100000,120000,130000,150000};        //车辆的传输速率
	
	public static int numVehicle = 30;     //汽车的数量
	public static double vehicleSingleRoad = 3.5;    //马路的单条道宽度为3.5m
	
	public static int vehicleFirstEdgeDidtance = 20;  //初始最边上的车辆距离道路尽头距离
	
	
	public static int uavSpeed = 20;      //无人机飞行速度20m/s
	public static int fixedHight = 300;   //无人机飞行固定高度300m
	public static int radius = 100;      //无人机飞行半径为100m
	public static double FUavCpu = 10;      //无人机的计算能力10GHz
	
	public static double FMecCpu = 50;     //移动边缘计算服务器的计算能力为50GHz
	
	public static int westToEast = 0;     //车辆从西向东运行
	public static int eastToWest = 1;     //车辆从东向西运行
	public static int northToSouth = 2;   //车辆从北向南运行
	public static int southToNorth = 3;   //车辆从南向北运行
	
	
	public static int numVehicleTask = 5;    //车辆任务种类，比如：导航、视频连线、在线音乐
	
	public static double proportion_t = 0.8;       //计算决定性函数时间的占比
	public static double proportion_e = 0.2;         //计算决定性函数能量的占比
	
	public static double RLTE = 1;          //LTE接口的数据传输率100Mbps 12.5
	public static double RWIFI = 0.1;          //WiFi接口的数据传输率22Mbps 2.75
	
	public static int mecMax = 15;            //MEC上同时可以执行的任务数量
	public static double vehicleTaskUnit = 1.857;    //一个车辆任务的最小值
	
	public static double vehicleSpeed = 20;  //车辆的运行速度72km/h
	//public static int P = 10;    //车辆的传输功率
}
