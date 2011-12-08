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
package com.rcharts.client.xychart;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rcharts.client.Point;

public class XYDataTable<E extends Comparable <? super E>> {

	private Map<String, List<Point>> pointMap = new LinkedHashMap<String, List<Point>>();
	
	private double xMax;
	private double yMax;
	private double xMin;
	private double yMin;
	
	public XYDataTable(){
		super();
	}

	private void updateMaxMin(double xValue, double yValue){
		if(xValue > xMax){
			xMax = xValue;
		}
		else if(xValue < xMin){
			xMin = xValue;
		}
		if(yValue > yMax){
			yMax = yValue;
		}
		else if(yValue < yMin){
			yMin = yValue;
		}
	}
		
	public void add(Double xValue, Double yValue, String series){
		if(pointMap.get(series)==null){
			pointMap.put(series, new ArrayList<Point>());
		}
		pointMap.get(series).add(new Point(xValue, yValue));
		updateMaxMin(xValue, yValue);
	}
	
	public List<Point> get(String series){
		return pointMap.get(series);
	}

	
	public Set<String> getSeriesNames(){
		return pointMap.keySet();
	}
	
	
	/**
	 * @return the pointMap
	 */
	public Map<String, List<Point>> getPointMap() {
		return pointMap;
	}


	/**
	 * @param pointMap the pointMap to set
	 */
	public void setPointMap(Map<String, List<Point>> pointMap) {
		this.pointMap = pointMap;
	}
	

	/**
	 * @return the xMax
	 */
	public double getxMax() {
		return xMax;
	}

	/**
	 * @return the yMax
	 */
	public double getyMax() {
		return yMax;
	}

	/**
	 * @return the xMin
	 */
	public double getxMin() {
		return xMin;
	}

	/**
	 * @return the yMin
	 */
	public double getyMin() {
		return yMin;
	}

	@Override
	public String toString(){
		Set<String>keySet = pointMap.keySet();
		String msg = "";
		for(String s : keySet){
			List<Point> points = pointMap.get(s);
			msg += "\n"+s;
			for(Point p : points){
				msg += p.toString();
			}
		}
		return msg;
	}
	
}
