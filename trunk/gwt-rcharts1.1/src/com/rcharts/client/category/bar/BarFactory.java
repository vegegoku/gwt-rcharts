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
package com.rcharts.client.category.bar;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.Window;
import com.hydro4ge.raphaelgwt.client.Raphael.Rect;
import com.hydro4ge.raphaelgwt.client.Raphael.Shape;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.AxisLabelHelper;
import com.rcharts.client.Chart;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.category.Axis;
import com.rcharts.client.category.Axis3D;
import com.rcharts.client.category.CategoryChart;
import com.rcharts.client.category.CategoryDataTable;
import com.rcharts.client.category.Rect3D;
import com.rcharts.client.combo.ComboChart;

public class BarFactory {

	protected Chart chart;
	protected CategoryDataTable<RaphaelObject> barDataTable;
	protected CategoryDataTable<Double> dataTable;
	protected double axisWidth;
	protected double axisHeight;
	private Map<String, String> colorMap;
	protected double barGap;
	protected double barGroupGap;
	private boolean isStacked = false;
	private double zDim = Axis.tickMarkLength/2;
	
	
	/**
	 * Constructor for BarFactory initialize by reference of chart
	 * For chart other than BarChart barGap, barGroupGap and isStacked has to be
	 * initialized separately by setter methods
	 */
	public BarFactory(){
		try{
			chart = RaphaelFactory.get();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		if(chart instanceof CategoryChart){
			CategoryChart bc = (CategoryChart)chart;
			dataTable = bc.getDataTable();
		}
//		axisWidth = AxisLabelHelper.getXAxisLength();
		if(chart instanceof ComboChart){
			ComboChart comboCh = (ComboChart) chart;
			axisWidth = comboCh.getRealAxisLenth();
		}
		else{
			axisWidth = AxisLabelHelper.getXAxisLength();			
		}
		axisHeight = AxisLabelHelper.getYAxisLength();
		colorMap = RaphaelFactory.getColorMap();
		if(chart instanceof BarChart){
			BarChart barChart = (BarChart) chart;
			barGap =barChart.getBarGap();
			barGroupGap = barChart.getBarGroupGap();			
			isStacked = barChart.isStacked();
		}
		barDataTable = new CategoryDataTable<RaphaelObject>();
	}

	public CategoryDataTable<Double> getDataTable() {
		return dataTable;
	}

	public void setDataTable(CategoryDataTable<Double> dataTable) {
		this.dataTable = dataTable;
	}

	public double getAxisWidth() {
		return axisWidth;
	}

	public void setAxisWidth(double axisWidth) {
		this.axisWidth = axisWidth;
	}

	public double getAxisHeight() {
		return axisHeight;
	}

	public void setAxisHeight(double axisLength) {
		this.axisHeight = axisLength;
	}

	public Map<String, String> getColorMap() {
		return colorMap;
	}

	public void setColorMap(Map<String, String> colorMap) {
		this.colorMap = colorMap;
	}

	/**
	 * @return the barGap
	 */
	public double getBarGap() {
		return barGap;
	}

	/**
	 * @param barGap the barGap to set
	 */
	public void setBarGap(double barGap) {
		this.barGap = barGap;
	}

	/**
	 * @return the barGroupGap
	 */
	public double getBarGroupGap() {
		return barGroupGap;
	}

	/**
	 * @param barGroupGap the barGroupGap to set
	 */
	public void setBarGroupGap(double barGroupGap) {
		this.barGroupGap = barGroupGap;
	}

	/**
	 * @return the isStacked
	 */
	public boolean isStacked() {
		return isStacked;
	}

	/**
	 * @param isStacked the isStacked to set
	 */
	public void setStacked(boolean isStacked) {
		this.isStacked = isStacked;
	}

	public void init(CategoryDataTable<Double> dataTable, double axisWidth,
			double axisLength, Map<String, String> colorMap) {
		this.dataTable = dataTable;
		this.axisWidth = axisWidth;
		this.axisHeight = axisLength;
		this.colorMap = colorMap;
	}

	protected double getBarSecondDimension(boolean isBar){
		
		double noOfBarGap = dataTable.getCategoryCount() * (dataTable.getSeriesCount()-1);
		double noOfBars = dataTable.getCategoryCount() * dataTable.getSeriesCount();
		double totalBarGapLength = (noOfBarGap) * barGap;
		
		//if bars are being stacked no gap is there between bars		
		double totalBarGroupGapLength = 0;
		//note : noOfBarGaps are deducted by category gaps as bar gap merges there
		double catCount = dataTable.getCategoryCount();
//		totalBarGapLength = (noOfBarGaps - catCount + 1) * barGap;
		totalBarGroupGapLength = (catCount) * barGroupGap;
	
		//ensuring barGap and barGroupGap do not exceed to kick off the bars
		double totalGapSpace = totalBarGapLength + totalBarGapLength;
		
		if((isBar && totalGapSpace >= (axisHeight - noOfBars)) 
				|| (!isBar && totalGapSpace >= (axisWidth - noOfBars))){
			barGap = barGap - 1.5;
			barGroupGap = barGroupGap - 1.5;
			totalBarGapLength = noOfBars * barGap;
			totalBarGroupGapLength = (catCount - 1) * barGroupGap;
		}
		double dimension = 0;
		if(isBar){
			double height = axisHeight - (totalBarGapLength + totalBarGroupGapLength);
			dimension = height/noOfBars;
		}
		else{
			double width = axisWidth - (totalBarGapLength + totalBarGroupGapLength);
			dimension = width/noOfBars;
		}
		if(isStacked){
			dimension = dimension * dataTable.getSeriesCount();
		}
		if(chart.is_3d()){
			double rad = Math.toRadians(Axis3D.angle);
			zDim = dimension/2;
			if(zDim > Axis.tickMarkLength){
				zDim = Axis.tickMarkLength;
			}
			if(isBar){
				return dimension - (zDim * Math.cos(rad));				
			}
			else{
				return dimension - (zDim * Math.sin(rad));
			}
		}
		return dimension;
	}
	
	protected double getBarHeight(){
		return getBarSecondDimension(true);
	}
	
	protected double getColumnWidth(){
		return getBarSecondDimension(false);
	}
	
	
	/**
	 * @return the zDim
	 */
	public double getzDim() {
		return zDim;
	}

	/**
	 * @param zDim the zDim to set
	 */
	public void setzDim(double zDim) {
		this.zDim = zDim;
	}

	protected void create(boolean isBar){
		double ratio = 0;
		double dataTableMaxValue = dataTable.getMaxValue();
		if(isStacked){
			dataTableMaxValue = dataTable.getMaxForStacked();
		}

		if(isBar){
			ratio = axisWidth/dataTableMaxValue;
		}
		else{
			ratio = axisHeight/dataTableMaxValue;
		}
		if(dataTable.getMin() < 0){
			ratio = ratio/2;
		}
		Set<String> categoryNames = dataTable.getCategoryNames();
		Set<String> seriesNames = dataTable.getSeriesNames();
		Iterator<String> catIt = categoryNames.iterator();
		Iterator<String> seriesIt = seriesNames.iterator();		
		double barHeight = 0;
		double columnWidth = 0;
		if(isBar){
			barHeight = getBarHeight();
		}
		else{
			columnWidth = getColumnWidth();
		}
		while(seriesIt.hasNext()){
			String series = seriesIt.next();
			while(catIt.hasNext()){
				String category = catIt.next();
				double value = dataTable.get(category, series);
				double barValueDimension = ratio * value;
				Shape r = null;
				if(isBar){
					if(chart.is_3d()){
						//getBarHeight();
//						r = Rect3D.get3DRect(chart, barValueDimension, zDim, 
//								getBarHeight(), Axis3D.angle);						
						r = Rect3D.get3DRect(chart, barValueDimension, zDim, 
								barHeight, Axis3D.angle);						
					}
					else if(barValueDimension > 0){
//						r = chart.new Rect(0, 0, barValueDimension, getBarHeight());																
						r = chart.new Rect(0, 0, barValueDimension, barHeight);																
					}
					else{
//						r = chart.new Rect(barValueDimension, 0, -barValueDimension, getBarHeight());
						r = chart.new Rect(barValueDimension, 0, -barValueDimension, barHeight);
					}
				}
				else{
					if(chart.is_3d()){
//						r = Rect3D.get3DRect(chart, getColumnWidth(), 
//								zDim, barValueDimension , Axis3D.angle);
						r = Rect3D.get3DRect(chart, columnWidth, 
								zDim, barValueDimension , Axis3D.angle);
					}
					else if(barValueDimension > 0){
//						r = chart.new Rect(0, 0,getColumnWidth(), barValueDimension);
						r = chart.new Rect(0, 0, columnWidth, barValueDimension);
					}
					else{
//						r = chart.new Rect(0, barValueDimension, getColumnWidth(), -barValueDimension);
						r = chart.new Rect(0, barValueDimension, columnWidth, -barValueDimension);
					}
				}
				barDataTable.add(r, category, series);
			}
			catIt = categoryNames.iterator();
		}		
	}
	
	public double getZDim(){
		return zDim;
	}
	
	public CategoryDataTable<RaphaelObject> getBarDataTable(){
		create(true);
		return barDataTable;
	}
	
	public CategoryDataTable<RaphaelObject> getColumnDataTable(){
		create(false);
		return barDataTable;
	}
	
}
