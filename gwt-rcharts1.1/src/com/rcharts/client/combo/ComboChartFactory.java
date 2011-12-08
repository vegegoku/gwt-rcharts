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
package com.rcharts.client.combo;

import java.util.Collections;
import java.util.List;

import com.hydro4ge.raphaelgwt.client.BBox;
import com.hydro4ge.raphaelgwt.client.Raphael.Set;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.Align;
import com.rcharts.client.AxisLabelHelper;
import com.rcharts.client.Point;
import com.rcharts.client.RHorizontalPanel;
import com.rcharts.client.RVerticalPanel;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.category.Axis;
import com.rcharts.client.combo.ComboChart.ComboAxisPlot;

public class ComboChartFactory {

	private static ComboChart chart = (ComboChart) RaphaelFactory.get();
	private static Point origin = new Point(0, 0);
	
	public static Set createChart(List<ComboAxisPlot> list){
		Set leftAxesSet = chart.new Set();
		Set centerPlot = chart.new Set();
		Set rightAxesSet = chart.new Set();
		Set bottomAxesSet = chart.new Set();
		int i = 0;
		Axis firstXAxis = list.get(0).xAxis;
		Axis firstYAxis = list.get(0).yAxis;
		Collections.reverse(list);
		RHorizontalPanel leftPanel = new RHorizontalPanel(0);
		RHorizontalPanel rightPanel = new RHorizontalPanel();
		RVerticalPanel bottomPanel = new RVerticalPanel();
		Align hAlign = Align.BOTTOM;
		Axis preAxis = null;
		for(ComboAxisPlot axisPlot : list){		
			Axis xAxis = axisPlot.xAxis;
			Axis yAxis = axisPlot.yAxis;
			Set plot = (Set) axisPlot.plot;			
			i = list.indexOf(axisPlot);
			if((i%2) != 0) {//i == 0 || (i%2 != 0 && i != 1)){
				yAxis.get().attr("fill", "red");
				//leftPanel.add(yAxis.get(), hAlign);
				RaphaelObject ro = yAxis.getSecondAxisSet();
				ro.attr("opacity", 1);
				ro = yAxis.getFirstAxisSet();
				ro.attr("opacity", 0);
				rightPanel.add(yAxis.get(), hAlign);
			}
			else if( (i%2) == 0){ //i == 1 || (i%2 == 0 && i != 0)){
				RaphaelObject ro = yAxis.getSecondAxisSet();
			//	ro.attr("opacity", 1);
			//	yAxis.get().attr("fill", "blue");
			//	rightPanel.add(yAxis.get(), hAlign);
				leftPanel.add(yAxis.get(), hAlign);
			}
			if(preAxis != xAxis || i == 0){
				bottomPanel.add(xAxis.get(), Align.LEFT);				
			}
			double dx = origin.getX() - plot.getBBox().x();
			double dy = origin.getY() - plot.getBBox().y() - plot.getBBox().height();
			plot.translate(dx, dy);			
			centerPlot.push(plot);
			i++;
			preAxis = xAxis;
		}
		leftAxesSet.push(leftPanel.get());
		rightAxesSet.push(rightPanel.get());
		bottomAxesSet.push(bottomPanel.get());
		
		BBox leftBox = firstYAxis.getAxisLine().getBBox();
		double lDx = origin.getX() - leftBox.x();// - (new ComboAxisLabelHelper(chart).getAxisWidth(0)-10)/2;// - leftBox.width()/2;
		double lDy = origin.getY() - leftBox.height() - leftBox.y();
		leftAxesSet.translate(lDx, lDy);
		
		BBox bottomBox = firstXAxis.getAxisLine().getBBox();
		double bDx = origin.getX() - bottomBox.x();
		double bDy = origin.getY()- bottomBox.height() - bottomBox.y();// - AxisLabelHelper.getYAxisLength();// + (new ComboAxisLabelHelper(chart).getAxisHeight()-10)/2;
		bottomAxesSet.translate(bDx, bDy);
		
		BBox centerBox = centerPlot.getBBox();
		double cDx = origin.getX() - centerBox.x();
		double cDy = origin.getY() -centerBox.height() - centerBox.y();
		centerPlot.translate(cDx, cDy);

		
		BBox rightBox = rightAxesSet.getBBox();
		double rDx = origin.getX() + centerBox.width() - rightBox.x();
		double rDy = origin.getY() - rightBox.height() - rightBox.y();
		rightAxesSet.translate(rDx, rDy);
		
		Set chartSet = chart.new Set();
		chartSet.push(leftAxesSet);
		chartSet.push(centerPlot);
		chartSet.push(bottomAxesSet);
		chartSet.push(rightAxesSet);
		return chartSet;
	}
}
