package Task;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import Resource.Building;
import Resource.Road;
import Tool.C;

public class MapGenerate {

	public static void mapGenerate() throws IOException {   //180种地图的生成，写入txt文件
		// TODO Auto-generated method stub
		AreaGenerate areaGenerate = new AreaGenerate();
		RoadGenerate roadGenerate = new RoadGenerate();
		BuildingGenerate buildingGenerate = new BuildingGenerate();
		BuildingSetGenerate buildingSetGenerate = new BuildingSetGenerate();
		String txtName;
		int roadNumTemp;
		int count=1;
		for(int i=0; i<C.areaNum ; i++){
			for(int j=0;j<C.roadNum;j++){
				roadNumTemp=j+1;
				for(int k=0; k<C.buildingDensity;k++){
					for(int m=0;m<C.buildingRandomDistribute;m++){
						txtName = (int)areaGenerate.area[i].getLength()+"_"+(int)areaGenerate.area[i].getWidth()+"_"+roadNumTemp+"_"+"road"+"_"+C.buildingProportion[k]+"_"+m+"_"+"random";
						System.out.print("第"+count++ +"张地图:");
						System.out.println(txtName);
						FileWriter writer = new FileWriter("MapSource\\"+txtName+".txt",true);
						System.out.println("地图长为:"+areaGenerate.area[i].getLength()+"  "+"地图宽为:"+areaGenerate.area[i].getWidth());
						writer.write((int)areaGenerate.area[i].getLength()+"\t"+(int)areaGenerate.area[i].getWidth());
						writer.write("\r\n");
						//System.err.println("m="+m);
						switch(j){
						case 0:
							Road road = roadGenerate.roadOneGenerate(areaGenerate.area[i]);
							//System.err.println("i="+i+" "+"j="+j);
							System.out.println("第一条道路4个点的坐标:["+road.getPoint(0).getX()+","+road.getPoint(0).getY()+"]"+" ["+road.getPoint(1).getX()+","+road.getPoint(1).getY()+"]"+" ["+road.getPoint(2).getX()+","+road.getPoint(2).getY()+"]"+" ["+road.getPoint(3).getX()+","+road.getPoint(3).getY()+"]");
							//writer.write("%%\t");
							writer.write((int)road.getPoint(0).getX()+"\t"+(int)road.getPoint(0).getY()+"\t"+(int)road.getPoint(1).getX()+"\t"+(int)road.getPoint(1).getY()+"\t"+(int)road.getPoint(2).getX()+"\t"+(int)+road.getPoint(2).getY()+"\t"+(int)road.getPoint(3).getX()+"\t"+(int)road.getPoint(3).getY());
							writer.write("\r\n");
							List list;
							Building building =new Building();
							switch(k){
							case 0:
								list = buildingSetGenerate.oneRoadBuildingSet(areaGenerate.area[i], i, C.buildingProportion[0]);
								System.out.println("大楼的4个坐标点为:");
								for(int s=0;s<list.size();s++){
									building = (Building) list.get(s);
									System.out.println("第"+s+"个大楼:["+building.getPoint(0).getX()+","+building.getPoint(0).getY()+"]"+" ["+building.getPoint(1).getX()+","+building.getPoint(1).getY()+"]"+" ["+building.getPoint(2).getX()+","+building.getPoint(2).getY()+"]"+" ["+building.getPoint(3).getX()+","+building.getPoint(3).getY()+"]");
									writer.write((int)building.getPoint(0).getX()+"\t"+(int)building.getPoint(0).getY()+"\t"+(int)building.getPoint(1).getX()+"\t"+(int)building.getPoint(1).getY()+"\t"+(int)building.getPoint(2).getX()+"\t"+(int)building.getPoint(2).getY()+"\t"+(int)building.getPoint(3).getX()+"\t"+(int)building.getPoint(3).getY());
									writer.write("\t"+(int)building.getHeight());
									writer.write("\r\n");
								}
								break;
							case 1:
								list = buildingSetGenerate.oneRoadBuildingSet(areaGenerate.area[i], i, C.buildingProportion[1]);
								System.out.println("大楼的4个坐标点为:");
								for(int s=0;s<list.size();s++){
									building = (Building) list.get(s);
									System.out.println("第"+s+"个大楼:["+building.getPoint(0).getX()+","+building.getPoint(0).getY()+"]"+" ["+building.getPoint(1).getX()+","+building.getPoint(1).getY()+"]"+" ["+building.getPoint(2).getX()+","+building.getPoint(2).getY()+"]"+" ["+building.getPoint(3).getX()+","+building.getPoint(3).getY()+"]");
									writer.write((int)building.getPoint(0).getX()+"\t"+(int)building.getPoint(0).getY()+"\t"+(int)building.getPoint(1).getX()+"\t"+(int)building.getPoint(1).getY()+"\t"+(int)building.getPoint(2).getX()+"\t"+(int)building.getPoint(2).getY()+"\t"+(int)building.getPoint(3).getX()+"\t"+(int)building.getPoint(3).getY());
									writer.write("\t"+(int)building.getHeight());
									writer.write("\r\n");
								}
								break;
							case 2:
								list = buildingSetGenerate.oneRoadBuildingSet(areaGenerate.area[i], i, C.buildingProportion[2]);
								System.out.println("大楼的4个坐标点为:");
								for(int s=0;s<list.size();s++){
									building = (Building) list.get(s);
									System.out.println("第"+s+"个大楼:["+building.getPoint(0).getX()+","+building.getPoint(0).getY()+"]"+" ["+building.getPoint(1).getX()+","+building.getPoint(1).getY()+"]"+" ["+building.getPoint(2).getX()+","+building.getPoint(2).getY()+"]"+" ["+building.getPoint(3).getX()+","+building.getPoint(3).getY()+"]");
									writer.write((int)building.getPoint(0).getX()+"\t"+(int)building.getPoint(0).getY()+"\t"+(int)building.getPoint(1).getX()+"\t"+(int)building.getPoint(1).getY()+"\t"+(int)building.getPoint(2).getX()+"\t"+(int)building.getPoint(2).getY()+"\t"+(int)building.getPoint(3).getX()+"\t"+(int)building.getPoint(3).getY());
									writer.write("\t"+(int)building.getHeight());
									writer.write("\r\n");
								}
								break;
							}
							break;
						case 1:
							Road[] road1 = roadGenerate.roadTwoGenerate(areaGenerate.area[i]);
							//System.err.println("i="+i+" "+"j="+j);
							System.out.println("第一条道路4个点的坐标:["+road1[0].getPoint(0).getX()+","+road1[0].getPoint(0).getY()+"]"+" ["+road1[0].getPoint(1).getX()+","+road1[0].getPoint(1).getY()+"]"+" ["+road1[0].getPoint(2).getX()+","+road1[0].getPoint(2).getY()+"]"+" ["+road1[0].getPoint(3).getX()+","+road1[0].getPoint(3).getY()+"]");
							System.out.println("第二条道路4个点的坐标:["+road1[1].getPoint(0).getX()+","+road1[1].getPoint(0).getY()+"]"+" ["+road1[1].getPoint(1).getX()+","+road1[1].getPoint(1).getY()+"]"+" ["+road1[1].getPoint(2).getX()+","+road1[1].getPoint(2).getY()+"]"+" ["+road1[1].getPoint(3).getX()+","+road1[1].getPoint(3).getY()+"]");
							//writer.write("%%\t");
							writer.write((int)road1[0].getPoint(0).getX()+"\t"+(int)road1[0].getPoint(0).getY()+"\t"+(int)road1[0].getPoint(1).getX()+"\t"+(int)road1[0].getPoint(1).getY()+"\t"+(int)road1[0].getPoint(2).getX()+"\t"+(int)road1[0].getPoint(2).getY()+"\t"+(int)road1[0].getPoint(3).getX()+"\t"+(int)road1[0].getPoint(3).getY());
							writer.write("\r\n");
							//writer.write("%%\t");
							writer.write((int)road1[1].getPoint(0).getX()+"\t"+(int)road1[1].getPoint(0).getY()+"\t"+(int)road1[1].getPoint(1).getX()+"\t"+(int)road1[1].getPoint(1).getY()+"\t"+(int)road1[1].getPoint(2).getX()+"\t"+(int)road1[1].getPoint(2).getY()+"\t"+(int)road1[1].getPoint(3).getX()+"\t"+(int)road1[1].getPoint(3).getY());
							writer.write("\r\n");
							List list1;
							Building building1 =new Building();
							switch(k){
							case 0:
								list1 = buildingSetGenerate.twoRoadBuildingSet(areaGenerate.area[i], i, C.buildingProportion[0]);
								System.out.println("大楼的4个坐标点为:");
								for(int s=0;s<list1.size();s++){
									building1 = (Building) list1.get(s);
									System.out.println("第"+s+"个大楼:["+building1.getPoint(0).getX()+","+building1.getPoint(0).getY()+"]"+" ["+building1.getPoint(1).getX()+","+building1.getPoint(1).getY()+"]"+" ["+building1.getPoint(2).getX()+","+building1.getPoint(2).getY()+"]"+" ["+building1.getPoint(3).getX()+","+building1.getPoint(3).getY()+"]");
									writer.write((int)building1.getPoint(0).getX()+"\t"+(int)building1.getPoint(0).getY()+"\t"+(int)building1.getPoint(1).getX()+"\t"+(int)building1.getPoint(1).getY()+"\t"+(int)building1.getPoint(2).getX()+"\t"+(int)building1.getPoint(2).getY()+"\t"+(int)building1.getPoint(3).getX()+"\t"+(int)building1.getPoint(3).getY());								
									writer.write("\t"+(int)building1.getHeight());
									writer.write("\r\n");
								}
								break;
							case 1:
								list1 = buildingSetGenerate.twoRoadBuildingSet(areaGenerate.area[i], i, C.buildingProportion[1]);
								System.out.println("大楼的4个坐标点为:");
								for(int s=0;s<list1.size();s++){
									building1 = (Building) list1.get(s);
									System.out.println("第"+s+"个大楼:["+building1.getPoint(0).getX()+","+building1.getPoint(0).getY()+"]"+" ["+building1.getPoint(1).getX()+","+building1.getPoint(1).getY()+"]"+" ["+building1.getPoint(2).getX()+","+building1.getPoint(2).getY()+"]"+" ["+building1.getPoint(3).getX()+","+building1.getPoint(3).getY()+"]");
									writer.write((int)building1.getPoint(0).getX()+"\t"+(int)building1.getPoint(0).getY()+"\t"+(int)building1.getPoint(1).getX()+"\t"+(int)building1.getPoint(1).getY()+"\t"+(int)building1.getPoint(2).getX()+"\t"+(int)building1.getPoint(2).getY()+"\t"+(int)building1.getPoint(3).getX()+"\t"+(int)building1.getPoint(3).getY());								
									writer.write("\t"+(int)building1.getHeight());
									writer.write("\r\n");
								}
								break;
							case 2:
								list1 = buildingSetGenerate.twoRoadBuildingSet(areaGenerate.area[i], i, C.buildingProportion[2]);
								System.out.println("大楼的4个坐标点为:");
								for(int s=0;s<list1.size();s++){
									building1 = (Building) list1.get(s);
									System.out.println("第"+s+"个大楼:["+building1.getPoint(0).getX()+","+building1.getPoint(0).getY()+"]"+" ["+building1.getPoint(1).getX()+","+building1.getPoint(1).getY()+"]"+" ["+building1.getPoint(2).getX()+","+building1.getPoint(2).getY()+"]"+" ["+building1.getPoint(3).getX()+","+building1.getPoint(3).getY()+"]");
									writer.write((int)building1.getPoint(0).getX()+"\t"+(int)building1.getPoint(0).getY()+"\t"+(int)building1.getPoint(1).getX()+"\t"+(int)building1.getPoint(1).getY()+"\t"+(int)building1.getPoint(2).getX()+"\t"+(int)building1.getPoint(2).getY()+"\t"+(int)building1.getPoint(3).getX()+"\t"+(int)building1.getPoint(3).getY());								
									writer.write("\t"+(int)building1.getHeight());
									writer.write("\r\n");
								}
								break;
							}
			    			break;
						case 2:
							Road[] road2 = roadGenerate.roadThreeGenerate(areaGenerate.area[i], i);
							//System.err.println("i="+i+" "+"j="+j);
							System.out.println("第一条道路4个点的坐标:["+road2[0].getPoint(0).getX()+","+road2[0].getPoint(0).getY()+"]"+" ["+road2[0].getPoint(1).getX()+","+road2[0].getPoint(1).getY()+"]"+" ["+road2[0].getPoint(2).getX()+","+road2[0].getPoint(2).getY()+"]"+" ["+road2[0].getPoint(3).getX()+","+road2[0].getPoint(3).getY()+"]");
							System.out.println("第二条道路4个点的坐标:["+road2[1].getPoint(0).getX()+","+road2[1].getPoint(0).getY()+"]"+" ["+road2[1].getPoint(1).getX()+","+road2[1].getPoint(1).getY()+"]"+" ["+road2[1].getPoint(2).getX()+","+road2[1].getPoint(2).getY()+"]"+" ["+road2[1].getPoint(3).getX()+","+road2[1].getPoint(3).getY()+"]");
							System.out.println("第三条道路4个点的坐标:["+road2[2].getPoint(0).getX()+","+road2[2].getPoint(0).getY()+"]"+" ["+road2[2].getPoint(1).getX()+","+road2[2].getPoint(1).getY()+"]"+" ["+road2[2].getPoint(2).getX()+","+road2[2].getPoint(2).getY()+"]"+" ["+road2[2].getPoint(3).getX()+","+road2[2].getPoint(3).getY()+"]");
		                    //writer.write("%%\t");				
							writer.write((int)road2[0].getPoint(0).getX()+"\t"+(int)road2[0].getPoint(0).getY()+"\t"+(int)road2[0].getPoint(1).getX()+"\t"+(int)road2[0].getPoint(1).getY()+"\t"+(int)road2[0].getPoint(2).getX()+"\t"+(int)road2[0].getPoint(2).getY()+"\t"+(int)road2[0].getPoint(3).getX()+"\t"+(int)road2[0].getPoint(3).getY());
							writer.write("\r\n");
							//writer.write("%%\t");
							writer.write((int)road2[1].getPoint(0).getX()+"\t"+(int)road2[1].getPoint(0).getY()+"\t"+(int)road2[1].getPoint(1).getX()+"\t"+(int)road2[1].getPoint(1).getY()+"\t"+(int)road2[1].getPoint(2).getX()+"\t"+(int)road2[1].getPoint(2).getY()+"\t"+(int)road2[1].getPoint(3).getX()+"\t"+(int)road2[1].getPoint(3).getY());
							writer.write("\r\n");
							//writer.write("%%\t");
							writer.write((int)road2[2].getPoint(0).getX()+"\t"+(int)road2[2].getPoint(0).getY()+"\t"+(int)road2[2].getPoint(1).getX()+"\t"+(int)road2[2].getPoint(1).getY()+"\t"+(int)road2[2].getPoint(2).getX()+"\t"+(int)road2[2].getPoint(2).getY()+"\t"+(int)road2[2].getPoint(3).getX()+"\t"+(int)road2[2].getPoint(3).getY());
							writer.write("\r\n");
							List list2;
							Building building2 =new Building();
							switch(k){
							case 0:
								list2 = buildingSetGenerate.threeRoadBuildingSet(areaGenerate.area[i], i, C.buildingProportion[0]);
								System.out.println("大楼的4个坐标点为:");
								for(int s=0;s<list2.size();s++){
									building2 = (Building) list2.get(s);
									System.out.println("第"+s+"个大楼:["+building2.getPoint(0).getX()+","+building2.getPoint(0).getY()+"]"+" ["+building2.getPoint(1).getX()+","+building2.getPoint(1).getY()+"]"+" ["+building2.getPoint(2).getX()+","+building2.getPoint(2).getY()+"]"+" ["+building2.getPoint(3).getX()+","+building2.getPoint(3).getY()+"]");
									writer.write((int)building2.getPoint(0).getX()+"\t"+(int)building2.getPoint(0).getY()+"\t"+(int)building2.getPoint(1).getX()+"\t"+(int)building2.getPoint(1).getY()+"\t"+(int)building2.getPoint(2).getX()+"\t"+(int)building2.getPoint(2).getY()+"\t"+(int)building2.getPoint(3).getX()+"\t"+(int)building2.getPoint(3).getY());
									writer.write("\t"+(int)building2.getHeight());
									writer.write("\r\n");
								}
								break;
							case 1:
								list2 = buildingSetGenerate.threeRoadBuildingSet(areaGenerate.area[i], i, C.buildingProportion[1]);
								System.out.println("大楼的4个坐标点为:");
								for(int s=0;s<list2.size();s++){
									building2 = (Building) list2.get(s);
									System.out.println("第"+s+"个大楼:["+building2.getPoint(0).getX()+","+building2.getPoint(0).getY()+"]"+" ["+building2.getPoint(1).getX()+","+building2.getPoint(1).getY()+"]"+" ["+building2.getPoint(2).getX()+","+building2.getPoint(2).getY()+"]"+" ["+building2.getPoint(3).getX()+","+building2.getPoint(3).getY()+"]");
									writer.write((int)building2.getPoint(0).getX()+"\t"+(int)building2.getPoint(0).getY()+"\t"+(int)building2.getPoint(1).getX()+"\t"+(int)building2.getPoint(1).getY()+"\t"+(int)building2.getPoint(2).getX()+"\t"+(int)building2.getPoint(2).getY()+"\t"+(int)building2.getPoint(3).getX()+"\t"+(int)building2.getPoint(3).getY());
									writer.write("\t"+(int)building2.getHeight());
									writer.write("\r\n");
								}
								break;
							case 2:
								list2 = buildingSetGenerate.threeRoadBuildingSet(areaGenerate.area[i], i, C.buildingProportion[2]);
								System.out.println("大楼的4个坐标点为:");
								for(int s=0;s<list2.size();s++){
									building2 = (Building) list2.get(s);
									System.out.println("第"+s+"个大楼:["+building2.getPoint(0).getX()+","+building2.getPoint(0).getY()+"]"+" ["+building2.getPoint(1).getX()+","+building2.getPoint(1).getY()+"]"+" ["+building2.getPoint(2).getX()+","+building2.getPoint(2).getY()+"]"+" ["+building2.getPoint(3).getX()+","+building2.getPoint(3).getY()+"]");
									writer.write((int)building2.getPoint(0).getX()+"\t"+(int)building2.getPoint(0).getY()+"\t"+(int)building2.getPoint(1).getX()+"\t"+(int)building2.getPoint(1).getY()+"\t"+(int)building2.getPoint(2).getX()+"\t"+(int)building2.getPoint(2).getY()+"\t"+(int)building2.getPoint(3).getX()+"\t"+(int)building2.getPoint(3).getY());
									writer.write("\t"+(int)building2.getHeight());
									writer.write("\r\n");
								}
								break;
							}
						    break;
						case 3:
							Road[] road3 = roadGenerate.roadFourGenerate(areaGenerate.area[i], i);
							//System.out.println("i="+i+" "+"j="+j);
							System.out.println("第一条道路4个点的坐标:["+road3[0].getPoint(0).getX()+","+road3[0].getPoint(0).getY()+"]"+" ["+road3[0].getPoint(1).getX()+","+road3[0].getPoint(1).getY()+"]"+" ["+road3[0].getPoint(2).getX()+","+road3[0].getPoint(2).getY()+"]"+" ["+road3[0].getPoint(3).getX()+","+road3[0].getPoint(3).getY()+"]");
							System.out.println("第二条道路4个点的坐标:["+road3[1].getPoint(0).getX()+","+road3[1].getPoint(0).getY()+"]"+" ["+road3[1].getPoint(1).getX()+","+road3[1].getPoint(1).getY()+"]"+" ["+road3[1].getPoint(2).getX()+","+road3[1].getPoint(2).getY()+"]"+" ["+road3[1].getPoint(3).getX()+","+road3[1].getPoint(3).getY()+"]");
							System.out.println("第三条道路4个点的坐标:["+road3[2].getPoint(0).getX()+","+road3[2].getPoint(0).getY()+"]"+" ["+road3[2].getPoint(1).getX()+","+road3[2].getPoint(1).getY()+"]"+" ["+road3[2].getPoint(2).getX()+","+road3[2].getPoint(2).getY()+"]"+" ["+road3[2].getPoint(3).getX()+","+road3[2].getPoint(3).getY()+"]");
							System.out.println("第四条道路4个点的坐标:["+road3[3].getPoint(0).getX()+","+road3[3].getPoint(0).getY()+"]"+" ["+road3[3].getPoint(1).getX()+","+road3[3].getPoint(1).getY()+"]"+" ["+road3[3].getPoint(2).getX()+","+road3[3].getPoint(2).getY()+"]"+" ["+road3[3].getPoint(3).getX()+","+road3[3].getPoint(3).getY()+"]");
							//writer.write("%%\t");
							writer.write((int)road3[0].getPoint(0).getX()+"\t"+(int)road3[0].getPoint(0).getY()+"\t"+(int)road3[0].getPoint(1).getX()+"\t"+(int)road3[0].getPoint(1).getY()+"\t"+(int)road3[0].getPoint(2).getX()+"\t"+(int)road3[0].getPoint(2).getY()+"\t"+(int)road3[0].getPoint(3).getX()+"\t"+(int)road3[0].getPoint(3).getY());
							writer.write("\r\n");
							//writer.write("%%\t");
							writer.write((int)road3[1].getPoint(0).getX()+"\t"+(int)road3[1].getPoint(0).getY()+"\t"+(int)road3[1].getPoint(1).getX()+"\t"+(int)road3[1].getPoint(1).getY()+"\t"+(int)road3[1].getPoint(2).getX()+"\t"+(int)road3[1].getPoint(2).getY()+"\t"+(int)road3[1].getPoint(3).getX()+"\t"+(int)road3[1].getPoint(3).getY());
							writer.write("\r\n");
							//writer.write("%%\t");
							writer.write((int)road3[2].getPoint(0).getX()+"\t"+(int)road3[2].getPoint(0).getY()+"\t"+(int)road3[2].getPoint(1).getX()+"\t"+(int)road3[2].getPoint(1).getY()+"\t"+(int)road3[2].getPoint(2).getX()+"\t"+(int)road3[2].getPoint(2).getY()+"\t"+(int)road3[2].getPoint(3).getX()+"\t"+(int)road3[2].getPoint(3).getY());
							writer.write("\r\n");
							//writer.write("%%\t");
							writer.write((int)road3[3].getPoint(0).getX()+"\t"+(int)road3[3].getPoint(0).getY()+"\t"+(int)road3[3].getPoint(1).getX()+"\t"+(int)road3[3].getPoint(1).getY()+"\t"+(int)road3[3].getPoint(2).getX()+"\t"+(int)road3[3].getPoint(2).getY()+"\t"+(int)road3[3].getPoint(3).getX()+"\t"+(int)road3[3].getPoint(3).getY());
							writer.write("\r\n");
							List list3;
							Building building3 =new Building();
							switch(k){
							case 0:
								list3 = buildingSetGenerate.fourRoadBuildingSet(areaGenerate.area[i], i, C.buildingProportion[0]);
								System.out.println("大楼的4个坐标点为:");
								for(int s=0;s<list3.size();s++){
									building3 = (Building) list3.get(s);
									System.out.println("第"+s+"个大楼:["+building3.getPoint(0).getX()+","+building3.getPoint(0).getY()+"]"+" ["+building3.getPoint(1).getX()+","+building3.getPoint(1).getY()+"]"+" ["+building3.getPoint(2).getX()+","+building3.getPoint(2).getY()+"]"+" ["+building3.getPoint(3).getX()+","+building3.getPoint(3).getY()+"]");
									writer.write((int)building3.getPoint(0).getX()+"\t"+(int)building3.getPoint(0).getY()+"\t"+(int)building3.getPoint(1).getX()+"\t"+(int)building3.getPoint(1).getY()+"\t"+(int)building3.getPoint(2).getX()+"\t"+(int)building3.getPoint(2).getY()+"\t"+(int)building3.getPoint(3).getX()+"\t"+(int)building3.getPoint(3).getY());
									writer.write("\t"+(int)building3.getHeight());
									writer.write("\r\n");
								}
								break;
							case 1:
								list3 = buildingSetGenerate.fourRoadBuildingSet(areaGenerate.area[i], i, C.buildingProportion[1]);
								System.out.println("大楼的4个坐标点为:");
								for(int s=0;s<list3.size();s++){
									building3 = (Building) list3.get(s);
									System.out.println("第"+s+"个大楼:["+building3.getPoint(0).getX()+","+building3.getPoint(0).getY()+"]"+" ["+building3.getPoint(1).getX()+","+building3.getPoint(1).getY()+"]"+" ["+building3.getPoint(2).getX()+","+building3.getPoint(2).getY()+"]"+" ["+building3.getPoint(3).getX()+","+building3.getPoint(3).getY()+"]");
									writer.write((int)building3.getPoint(0).getX()+"\t"+(int)building3.getPoint(0).getY()+"\t"+(int)building3.getPoint(1).getX()+"\t"+(int)building3.getPoint(1).getY()+"\t"+(int)building3.getPoint(2).getX()+"\t"+(int)building3.getPoint(2).getY()+"\t"+(int)building3.getPoint(3).getX()+"\t"+(int)building3.getPoint(3).getY());
									writer.write("\t"+(int)building3.getHeight());
									writer.write("\r\n");
								}
								break;
							case 2:
								list3 = buildingSetGenerate.fourRoadBuildingSet(areaGenerate.area[i], i, C.buildingProportion[2]);
								System.out.println("大楼的4个坐标点为:");
								for(int s=0;s<list3.size();s++){
									building3 = (Building) list3.get(s);
									System.out.println("第"+s+"个大楼:["+building3.getPoint(0).getX()+","+building3.getPoint(0).getY()+"]"+" ["+building3.getPoint(1).getX()+","+building3.getPoint(1).getY()+"]"+" ["+building3.getPoint(2).getX()+","+building3.getPoint(2).getY()+"]"+" ["+building3.getPoint(3).getX()+","+building3.getPoint(3).getY()+"]");
									writer.write((int)building3.getPoint(0).getX()+"\t"+(int)building3.getPoint(0).getY()+"\t"+(int)building3.getPoint(1).getX()+"\t"+(int)building3.getPoint(1).getY()+"\t"+(int)building3.getPoint(2).getX()+"\t"+(int)building3.getPoint(2).getY()+"\t"+(int)building3.getPoint(3).getX()+"\t"+(int)building3.getPoint(3).getY());
									writer.write("\t"+(int)building3.getHeight());
									writer.write("\r\n");
								}
								break;
							}
							break;
						}
						writer.close();
					}
				}
			}
		}
		
	}
	public static void main(String[] args) throws IOException {
		mapGenerate();
	}

	
}