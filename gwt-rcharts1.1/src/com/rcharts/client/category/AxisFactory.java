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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.AxisConfiguration;
import com.rcharts.client.AxisLabelHelper;
import com.rcharts.client.Chart;
import com.rcharts.client.RaphaelFactory;

/**
 * Generates Axes for charts
 */

public class AxisFactory {

	private RaphaelObject xRaphaelAxis;
	private RaphaelObject yRaphaelAxis;
	/**
	 * By default xAxis would be taken as value axis for other case take care
	 */
	private Axis xAxis;
	private Axis yAxis;
	
	private Axis xAxisAtTop;
	private Axis yAxisAtRight;
	
	private Chart chart;
	private AxisConfiguration axisConfiguration;
	
	
	public AxisFactory(){
		chart = RaphaelFactory.get();
		axisConfiguration = chart.getAxisConfiguration();
	}
	
	private void createXAxis(){
		List<String> labels = new ArrayList<String>(chart.getXAxisLabels());
		Axis axis = null;//new AxisPositive(labels, Orientation.HORIZONTAL,
					//AxisLabelHelper.getXAxisLength());
		if(chart.is_3d()){
			axis = new Axis3D();
			axis.setTickLabel(labels);
			axis.setOrientation(Orientation.HORIZONTAL);
			axis.setAxisLength(AxisLabelHelper.getXAxisLength());
		}
		else{
			axis = new AxisPositive(labels, Orientation.HORIZONTAL,
						AxisLabelHelper.getXAxisLength());
		}
		axis.setGridLineLength(AxisLabelHelper.getYAxisLength());
		axis.setValueAxis(chart.isXValueAxis());
		axis.setShowGridLine(chart.isShowGridLine());
		axis.init();
		axis.makeAxis();
		if(!chart.is_3d()){
			if(!axisConfiguration.isDoubleXAxis()){
				if(axisConfiguration.isxAxisAtNorth()){
					axis.getSecondAxis().attr("opacity", 0);
				}
				else{
					axis.getFirstAxis().attr("opacity", 0);
				}
			}			
		}
		chart.setxAxis(axis);
		xAxis = axis;
		if(chart.isShowGridLine()){
			axis.showGridLine();
		}
	}
	
	private void createYAxis(){

		List<String> labels = new ArrayList<String>(chart.getYAxisLabels());
		Axis axis = null;//new AxisPositive(labels, Orientation.VERTICAL, AxisLabelHelper.getYAxisLength());
		if(chart.is_3d()){
			axis = new Axis3D();
			axis.setTickLabel(labels);
			axis.setOrientation(Orientation.VERTICAL);
			axis.setAxisLength(AxisLabelHelper.getYAxisLength());
		}
		else{
			axis = new AxisPositive(labels, Orientation.VERTICAL, AxisLabelHelper.getYAxisLength());
		}
		axis.setGridLineLength(AxisLabelHelper.getXAxisLength());
		axis.setValueAxis(chart.isYValueAxis());
		axis.init();
		axis.makeAxis();	
		if(!chart.is_3d()){
			if(!axisConfiguration.isDoubleYAxis()){
				if(axisConfiguration.isyAxisAtEast()){
					axis.getFirstAxis().attr("opacity", 0);
				}
				else{
					axis.getSecondAxis().attr("opacity", 0);
				}
			}			
		}
		chart.setyAxis(axis);
		yAxis = axis;
		if(chart.isShowGridLine()){
			axis.showGridLine();
		}
	}
	
	public RaphaelObject getXRaphaelAxis(){
		if(xAxis == null){
			createXAxis();
		}
		return xAxis.get();
	}
	
	public RaphaelObject getYRaphaelAxis(){
		if(yAxis == null){
			createYAxis();
		}
		return yAxis.get();
	}
	
	public Axis getXAxis(){
		if(xAxis == null){
			createXAxis();
		}
		return xAxis;
	}
	
	public Axis getYAxis(){
		if(yAxis == null){
			createYAxis();
		}
		return yAxis;
	}
	
	public void setXAxis(Axis xAxis){
		this.xAxis = xAxis;
	}
	
	public void setYAxis(Axis yAxis){
		this.yAxis = yAxis;
	}
}
