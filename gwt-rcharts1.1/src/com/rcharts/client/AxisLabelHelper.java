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
import com.hydro4ge.raphaelgwt.client.PathBuilder;
import com.hydro4ge.raphaelgwt.client.Raphael.Path;
import com.hydro4ge.raphaelgwt.client.Raphael.Set;
import com.hydro4ge.raphaelgwt.client.Raphael.Text;
import com.rcharts.client.category.Axis;
import com.rcharts.client.category.CategoryChart;
import com.rcharts.client.combo.ComboChart;
import com.rcharts.client.xychart.XYChart;

public class AxisLabelHelper {
	
	public static double getXAxisLength(){
		return RaphaelFactory.get().chartPanel.getWidth()-10-getYWidth();
	}
	
	public static double getYAxisLength(){
		return RaphaelFactory.get().chartPanel.getHeight()-10-getXHeight();
	}
	
	public static double getXHeight(){
		double height =0;
		Chart chart = RaphaelFactory.get();
		if(chart instanceof ComboChart){
			return ((ComboChart) chart).getAxisHeightOffset();
		}
		double maxValue = 0;
		String lengthyCat = null;
		double tickMarkLength = Axis.tickMarkLength;
		if(chart instanceof CategoryChart){
			CategoryChart ch = ((CategoryChart)chart);
			maxValue = ch.getDataTable().getMaxValue();
			lengthyCat = ch.getDataTable().getMostLenthgyCategory();
		}
		else{
			if(chart instanceof XYChart){
				XYChart ch = (XYChart) chart;
				maxValue = ch.getDataTable().getxMax();
			}
		}
		PathBuilder pb = new PathBuilder();
		pb.M(0, 0);
		pb.L(10,0);
		Path axisPath = chart.new Path(pb);
		pb = new PathBuilder();
		pb.M(0, 0);
		pb.L(0, tickMarkLength);
		Path tickPath = chart.new Path(pb);
		Text label = null;
		if(chart.isXValueAxis()){
			label = chart.new Text(0, tickMarkLength+20, maxValue+"");
		}
		else{
			label = chart.new Text(0, tickMarkLength+20, lengthyCat);
		}
		axisPath.attr(chart.getAxisStyle());
		label.attr(chart.getAxisTickLabelStyle());
		Set set = chart.new Set();
		set.push(axisPath);
		set.push(tickPath);
		set.push(label);
		height = set.getBBox().height();
		set.remove();
		if(chart.is_3d()){
			return height * 1.5;
		}
		return height*2;
	}
	
	
	public static double getYWidth(){
		double width = 0;
		Chart chart = RaphaelFactory.get();
		if(chart instanceof ComboChart){
			return ((ComboChart) chart).getAxisWidthOffset();
		}
		double maxValue = 0;
		String lengthyCat = null;
		double tickMarkLength = Axis.tickMarkLength;
		if(chart instanceof CategoryChart){
			CategoryChart ch = ((CategoryChart)chart);
			maxValue = ch.getDataTable().getMaxValue();
			lengthyCat = ch.getDataTable().getMostLenthgyCategory();
		}
		else{
			if(chart instanceof XYChart){
				XYChart ch = (XYChart) chart;
				maxValue = ch.getDataTable().getxMax();
			}
		}
		PathBuilder pb = new PathBuilder();
		pb.M(0, 0);
		pb.L(0, 10);
		Path axisPath = chart.new Path(pb);
		pb = new PathBuilder();
		pb.M(0, 0);
		pb.L(tickMarkLength, 0);
		Path tickPath = chart.new Path(pb);
		Text label = null;
		if(chart.isXValueAxis()){
			label = chart.new Text(tickMarkLength+20, 0, maxValue+"");
		}
		else{
			label = chart.new Text(tickMarkLength+20, 0, lengthyCat);
		}
		axisPath.attr(chart.getAxisStyle());
		label.attr(chart.getAxisTickLabelStyle());
		label.attr("text-anchor", "start");
		Set set = chart.new Set();
		set.push(axisPath);
		set.push(tickPath);
		set.push(label);
		width = set.getBBox().width();
		set.remove();
		if(chart.is_3d()){
			return width * 1.5;
		}
		return width*2;
	}
}

