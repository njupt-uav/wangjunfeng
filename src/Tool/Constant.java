package Tool;

import java.util.Random;

public class Constant {
	public int W = 20;  //信道带宽为20MHz
	public int AV2I = 4;   //车辆到基础设施V2I信道的通过损失指数
	public int N0 = -100;    //背景噪声为-100dB
	public int FU = 10;     //无人机的计算能力10GHz
	public int FM = 50;     //边缘计算服务器的计算能力50GHz
	public int eVCPU = 1;   //在本地执行的车辆的每个CPU周期中消耗的能量1u
	public int eUCPU = 1;   //在无人机上执行时，每个CPU周期中消耗的能量1u
	public int eVSEND = 25;   //一个车辆发送一个数据单元给其他设备消耗的能量25u
	public int eUSEND = 50;   //一个无人机发送一个数据单元给其他设备消耗的能量50u
	public float PI = 3.14159f;
	public static double getFV(){      //通过随机数来随机获取车辆的计算能力
		Random random = new Random();
		int rand = random.nextInt(100);
		rand = rand%4;
		System.out.println(rand);
		double FV=0;
		double[] FVCPU = {0.5,0.7,0.8,1};   //单位：GHz
		switch(rand){
			case 0:
				FV=FVCPU[0];
				break;
			case 1:
				FV=FVCPU[1];
				break;
			case 2:
				FV=FVCPU[2];
				break;
			case 3:
				FV=FVCPU[3];	
				break;
			default:
				System.out.println("随机变量生成出错!");
				break;
		}
		return FV;
	}
	/*public static void main(String[] args) {
		double FV = getFV();
		System.out.println(FV);
	}*/
	
}
