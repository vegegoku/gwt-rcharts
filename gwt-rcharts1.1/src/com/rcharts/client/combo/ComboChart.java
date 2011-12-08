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

import java.util.ArrayList;
import java.util.List;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.AxisLabelHelper;
import com.rcharts.client.Position;
import com.rcharts.client.RCellPanel;
import com.rcharts.client.RDockPanel;
import com.rcharts.client.RHorizontalPanel;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.category.Axis;
import com.rcharts.client.category.AxisFactory;
import com.rcharts.client.category.LegendFactory;
import com.rcharts.client.category.bar.BarChartFactory;
import com.rcharts.client.category.line.LineChartFactory;

public class ComboChart extends ComboUtil {
	
	public static class ComboAxisPlot{
		Axis xAxis;
		Axis yAxis;
		RaphaelObject plot;
		
		public ComboAxisPlot(){
			
		}
		
		public ComboAxisPlot(Axis xAxis, Axis yAxis, RaphaelObject plot) {
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.plot = plot;
		}
		
	}
	
	List<Set> plotList = new ArrayList<Set>();
	ComboChartStyle currentChart;
	private List<ComboAxisPlot> comboAxisList = new ArrayList<ComboChart.ComboAxisPlot>();
	private double axisWidthOffset;
	private double axisHeightOffset;
	private double realAxisLenth;
	private boolean showLinePointsAtMid;
	
	public ComboChart(int width, int height) {
		super(width, height);
		isBar = false;
	}

	public ComboChartStyle getCurrentChart(){
		return currentChart;
	}
	
	@Override
	protected void initRaphaelFactory(){
		setDataTable(comboChartList.get(0).getDataTable());
		super.initRaphaelFactory();
	}

	@Override
	protected void draw() {
		chartPanel = new RDockPanel();
		setTitle(chartPanel);
		chartPanel.add(getLegend(comboChartList).get(), getLegendPosition());
		RHorizontalPanel panel = new RHorizontalPanel();
		AxisFactory axisFactory = null;
		ComboAxisLabelHelper axisHelper = new ComboAxisLabelHelper(this);
		axisHeightOffset = axisHelper.getAxisHeight();
		axisWidthOffset = axisHelper.getAxisWidth(0);
		realAxisLenth = AxisLabelHelper.getXAxisLength();;
		Axis xAxis = null;
		for(ComboChartStyle comboStyle : comboChartList){
			setDataTable(comboStyle.getDataTable());
			comboStyle.wrap(this);
			axisFactory = new AxisFactory();
			if(xAxis != null){
				axisFactory.setXAxis(xAxis);
				axisWidthOffset = chartPanel.getWidth() - 10;
				if(is_3d()){
					_3d = false;
					axisFactory.getYAxis();
					_3d = true;
				}
			}
			else{
				xAxis = axisFactory.getXAxis();
			}
			RaphaelObject r = drawChart(comboStyle, axisFactory);
			r.toFront();
			comboAxisList.add(new ComboAxisPlot(axisFactory.getXAxis(), axisFactory.getYAxis(), r));
			panel.add(axisFactory.getYRaphaelAxis());			
		}
		xAxis.showGridLine();
		RaphaelObject plot = ComboChartFactory.createChart(comboAxisList);
		chartPanel.add(plot, Position.NORTH);
		setHandlers(comboChartList);
		setPlotStyle(plot);
	}

	
	private void setHandlers(List<ComboChartStyle> chartList){
		for(ComboChartStyle chart : chartList){
			if(chart instanceof ComboBarChart){
				ComboBarChart comboBar = (ComboBarChart) chart;
				comboBar.setBarHandler();
			}
			else if(chart instanceof ComboLineChart){
				ComboLineChart comboLine = (ComboLineChart) chart;
				comboLine.setHandler();
			}
		}
	}
	
	private RCellPanel getLegend(List<ComboChartStyle> comboCharts){
		RHorizontalPanel panel = new RHorizontalPanel();
		for(ComboChartStyle comboChart : comboCharts){
			comboChart.wrap(this);
			setDataTable(comboChart.getDataTable());
			RaphaelFactory.setSeries(comboChart.getDataTable().getSeriesNames());
			RaphaelFactory.setColorMap(comboChart.getColorMap());
			LegendFactory legFactory = new LegendFactory();
			panel.add(legFactory.getLegendPanel());
			comboChart.setLegendMarkMap(legFactory.getLegendMarkMap());
		}
		return panel;
	}
	
	private RaphaelObject drawChart(ComboChartStyle chart, AxisFactory axisFactory){
		RaphaelObject ro = null;
		RaphaelFactory.setColorMap(chart.getColorMap());
		chart.wrap(this);
		if(chart instanceof ComboBarChart){
			ro = drawBarChart((ComboBarChart) chart, axisFactory);
		}
		else if(chart instanceof ComboLineChart){
			ro = drawLineChart((ComboLineChart) chart, axisFactory);
		}
		return ro;
	}
	
	private RaphaelObject drawBarChart(ComboBarChart chart, AxisFactory axisFactory){
		BarChartFactory barChartFactory = new BarChartFactory(axisFactory, chart.getBarGap(),
				chart.getBarGroupGap(), chart.isStacked());
		barChartFactory.setAxisWidth(realAxisLenth);
		RaphaelObject barPlot = null;
		if(chart.isStacked()){
			barPlot = barChartFactory.getStackedBarPlot();
		}
		else{
			barPlot = barChartFactory.getBarPlot();
		}
		chart.setBarDataTable(barChartFactory.getBarDataTable());
		return barPlot;
	}
	
	private RaphaelObject drawLineChart(ComboLineChart chart, AxisFactory axisFactory){
		LineChartFactory lineChartFactory = new LineChartFactory(axisFactory, chart.isSplineEffect(), this.new Set());
		RaphaelObject linePlot = lineChartFactory.getPlot(chart.getType());
		chart.setPointDataTable(lineChartFactory.getPointDataTable());
		return linePlot;
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the axisWidthOffset
	 */
	public double getAxisWidthOffset() {
		return axisWidthOffset;
	}

	/**
	 * @param axisWidthOffset the axisWidthOffset to set
	 */
	public void setAxisWidthOffset(double axisWidthOffset) {
		this.axisWidthOffset = axisWidthOffset;
	}

	/**
	 * @return the axisHeightOffset
	 */
	public double getAxisHeightOffset() {
		return axisHeightOffset;
	}

	/**
	 * @param axisHeightOffset the axisHeightOffset to set
	 */
	public void setAxisHeightOffset(double axisHeightOffset) {
		this.axisHeightOffset = axisHeightOffset;
	}

	/**
	 * @return the realAxisWidthOffset
	 */
	public double getRealAxisLenth() {
		return realAxisLenth;
	}

	/**
	 * @param realAxisWidthOffset the realAxisWidthOffset to set
	 */
	public void setRealAxisLenth(double realAxisWidthOffset) {
		this.realAxisLenth = realAxisWidthOffset;
	}

	/**
	 * @return the showLinePointsAtMid
	 */
	public boolean isShowLinePointsAtMid() {
		return showLinePointsAtMid;
	}

	/**
	 * @param showLinePointsAtMid the showLinePointsAtMid to set
	 */
	public void setShowLinePointsAtMid(boolean showLinePointsAtMid) {
		this.showLinePointsAtMid = showLinePointsAtMid;
	}

}
