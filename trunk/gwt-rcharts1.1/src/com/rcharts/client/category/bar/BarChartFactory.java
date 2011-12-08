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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.google.gwt.user.client.Window;
import com.hydro4ge.raphaelgwt.client.Raphael.Rect;
import com.hydro4ge.raphaelgwt.client.Raphael.Shape;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.Align;
import com.rcharts.client.AxisLabelHelper;
import com.rcharts.client.Chart;
import com.rcharts.client.ChartFactory;
import com.rcharts.client.Console;
import com.rcharts.client.Point;
import com.rcharts.client.RCellPanel;
import com.rcharts.client.RHorizontalPanel;
import com.rcharts.client.RVerticalPanel;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.RCellPanel.Movement;
import com.rcharts.client.category.Axis;
import com.rcharts.client.category.Axis3D;
import com.rcharts.client.category.AxisFactory;
import com.rcharts.client.category.CategoryChart;
import com.rcharts.client.category.CategoryDataTable;
import com.rcharts.client.combo.ComboChart;

public class BarChartFactory {

//	protected BarChart chart;
	protected Chart chart;
	protected double axisWidth;
	protected double axisHeight;
	protected CategoryDataTable<Double> dataTable;
	protected CategoryDataTable<RaphaelObject> barDataTable;
	private double valueInterval = -1;
	protected double barGap;
	protected double barGroupGap;
	protected Map<String, String> colorMap;
	private Point origin = new Point(100, 100);
	private RaphaelObject xRaphaelAxis;
	private RaphaelObject yRaphaelAxis;
	private Axis xAxis;
	private Axis yAxis;
	protected RaphaelObject barSet;
	protected com.hydro4ge.raphaelgwt.client.Raphael.Set chartSet;
	protected Stack<Shape> chartStack;
	private boolean showGridLine = false;
	private RaphaelObject gridLineSet;
	private boolean isBar;
	protected boolean isStacked;
	private BarFactory barFactory;
	/**
	 * Initialize everything if the chart is BarChart else isStacked,barGap and barGroupGap has to 
	 * be initialized separately by setter methods
	 */
	public BarChartFactory(){
		try{
			chart = RaphaelFactory.get();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		init();
	}


	/**
	 * Initialize everything if the chart is BarChart else isStacked, barGap and barGroupGap has to 
	 * be initialized separately by setter methods
	 */
	protected void init(){
		CategoryChart catChart = (CategoryChart) chart;
		isBar = catChart.isBar();
		if(chart instanceof BarChart){
			BarChart barChart = (BarChart)chart;
			barGap = barChart.getBarGap();
			barGroupGap = barChart.getBarGroupGap();
			isStacked = barChart.isStacked();
		}
		colorMap = RaphaelFactory.getColorMap();
		dataTable = catChart.getDataTable();
		axisWidth = AxisLabelHelper.getXAxisLength();
		axisHeight = AxisLabelHelper.getYAxisLength();
		barFactory = new BarFactory();
		barFactory.setStacked(isStacked);
		if(isBar){
			barDataTable = barFactory.getBarDataTable();
		}
		else{
			barDataTable = barFactory.getColumnDataTable();
		}
		if(chart instanceof BarChart){
			BarChart barChart = (BarChart)chart;
			barChart.setzDim(barFactory.getZDim());
		}
		AxisFactory axisFactory = new AxisFactory();
		xAxis = axisFactory.getXAxis();
		yAxis = axisFactory.getYAxis();
		xRaphaelAxis = axisFactory.getXRaphaelAxis();
		yRaphaelAxis = axisFactory.getYRaphaelAxis();
		chartSet = chart.new Set();
		chartStack = new Stack<Shape>();
		gridLineSet = chart.new Set();
		pushAxisToChart(xAxis);
		pushAxisToChart(yAxis);
	}

	
	public BarChartFactory(AxisFactory axisFactory, double barGap, 
			double barGroupGap, boolean isStacked){
		chart = RaphaelFactory.get();
		CategoryChart catChart = (CategoryChart) chart;
		isBar = catChart.isBar();
		this.barGap = barGap;
		this.barGroupGap = barGroupGap;
		this.isStacked = isStacked;
		
		colorMap = RaphaelFactory.getColorMap();
		dataTable = catChart.getDataTable();
		if(chart instanceof ComboChart){
			ComboChart comboCh = (ComboChart) chart;
			axisWidth = comboCh.getRealAxisLenth();
		}
		else{
			axisWidth = AxisLabelHelper.getXAxisLength();			
		}
		axisHeight = AxisLabelHelper.getYAxisLength();
		barFactory = new BarFactory();
		barFactory.setStacked(isStacked);
		barFactory.setBarGap(barGap);
		barFactory.setBarGroupGap(barGroupGap);
		if(isBar){
			barDataTable = barFactory.getBarDataTable();
		}
		else{
			barDataTable = barFactory.getColumnDataTable();
		}
		xAxis = axisFactory.getXAxis();
		yAxis = axisFactory.getYAxis();
		xRaphaelAxis = axisFactory.getXRaphaelAxis();
		yRaphaelAxis = axisFactory.getYRaphaelAxis();
		chartSet = chart.new Set();
		chartStack = new Stack<Shape>();
		gridLineSet = chart.new Set();
		pushAxisToChart(xAxis);
		pushAxisToChart(yAxis);
	}
	
	/**
	 * @return the axisWidth
	 */
	public double getAxisWidth() {
		return axisWidth;
	}



	/**
	 * @param axisWidth the axisWidth to set
	 */
	public void setAxisWidth(double axisWidth) {
		this.axisWidth = axisWidth;
	}



	/**
	 * @return the axisHeight
	 */
	public double getAxisHeight() {
		return axisHeight;
	}



	/**
	 * @param axisHeight the axisHeight to set
	 */
	public void setAxisHeight(double axisHeight) {
		this.axisHeight = axisHeight;
	}



	/**
	 * @return the dataTable
	 */
	public CategoryDataTable<Double> getDataTable() {
		return dataTable;
	}



	/**
	 * @param dataTable the dataTable to set
	 */
	public void setDataTable(CategoryDataTable<Double> dataTable) {
		this.dataTable = dataTable;
	}
	

	/**
	 * @return the valueInterval
	 */
	public double getValueInterval() {
		return valueInterval;
	}



	/**
	 * @param valueInterval the valueInterval to set
	 */
	public void setValueInterval(double valueInterval) {
		this.valueInterval = valueInterval;
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


	/**
	 * @return the colorMap
	 */
	public Map<String, String> getColorMap() {
		return colorMap;
	}



	/**
	 * @param colorMap the colorMap to set
	 */
	public void setColorMap(Map<String, String> colorMap) {
		this.colorMap = colorMap;
	}

	

	/**
	 * @return the barDataTable
	 */
	public CategoryDataTable<RaphaelObject> getBarDataTable() {
		return barDataTable;
	}



	/**
	 * @param barDataTable the barDataTable to set
	 */
	public void setBarDataTable(CategoryDataTable<RaphaelObject> barDataTable) {
		this.barDataTable = barDataTable;
	}



	/**
	 * @return the showGridLine
	 */
	public boolean isShowGridLine() {
		return showGridLine;
	}



	/**
	 * @param showGridLine the showGridLine to set
	 */
	public void setShowGridLine(boolean showGridLine) {
		this.showGridLine = showGridLine;
	}



	/**
	 * @return the gridLineSet
	 */
	public RaphaelObject getGridLineSet() {
		return gridLineSet;
	}



	/**
	 * @param gridLineSet the gridLineSet to set
	 */
	public void setGridLineSet(RaphaelObject gridLineSet) {
		this.gridLineSet = gridLineSet;
	}

	
	protected void pushAxisToChart(Axis axis){
		Iterator<Shape> it = axis.getAxisList().iterator();
		while(it.hasNext()){
			Shape axisObj = (Shape)it.next();
			chartStack.push(axisObj);
			chartSet.push(axisObj);
		}
	}
	
	private RCellPanel getBarPanel(){
		if(isBar){
			return new RVerticalPanel();
		}
		else{
			return new RHorizontalPanel();
		}
	}
	
	private Align getPanelAlignment(){
		if(isBar){
			return Align.LEFT;
		}
		else{
			return Align.BOTTOM;
		}
	}
	
	private Align getPanelAlignment(double value){
		if(value > 0){
			if(isBar){
				return Align.LEFT;
			}
			else{
				return Align.BOTTOM;
			}
		}
		else{
			if(isBar){
				return Align.RIGHT;				
			}
			else{
				return Align.TOP;
			}
		}
	}
	
	private Movement getMovement(){
		if(isBar){
			return Movement.UPWORD;
		}
		else{
			return Movement.RIGHT;
		}
	}
	
	
	protected void createStackedBars(){
		Set<String> catNames = barDataTable.getCategoryNames();
		Set<String> serNames = barDataTable.getSeriesNames();
		Iterator<String> catIt = catNames.iterator();
		Iterator<String> serIt = serNames.iterator();

		//RVerticalPanel vPanel = new RVerticalPanel(0);
		RCellPanel vPanel = getBarPanel();
		Movement movement = getMovement();		
		vPanel.setMovement(movement);
		vPanel.setSpacing(barGroupGap);
		
		while(catIt.hasNext()){
			String catName = catIt.next();
			RCellPanel innerPanel = null;
			if(vPanel instanceof RVerticalPanel){
				innerPanel = new RHorizontalPanel();
			}
			else{
				innerPanel = new RVerticalPanel();
				innerPanel.setMovement(Movement.UPWORD);
			}
			innerPanel.setSpacing(0);		
			while(serIt.hasNext()){
				String serName = serIt.next();
				RaphaelObject bar = null;
				bar = barDataTable.get(catName, serName);
				double value = dataTable.get(catName, serName);
				innerPanel.add(bar, getPanelAlignment(value));
				chartStack.push((Shape)bar);
				chartSet.push((Shape)bar);
			}
			//(barGroupGap - barGap) instead of (barGroupGap) because in every inner iteration it adds
			// the bars with spacing as bar gap.
			vPanel.add(innerPanel, getPanelAlignment());
			serIt =  serNames.iterator();
		}
		if(chart.is_3d() && dataTable.getMin() < 0){
			alignBarsFor3D();
		}
		stack3DBars();
		barSet = vPanel.get();
		
	}

	protected void stack3DBars(){
		double rad = Math.toRadians(Axis3D.angle);
		double zDim = barFactory.getzDim();
		double sin = zDim * Math.sin(rad);//Axis.tickMarkLength/2 * Math.sin(rad);
		double cos = zDim * Math.cos(rad);//Axis.tickMarkLength/2 * Math.cos(rad);
		for(String cat : dataTable.getCategoryNames()){
			int i = 0;
			for(String ser : dataTable.getSeriesNames()){
				//need to translate the inner lower edge of +ve bars to axis 0
				RaphaelObject bar = barDataTable.get(cat, ser);
				if(!isBar){
					bar.translate(0, i*sin);
				}
				else{
					bar.translate(i*-cos, 0);
				}
			i++;
			}
		}
		
	}
	
	protected void createBars(){
		
		Set<String> catNames = barDataTable.getCategoryNames();
		Set<String> serNames = barDataTable.getSeriesNames();
		Iterator<String> catIt = catNames.iterator();
		Iterator<String> serIt = serNames.iterator();

		//RVerticalPanel vPanel = new RVerticalPanel(0);
		RCellPanel vPanel = getBarPanel();
		Align alignBars = getPanelAlignment();
		Movement movement = getMovement();

		vPanel.setMovement(movement);
		
		//spacing should be zero for the first time padding is added
		vPanel.setSpacing(0);
		Rect paddingBar = null;
		double padding = barGroupGap/2-barGap;
		if(isBar){
			paddingBar = chart.new Rect(0, 0, 1, padding);
		}
		else{
			paddingBar = chart.new Rect(0, 0, padding, 1);
		}
		paddingBar.attr("opacity", 0);
		vPanel.add(paddingBar, Align.BOTTOM);

		
		while(catIt.hasNext()){
			String catName = catIt.next();
			vPanel.setSpacing(barGap);
			while(serIt.hasNext()){
				String serName = serIt.next();
				RaphaelObject bar = null;
				bar = barDataTable.get(catName, serName);
				double value = dataTable.get(catName, serName);
				vPanel.add(bar, getPanelAlignment(value));
				chartStack.push((Shape)bar);
				chartSet.push((Shape)bar);
			}
			//(barGroupGap - barGap) instead of (barGroupGap) because in every inner iteration it adds
			// the bars with spacing as bar gap.
			vPanel.updateSpacing(barGroupGap-barGap);
			serIt =  serNames.iterator();
		}
		if(chart.is_3d()){// && dataTable.getMin() < 0){
			alignBarsFor3D();
		}
		barSet = vPanel.get();
	}
	
	
	private void alignBarsFor3D(){
		if(dataTable.getMin() > 0){
			return;
		}
		//for 3D
		double rad = Math.toRadians(Axis3D.angle);
/*		double sin = Axis.tickMarkLength/2 * Math.sin(rad);
		double cos = Axis.tickMarkLength/2 * Math.cos(rad);
*/
		double zDim = barFactory.getzDim();
		double sin = zDim * Math.sin(rad);//Axis.tickMarkLength/2 * Math.sin(rad);
		double cos = zDim * Math.cos(rad);//Axis.tickMarkLength/2 * Math.cos(rad);

		for(String cat : dataTable.getCategoryNames()){
			for(String ser : dataTable.getSeriesNames()){
				double value = dataTable.get(cat, ser);
				//need to translate the inner lower edge of +ve bars to axis 0
				if(value > 0){
					RaphaelObject bar = barDataTable.get(cat, ser);
					if(!isBar){
						bar.translate(0, sin);						
					}
					else{
						bar.translate(-cos, 0);
					}
				}
			}
		}
	}
	
	private void createBarChart(boolean isBar){
		ChartFactory.createChart(xAxis, yAxis, barSet, dataTable.getMin());
	}

	
	
	
	
	

	protected void colorBars(){
		if(colorMap == null){
			return;
		}
		Set<String> seriesNames = dataTable.getSeriesNames();
		Iterator<String> it = seriesNames.iterator();
		while(it.hasNext()){
			String seriesName = it.next();
			List<RaphaelObject> seriesBars = barDataTable.getValueBySeries(seriesName);
			Iterator<RaphaelObject> bit = seriesBars.iterator();
			while(bit.hasNext()){
				RaphaelObject bar = bit.next();
				bar.attr("fill", colorMap.get(seriesName));
			}
		}
	}
	
	public RaphaelObject getBarPlot(){
		createBars();
		colorBars();
		return barSet;
	}
	
	public RaphaelObject getBarChart(){
		createBars();
		colorBars();
		createBarChart(isBar);
		return chartSet;
	}
	
	/**
	 * not in use 
	 * TODO: delete
	 * @return
	 */
	public RaphaelObject getColumnChart(){
		colorBars();
		createBarChart(false);
		return chartSet;
	}
	
	public RaphaelObject getStackedBarPlot(){
		createStackedBars();
		colorBars();
		return barSet;
	}
	
	public RaphaelObject getStackedBarChart(){
		createStackedBars();
		colorBars();
		createBarChart(isBar);
		return chartSet;
	}


	/**
	 * @return the xAxis
	 */
	public Axis getxAxis() {
		return xAxis;
	}


	/**
	 * @param xAxis the xAxis to set
	 */
	public void setxAxis(Axis xAxis) {
		this.xAxis = xAxis;
	}


	/**
	 * @return the yAxis
	 */
	public Axis getyAxis() {
		return yAxis;
	}


	/**
	 * @param yAxis the yAxis to set
	 */
	public void setyAxis(Axis yAxis) {
		this.yAxis = yAxis;
	}


	/**
	 * @return the isBar
	 */
	public boolean isBar() {
		return isBar;
	}


	/**
	 * @param isBar the isBar to set
	 */
	public void setBar(boolean isBar) {
		this.isBar = isBar;
	}
	

}
