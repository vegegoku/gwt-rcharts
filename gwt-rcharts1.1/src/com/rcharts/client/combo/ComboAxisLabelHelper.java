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

import java.util.List;

import com.hydro4ge.raphaelgwt.client.PathBuilder;
import com.hydro4ge.raphaelgwt.client.Raphael.Path;
import com.hydro4ge.raphaelgwt.client.Raphael.Set;
import com.hydro4ge.raphaelgwt.client.Raphael.Text;
import com.rcharts.client.Chart;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.category.Axis;
import com.rcharts.client.category.CategoryChart;
import com.rcharts.client.category.CategoryDataTable;
import com.rcharts.client.combo.ComboUtil.ComboChartStyle;
import com.rcharts.client.xychart.XYChart;

public class ComboAxisLabelHelper {

	private List<ComboChartStyle> chartList;
	private ComboChart chart;
	
	public ComboAxisLabelHelper(ComboChart chart){
		this.chart = chart;
		chartList = chart.comboChartList;
	}
	
	public double getAxisHeight(){
		double axisHeight = 0;
		java.util.Set<String> cats = null;
		java.util.Set<String> preCats = null;
		CategoryDataTable<Double> dataTable = null;
		for(ComboChartStyle chart : chartList){
			dataTable = chart.getDataTable();
			cats = dataTable.getCategoryNames();
			if(preCats != null && !cats.equals(preCats)){
				double width = getXHeight(dataTable, chart);
				if(width > axisHeight){
					axisHeight = width;
				}
			}
			else{
				axisHeight = getXHeight(dataTable, chart);
			}
			preCats = cats;
		}
		return axisHeight;
	}
	
	public double getAxisWidth(double gridLineLength){
		double axisWidth = 0;
		CategoryDataTable<Double> dataTable = null;
		for(ComboChartStyle chart : chartList){
			dataTable = chart.getDataTable();
			double width = getYWidth(dataTable, chart);
			axisWidth = axisWidth + width;
		}
		axisWidth = axisWidth + ((chartList.size()-2)*gridLineLength);
		return axisWidth;
	}
	
	public double getXHeight(CategoryDataTable<Double> dataTable, ComboChartStyle style){
		double height =0;
		double maxValue = dataTable.getMaxValue();
		String lengthyCat = dataTable.getMostLenthgyCategory();
		double tickMarkLength = Axis.tickMarkLength;
		PathBuilder pb = new PathBuilder();
		pb.M(0, 0);
		pb.L(10,0);
		Path axisPath = chart.new Path(pb);
		pb = new PathBuilder();
		pb.M(0, 0);
		pb.L(0, tickMarkLength);
		Path tickPath = chart.new Path(pb);
		Text label = null;
		if(style.isBar()){
			label = chart.new Text(0, tickMarkLength+20, maxValue+"");
		}
		else{
			label = chart.new Text(0, tickMarkLength+20, lengthyCat);
		}
		axisPath.attr(style.getAxisStyle());
		label.attr(style.getAxisTickLabelStyle());
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

	
	public double getYWidth(CategoryDataTable<Double> dataTable, ComboChartStyle style){
		double width = 0;
		double maxValue = dataTable.getMaxValue();
		String lengthyCat = dataTable.getMostLenthgyCategory();
		double tickMarkLength = Axis.tickMarkLength;
		PathBuilder pb = new PathBuilder();
		pb.M(0, 0);
		pb.L(0, 10);
		Path axisPath = chart.new Path(pb);
		pb = new PathBuilder();
		pb.M(0, 0);
		pb.L(tickMarkLength, 0);
		Path tickPath = chart.new Path(pb);
		Text label = null;
		if(style.isBar()){
			label = chart.new Text(tickMarkLength+20, 0, maxValue+"");
		}
		else{
			label = chart.new Text(tickMarkLength+20, 0, lengthyCat);
		}
		axisPath.attr(style.getAxisStyle());
		label.attr(style.getAxisTickLabelStyle());
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
