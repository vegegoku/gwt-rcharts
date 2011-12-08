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
package com.rcharts.client;

import com.google.gwt.user.client.Window;
import com.hydro4ge.raphaelgwt.client.BBox;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.category.Axis;
import com.rcharts.client.category.Axis3D;
import com.rcharts.client.category.Rect3D;
import com.rcharts.client.category.bar.BarChart;
import com.rcharts.client.xychart.XYDataTable;

/**
 * 
 * The sole purpose of this class is to take align the plot properly for charts havind Axex
 * i.e Category and XY charts. It aligns the plot of X and Y axis and the plot area under it
 * and align them all for any arbitrary origin point.
 *
 */
public class ChartFactory {

	private static Chart chart = RaphaelFactory.get();
	
	/**
	 * 
	 * @param xAxis 
	 * @param yAxis
	 * @param plotSet
	 * @param min  The minimum value of dataTable 
	 * @param isBar 
	 */
	public static void createChart(Axis xAxis, Axis yAxis, RaphaelObject plotSet
			, double min){
		chart = RaphaelFactory.get();
		Point origin = new Point(100, 100);
		RaphaelObject xRaphaelAxis = xAxis.get();
		RaphaelObject yRaphaelAxis = yAxis.get();
		double axisWidth = AxisLabelHelper.getXAxisLength();
		double axisHeight = AxisLabelHelper.getYAxisLength();
		String m = null;
		double xMax = 0;
		double yMax = 0;	
		
		if(xAxis.isValueAxis()){
			m = xAxis.getTickLabel().get(xAxis.getTickLabel().size() -1);
			xMax = Double.parseDouble(m);
		}
		if(yAxis.isValueAxis()){
			m = yAxis.getTickLabel().get(yAxis.getTickLabel().size() - 1);
			yMax = Double.parseDouble(m);
		}
		
		BBox xBox = xAxis.getAxisLine().getBBox();
		double xDx = origin.getX() - xBox.x();
		double xDy = origin.getY() - xBox.y() -xBox.height();
		xRaphaelAxis.translate(xDx, xDy);

		BBox yBox = yAxis.getAxisLine().getBBox();
		double yDx = origin.getX() - yBox.x();// - yBox.width();
		double yDy = (origin.getY() - yBox.height()) - yBox.y();
		yRaphaelAxis.translate(yDx, yDy);		

		BBox cBox = plotSet.getBBox();
		double cDx = origin.getX() - cBox.x();
		double cDy = (origin.getY() - cBox.height()) - cBox.y();
		if(min < 0){
			if(xAxis.isValueAxis()){
				double ratio = axisWidth/(xMax*2);
				double diff = ratio * (xMax+min);
				cDx = cDx + diff;
			}
			if(yAxis.isValueAxis()){
				double ratio = axisHeight/(yMax*2);
				double diff = ratio * (yMax+min);
				cDy = cDy - diff;
			}
			
		}
		plotSet.translate(cDx, cDy);
		if(chart.is_3d()){
			double dx = Axis.tickMarkLength/2;
			if(chart instanceof BarChart){
				BarChart barChart = (BarChart) chart;
				dx = barChart.getzDim();
			}
//			Point p = Rect3D.get3DTranslationPoint(Axis.tickMarkLength/2, Axis3D.angle);
			Point p = Rect3D.get3DTranslationPoint(dx, Axis3D.angle);
			plotSet.translate(-p.getX(), p.getY());
			plotSet.toFront();
		}
	}
	
	public static void createXYChart(Axis xAxis, Axis yAxis, RaphaelObject plotSet,
			XYDataTable<Double> dataTable){
		chart = RaphaelFactory.get();
		Point origin = new Point(100, 100);
		RaphaelObject xRaphaelAxis = xAxis.get();
		RaphaelObject yRaphaelAxis = yAxis.get();
		double axisWidth = AxisLabelHelper.getXAxisLength();
		double axisHeight = AxisLabelHelper.getYAxisLength();
		String m = null;
		double xMax = 0;
		double yMax = 0;
		
		m = xAxis.getTickLabel().get(xAxis.getTickLabel().size() -1);
		xMax = Double.parseDouble(m);
		m = yAxis.getTickLabel().get(yAxis.getTickLabel().size() - 1);
		yMax = Double.parseDouble(m);
		
/*		BBox xBox = xAxis.getAxisLine().getBBox();
		double xDx = origin.getX() - xBox.x();
		double xDy = origin.getY() - xBox.y();
		xRaphaelAxis.translate(xDx, xDy);

		BBox yBox = yAxis.getAxisLine().getBBox();
		double yDx = origin.getX() - yBox.x();// - yBox.width();
		double yDy = (origin.getY() - yBox.height()) - yBox.y();
		yRaphaelAxis.translate(yDx, yDy);		
*/
		BBox xBox = xAxis.getAxisLine().getBBox();
		double xDx = origin.getX() - xBox.x();
		double xDy = origin.getY() - xBox.y() -xBox.height();
		xRaphaelAxis.translate(xDx, xDy);

		BBox yBox = yAxis.getAxisLine().getBBox();
		double yDx = origin.getX() - yBox.x();// - yBox.width();
		double yDy = (origin.getY() - yBox.height()) - yBox.y();
		yRaphaelAxis.translate(yDx, yDy);		
		
		
		BBox cBox = plotSet.getBBox();
		double cDx = origin.getX() - cBox.x();
		double cDy = (origin.getY() - cBox.height()) - cBox.y();
		double xMin = dataTable.getxMin();
		double yMin = dataTable.getyMin();
		if(xMin < 0){
			double ratio = axisWidth/(xMax*2);
			double diff = ratio * (xMax+xMin);
			cDx = cDx + diff;			
		}
		if(yMin < 0){
			double ratio = axisHeight/(yMax*2);
			double diff = ratio * (yMax+yMin);
			cDy = cDy - diff;			
		}

		plotSet.translate(cDx, cDy);

	}
	
}
