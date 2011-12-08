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
package com.rcharts.client.category.line;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hydro4ge.raphaelgwt.client.PathBuilder;
import com.hydro4ge.raphaelgwt.client.Raphael.Circle;
import com.hydro4ge.raphaelgwt.client.Raphael.Path;
import com.hydro4ge.raphaelgwt.client.Raphael.Set;
import com.hydro4ge.raphaelgwt.client.Raphael.Shape;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.AxisLabelHelper;
import com.rcharts.client.ChartFactory;
import com.rcharts.client.Point;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.category.Axis;
import com.rcharts.client.category.AxisFactory;
import com.rcharts.client.category.CategoryChart;
import com.rcharts.client.category.CategoryDataTable;
import com.rcharts.client.category.line.LineChart.LineChartType;
import com.rcharts.client.category.line.Spline.SPoint;
import com.rcharts.client.combo.ComboChart;

public class LineChartFactory {

	private CategoryChart chart;
	private Axis xAxis;
	private Axis yAxis;
	private double axisWidth;
	private double axisHeight;
	private Set chartSet;
	private CategoryDataTable<Double> dataTable;
	private CategoryDataTable<RaphaelObject> pointDataTable = new CategoryDataTable<RaphaelObject>();
	private Map<String, String> colorMap;
	public static int POINT_RADIUS = 4;
	private Set plotSet;
	private boolean isSpline;
	private Map<String, RaphaelObject> pathMap = new LinkedHashMap<String, RaphaelObject>();
	
	
	public LineChartFactory(){
		chart = (CategoryChart) RaphaelFactory.get();
		colorMap = RaphaelFactory.getColorMap();
		chartSet = chart.new Set();
		plotSet = chart.new Set();
		init();
	}
	
	private void init(){
		dataTable = chart.getDataTable();
		chartSet = chart.new Set();
		AxisFactory axisFactory = new AxisFactory();
		xAxis = axisFactory.getXAxis();
		yAxis = axisFactory.getYAxis();
		axisWidth = AxisLabelHelper.getXAxisLength();
		axisHeight = AxisLabelHelper.getYAxisLength();
		if(chart instanceof LineChart){
			LineChart lc = (LineChart) chart;
			isSpline = lc.isSplineEffect();
		}
		pushAxisToChart();
	}
	
	
	public LineChartFactory(AxisFactory axisFactory, boolean isSpline, Set chartSet){
		chart = (CategoryChart) RaphaelFactory.get();
		dataTable = chart.getDataTable();
		plotSet = chart.new Set();
		this.chartSet = chartSet;
		xAxis = axisFactory.getXAxis();
		yAxis = axisFactory.getYAxis();
		axisWidth = AxisLabelHelper.getXAxisLength();
		axisHeight = AxisLabelHelper.getYAxisLength();
		if(chart instanceof ComboChart){
			ComboChart ch = (ComboChart) chart;
			axisWidth = ch.getRealAxisLenth();
		}
		this.isSpline = isSpline;
		colorMap = RaphaelFactory.getColorMap();
		//pushAxisToChart();
	}
	
	private void pushAxisToChart(){
		for(Shape s : xAxis.getAxisList()){
			chartSet.push(s);
		}
		for(Shape s : yAxis.getAxisList()){
			chartSet.push(s);
		}
	}
	
	/**
	 * @return the pathMap
	 */
	public Map<String, RaphaelObject> getPathMap() {
		return pathMap;
	}

	/**
	 * @param pathMap the pathMap to set
	 */
	public void setPathMap(Map<String, RaphaelObject> pathMap) {
		this.pathMap = pathMap;
	}

	public void createAreaChart(boolean isBar){
		com.hydro4ge.raphaelgwt.client.Raphael.Set plotBox = chart.new Set();
		
		List<Point> tickPoints =null;
		List<String> tickLabels = null;
		if(isBar){
			tickPoints = yAxis.getTickPoints();
			tickLabels = yAxis.getTickLabel();
		}
		else{
			tickPoints = xAxis.getTickPoints();
			tickLabels = xAxis.getTickLabel();
		}
		
		Point originPoint = tickPoints.get(0);
		double originX = originPoint.getX();
		double ratio = 0;
		double interval = tickPoints.get(1).getY() - tickPoints.get(0).getY();
        double interval2 = tickPoints.get(1).getX() - tickPoints.get(0).getX();
		
		if(isBar){
			ratio = axisWidth/dataTable.getMaxValue();
		}
		else{
			ratio = axisHeight/dataTable.getMaxValue();
		}
		if(dataTable.getMin() < 0){
			ratio = ratio/2;
		}
		java.util.Set<String> seriesNames = dataTable.getSeriesNames();
		Iterator<String> seriesIt = seriesNames.iterator();
		Iterator<String> catIt = tickLabels.iterator();
		Iterator<Point> pointIt = tickPoints.iterator();
		Shape org = chart.new Circle(originX, originPoint.getY(), .1);
		plotBox.push(org);
		//chart.push(org);
		chartSet.push(org);
		int i = 0;
		boolean move = true;
		while(seriesIt.hasNext()){
			PathBuilder linePath = new PathBuilder();
			List<Point> pointList = new ArrayList<Point>();
			String series = seriesIt.next();
			String color = colorMap.get(series);
			move = true;
			while(catIt.hasNext()){
				String category = catIt.next();
				Point domainPoint = pointIt.next();
				double value = dataTable.get(category, series);
				double valuePoint = ratio * value;
				if(isBar){
					double y =interval + domainPoint.getY();
					double x = originX + valuePoint;
					pointList.add(new Point(x,y));
					Circle c = chart.new Circle(x,y,POINT_RADIUS);
					c.attr("fill", color);
					plotBox.push(c);
					chartSet.push(c);
					//chart.push(c);
					pointDataTable.add(c, category, series);
				}
				else{
					double x = interval2 + domainPoint.getX();
					double y = originPoint.getY() - valuePoint;
					//TODO : implement with ComboLineChart and ComboAreaChart to have
					//Points in the mid of the category slabs
					if(chart instanceof ComboChart){
						ComboChart comboCh = (ComboChart) chart;
						if(comboCh.isShowLinePointsAtMid()){
							x = x - interval2/2;
						}
					}
					pointList.add(new Point(x,y));
					Circle c = chart.new Circle(x,y,POINT_RADIUS);
					c.attr("fill", color);
					plotBox.push(c);
					chartSet.push(c);
					//chart.push(c);
					pointDataTable.add(c, category, series);
				}
			}
			Collections.sort(pointList, new Comparator<Point>() {

				@Override
				public int compare(Point o1, Point o2) {
					double x1 = o1.getX();
					double x2 = o2.getX();
					if(x1 > x2){
						return 1;
					}
					else if(x1 < x2){
						return -1;
					}
					else{
						return 0;
					}
				}
			});
			int ctr = 0;
			Point lastPoint_3D = pointList.get(pointList.size()-1);
			List<Point> backupPointList = null;
			PathBuilder _3DArea = null;
			if(chart.is_3d()){
				_3DArea = new PathBuilder();
				backupPointList = new ArrayList<Point>(pointList);
				backupPointList.add(lastPoint_3D);
				backupPointList.add(0,originPoint);
				pointList = Line3DHelper.get3DLine(pointList, 0);
				pointList.add(0, new Point(originPoint.getX(), originPoint.getY()));
				//TODO : dyfunction of spline algorithm for some sets of data which is 
				// very clearly visible in 3D effect. Develop proper algorithm. 
				if(!isSpline){
					pointList.add(Line3DHelper.get3DPoint(originPoint));										
				}
				else{
					pointList.add(0, Line3DHelper.get3DPoint(originPoint));										
				}
				//pointList.add(0, Line3DHelper.get3DPoint(originPoint));					
			}
			else{
				pointList.add(0, new Point(originPoint.getX(), originPoint.getY()));				
			}
			if(isSpline){//chart.isSplineEffect()){
				linePath = Spline.getSplineForPoints(pointList);
				if(chart.is_3d()){
					_3DArea = Spline.getSplineForPoints(backupPointList);					
				}
			}
			else{
				linePath = Spline.getLineForPoints(pointList);
				if(chart.is_3d()){
					_3DArea = Spline.getLineForPoints(backupPointList);					
				}
			}
			
			if(chart.is_3d()){
				linePath.M(lastPoint_3D.getX(), lastPoint_3D.getY());
			}
			

//			linePath.L(originPoint.getX()+axisWidth, pointList.get(pointList.size()-1).getY());
			if(dataTable.getMin()<0){
				//linePath.L(originPoint.getX()+(axisWidth/2), pointList.get(pointList.size()-1).getY());	
				linePath.L(originPoint.getX()+(axisWidth/2), originPoint.getY());
			}
			else{
				linePath.L(originPoint.getX()+axisWidth, originPoint.getY());				
				if(chart.is_3d()){
					_3DArea.L(originPoint.getX()+axisWidth, originPoint.getY());
				}
			}
			
			
			
			linePath.Z();
			Path line = chart.new Path(linePath);
			line.attr("fill", color);
			line.attr("opacity", 0.5);
			line.attr("stroke-width", 3);
			line.setTitle(series);
			//chart.push(line);
			if(chart.is_3d()){
				_3DArea.Z();
				Path area3D = chart.new Path(_3DArea);
				area3D.attr("fill", color);
				area3D.attr("opacity", 0.5);
				area3D.attr("stroke-width", 3);
				area3D.setTitle(series);
				plotBox.push(area3D);
				chartSet.push(area3D);
			}
			chartSet.push(line);
			plotBox.push(line);
			pathMap.put(series, line);
			catIt = tickLabels.iterator();
			pointIt = tickPoints.iterator();
			
		}		
		this.plotSet = plotBox;
		
	}
	
	
	
	
	protected void create(boolean isBar, boolean isLineChart){
		com.hydro4ge.raphaelgwt.client.Raphael.Set plotBox = chart.new Set();
		List<Point> tickPoints =null;
		List<String> tickLabels = null;
		if(isBar){
			tickPoints = yAxis.getTickPoints();
			tickLabels = yAxis.getTickLabel();
		}
		else{
			tickPoints = xAxis.getTickPoints();
			tickLabels = xAxis.getTickLabel();
		}
		
		Point originPoint = tickPoints.get(0);
		double originX = originPoint.getX();
		double ratio = 0;
		double interval = tickPoints.get(1).getY() - tickPoints.get(0).getY();
		double interval2 = tickPoints.get(1).getX() - tickPoints.get(0).getX();
		if(isBar){
			ratio = axisWidth/dataTable.getMaxValue();
		}
		else{
			ratio = axisHeight/dataTable.getMaxValue();
		}
		if(dataTable.getMin() < 0){
			ratio = ratio/2;
		}
		java.util.Set<String> seriesNames = dataTable.getSeriesNames();
		Iterator<String> seriesIt = seriesNames.iterator();
		Iterator<String> catIt = tickLabels.iterator();
		Iterator<Point> pointIt = tickPoints.iterator();
		Shape org = chart.new Circle(originX, originPoint.getY(), .1);
		plotBox.push(org);
		//chart.push(org);
		chartSet.push(org);
		int i = 0;
		boolean move = true;
		while(seriesIt.hasNext()){
			PathBuilder linePath = new PathBuilder();
			String series = seriesIt.next();
			String color = colorMap.get(series);
			List<SPoint> pointList = new ArrayList<SPoint>();
			move = true;
			while(catIt.hasNext()){
				String category = catIt.next();
				Point domainPoint = pointIt.next();
				double value = dataTable.get(category, series);
				double valuePoint = ratio * value;
				Shape c = null;
				if(isBar){
					double y =interval + domainPoint.getY();
					double x = originX + valuePoint;
					c = chart.new Circle(x, y, POINT_RADIUS);
					if(move){
						linePath.M(x,y);
						move = false;
					}
					else{
						linePath.L(x,y);
						
					}
					pointList.add(new SPoint(x, y));
				}
				else{
					double x = interval2 + domainPoint.getX();
					double y = originPoint.getY() - valuePoint;

					//if line chart is need to be ploted in mid of the category slabe
					//TODO: implement for bar also;
					if(chart instanceof ComboChart){
						ComboChart comboCh = (ComboChart) chart;
						if(comboCh.isShowLinePointsAtMid()){
							x = x - interval2/2;
						}
					}
					
					c = chart.new Circle(x,y, POINT_RADIUS);
					if(move){
						linePath.M(x,y);
						move = false;
					}
					else{
						linePath.L(x,y);
						
					}
					pointList.add(new SPoint(x, y));
				}
				c.attr("fill", color);
				pointDataTable.add(c, category, series);
				plotBox.push(c);
				//chart.push(c);
				chartSet.push(c);
			}
			if(chart.is_3d()){
				pointList = Line3DHelper.get3DLine(pointList);
			}
			if(isSpline){//chart.isSplineEffect()){
				linePath = Spline.getSpline(pointList);
			}
			else{
				linePath = Spline.getLine(pointList);
			}
			if(chart.is_3d()){
				linePath.Z();
			}
			Path line = chart.new Path(linePath);
			line.attr("stroke", color);
			line.attr("stroke-width", 2);
			
			if(chart.is_3d()){
				line.attr("fill", color);
			}
			
			if(!isLineChart){
				line.hide();
			}
			
			plotBox.push(line);
			chartSet.push(line);
			pathMap.put(series, line);
			catIt = tickLabels.iterator();
			pointIt = tickPoints.iterator();
		}		
		this.plotSet = plotBox;
	}
	

	
	
	
	public CategoryDataTable<RaphaelObject> getPointDataTable(){
		return pointDataTable;
	}
	
	public Axis getXAxis(){
		return xAxis;
	}
	
	public Axis getYAxis(){
		return yAxis;
	}
	
	public RaphaelObject getScatterChart(){
//		create(true, false);
		create(chart.isBar(), false);
		ChartFactory.createChart(xAxis, yAxis, plotSet, dataTable.getMin());
		return chartSet;
	}
	
	public RaphaelObject getLineChart(){
//		LineChart ch = (LineChart)chart;
		create(chart.isBar(), true);
		ChartFactory.createChart(xAxis, yAxis, plotSet, dataTable.getMin());
		return chartSet;
	}
	
	public RaphaelObject getAreaChart(){
		createAreaChart(chart.isBar());
		ChartFactory.createChart(xAxis, yAxis, plotSet, dataTable.getMin());
		return chartSet;
	}

	public RaphaelObject getPlot(LineChartType type){
		switch(type){
		case SCATTER:
			create(chart.isBar(), false);
			return plotSet;
		case LINE:
			create(chart.isBar(), true);
			return plotSet;
		case AREA:
			createAreaChart(chart.isBar());
			return plotSet;
		default:
			return null;
		}
	}
}
