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

import java.util.List;

import com.google.gwt.user.client.Window;
import com.rcharts.client.Chart;
import com.rcharts.client.ChartStyles;
import com.rcharts.client.Point;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.category.bar.BarChart;

public class AxisPositive extends Axis {


	public AxisPositive(){
		super();
	}
	
	
	
	public AxisPositive(List<String> tickLabels, Orientation orient,
			double axisLength) {
		super(tickLabels, orient, axisLength);
	}



	/**
	 * Sets the list of points 'points' where tickLabels and tickMarks are going to be drawn
	 */
	public void init(){
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
				//TODO: remove code
				//i = i - pxInterval/2;		
			}
		}

		double max = axisLength + x;
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
		if(!isValueAxis){
			Chart style = RaphaelFactory.get();
			if(style instanceof BarChart){
				//TODO: remove the code
				//i = i + pxInterval/2;				
			}
		}

		double min = y;
		while(Math.floor(i) > min){
			i = i - pxInterval;
			Point p = new Point(x,i);
			tickPoints.add(p);
		}

	}
}
