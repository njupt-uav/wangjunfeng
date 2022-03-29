package City;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.RandomAccess;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Resource.Area;
import Resource.Building;
import Resource.Road;
import Resource.Vehicle;
import Task.AreaGenerate;
import Task.VehicleSetGenerate;
import Tool.C;

public class MapReadGenerate {
	public static void mapReadGenerate() throws IOException{
		String txtName;
				//"MapSource\\800_800_1_road_0.1_0_random.txt";
		AreaGenerate areaGenerate = new AreaGenerate();
		
		VehicleSetGenerate vehicleSetGenerate = new VehicleSetGenerate();   //车辆生成
		
		FileReader fr;
		BufferedReader br;
		int count = 0;
		int buildingNum;
		String s = null; 
		String[] strs;
		for(int i=0;i<C.areaNum;i++){
			for(int j=1;j<=C.roadNum;j++){
				for(int k=0;k<C.buildingDensity;k++){
					for(int m=0;m<C.buildingRandomDistribute;m++){
						txtName = "MapSource\\"+(int)areaGenerate.getArea(i).getLength()+"_"+(int)areaGenerate.getArea(i).getWidth()+"_"+j+"_"+"road"+"_"+C.buildingProportion[k]+"_"+m+"_"+"random.txt";
						fr = new FileReader(txtName);
						br = new BufferedReader(fr);
						count = 0;
						RandomAccessFile file = new RandomAccessFile(new File(txtName),"r");
						while(file.readLine()!=null){ 
							count++;
						}
						file.close();
						switch(j){
						case 1:
							buildingNum = count - 2;
							Map map = new Map();
							map.area = new Area();
							s=br.readLine();
							strs= s.split("\t");
							map.area.setLength(Double.parseDouble(strs[0]));
							map.area.setWidth(Double.parseDouble(strs[1]));
							s=br.readLine();
							strs = s.split("\t");
							map.road = new Road[1];
							map.road[0] = new Road();
							map.road[0].setPoint(0, Double.parseDouble(strs[0]), Double.parseDouble(strs[1]));
							map.road[0].setPoint(1, Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
							map.road[0].setPoint(2, Double.parseDouble(strs[4]), Double.parseDouble(strs[5]));
							map.road[0].setPoint(3, Double.parseDouble(strs[6]), Double.parseDouble(strs[7]));
							map.building = new Building[buildingNum];
							for(int bi=0;bi<buildingNum;bi++){
								s = br.readLine();
								strs = s.split("\t");
								map.building[bi] = new Building();
								map.building[bi].setPoint(0, Double.parseDouble(strs[0]), Double.parseDouble(strs[1]));
								map.building[bi].setPoint(1, Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
								map.building[bi].setPoint(2, Double.parseDouble(strs[4]), Double.parseDouble(strs[5]));
								map.building[bi].setPoint(3, Double.parseDouble(strs[6]), Double.parseDouble(strs[7]));
								map.building[bi].setHeight(Double.parseDouble(strs[8]));
							}
								//System.out.println(strs[0]+"  "+strs[1]+"  "+strs[2]+"  "+strs[3]+"  "+strs[4]+"  "+strs[5]+"  "+strs[6]+"  "+strs[7]+"  "+strs[8]);
								try {
						        	//File file = new File("‪D:\\javaWeb\\Hello\\in.jpg");
									BufferedImage buffImg = null;
									if(i==0){
										buffImg = ImageIO.read(new File("CityMap\\800.jpg"));
									}
									if(i==1){
										buffImg = ImageIO.read(new File("CityMap\\1000.jpg"));
									}
									if(i==2){
										buffImg = ImageIO.read(new File("CityMap\\1200.jpg"));
									}
							        Graphics2D g = (Graphics2D) buffImg.getGraphics();
							 
							        //设置抗锯齿，防止图片或文字模糊
							        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						 
						            //绘制图片
						            if(i==0){
						            	ImageIcon imgIcon = new ImageIcon("800.jpg");
						            	Image img = imgIcon.getImage();
						            	g.drawImage(img,0,0,null);
						            }
						            if(i==1){
						            	ImageIcon imgIcon = new ImageIcon("1000.jpg");
						            	Image img = imgIcon.getImage();
						            	g.drawImage(img,0,0,null);
						            }
						            if(i==2){
						            	ImageIcon imgIcon = new ImageIcon("1200.jpg");
						            	Image img = imgIcon.getImage();
						            	g.drawImage(img,0,0,null);
						            }
						            g.setColor(Color.black);
						            g.drawLine((int)map.road[0].getPoint(0).getX(), (int)map.road[0].getPoint(0).getY(), (int)map.road[0].getPoint(3).getX(), (int)map.road[0].getPoint(3).getY());
						            g.drawLine((int)map.road[0].getPoint(1).getX(), (int)map.road[0].getPoint(1).getY(), (int)map.road[0].getPoint(2).getX(), (int)map.road[0].getPoint(2).getY());						            
						            for(int bj=0;bj<buildingNum;bj++){
						            	g.drawLine((int)map.building[bj].getPoint(0).getX(), (int)map.building[bj].getPoint(0).getY(), (int)map.building[bj].getPoint(3).getX(), (int)map.building[bj].getPoint(3).getY());
						            	g.drawLine((int)map.building[bj].getPoint(0).getX(), (int)map.building[bj].getPoint(0).getY(), (int)map.building[bj].getPoint(1).getX(), (int)map.building[bj].getPoint(1).getY());
						            	g.drawLine((int)map.building[bj].getPoint(1).getX(), (int)map.building[bj].getPoint(1).getY(), (int)map.building[bj].getPoint(2).getX(), (int)map.building[bj].getPoint(2).getY());
						            	g.drawLine((int)map.building[bj].getPoint(2).getX(), (int)map.building[bj].getPoint(2).getY(), (int)map.building[bj].getPoint(3).getX(), (int)map.building[bj].getPoint(3).getY());
						            	if(map.building[bj].getHeight()==C.heightSet[0]){
						            		g.setColor(new Color(0,10,10));
						            	}
						            	if(map.building[bj].getHeight()==C.heightSet[1]){
						            		g.setColor(new Color(0,50,50));
						            	}
						            	if(map.building[bj].getHeight()==C.heightSet[2]){
						            		g.setColor(new Color(0,100,100));
						            	}
						            	if(map.building[bj].getHeight()==C.heightSet[3]){
						            		g.setColor(new Color(0,150,150));
						            	}
						            	if(map.building[bj].getHeight()==C.heightSet[4]){
						            		g.setColor(new Color(0,200,200));
						            	}
						            	if(map.building[bj].getHeight()==C.heightSet[5]){
						            		g.setColor(new Color(0,255,255));
						            	}
						            	g.fillRect((int)map.building[bj].getPoint(0).getX(), (int)map.building[bj].getPoint(0).getY(), (int)map.building[bj].length, (int)map.building[bj].width);
						            }
						            
						            Vehicle[] vehicle = vehicleSetGenerate.oneRoadVehicleSet(areaGenerate.getArea(i));
						            g.setColor(new Color(0,0,0));
						            for(int vi=0; vi<vehicle.length; vi++){
						            	g.drawLine((int)vehicle[vi].getPoint().getX(), (int)vehicle[vi].getPoint().getY(), (int)vehicle[vi].getPoint().getX(), (int)vehicle[vi].getPoint().getY());
						            }
						            
						    
						            
						            ImageIO.write(buffImg,"jpg", new File("CityMap\\"+txtName+".jpg"));
						        } catch (Exception e) {
						            e.printStackTrace();
						        }
							
							break;
							
						case 2:
							buildingNum = count - 3;
							Map map1 = new Map();
							map1.area = new Area();
							s=br.readLine();
							strs= s.split("\t");
							map1.area.setLength(Double.parseDouble(strs[0]));
							map1.area.setWidth(Double.parseDouble(strs[1]));
							s=br.readLine();
							strs = s.split("\t");
							map1.road = new Road[2];
							map1.road[0] = new Road();
							map1.road[0].setPoint(0, Double.parseDouble(strs[0]), Double.parseDouble(strs[1]));
							map1.road[0].setPoint(1, Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
							map1.road[0].setPoint(2, Double.parseDouble(strs[4]), Double.parseDouble(strs[5]));
							map1.road[0].setPoint(3, Double.parseDouble(strs[6]), Double.parseDouble(strs[7]));
							
							s=br.readLine();
							strs = s.split("\t");
							map1.road[1] = new Road();
							map1.road[1].setPoint(0, Double.parseDouble(strs[0]), Double.parseDouble(strs[1]));
							map1.road[1].setPoint(1, Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
							map1.road[1].setPoint(2, Double.parseDouble(strs[4]), Double.parseDouble(strs[5]));
							map1.road[1].setPoint(3, Double.parseDouble(strs[6]), Double.parseDouble(strs[7]));
							
							map1.building = new Building[buildingNum];
							for(int bi=0;bi<buildingNum;bi++){
								s = br.readLine();
								strs = s.split("\t");
								map1.building[bi] = new Building();
								map1.building[bi].setPoint(0, Double.parseDouble(strs[0]), Double.parseDouble(strs[1]));
								map1.building[bi].setPoint(1, Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
								map1.building[bi].setPoint(2, Double.parseDouble(strs[4]), Double.parseDouble(strs[5]));
								map1.building[bi].setPoint(3, Double.parseDouble(strs[6]), Double.parseDouble(strs[7]));
								map1.building[bi].setHeight(Double.parseDouble(strs[8]));
							}
								//System.out.println(strs[0]+"  "+strs[1]+"  "+strs[2]+"  "+strs[3]+"  "+strs[4]+"  "+strs[5]+"  "+strs[6]+"  "+strs[7]+"  "+strs[8]);
								try {
						        	//File file = new File("‪D:\\javaWeb\\Hello\\in.jpg");
									BufferedImage buffImg = null;
									if(i==0){
										buffImg = ImageIO.read(new File("CityMap\\800.jpg"));
									}
									if(i==1){
										buffImg = ImageIO.read(new File("CityMap\\1000.jpg"));
									}
									if(i==2){
										buffImg = ImageIO.read(new File("CityMap\\1200.jpg"));
									}
							        Graphics2D g = (Graphics2D) buffImg.getGraphics();
							 
							        //设置抗锯齿，防止图片或文字模糊
							        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						 
						            //绘制图片
						            if(i==0){
						            	ImageIcon imgIcon = new ImageIcon("800.jpg");
						            	Image img = imgIcon.getImage();
						            	g.drawImage(img,0,0,null);
						            }
						            if(i==1){
						            	ImageIcon imgIcon = new ImageIcon("1000.jpg");
						            	Image img = imgIcon.getImage();
						            	g.drawImage(img,0,0,null);
						            }
						            if(i==2){
						            	ImageIcon imgIcon = new ImageIcon("1200.jpg");
						            	Image img = imgIcon.getImage();
						            	g.drawImage(img,0,0,null);
						            }
						            g.setColor(Color.black);
						            g.drawLine((int)map1.road[0].getPoint(0).getX(), (int)map1.road[0].getPoint(0).getY(), (int)map1.road[0].getPoint(3).getX(), (int)map1.road[0].getPoint(3).getY());
						            g.drawLine((int)map1.road[0].getPoint(1).getX(), (int)map1.road[0].getPoint(1).getY(), (int)map1.road[0].getPoint(2).getX(), (int)map1.road[0].getPoint(2).getY());	
						            g.drawLine((int)map1.road[1].getPoint(0).getX(), (int)map1.road[1].getPoint(0).getY(), (int)map1.road[1].getPoint(1).getX(), (int)map1.road[1].getPoint(1).getY());
						            g.drawLine((int)map1.road[1].getPoint(3).getX(), (int)map1.road[1].getPoint(3).getY(), (int)map1.road[1].getPoint(2).getX(), (int)map1.road[1].getPoint(2).getY());	
						            for(int bj=0;bj<buildingNum;bj++){
						            	g.drawLine((int)map1.building[bj].getPoint(0).getX(), (int)map1.building[bj].getPoint(0).getY(), (int)map1.building[bj].getPoint(3).getX(), (int)map1.building[bj].getPoint(3).getY());
						            	g.drawLine((int)map1.building[bj].getPoint(0).getX(), (int)map1.building[bj].getPoint(0).getY(), (int)map1.building[bj].getPoint(1).getX(), (int)map1.building[bj].getPoint(1).getY());
						            	g.drawLine((int)map1.building[bj].getPoint(1).getX(), (int)map1.building[bj].getPoint(1).getY(), (int)map1.building[bj].getPoint(2).getX(), (int)map1.building[bj].getPoint(2).getY());
						            	g.drawLine((int)map1.building[bj].getPoint(2).getX(), (int)map1.building[bj].getPoint(2).getY(), (int)map1.building[bj].getPoint(3).getX(), (int)map1.building[bj].getPoint(3).getY());
						            	if(map1.building[bj].getHeight()==C.heightSet[0]){
						            		g.setColor(new Color(0,10,10));
						            	}
						            	if(map1.building[bj].getHeight()==C.heightSet[1]){
						            		g.setColor(new Color(0,50,50));
						            	}
						            	if(map1.building[bj].getHeight()==C.heightSet[2]){
						            		g.setColor(new Color(0,100,100));
						            	}
						            	if(map1.building[bj].getHeight()==C.heightSet[3]){
						            		g.setColor(new Color(0,150,150));
						            	}
						            	if(map1.building[bj].getHeight()==C.heightSet[4]){
						            		g.setColor(new Color(0,200,200));
						            	}
						            	if(map1.building[bj].getHeight()==C.heightSet[5]){
						            		g.setColor(new Color(0,255,255));
						            	}						            
						            	g.fillRect((int)map1.building[bj].getPoint(0).getX(), (int)map1.building[bj].getPoint(0).getY(), (int)map1.building[bj].length, (int)map1.building[bj].width);
						            }	
						            
						            Vehicle[] vehicle = vehicleSetGenerate.twoRoadVehicleSet(areaGenerate.getArea(i));
						            g.setColor(new Color(0,0,0));
						            for(int vi=0; vi<vehicle.length; vi++){
						            	g.drawLine((int)vehicle[vi].getPoint().getX(), (int)vehicle[vi].getPoint().getY(), (int)vehicle[vi].getPoint().getX(), (int)vehicle[vi].getPoint().getY());
						            }
						            
						            
						            ImageIO.write(buffImg,"jpg", new File("CityMap\\"+txtName+".jpg"));
						        } catch (Exception e) {
						            e.printStackTrace();
						        }
							
							break;
						case 3:
							buildingNum = count - 4;
							Map map2 = new Map();
							map2.area = new Area();
							//String s2 = null;
							//s2 = null;
							s=br.readLine();
							//String[] strs2;
							strs= s.split("\t");
							map2.area.setLength(Double.parseDouble(strs[0]));
							map2.area.setWidth(Double.parseDouble(strs[1]));
							s=br.readLine();
							strs = s.split("\t");
							map2.road = new Road[3];
							map2.road[0] = new Road();
							map2.road[0].setPoint(0, Double.parseDouble(strs[0]), Double.parseDouble(strs[1]));
							map2.road[0].setPoint(1, Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
							map2.road[0].setPoint(2, Double.parseDouble(strs[4]), Double.parseDouble(strs[5]));
							map2.road[0].setPoint(3, Double.parseDouble(strs[6]), Double.parseDouble(strs[7]));
							
							s=br.readLine();
							strs = s.split("\t");
							map2.road[1] = new Road();
							map2.road[1].setPoint(0, Double.parseDouble(strs[0]), Double.parseDouble(strs[1]));
							map2.road[1].setPoint(1, Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
							map2.road[1].setPoint(2, Double.parseDouble(strs[4]), Double.parseDouble(strs[5]));
							map2.road[1].setPoint(3, Double.parseDouble(strs[6]), Double.parseDouble(strs[7]));
							
							s=br.readLine();
							strs = s.split("\t");
							map2.road[2] = new Road();
							map2.road[2].setPoint(0, Double.parseDouble(strs[0]), Double.parseDouble(strs[1]));
							map2.road[2].setPoint(1, Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
							map2.road[2].setPoint(2, Double.parseDouble(strs[4]), Double.parseDouble(strs[5]));
							map2.road[2].setPoint(3, Double.parseDouble(strs[6]), Double.parseDouble(strs[7]));
							
							map2.building = new Building[buildingNum];
							for(int bi=0;bi<buildingNum;bi++){
								s = br.readLine();
								strs = s.split("\t");
								map2.building[bi] = new Building();
								map2.building[bi].setPoint(0, Double.parseDouble(strs[0]), Double.parseDouble(strs[1]));
								map2.building[bi].setPoint(1, Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
								map2.building[bi].setPoint(2, Double.parseDouble(strs[4]), Double.parseDouble(strs[5]));
								map2.building[bi].setPoint(3, Double.parseDouble(strs[6]), Double.parseDouble(strs[7]));
								map2.building[bi].setHeight(Double.parseDouble(strs[8]));
							}
								//System.out.println(strs[0]+"  "+strs[1]+"  "+strs[2]+"  "+strs[3]+"  "+strs[4]+"  "+strs[5]+"  "+strs[6]+"  "+strs[7]+"  "+strs[8]);
								try {
						        	//File file = new File("‪D:\\javaWeb\\Hello\\in.jpg");
									BufferedImage buffImg = null;
									if(i==0){
										buffImg = ImageIO.read(new File("CityMap\\800.jpg"));
									}
									if(i==1){
										buffImg = ImageIO.read(new File("CityMap\\1000.jpg"));
									}
									if(i==2){
										buffImg = ImageIO.read(new File("CityMap\\1200.jpg"));
									}
							        Graphics2D g = (Graphics2D) buffImg.getGraphics();
							 
							        //设置抗锯齿，防止图片或文字模糊
							        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						 
						            //绘制图片
						            if(i==0){
						            	ImageIcon imgIcon = new ImageIcon("800.jpg");
						            	Image img = imgIcon.getImage();
						            	g.drawImage(img,0,0,null);
						            }
						            if(i==1){
						            	ImageIcon imgIcon = new ImageIcon("1000.jpg");
						            	Image img = imgIcon.getImage();
						            	g.drawImage(img,0,0,null);
						            }
						            if(i==2){
						            	ImageIcon imgIcon = new ImageIcon("1200.jpg");
						            	Image img = imgIcon.getImage();
						            	g.drawImage(img,0,0,null);
						            }
						            g.setColor(Color.black);
						            g.drawLine((int)map2.road[0].getPoint(0).getX(), (int)map2.road[0].getPoint(0).getY(), (int)map2.road[0].getPoint(3).getX(), (int)map2.road[0].getPoint(3).getY());
						            g.drawLine((int)map2.road[0].getPoint(1).getX(), (int)map2.road[0].getPoint(1).getY(), (int)map2.road[0].getPoint(2).getX(), (int)map2.road[0].getPoint(2).getY());	
						            g.drawLine((int)map2.road[1].getPoint(0).getX(), (int)map2.road[1].getPoint(0).getY(), (int)map2.road[1].getPoint(3).getX(), (int)map2.road[1].getPoint(3).getY());
						            g.drawLine((int)map2.road[1].getPoint(1).getX(), (int)map2.road[1].getPoint(1).getY(), (int)map2.road[1].getPoint(2).getX(), (int)map2.road[1].getPoint(2).getY());	
						            g.drawLine((int)map2.road[2].getPoint(0).getX(), (int)map2.road[2].getPoint(0).getY(), (int)map2.road[2].getPoint(1).getX(), (int)map2.road[2].getPoint(1).getY());
						            g.drawLine((int)map2.road[2].getPoint(3).getX(), (int)map2.road[2].getPoint(3).getY(), (int)map2.road[2].getPoint(2).getX(), (int)map2.road[2].getPoint(2).getY());	
						            for(int bj=0;bj<buildingNum;bj++){
						            	g.drawLine((int)map2.building[bj].getPoint(0).getX(), (int)map2.building[bj].getPoint(0).getY(), (int)map2.building[bj].getPoint(3).getX(), (int)map2.building[bj].getPoint(3).getY());
						            	g.drawLine((int)map2.building[bj].getPoint(0).getX(), (int)map2.building[bj].getPoint(0).getY(), (int)map2.building[bj].getPoint(1).getX(), (int)map2.building[bj].getPoint(1).getY());
						            	g.drawLine((int)map2.building[bj].getPoint(1).getX(), (int)map2.building[bj].getPoint(1).getY(), (int)map2.building[bj].getPoint(2).getX(), (int)map2.building[bj].getPoint(2).getY());
						            	g.drawLine((int)map2.building[bj].getPoint(2).getX(), (int)map2.building[bj].getPoint(2).getY(), (int)map2.building[bj].getPoint(3).getX(), (int)map2.building[bj].getPoint(3).getY());
						            	if(map2.building[bj].getHeight()==C.heightSet[0]){
						            		g.setColor(new Color(0,10,10));
						            	}
						            	if(map2.building[bj].getHeight()==C.heightSet[1]){
						            		g.setColor(new Color(0,50,50));
						            	}
						            	if(map2.building[bj].getHeight()==C.heightSet[2]){
						            		g.setColor(new Color(0,100,100));
						            	}
						            	if(map2.building[bj].getHeight()==C.heightSet[3]){
						            		g.setColor(new Color(0,150,150));
						            	}
						            	if(map2.building[bj].getHeight()==C.heightSet[4]){
						            		g.setColor(new Color(0,200,200));
						            	}
						            	if(map2.building[bj].getHeight()==C.heightSet[5]){
						            		g.setColor(new Color(0,255,255));
						            	}
						            	//g.setColor(Color.green);
						            	g.fillRect((int)map2.building[bj].getPoint(0).getX(), (int)map2.building[bj].getPoint(0).getY(), (int)map2.building[bj].length, (int)map2.building[bj].width);
						            }
						            
						            
						            Vehicle[] vehicle = vehicleSetGenerate.threeRoadVehicleSet(areaGenerate.getArea(i), i);
						            g.setColor(new Color(0,0,0));
						            for(int vi=0; vi<vehicle.length; vi++){
						            	g.drawLine((int)vehicle[vi].getPoint().getX(), (int)vehicle[vi].getPoint().getY(), (int)vehicle[vi].getPoint().getX(), (int)vehicle[vi].getPoint().getY());
						            }
						            
						            //绘制文字
						           /* Font f = new Font("宋体",Font.PLAIN,30);
						            g.setColor(Color.RED);
						            g.setFont(f);
						            g.drawString("我是渣渣辉",10,100);*/
						            ImageIO.write(buffImg,"jpg", new File("CityMap\\"+txtName+".jpg"));
						        } catch (Exception e) {
						            e.printStackTrace();
						        }
							
							break;
						case 4:
							buildingNum = count - 5;
							Map map3 = new Map();
							map3.area = new Area();
							//String s3 = null;
							//s = null;
							s=br.readLine();
							//String[] strs3;
							strs= s.split("\t");
							map3.area.setLength(Double.parseDouble(strs[0]));
							map3.area.setWidth(Double.parseDouble(strs[1]));
							s=br.readLine();
							strs = s.split("\t");
							map3.road = new Road[4];
							map3.road[0] = new Road();
							map3.road[0].setPoint(0, Double.parseDouble(strs[0]), Double.parseDouble(strs[1]));
							map3.road[0].setPoint(1, Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
							map3.road[0].setPoint(2, Double.parseDouble(strs[4]), Double.parseDouble(strs[5]));
							map3.road[0].setPoint(3, Double.parseDouble(strs[6]), Double.parseDouble(strs[7]));
							
							s=br.readLine();
							strs = s.split("\t");
							map3.road[1] = new Road();
							map3.road[1].setPoint(0, Double.parseDouble(strs[0]), Double.parseDouble(strs[1]));
							map3.road[1].setPoint(1, Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
							map3.road[1].setPoint(2, Double.parseDouble(strs[4]), Double.parseDouble(strs[5]));
							map3.road[1].setPoint(3, Double.parseDouble(strs[6]), Double.parseDouble(strs[7]));
							
							s=br.readLine();
							strs = s.split("\t");
							map3.road[2] = new Road();
							map3.road[2].setPoint(0, Double.parseDouble(strs[0]), Double.parseDouble(strs[1]));
							map3.road[2].setPoint(1, Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
							map3.road[2].setPoint(2, Double.parseDouble(strs[4]), Double.parseDouble(strs[5]));
							map3.road[2].setPoint(3, Double.parseDouble(strs[6]), Double.parseDouble(strs[7]));
							
							s=br.readLine();
							strs = s.split("\t");
							map3.road[3] = new Road();
							map3.road[3].setPoint(0, Double.parseDouble(strs[0]), Double.parseDouble(strs[1]));
							map3.road[3].setPoint(1, Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
							map3.road[3].setPoint(2, Double.parseDouble(strs[4]), Double.parseDouble(strs[5]));
							map3.road[3].setPoint(3, Double.parseDouble(strs[6]), Double.parseDouble(strs[7]));
							
							map3.building = new Building[buildingNum];
							for(int bi=0;bi<buildingNum;bi++){
								s = br.readLine();
								strs = s.split("\t");
								map3.building[bi] = new Building();
								map3.building[bi].setPoint(0, Double.parseDouble(strs[0]), Double.parseDouble(strs[1]));
								map3.building[bi].setPoint(1, Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
								map3.building[bi].setPoint(2, Double.parseDouble(strs[4]), Double.parseDouble(strs[5]));
								map3.building[bi].setPoint(3, Double.parseDouble(strs[6]), Double.parseDouble(strs[7]));
								map3.building[bi].setHeight(Double.parseDouble(strs[8]));
							}
								//System.out.println(strs[0]+"  "+strs[1]+"  "+strs[2]+"  "+strs[3]+"  "+strs[4]+"  "+strs[5]+"  "+strs[6]+"  "+strs[7]+"  "+strs[8]);
								try {
						        	//File file = new File("‪D:\\javaWeb\\Hello\\in.jpg");
									BufferedImage buffImg = null;
									if(i==0){
										buffImg = ImageIO.read(new File("CityMap\\800.jpg"));
									}
									if(i==1){
										buffImg = ImageIO.read(new File("CityMap\\1000.jpg"));
									}
									if(i==2){
										buffImg = ImageIO.read(new File("CityMap\\1200.jpg"));
									}
							        Graphics2D g = (Graphics2D) buffImg.getGraphics();
							 
							        //设置抗锯齿，防止图片或文字模糊
							        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						 
						            //绘制图片
						            if(i==0){
						            	ImageIcon imgIcon = new ImageIcon("800.jpg");
						            	Image img = imgIcon.getImage();
						            	g.drawImage(img,0,0,null);
						            }
						            if(i==1){
						            	ImageIcon imgIcon = new ImageIcon("1000.jpg");
						            	Image img = imgIcon.getImage();
						            	g.drawImage(img,0,0,null);
						            }
						            if(i==2){
						            	ImageIcon imgIcon = new ImageIcon("1200.jpg");
						            	Image img = imgIcon.getImage();
						            	g.drawImage(img,0,0,null);
						            }
						            g.setColor(Color.black);
						            g.drawLine((int)map3.road[0].getPoint(0).getX(), (int)map3.road[0].getPoint(0).getY(), (int)map3.road[0].getPoint(3).getX(), (int)map3.road[0].getPoint(3).getY());
						            g.drawLine((int)map3.road[0].getPoint(1).getX(), (int)map3.road[0].getPoint(1).getY(), (int)map3.road[0].getPoint(2).getX(), (int)map3.road[0].getPoint(2).getY());	
						            g.drawLine((int)map3.road[1].getPoint(0).getX(), (int)map3.road[1].getPoint(0).getY(), (int)map3.road[1].getPoint(3).getX(), (int)map3.road[1].getPoint(3).getY());
						            g.drawLine((int)map3.road[1].getPoint(1).getX(), (int)map3.road[1].getPoint(1).getY(), (int)map3.road[1].getPoint(2).getX(), (int)map3.road[1].getPoint(2).getY());	
						            g.drawLine((int)map3.road[2].getPoint(0).getX(), (int)map3.road[2].getPoint(0).getY(), (int)map3.road[2].getPoint(1).getX(), (int)map3.road[2].getPoint(1).getY());
						            g.drawLine((int)map3.road[2].getPoint(3).getX(), (int)map3.road[2].getPoint(3).getY(), (int)map3.road[2].getPoint(2).getX(), (int)map3.road[2].getPoint(2).getY());	
						            g.drawLine((int)map3.road[3].getPoint(0).getX(), (int)map3.road[3].getPoint(0).getY(), (int)map3.road[3].getPoint(1).getX(), (int)map3.road[3].getPoint(1).getY());
						            g.drawLine((int)map3.road[3].getPoint(3).getX(), (int)map3.road[3].getPoint(3).getY(), (int)map3.road[3].getPoint(2).getX(), (int)map3.road[3].getPoint(2).getY());	
						            for(int bj=0;bj<buildingNum;bj++){
						            	g.drawLine((int)map3.building[bj].getPoint(0).getX(), (int)map3.building[bj].getPoint(0).getY(), (int)map3.building[bj].getPoint(3).getX(), (int)map3.building[bj].getPoint(3).getY());
						            	g.drawLine((int)map3.building[bj].getPoint(0).getX(), (int)map3.building[bj].getPoint(0).getY(), (int)map3.building[bj].getPoint(1).getX(), (int)map3.building[bj].getPoint(1).getY());
						            	g.drawLine((int)map3.building[bj].getPoint(1).getX(), (int)map3.building[bj].getPoint(1).getY(), (int)map3.building[bj].getPoint(2).getX(), (int)map3.building[bj].getPoint(2).getY());
						            	g.drawLine((int)map3.building[bj].getPoint(2).getX(), (int)map3.building[bj].getPoint(2).getY(), (int)map3.building[bj].getPoint(3).getX(), (int)map3.building[bj].getPoint(3).getY());
						            	if(map3.building[bj].getHeight()==C.heightSet[0]){
						            		g.setColor(new Color(0,10,10));
						            	}
						            	if(map3.building[bj].getHeight()==C.heightSet[1]){
						            		g.setColor(new Color(0,50,50));
						            	}
						            	if(map3.building[bj].getHeight()==C.heightSet[2]){
						            		g.setColor(new Color(0,100,100));
						            	}
						            	if(map3.building[bj].getHeight()==C.heightSet[3]){
						            		g.setColor(new Color(0,150,150));
						            	}
						            	if(map3.building[bj].getHeight()==C.heightSet[4]){
						            		g.setColor(new Color(0,200,200));
						            	}
						            	if(map3.building[bj].getHeight()==C.heightSet[5]){
						            		g.setColor(new Color(0,255,255));
						            	}
						            	//g.setColor(Color.green);
						            	g.fillRect((int)map3.building[bj].getPoint(0).getX(), (int)map3.building[bj].getPoint(0).getY(), (int)map3.building[bj].length, (int)map3.building[bj].width);
						            }
						            
						            Vehicle[] vehicle = vehicleSetGenerate.fourRoadVehicleSet(areaGenerate.getArea(i), i);
						            g.setColor(new Color(0,0,0));
						            for(int vi=0; vi<vehicle.length; vi++){
						            	g.drawLine((int)vehicle[vi].getPoint().getX(), (int)vehicle[vi].getPoint().getY(), (int)vehicle[vi].getPoint().getX(), (int)vehicle[vi].getPoint().getY());
						            }
						            //绘制文字
						           /* Font f = new Font("宋体",Font.PLAIN,30);
						            g.setColor(Color.RED);
						            g.setFont(f);
						            g.drawString("我是渣渣辉",10,100);*/
						            ImageIO.write(buffImg,"jpg", new File("CityMap\\"+txtName+".jpg"));
						        } catch (Exception e) {
						            e.printStackTrace();
						        }
							
							break;
						}
					}
					
				}
			}
		}
		
		
		
		/*FileReader fr = new FileReader(txtName);
		BufferedReader br = new BufferedReader(fr);*/

		//int count = 0;
		/*RandomAccessFile file = new RandomAccessFile(new File(txtName),"r");
		while(file.readLine()!=null){ 
			count++;
		}*/
		//int buildingNum = count-2;
	//	file.close();
	//	System.out.println(count);
		/*Map map = new Map();
		map.area = new Area();
		String s = null;
		s=br.readLine();
		String[] strs;
		strs= s.split("\t");
		map.area.setLength(Double.parseDouble(strs[0]));
		map.area.setWidth(Double.parseDouble(strs[1]));
		s=br.readLine();
		strs = s.split("\t");
		map.road = new Road[1];
		map.road[0] = new Road();
		map.road[0].setPoint(0, Double.parseDouble(strs[0]), Double.parseDouble(strs[1]));
		map.road[0].setPoint(1, Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
		map.road[0].setPoint(2, Double.parseDouble(strs[4]), Double.parseDouble(strs[5]));
		map.road[0].setPoint(3, Double.parseDouble(strs[6]), Double.parseDouble(strs[7]));
		map.building = new Building[buildingNum];
		for(int i=0;i<buildingNum;i++){
			s = br.readLine();
			strs = s.split("\t");
			map.building[i] = new Building();
			map.building[i].setPoint(0, Double.parseDouble(strs[0]), Double.parseDouble(strs[1]));
			map.building[i].setPoint(1, Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
			map.building[i].setPoint(2, Double.parseDouble(strs[4]), Double.parseDouble(strs[5]));
			map.building[i].setPoint(3, Double.parseDouble(strs[6]), Double.parseDouble(strs[7]));
			map.building[i].setHeight(Double.parseDouble(strs[8]));
			//System.out.println(strs[0]+"  "+strs[1]+"  "+strs[2]+"  "+strs[3]+"  "+strs[4]+"  "+strs[5]+"  "+strs[6]+"  "+strs[7]+"  "+strs[8]);
		}
		
		 try {
	        	//File file = new File("‪D:\\javaWeb\\Hello\\in.jpg");
	            BufferedImage buffImg = ImageIO.read(new File("CityMap\\800.jpg"));
	            Graphics2D g = (Graphics2D) buffImg.getGraphics();
	 
	            //设置抗锯齿，防止图片或文字模糊
	            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	 
	            //绘制图片
	            ImageIcon imgIcon = new ImageIcon("800.jpg");
	            Image img = imgIcon.getImage();
	            g.drawImage(img,0,0,null);
	            
	            g.setColor(Color.black);
	            g.drawLine((int)map.road[0].getPoint(0).getX(), (int)map.road[0].getPoint(0).getY(), (int)map.road[0].getPoint(3).getX(), (int)map.road[0].getPoint(3).getY());
	            g.drawLine((int)map.road[0].getPoint(1).getX(), (int)map.road[0].getPoint(1).getY(), (int)map.road[0].getPoint(2).getX(), (int)map.road[0].getPoint(2).getY());
	            
	            for(int j=0;j<buildingNum;j++){
	            	g.drawLine((int)map.building[j].getPoint(0).getX(), (int)map.building[j].getPoint(0).getY(), (int)map.building[j].getPoint(3).getX(), (int)map.building[j].getPoint(3).getY());
	            	g.drawLine((int)map.building[j].getPoint(0).getX(), (int)map.building[j].getPoint(0).getY(), (int)map.building[j].getPoint(1).getX(), (int)map.building[j].getPoint(1).getY());
	            	g.drawLine((int)map.building[j].getPoint(1).getX(), (int)map.building[j].getPoint(1).getY(), (int)map.building[j].getPoint(2).getX(), (int)map.building[j].getPoint(2).getY());
	            	g.drawLine((int)map.building[j].getPoint(2).getX(), (int)map.building[j].getPoint(2).getY(), (int)map.building[j].getPoint(3).getX(), (int)map.building[j].getPoint(3).getY());
	            	if(map.building[j].getHeight()==C.heightSet[0]){
	            		g.setColor(new Color(0,10,10));
	            	}
	            	if(map.building[j].getHeight()==C.heightSet[1]){
	            		g.setColor(new Color(0,50,50));
	            	}
	            	if(map.building[j].getHeight()==C.heightSet[2]){
	            		g.setColor(new Color(0,100,100));
	            	}
	            	if(map.building[j].getHeight()==C.heightSet[3]){
	            		g.setColor(new Color(0,150,150));
	            	}
	            	if(map.building[j].getHeight()==C.heightSet[4]){
	            		g.setColor(new Color(0,200,200));
	            	}
	            	if(map.building[j].getHeight()==C.heightSet[5]){
	            		g.setColor(new Color(0,255,255));
	            	}
	            	//g.setColor(Color.green);
	            	g.fillRect((int)map.building[j].getPoint(0).getX(), (int)map.building[j].getPoint(0).getY(), (int)map.building[j].length, (int)map.building[j].width);
	            }
	            //绘制文字
	            Font f = new Font("宋体",Font.PLAIN,30);
	            g.setColor(Color.RED);
	            g.setFont(f);
	            g.drawString("我是渣渣辉",10,100);
	 
	            ImageIO.write(buffImg,"jpg", new File("CityMap\\out.jpg"));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }*/
		
		/*Scanner scan = new Scanner(s);
		map.area.setLength(Double.parseDouble(scan.next()));
		map.area.setWidth(Double.parseDouble(scan.next()));
		s=br.readLine();
		scan = new Scanner(s);
		map.road = new Road[1];
		map.road[0] = new Road();
		map.road[0].setPoint(0, Double.parseDouble(scan.next()), Double.parseDouble(scan.next()));
		map.road[0].setPoint(1, Double.parseDouble(scan.next()), Double.parseDouble(scan.next()));
		map.road[0].setPoint(2, Double.parseDouble(scan.next()), Double.parseDouble(scan.next()));
		map.road[0].setPoint(3, Double.parseDouble(scan.next()), Double.parseDouble(scan.next()));
		
		while((s=br.readLine())!=null){
			
		}*/
		/*System.out.println(map.area.getLength());
		System.out.println(map.area.getWidth());
		System.out.println(map.road[0].getPoint(0).getX());
		System.out.println(map.road[0].getPoint(0).getY());
		System.out.println(map.road[0].getPoint(1).getX());
		System.out.println(map.road[0].getPoint(1).getY());
		System.out.println(map.road[0].getPoint(2).getX());
		System.out.println(map.road[0].getPoint(2).getY());
		System.out.println(map.road[0].getPoint(3).getX());
		System.out.println(map.road[0].getPoint(3).getY());
		System.out.println(map.building[97].getPoint(1).getX());
		System.out.println(map.building[97].getHeight());*/
		//System.out.println(map.road[0].getPoint(0).getX());
		//s=br.readLine();
		/*int[] num = new int[2];
		Scanner scan = new Scanner(s);
		num[0]=Integer.parseInt(scan.next());
		num[1]=Integer.parseInt(scan.next());
		System.out.println(num[0]);
		System.out.println(num[1]);
		System.out.println(num[0]*num[1]);
		s=br.readLine();
		scan = new Scanner(s);
		br.close();
		num[0]=Integer.parseInt(scan.next());
		num[1]=Integer.parseInt(scan.next());
		System.out.println(num[0]);
		System.out.println(num[1]);*/
	}
	public static void main(String[] args) throws IOException {
		mapReadGenerate();
	}
}
