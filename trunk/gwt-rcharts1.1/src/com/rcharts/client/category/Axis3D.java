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

import java.util.Iterator;

import com.google.gwt.user.client.Window;
import com.hydro4ge.raphaelgwt.client.PathBuilder;
import com.hydro4ge.raphaelgwt.client.Raphael.Circle;
import com.hydro4ge.raphaelgwt.client.Raphael.Path;
import com.hydro4ge.raphaelgwt.client.Raphael.Set;
import com.rcharts.client.Chart;
import com.rcharts.client.ChartStyles;
import com.rcharts.client.Point;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.category.bar.BarChart;
import com.rcharts.client.styles.Style;

/**
 * Makes the axis with 3D effect
 */
public class Axis3D extends Axis {

	public static double angle = 45;
	public Axis3D(){
		super();
	}
	
	public Axis3D(double angle){
		this();
		Axis3D.angle = angle;
	}
	
	
	
	/**
	 * @return the angle
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * @param angle the angle to set
	 */
	public void setAngle(double angle) {
		Axis3D.angle = angle;
	}



	@Override
	public void init() {
		// no of points on axis for tick labels and mark. We must include starting point 'zero'
		//hence doing tickLable.size()+1
		double noOfPoints = tickLabels.size();
		//noOfPoints : axis length in pixels ratio i.e distance between two tick labels 
		// in pixels
		double pxInterval = axisLength/noOfPoints;
		double max = axisLength;
		double x = startPoint.getX();
		double y = startPoint.getY();
		double i = 0;
		if(orient == Orientation.HORIZONTAL){
			makeHorizontalTickPoints(pxInterval);
			return;
		}
		else{
			makeVerticalTickPoints(pxInterval);
			return;
		}

	}
	
	/**
	 * Positive X axis : x increase
	 * @param pxInterval
	 */
	private void makeHorizontalTickPoints(double pxInterval){
		double x = startPoint.getX();
		double y = startPoint.getY();
		
		double i = x;
		//category axis label tends to be in the mid of the axis intervals	
		if(!isValueAxis){
			ChartStyles style = RaphaelFactory.get();
			if(style instanceof BarChart){
//				i = i - pxInterval/2;
				
			}
		}

		double max = axisLength + x;
		//max = max - pxInterval;
		while(Math.ceil(i) < max){
			i = i + pxInterval;
			//x increments for points on +ve x axis
			Point p = new Point(i,y);
			tickPoints.add(p);
		}
		
	}
	
	/**
	 * Positive Y axis : y decreases (*coordinate system of screen)
	 * @param pxInterval
	 */
	private void makeVerticalTickPoints(double pxInterval){
		double x = startPoint.getX();
		double y = startPoint.getY();
		double i = y + axisLength;

		//category axis label tends to be in the mid of the axis intervals	
		/**
		 * TODO: add the same code in AxisPositive class and test it for all other charts
		 * to see the extra tick on vertical axis when its a category axis disappear
		 *	if(!isValueAxis()){
		 *		min = min + pxInterval;
		 *	} 
		 */
		
		double min = y;//+ pxInterval;
		while(Math.floor(i) > min){
			i = i - pxInterval;
			Point p = new Point(x,i);
			tickPoints.add(p);
		}
	}


	@Override
	public void makeTickMarks(){
		showGridLine();
		boolean flag = true;
		if(second && orient == Orientation.VERTICAL){
			return;
		}
		
		if(!second && orient == Orientation.HORIZONTAL){
			return;
		}
		
		PathBuilder ax = new PathBuilder();
		Iterator<Point> it = tickPoints.iterator();
		Style tickMarkStyle = new Style();
		if(orient == Orientation.HORIZONTAL){
			tickMarkStyle = RaphaelFactory.get().gethAxisTickMarkStyle();			
		}
		else{
			tickMarkStyle = RaphaelFactory.get().getvAxisTickMarkStyle();			
		}
		double ytan = tickMarkLength * Math.tan(Math.toRadians(angle));
		while(it.hasNext()){
			Point p = it.next();
			PathBuilder pb = new PathBuilder();
			if(orient == Orientation.HORIZONTAL){
				//do vertical line i.e increment y
				if(flag){
					ax.M(startPoint.getX(), startPoint.getY());
					ax.L(startPoint.getX() - ytan, startPoint.getY() + tickMarkLength);
					Path axP = chart.new Path(ax);
					set.push(axP);
					axisList.add(axP);
					_3DAxisSet.push(axP);
					ax = new PathBuilder();
					ax.M(startPoint.getX() - ytan, startPoint.getY() + tickMarkLength);					
					ax.l(axisLength, 0);
					axP = chart.new Path(ax);
					set.push(axP);
					axisList.add(axP);
					_3DAxisSet.push(axP);
					flag = false;
				}
				pb.M(p.getX(), p.getY());
				pb.L(p.getX() - ytan, p.getY() + tickMarkLength);
			}
			else{
				if(flag){
					Point lastP = tickPoints.get(tickPoints.size()-1);
					ax.M(lastP.getX(), lastP.getY() + ytan);
					ax.l(0, axisLength);
					Path axP = chart.new Path(ax);
					set.push(axP);
					axisList.add(axP);
					_3DAxisSet.push(axP);
					flag = false;
				}
				pb.M(p.getX(), p.getY() + ytan);
				pb.L(p.getX()+tickMarkLength, p.getY());
			}
			Path tickPath = chart.new Path(pb);
			tickPath.attr(tickMarkStyle);
			tickMarkSet.push(tickPath);
			raphaelTickMarks.add(tickPath);
			axisList.add(tickPath);
			set.push(tickPath);
			if(second){
				secondAxisSet.push(tickPath);
			}
			else{
				firstAxisSet.push(tickPath);
			}
		}
		
		updateTickPoints(tickMarkLength);
		_3DAxisSet.attr(chart.get_3DAxesStyle());
	}
	
	@Override
	public void makeTickLabel(){
		if(second && orient == Orientation.HORIZONTAL){
			return;
		}
		super.makeTickLabel();
		if(!second && orient == Orientation.VERTICAL){
			tickLabelSet.attr("opacity", 0);
		}
	}

}
