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

import java.util.Iterator;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.user.client.Window;
import com.hydro4ge.raphaelgwt.client.BBox;
import com.hydro4ge.raphaelgwt.client.PathBuilder;
import com.hydro4ge.raphaelgwt.client.Raphael;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.hydro4ge.raphaelgwt.client.Raphael.Rect;
import com.rcharts.client.category.Axis;
import com.rcharts.client.category.Axis3D;
import com.rcharts.client.pie.PieChart;
import com.rcharts.client.styles.Style;

/**
 * @author Saurabh Tripathi
 * Base class for all the Charts throughout the application
 */
public abstract class Chart extends ChartStyles{
	
	
	protected Raphael raphael;
	private String title;
	private String subTitle;
	private Position titlePosition = Position.NORTH;
	private Position subTitlePosition = Position.NORTH;
	private Position legendPosition = Position.NORTH;
	protected List<Legend> legendList;
	protected Set legendSet;
	protected RDockPanel chartPanel;
	private int width;
	private int height;
	private String xAxisTitle;
	private String yAxisTitle;
	protected TextCollector textHandler = new TextCollector();
	private AxisConfiguration axisConfiguration = new AxisConfiguration();
	private boolean loaded;
	protected boolean _3d;
	protected boolean showLabels;
	protected Shape chartRect;
	private boolean showGridLine;

	//Axis reference mostly stands for scroll charts
	private Axis xAxis;
	private Axis yAxis;
	/**
	 * Default Constructor for abstract class Chart.
	 * It initializes the {@link Raphael Raphael} object in it by requesting it from 
	 * {@link RaphaelFactory} 
	 * @throws ChartProcessException 
	 */
	public Chart(int width, int height){
		super(width, height);
		this.width = width;
		this.height = height;
		RaphaelFactory.set(this, this);
		raphael = this;//RaphaelFactory.getRaphael();
		this.addTextListener(textHandler);
	}
	
		
	/**
	 * 
	 * @return Title of the chart
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets title of the chart
	 * @param String title of chart
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Returns sub title of the chart
	 * @return String sub-title of the chart
	 */
	public String getSubTitle() {
		return subTitle;
	}
	
	/**
	 * Sets sub-title of the chart
	 * @param String subTitle subtitle of the chart
	 */
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	/**
	 * Returns the Position[EAST,WEST,NORTH,SOUTH] of legends
	 * @return {@link Position Position} of Legend
	 */
	public Position getLegendPosition() {
		return legendPosition;
	}
	
	/**
	 * Sets the Position[EAST,WEST,NORTH,SOUTH] of legends
	 * @param {@link Position Position} of Legend
	 */
	public void setLegendPosition(Position legendPosition) {
		this.legendPosition = legendPosition;
	}
	
	
	/**
	 * Returns the Position[EAST,WEST,NORTH,SOUTH] of title for chart
	 * @return {@link Position}
	 */
	public Position getTitlePosition() {
		return titlePosition;
	}

	/**
	 * Sets the Position[EAST,WEST,NORTH,SOUTH] of title for chart
	 * @param {@link Position} titlePosition
	 */
	public void setTitlePosition(Position titlePosition) {
		this.titlePosition = titlePosition;
	}

	/**
	 * Returns the Position[EAST,WEST,NORTH,SOUTH] of sub title for chart
	 * @return {@link Position}
	 */
	public Position getSubTitlePosition() {
		return subTitlePosition;
	}

	/**
	 * Sets the Position[EAST,WEST,NORTH,SOUTH] of sub title for chart
	 * @param {@link Position} titlePosition
	 */
	public void setSubTitlePosition(Position subTitlePosition) {
		this.subTitlePosition = subTitlePosition;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}


	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}


	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}


	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	public AxisConfiguration getAxisConfiguration(){
		return axisConfiguration;
	}

	/**
	 * 
	 * set the x axis at the top of chart; by default x axis comes at bottom
	 */
	public void setXAxisAtTop(){
		axisConfiguration.setxAxisAtNorth(true);
	}
	
	/**
	 * Set the y axis at the right of chart; by default y axis comes at left
	 */
	public void setYAxisAtRight(){
		axisConfiguration.setyAxisAtEast(true);
	}
	
	/**
	 * Set the x axis on both top and bottom of the chart
	 */
	public void showDoubleXAxis(){
		axisConfiguration.setDoubleXAxis(true);
	}
	
	/**
	 * Set the y axis on both right and left of the chart
	 */
	public void showDoubleYAxis(){
		axisConfiguration.setDoubleYAxis(true);
	}
	
	/**
	 * @return the xAxisTitle
	 */
	public String getxAxisTitle() {
		return xAxisTitle;
	}


	/**
	 * @param xAxisTitle the xAxisTitle to set
	 */
	public void setxAxisTitle(String xAxisTitle) {
		this.xAxisTitle = xAxisTitle;
	}


	/**
	 * @return the yAxisTitle
	 */
	public String getyAxisTitle() {
		return yAxisTitle;
	}


	/**
	 * @param yAxisTitle the yAxisTitle to set
	 */
	public void setyAxisTitle(String yAxisTitle) {
		this.yAxisTitle = yAxisTitle;
	}


	/**
	 * @return the showLabels
	 */
	public boolean isShowLabels() {
		return showLabels;
	}


	/**
	 * @param showLabels the showLabels to set
	 */
	public void setShowLabels(boolean showLabels) {
		this.showLabels = showLabels;
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
	 * Draws the chart.
	 * to be implemented by sub classes
	 */
	protected abstract void draw();	
	
	protected abstract void initRaphaelFactory();
	
	public abstract java.util.Set<String> getXAxisLabels();
	
	public abstract java.util.Set<String> getYAxisLabels();
	
	public abstract boolean isXValueAxis();
	
	public abstract boolean isYValueAxis();
	
	/**
	 * @return the is3D
	 */
	public boolean is_3d() {
		return _3d;
	}


	/**
	 * @param is3d the is3D to set
	 */
	public void set_3d(boolean is3d) {
		_3d = is3d;
	}


	/**
	 * @return the chartPanel
	 */
	public RDockPanel getChartPanel() {
		return chartPanel;
	}


	/**
	 * @param chartPanel the chartPanel to set
	 */
	public void setChartPanel(RDockPanel chartPanel) {
		this.chartPanel = chartPanel;
	}


	@Override
	public void onLoad(){
		if(!loaded){
			RaphaelFactory.setCurrentObject(this);
			initRaphaelFactory();
			draw();			
		}
		loaded = true;
	}
		

	private void styleChart(Shape dockRect){
		dockRect.attr("stroke-width", 2);
		dockRect.attr(getChartStyle());
		dockRect.show();
	}

	
	protected void setTitle(RDockPanel chartPanel) {
		chartRect = chartPanel.getCurrentDock();
		styleChart(chartRect);//chartPanel.getCurrentDock());
		setRchartBackGround(chartRect);

		if(getTitle() != null){
			RaphaelObject title = raphael.new Text(0, 0, getTitle());
			title.attr(getTitleStyle());
			chartPanel.add(title, getTitlePosition());				
			setRtitle(title);
		}
		if(getSubTitle()!= null){
			RaphaelObject subTitle = raphael.new Text(0, 0, getSubTitle());
			subTitle.attr(getSubTitleStyle());
			chartPanel.add(subTitle, getSubTitlePosition());
			setRsubTitle(subTitle);
		}
		if(xAxisTitle!=null){
			RaphaelObject xTitle = raphael.new Text(0, 0, xAxisTitle);
			xTitle.attr(getHAxisTitleStyle());
			if(axisConfiguration.isxAxisAtNorth()){
				chartPanel.add(xTitle, Position.NORTH);
			}
			else{
				chartPanel.add(xTitle, Position.SOUTH);
			}
			setRhAxisTitle(xTitle);
		}
		if(yAxisTitle != null){
			RaphaelObject yTitle = raphael.new Text(0, 0, yAxisTitle);
			yTitle.attr(getVAxisTitleStyle());
			if(axisConfiguration.isyAxisAtEast()){
				chartPanel.add(yTitle, Position.EAST);
			}
			else{
				chartPanel.add(yTitle, Position.WEST);
			}
			setRvAxisTitle(yTitle);
		}
	}

	protected RCellPanel getLegendPanel(List<Legend> legList) {
		if(legList.size() == 0){
			return null;
		}
		Position pos = getLegendPosition();
		RCellPanel legendPanel = null;
		if(pos == Position.NORTH || pos == Position.SOUTH){
			legendPanel = new RHorizontalPanel();
		}
		else{
			legendPanel = new RVerticalPanel();
		}
		Iterator<Legend> itl = legList.iterator();
		while(itl.hasNext()){
			Legend l = itl.next();
			legendPanel.add(l.getLegendMark());
			Data pd = l.getData();
			legendPanel.add(raphael.new Text(0, 0, pd.getLabel()));
		}
		return legendPanel;
	}

	/**
	 * Sets the color of plot of line and bar chart family
	 * Used by BarChart and LineChart
	 * @param plot
	 */
	protected void setPlotStyle(RaphaelObject plot){
		if(this instanceof PieChart){
			return;
		}
		Set rPlot = this.new Set();
		Style color = getPlotStyle();
		if(color == null){
			return;
		}
		BBox xbox = xAxis.getAxisLine().getBBox();
		BBox ybox = yAxis.getAxisLine().getBBox();
		double plotx = xbox.x();//plot.getBBox().x() + AxisLabelHelper.getYWidth();
		double ploty = xbox.y();//plot.getBBox().y() + AxisLabelHelper.getXHeight();
		double w = xbox.width();//AxisLabelHelper.getXAxisLength();//plot.getBBox().width();
		double h = ybox.height();//AxisLabelHelper.getYAxisLength();//plot.getBBox().height();
		Rect r = this.new Rect(plotx, ploty, w, h);
//		r.attr("fill", color);
		r.attr(color);
		r.toBack();

		rPlot.push(r);
		if(_3d){
			//z plane : the vertical plane with y axis
			double x = ybox.x();
			double y = ybox.y();
			double height = ybox.height();
			double ytan = Axis.tickMarkLength * Math.tan(Math.toRadians(Axis3D.angle));
			double x1 = x - Axis.tickMarkLength;
			double y1 = y + ytan;
			double xw = x;
			double yw = y + height;
			double x1w = x1;
			double y1w = y1 + height; 
			PathBuilder pb = new PathBuilder();
			pb.M(x, y);
			pb.L(x1, y1);
			pb.L(x1, y1 + height);
			pb.L(x, y + height);
			pb.Z();
			Path zPlane = this.new Path(pb);
//			zPlane.attr("fill", color);
			zPlane.attr(color);
			zPlane.toBack();
			
			rPlot.push(zPlane);
			
			//  x plane attached with x / horizontal axis
			double width = xbox.width();
			pb = new PathBuilder();
			pb.M(xw, yw);
			pb.L(x1w, y1w);
			pb.L(x1w + width, y1w);
			pb.L(xw + width, yw);
			Path xPlane = this.new Path(pb);
//			xPlane.attr("fill", color);
			xPlane.attr(color);
			xPlane.toBack();
			
			rPlot.push(xPlane);
		}
		chartRect.toBack();
		
		
		setRplotBackGround(rPlot);
	}
	
	public abstract void clear();


	/**
	 * @return the showGridLine
	 */
	public boolean isShowGridLine() {
		return showGridLine;
	}


	/**
	 * @param showGridLine the showGridLine to set
	 */
	public void showGridLine(boolean showGridLine) {
		this.showGridLine = showGridLine;
	} 


}
