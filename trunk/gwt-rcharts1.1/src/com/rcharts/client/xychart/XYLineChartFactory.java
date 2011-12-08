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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.hydro4ge.raphaelgwt.client.PathBuilder;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.hydro4ge.raphaelgwt.client.Raphael.Circle;
import com.hydro4ge.raphaelgwt.client.Raphael.Path;
import com.hydro4ge.raphaelgwt.client.Raphael.Set;
import com.hydro4ge.raphaelgwt.client.Raphael.Shape;
import com.rcharts.client.AxisLabelHelper;
import com.rcharts.client.Chart;
import com.rcharts.client.ChartFactory;
import com.rcharts.client.Point;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.ValueAxisLabelMaker;
import com.rcharts.client.category.Axis;
import com.rcharts.client.category.AxisFactory;
import com.rcharts.client.category.CategoryDataTable;

public class XYLineChartFactory {
	
	public static class PointData{
		private Point point;
		private RaphaelObject rPoint;
		
		public PointData(Point point, RaphaelObject rPoint){
			this.point = point;
			this.rPoint = rPoint;
		}
		
		public Point getPoint(){
			return point;
		}
		
		public RaphaelObject getRaphaelPoint(){
			return rPoint;
		}
	}
	
	private XYChart chart;
	private Set chartSet;
	private Stack<RaphaelObject> chartStack;
	private Axis xAxis;
	private Axis yAxis;
	private double axisWidth;
	private double axisHeight;
	private Map<String, String> colorMap;
	private Set plotSet;
	private XYDataTable<Double> dataTable;
	private Map<String, List<PointData>> pointDataTable;
	
	public static double POINT_RADIUS = 3;
	
	
	
	public XYLineChartFactory(){
		chart = (XYChart)RaphaelFactory.get();
		dataTable = chart.getDataTable();
		colorMap = RaphaelFactory.getColorMap();
		chartSet = chart.new Set();
		chartStack = new Stack<RaphaelObject>();
		AxisFactory axisFactory = new AxisFactory();
		xAxis = axisFactory.getXAxis();
		yAxis = axisFactory.getYAxis();
		axisWidth = AxisLabelHelper.getXAxisLength();
		axisHeight = AxisLabelHelper.getYAxisLength();
		plotSet = chart.new Set();
		pointDataTable = new HashMap<String, List<PointData>>();
		pushAxisToChart();
	}
	
	private void pushAxisToChart(){
		List<Shape> list = xAxis.getAxisList();
		for(Shape s : list){
			chartSet.push(s);
		}
		list = yAxis.getAxisList();
		for(Shape s : list){
			chartSet.push(s);
		}
	}
	
	
	protected void createXYLineChart(boolean isLineChart){
		com.hydro4ge.raphaelgwt.client.Raphael.Set plotBox = chart.new Set();

		Point originPoint = new Point(xAxis.getTickPoints().get(0).getX(), yAxis.getTickPoints().get(0).getY());
		Shape org = chart.new Circle(originPoint.getX(), originPoint.getY(), .1);
		plotBox.push(org);
		chartStack.push(org);
		chartSet.push(org);
		double xMax = Double.parseDouble(xAxis.getTickLabel().get(xAxis.getTickLabel().size() -1));
		double yMax = Double.parseDouble(yAxis.getTickLabel().get(xAxis.getTickLabel().size() -1));
		double xRatio = axisWidth/xMax;//dataTable.getxMax();
		double yRatio = axisHeight/yMax;//dataTable.getyMax();
		if(dataTable.getxMin() < 0){
			xRatio = xRatio/2;
		}
		if(dataTable.getyMin() < 0){
			yRatio = yRatio/2;
		}
		
		java.util.Set<String> seriesNames = dataTable.getSeriesNames();
		Iterator<String> seriesIt = seriesNames.iterator();
		boolean move = true;
		while(seriesIt.hasNext()){
			List<PointData> pData = new ArrayList<XYLineChartFactory.PointData>();
			String series = seriesIt.next();
			List<Point> points = dataTable.getPointMap().get(series);
			String color = colorMap.get(series);
			move = true;
			PathBuilder linePath = new PathBuilder();
			for(Point point : points){
				double x = originPoint.getX() + (point.getX() * xRatio);
				double y = originPoint.getY() - (point.getY() * yRatio);
				Circle c = chart.new Circle(x, y, POINT_RADIUS);
				c.attr("fill", color);
				if(move){
					linePath.M(x, y);
					move = false;
				}
				else{
					linePath.L(x, y);
				}
				plotBox.push(c);
				chartStack.push(c);
				chartSet.push(c);
				pData.add(new PointData(point, c));
			}
			Path line = chart.new Path(linePath);
			line.attr("stroke", color);
			line.attr("stroke-width", 2);
			if(!isLineChart){
				line.hide();
			}
			plotBox.push(line);
			chartSet.push(line);
			pointDataTable.put(series, pData);
		
		}
		this.plotSet = plotBox;
	}

	
	public Map<String, List<PointData>> getPointDataTable(){
		return pointDataTable;
	}
	
	public RaphaelObject getLineChart(){
		createXYLineChart(true);
		ChartFactory.createXYChart(xAxis, yAxis, plotSet, dataTable);
		return chartSet;
	}
	
	public RaphaelObject getScatterChart(){
		createXYLineChart(false);
		ChartFactory.createXYChart(xAxis, yAxis, plotSet, dataTable);
		return chartSet;		
	}
}
