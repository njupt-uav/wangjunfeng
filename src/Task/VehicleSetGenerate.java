package Task;
import java.util.*;
import java.util.Random;
import Resource.*;
import Tool.C;
public class VehicleSetGenerate {
	public static Vehicle[] oneRoadVehicleSet(Area area){
		Vehicle[] vehicle = new Vehicle[C.numVehicle];
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		RoadGenerate roadGenerate = new RoadGenerate();
		Road road = roadGenerate.roadOneGenerate(area);
		Random random = new Random();
		int rand;
		int[] randVehicle = new int[4];
		int i;
		int[] temp = new int[4];
		for(int circul = 0; circul<10000; circul++){
			int sum = 0, sumReal = 0;
			for(i=0; i<4; i++){
				temp[i] = random.nextInt(C.numVehicle+1);
				sum+=temp[i];
			}
			double coe = (double) (C.numVehicle+1)/sum;
			for(i=0; i<4; i++){
				temp[i] = (int) (temp[i]*coe);
				//System.out.println(temp[i]);
				sumReal+=temp[i];
			}
			if(sumReal<=C.numVehicle){
				break;
			}
		}
		randVehicle[0] = temp[0];
		randVehicle[1] = randVehicle[0]+temp[1];
		randVehicle[2] = randVehicle[1]+temp[2];
		randVehicle[3] = C.numVehicle;
		/*System.out.println(randVehicle[0]);
		System.out.println(randVehicle[1]);
		System.out.println(randVehicle[2]);
		System.out.println(randVehicle[3]);*/
		for(i=0; i<randVehicle[0]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road.getPoint(1).getY()+C.vehicleSingleRoad/2);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.westToEast);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[0]; i<randVehicle[1]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road.getPoint(1).getY()+C.vehicleSingleRoad/2+C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.westToEast);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[1]; i<randVehicle[2]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road.getPoint(1).getY()+C.vehicleSingleRoad/2+2*C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.eastToWest);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[2]; i<randVehicle[3]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road.getPoint(1).getY()+C.vehicleSingleRoad/2+3*C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.eastToWest);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		return vehicle;
	}
	
	public static Vehicle[] twoRoadVehicleSet(Area area){
		Vehicle[] vehicle = new Vehicle[C.numVehicle];
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		RoadGenerate roadGenerate = new RoadGenerate();
		Road[] road = roadGenerate.roadTwoGenerate(area);
		Random random = new Random();
		int rand;
		int[] randVehicle = new int[8];
		int i;
		int[] temp = new int[8];
		for(int circul = 0; circul<10000; circul++){
			int sum = 0, sumReal = 0;
			for(i=0; i<8; i++){
				temp[i] = random.nextInt(C.numVehicle+1);
				sum+=temp[i];
			}
			double coe = (double) (C.numVehicle+2)/sum;
			for(i=0; i<8; i++){
				temp[i] = (int) (temp[i]*coe);
				//System.out.println(temp[i]);
				sumReal+=temp[i];
			}
			if(sumReal<=C.numVehicle){
				break;
			}
		}
		randVehicle[0] = temp[0];
		randVehicle[1] = randVehicle[0]+temp[1];
		randVehicle[2] = randVehicle[1]+temp[2];
		randVehicle[3] = randVehicle[2]+temp[3];
		randVehicle[4] = randVehicle[3]+temp[4];
		randVehicle[5] = randVehicle[4]+temp[5];
		randVehicle[6] = randVehicle[5]+temp[6];
		randVehicle[7] = C.numVehicle;
		/*System.out.println(randVehicle[0]);
		System.out.println(randVehicle[1]);
		System.out.println(randVehicle[2]);
		System.out.println(randVehicle[3]);
		System.out.println(randVehicle[4]);
		System.out.println(randVehicle[5]);
		System.out.println(randVehicle[6]);
		System.out.println(randVehicle[7]);*/
		for(i=0; i<randVehicle[0]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[0].getPoint(1).getY()+C.vehicleSingleRoad/2);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.westToEast);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[0]; i<randVehicle[1]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[0].getPoint(1).getY()+C.vehicleSingleRoad/2+C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.westToEast);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[1]; i<randVehicle[2]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[0].getPoint(1).getY()+C.vehicleSingleRoad/2+2*C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.eastToWest);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[2]; i<randVehicle[3]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[0].getPoint(1).getY()+C.vehicleSingleRoad/2+3*C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.eastToWest);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[3]; i<randVehicle[4]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getWidth());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(road[1].getPoint(1).getX()+C.vehicleSingleRoad/2, rand);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.northToSouth);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV);
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[4]; i<randVehicle[5]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getWidth());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(road[1].getPoint(1).getX()+C.vehicleSingleRoad/2+C.vehicleSingleRoad, rand);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.northToSouth);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV);
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[5]; i<randVehicle[6]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getWidth());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(road[1].getPoint(1).getX()+C.vehicleSingleRoad/2+2*C.vehicleSingleRoad, rand);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.southToNorth);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV);
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[6]; i<randVehicle[7]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getWidth());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(road[1].getPoint(1).getX()+C.vehicleSingleRoad/2+3*C.vehicleSingleRoad, rand);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.southToNorth);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV);
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		return vehicle;
	} 
	
	public static Vehicle[] threeRoadVehicleSet(Area area , int index){
		Vehicle[] vehicle = new Vehicle[C.numVehicle];
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		RoadGenerate roadGenerate = new RoadGenerate();
		Road[] road = roadGenerate.roadThreeGenerate(area,index);
		Random random = new Random();
		int rand;
		int[] randVehicle = new int[12];
		int i;
		int[] temp = new int[12];
		for(int circul = 0; circul<10000; circul++){
			int sum = 0, sumReal = 0;
			for(i=0; i<12; i++){
				temp[i] = random.nextInt(C.numVehicle+1);
				sum+=temp[i];
			}
			double coe = (double) (C.numVehicle+3)/sum;
			for(i=0; i<12; i++){
				temp[i] = (int) (temp[i]*coe);
				//System.out.println(temp[i]);
				sumReal+=temp[i];
			}
			if(sumReal<=C.numVehicle){
				break;
			}
		}
		randVehicle[0] = temp[0];
		randVehicle[1] = randVehicle[0]+temp[1];
		randVehicle[2] = randVehicle[1]+temp[2];
		randVehicle[3] = randVehicle[2]+temp[3];
		randVehicle[4] = randVehicle[3]+temp[4];
		randVehicle[5] = randVehicle[4]+temp[5];
		randVehicle[6] = randVehicle[5]+temp[6];
		randVehicle[7] = randVehicle[6]+temp[7];
		randVehicle[8] = randVehicle[7]+temp[8];
		randVehicle[9] = randVehicle[8]+temp[9];
		randVehicle[10] = randVehicle[9]+temp[10];
		randVehicle[11] = C.numVehicle;
		/*System.out.println(randVehicle[0]);
		System.out.println(randVehicle[1]);
		System.out.println(randVehicle[2]);
		System.out.println(randVehicle[3]);
		System.out.println(randVehicle[4]);
		System.out.println(randVehicle[5]);
		System.out.println(randVehicle[6]);
		System.out.println(randVehicle[7]);
		System.out.println(randVehicle[8]);
		System.out.println(randVehicle[9]);
		System.out.println(randVehicle[10]);
		System.out.println(randVehicle[11]);*/
		for(i=0; i<randVehicle[0]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[0].getPoint(1).getY()+C.vehicleSingleRoad/2);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.westToEast);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[0]; i<randVehicle[1]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[0].getPoint(1).getY()+C.vehicleSingleRoad/2+C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.westToEast);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[1]; i<randVehicle[2]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[0].getPoint(1).getY()+C.vehicleSingleRoad/2+2*C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.eastToWest);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[2]; i<randVehicle[3]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[0].getPoint(1).getY()+C.vehicleSingleRoad/2+3*C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.eastToWest);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[3]; i<randVehicle[4]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[1].getPoint(1).getY()+C.vehicleSingleRoad/2);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.westToEast);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[4]; i<randVehicle[5]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[1].getPoint(1).getY()+C.vehicleSingleRoad/2+C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.westToEast);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[5]; i<randVehicle[6]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[1].getPoint(1).getY()+C.vehicleSingleRoad/2+2*C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.eastToWest);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[6]; i<randVehicle[7]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[1].getPoint(1).getY()+C.vehicleSingleRoad/2+3*C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.eastToWest);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		
		for(i=randVehicle[7]; i<randVehicle[8]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getWidth());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(road[2].getPoint(1).getX()+C.vehicleSingleRoad/2, rand);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.northToSouth);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV);
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[8]; i<randVehicle[9]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getWidth());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(road[2].getPoint(1).getX()+C.vehicleSingleRoad/2+C.vehicleSingleRoad, rand);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.northToSouth);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV);
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[9]; i<randVehicle[10]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getWidth());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(road[2].getPoint(1).getX()+C.vehicleSingleRoad/2+2*C.vehicleSingleRoad, rand);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.southToNorth);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV);
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[10]; i<randVehicle[11]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getWidth());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(road[2].getPoint(1).getX()+C.vehicleSingleRoad/2+3*C.vehicleSingleRoad, rand);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.southToNorth);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV);
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		return vehicle;
	} 
	
	public static Vehicle[] fourRoadVehicleSet(Area area , int index){
		Vehicle[] vehicle = new Vehicle[C.numVehicle];
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		RoadGenerate roadGenerate = new RoadGenerate();
		Road[] road = roadGenerate.roadFourGenerate(area,index);
		Random random = new Random();
		int rand;
		int[] randVehicle = new int[16];
		int i;
		int[] temp = new int[16];
		for(int circul = 0; circul<10000; circul++){
			int sum = 0, sumReal = 0;
			for(i=0; i<16; i++){
				temp[i] = random.nextInt(C.numVehicle+1);
				sum+=temp[i];
			}
			double coe = (double) (C.numVehicle+4)/sum;
			for(i=0; i<16; i++){
				temp[i] = (int) (temp[i]*coe);
				//System.out.println(temp[i]);
				sumReal+=temp[i];
			}
			if(sumReal<=C.numVehicle){
				break;
			}
		}
		randVehicle[0] = temp[0];
		randVehicle[1] = randVehicle[0]+temp[1];
		randVehicle[2] = randVehicle[1]+temp[2];
		randVehicle[3] = randVehicle[2]+temp[3];
		randVehicle[4] = randVehicle[3]+temp[4];
		randVehicle[5] = randVehicle[4]+temp[5];
		randVehicle[6] = randVehicle[5]+temp[6];
		randVehicle[7] = randVehicle[6]+temp[7];
		randVehicle[8] = randVehicle[7]+temp[8];
		randVehicle[9] = randVehicle[8]+temp[9];
		randVehicle[10] = randVehicle[9]+temp[10];
		randVehicle[11] = randVehicle[10]+temp[11];
		randVehicle[12] = randVehicle[11]+temp[12];
		randVehicle[13] = randVehicle[12]+temp[13];
		randVehicle[14] = randVehicle[13]+temp[14];
		randVehicle[15] = C.numVehicle;
		/*System.out.println(randVehicle[0]);
		System.out.println(randVehicle[1]);
		System.out.println(randVehicle[2]);
		System.out.println(randVehicle[3]);
		System.out.println(randVehicle[4]);
		System.out.println(randVehicle[5]);
		System.out.println(randVehicle[6]);
		System.out.println(randVehicle[7]);
		System.out.println(randVehicle[8]);
		System.out.println(randVehicle[9]);
		System.out.println(randVehicle[10]);
		System.out.println(randVehicle[11]);
		System.out.println(randVehicle[12]);
		System.out.println(randVehicle[13]);
		System.out.println(randVehicle[14]);
		System.out.println(randVehicle[15]);*/
		for(i=0; i<randVehicle[0]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[0].getPoint(1).getY()+C.vehicleSingleRoad/2);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.westToEast);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[0]; i<randVehicle[1]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[0].getPoint(1).getY()+C.vehicleSingleRoad/2+C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.westToEast);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[1]; i<randVehicle[2]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[0].getPoint(1).getY()+C.vehicleSingleRoad/2+2*C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.eastToWest);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[2]; i<randVehicle[3]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[0].getPoint(1).getY()+C.vehicleSingleRoad/2+3*C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.eastToWest);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[3]; i<randVehicle[4]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[1].getPoint(1).getY()+C.vehicleSingleRoad/2);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.westToEast);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[4]; i<randVehicle[5]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[1].getPoint(1).getY()+C.vehicleSingleRoad/2+C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.westToEast);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[5]; i<randVehicle[6]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[1].getPoint(1).getY()+C.vehicleSingleRoad/2+2*C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.eastToWest);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[6]; i<randVehicle[7]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getLength());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(rand, road[1].getPoint(1).getY()+C.vehicleSingleRoad/2+3*C.vehicleSingleRoad);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.eastToWest);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV); 
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		
		for(i=randVehicle[7]; i<randVehicle[8]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getWidth());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(road[2].getPoint(1).getX()+C.vehicleSingleRoad/2, rand);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.northToSouth);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV);
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[8]; i<randVehicle[9]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getWidth());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(road[2].getPoint(1).getX()+C.vehicleSingleRoad/2+C.vehicleSingleRoad, rand);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.northToSouth);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV);
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[9]; i<randVehicle[10]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getWidth());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(road[2].getPoint(1).getX()+C.vehicleSingleRoad/2+2*C.vehicleSingleRoad, rand);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.southToNorth);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV);
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[10]; i<randVehicle[11]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getWidth());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(road[2].getPoint(1).getX()+C.vehicleSingleRoad/2+3*C.vehicleSingleRoad, rand);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.southToNorth);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV);
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		
		for(i=randVehicle[11]; i<randVehicle[12]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getWidth());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(road[3].getPoint(1).getX()+C.vehicleSingleRoad/2, rand);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.northToSouth);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV);
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[12]; i<randVehicle[13]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getWidth());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(road[3].getPoint(1).getX()+C.vehicleSingleRoad/2+C.vehicleSingleRoad, rand);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.northToSouth);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV);
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[13]; i<randVehicle[14]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getWidth());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(road[3].getPoint(1).getX()+C.vehicleSingleRoad/2+2*C.vehicleSingleRoad, rand);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.southToNorth);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV);
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		for(i=randVehicle[14]; i<randVehicle[15]; i++){
			vehicle[i] = new Vehicle();
			rand = random.nextInt((int) area.getWidth());
			if(map.containsValue(rand)){
				i--;
			}
			else{
				map.put(i, rand);
				vehicle[i].setPoint(road[3].getPoint(1).getX()+C.vehicleSingleRoad/2+3*C.vehicleSingleRoad, rand);
				vehicle[i].setID(i);
				vehicle[i].setDir(C.southToNorth);
				vehicle[i].setTime(0);
				rand = random.nextInt(C.numFV);
				vehicle[i].setCalculation(C.FV[rand]);
				vehicle[i].setP(C.P[rand]);
			}
		}
		map.clear();
		return vehicle;
	} 
	
	
	
/*	public static void main(String[] args) {
		AreaGenerate areaGenerate = new AreaGenerate();
		Vehicle[] vehicle = fourRoadVehicleSet(areaGenerate.area[0],0);
		for(int i=0;i<vehicle.length;i++){
			System.out.println(vehicle[i].getPoint().getX()+"     "+vehicle[i].getPoint().getY()+"   "+vehicle[i].getCalculation());
			
		}
	}*/
}
