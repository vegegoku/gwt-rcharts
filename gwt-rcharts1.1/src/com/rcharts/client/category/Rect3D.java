/*
 * Copyright 2011 Saurabh Tripathi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rcharts.client.category;

import com.google.gwt.user.client.Window;
import com.hydro4ge.raphaelgwt.client.PathBuilder;
import com.hydro4ge.raphaelgwt.client.Raphael.Path;
import com.hydro4ge.raphaelgwt.client.Raphael.Shape;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.Chart;
import com.rcharts.client.Point;
import com.rcharts.client.RaphaelFactory;

public class Rect3D {

	/**
	 * Makes a 3-dimensional Parallelogram 
	 * @param chart 
	 * @param length of parallelogram x axis
	 * @param breadth of parallelogram z axis
	 * @param height of parallelogram y axis
	 * @param angle of z axis
	 *              x
	 * 			 1______2
	 * 		 z  /______/|
	 * 		   4|     |3|   
	 * 			|     | |   y
	 * 			|_____|/ 7
	 * 		    5     6    
	 * 
	 *	 *              x
	 * 			 1______7
	 * 		 z  /______/|
	 * 		   5|     |6|     height -ve  
	 * 			|     | |   y
	 * 			|_____|/ 2
	 * 		    4     3    
	 * 
	 *         2______1
	 *         /_____/|
	 *        3|    |4|
	 *         |    | |7
	 *         |____|/
 	 *        6      5
	 */
	public static Shape get3DRect(Chart chart, double length, double breadth, 
			double height, double angle){
		
		double rad = Math.toRadians(angle);
		double sin = breadth * Math.sin(rad);
		double cos = breadth * Math.cos(rad);
		Point p1 = new Point(0, 0);
		Point p2 = new Point(p1.getX()+length, p1.getY());
		Point p4 = new Point(p1.getX()-cos, p1.getY()+sin) ;
		Point p3 = new Point(p4.getX()+length, p4.getY());
		
		Point p5 = new Point(p4.getX(), p4.getY()+height);
		Point p6 = new Point(p3.getX(), p5.getY());
		
		Point p7 = new Point(p2.getX(), p2.getY()+height);
		/*
		 * TODO: Make implementation for negative value of length too for bar chart
		 * And make implementation in BarChartFactory where the bars are added to panel
		 * if min < 0 then all positive bars are translated to sin in y or cos in x
		 * direction
		 */
		//column chart negative values
		if(height < 0){
			swap(p4, p5);
			swap(p3, p6);
			swap(p2, p7);
			p1.setY(height);//p2.getY());
		}
		//bar chart negative values
		if(length < 0){
			swap(p1, p2);
			swap(p3, p4);
			swap(p5, p6);
			p7.setX(p7.getX()-length);
		}
		
		PathBuilder pb = new PathBuilder();
		
		//Top rect
		pb.M(p1.getX(), p1.getY());
		pb.L(p2.getX(), p2.getY());
		pb.L(p3.getX(), p3.getY());
		pb.L(p4.getX(), p4.getY());
		pb.L(p1.getX(), p1.getY());
		
		
		//Front rect
		pb.M(p4.getX(), p4.getY());
		pb.L(p5.getX(), p5.getY());
		pb.L(p6.getX(), p6.getY());
		pb.L(p3.getX(), p3.getY());
		
		//Side rect
		pb.M(p6.getX(), p6.getY());
		pb.L(p7.getX(), p7.getY());
		pb.L(p2.getX(), p2.getY());
		pb.L(p3.getX(), p3.getY());
		pb.Z();
		
		Path path = chart.new Path(pb);
		return path;
	}
	
	private static void swap(Point p1, Point p2){
		Point p = new Point(p1.getX(), p1.getY());
		p1.setX(p2.getX());
		p1.setY(p2.getY());
		p2.setX(p.getX());
		p2.setY(p.getY());
	}
	
	public static Point get3DTranslationPoint(double breadth, double angle){
		double rad = Math.toRadians(angle);
		double sin = breadth * Math.sin(rad);
		double cos = breadth * Math.cos(rad);
        return new Point(sin, cos);		
	}
}
