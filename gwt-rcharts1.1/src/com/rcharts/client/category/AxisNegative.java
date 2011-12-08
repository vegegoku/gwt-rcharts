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

import com.rcharts.client.Point;

/*
 * TODO: remove class 
 */
public class AxisNegative extends Axis{

	public AxisNegative() {
		super();
	}

	public AxisNegative(List<String> tickLabels, Orientation orient,
			double axisLength) {
		super(tickLabels, orient, axisLength);
	}

	/**
	 * Sets the list of points 'points' where tickLabels and tickMarks are going to be drawn
	 */
	
/*	
   public void init(){
		// no of points on axis for tick labels and mark. We must include starting point 'zero'
		//hence doing tickLable.size()+1
		double noOfPoints = tickLabels.size();
		//noOfPoints : axis length in pixels ratio i.e distance between two tick labels 
		// in pixels
		double pxInterval = axisLength/noOfPoints;
		double x = startPoint.getX();
		double y = startPoint.getY();
		double i = 0;
		double min = 0;
		if(orient == Orientation.HORIZONTAL){
			i = x + axisLength;
			min = x;
		}
		else{
			i = y + axisLength;
			min = y;
		}
		while(i > min){
			i = i - pxInterval;
			if(orient == Orientation.HORIZONTAL){
				Point p = new Point(i,y);
				tickPoints.add(p);
			}
			else{
				Point p = new Point(x,i);
				tickPoints.add(p);
			}
		}
		
	}
*/

	   public void init(){
			// no of points on axis for tick labels and mark. We must include starting point 'zero'
			//hence doing tickLable.size()+1
			double noOfPoints = tickLabels.size();
			//noOfPoints : axis length in pixels ratio i.e distance between two tick labels 
			// in pixels
			double pxInterval = axisLength/noOfPoints;
			double x = startPoint.getX();
			double y = startPoint.getY();
			double i = 0;
			double min = 0;
			if(orient == Orientation.HORIZONTAL){
				makeHorizontalTickPoints(pxInterval);
			}
			else{
				makeVerticalTickPoints(pxInterval);
			}
			
		}
	   
	   
	   /**
	    * Negative x axis :  X decreases
	    * @param pxInterval
	    */
	   private void makeHorizontalTickPoints(double pxInterval){
			double x = startPoint.getX();
			double y = startPoint.getY();
			double i = x + axisLength;
			
			//category axis label tends to be in the mid of the axis intervals	
			if(!isValueAxis){
				i = i + pxInterval/2;
			}

			double min = x;
			while(i > min){
				i = i - pxInterval;
				Point p = new Point(i,y);
				tickPoints.add(p);
			}
		   
	   }
	   
	   /**
	    * Negative y axis : y increases
	    * @param pxInterval
	    */
	   private void makeVerticalTickPoints(double pxInterval){
			double x = startPoint.getX();
			double y = startPoint.getY();
			double i = y;

			//category axis label tends to be in the mid of the axis intervals	
			if(!isValueAxis){
				i = i - pxInterval/2;
			}

			double max = axisLength + y;
			while(i < max){
				i = i + pxInterval;
				Point p = new Point(x,i);
				tickPoints.add(p);
			}
		   
	   }
}
