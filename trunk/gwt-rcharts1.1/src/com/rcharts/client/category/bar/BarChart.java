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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.user.client.ui.Widget;
import com.hydro4ge.raphaelgwt.client.BBox;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.Align;
import com.rcharts.client.Position;
import com.rcharts.client.RDockPanel;
import com.rcharts.client.category.Axis;
import com.rcharts.client.category.CategoryChart;
import com.rcharts.client.category.CategoryDataTable;
import com.rcharts.client.category.CategoryHoverHandler;
import com.rcharts.client.category.LegendFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BarChart extends CategoryChart {

	private double barGap = 0;
	private double barGroupGap = 5;
	private List<RaphaelObject> bubbles = new ArrayList<RaphaelObject>();
	private List<CategoryHoverHandler> handlers = new ArrayList<CategoryHoverHandler>();
	private boolean isStacked;
	private double zDim;
	
	public BarChart(int width, int height) {
		super(width, height);
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



	@Override
	protected void draw() {
		chartPanel = new RDockPanel();
		setTitle(chartPanel);		
		LegendFactory legFactory = new LegendFactory();
		RaphaelObject legPanel = legFactory.getLegendPanel();
		chartPanel.add(legPanel, getLegendPosition());
		if(is_3d()){
			Axis.tickMarkLength = (int) (chartPanel.getHeight()/15);
		}
		else{
			Axis.tickMarkLength = 5;
		}
		BarChartFactory chartFactory = new BarChartFactory();
		RaphaelObject barPlot = null; //chartFactory.getBarChart();
		if(isStacked){
			barPlot = chartFactory.getStackedBarChart();
		}
		else{
			barPlot = chartFactory.getBarChart();
		}
		//No Need to scale after the new implementation of AxisLabelHelper to get the
		// absolute length of x and y axis;
		BBox plotBox = barPlot.getBBox();
		if(is_3d()){
			double dx = (chartPanel.getWidth()-25)/plotBox.width();
			double dy = (chartPanel.getHeight()-25)/plotBox.height();
//			barPlot.scale(dx, dy, plotBox.x(), plotBox.y());			
		}
		chartPanel.add(barPlot, Position.WEST, Align.TOP);
		setPlotStyle(barPlot);
		setHandler(chartFactory.getBarDataTable(), legFactory.getLegendMarkMap());

		if(!is_3d()){
			barPlot.toFront();
			getxAxis().getAxisLine().toFront();
			getyAxis().getAxisLine().toFront();			
		}
	}
	
	private void setHandler(CategoryDataTable<RaphaelObject> barDataTable, 
			Map<String, RaphaelObject> legendMarkMap){
		java.util.Set<String> seriesNames = barDataTable.getSeriesNames(); 
		java.util.Set<String> catNames = barDataTable.getCategoryNames();
		Position pos = null;
		for(String catName : catNames){
			for(String serName : seriesNames){
				RaphaelObject bar = barDataTable.get(catName, serName);
				double value = dataTable.get(catName, serName);
				RaphaelObject legend = legendMarkMap.get(serName);
				RaphaelObject popupPoint = null;
				if(isBar){
					popupPoint = getBarPopupPoint(bar, value);
					if(value < 0){
						pos = Position.WEST;
					}
					else{
						pos = Position.EAST;
					}
				}
				else{
					popupPoint = getColumnPopupPoint(bar, value);
					if(value < 0){
						pos = Position.SOUTH;
					}
					else{
						pos = Position.NORTH;
					}
				}
				if(showLabels){
					renderLabels(value, popupPoint);
				}
				CategoryHoverHandler handler = GWT.create(CategoryHoverHandler.class);
				handler.init(catName, serName, value, legend, popupPoint, pos);
				bar.addMouseOverHandler(handler);
				((Widget)bar).addDomHandler(handler, MouseOutEvent.getType());	
				bubbles.add(handler.getBubble().get());
				handlers.add(handler);
			}
		}
	}
	
	
	protected RaphaelObject getBarPopupPoint(RaphaelObject bar, double value){
		BBox barBox = bar.getBBox();
		double x = barBox.x() + barBox.width()-2;
		double y = barBox.y() + barBox.height()/2;
		if(value < 0){
			x = barBox.x() + 2;
		}
		Shape popupPoint = this.new Circle(x, y, 1);
		popupPoint.hide();
		return popupPoint;
	}
	
	protected RaphaelObject getColumnPopupPoint(RaphaelObject bar, double value){
		BBox barBox = bar.getBBox();
		double x = barBox.x() + barBox.width()/2;
		double y = barBox.y() + 2;
		if(value < 0){
			y = barBox.y() + barBox.height() -2;
		}
		Shape popupPoint = this.new Circle(x, y, 1);
		popupPoint.hide();
		return popupPoint;
	}
	
	
	protected void renderLabels(double value, RaphaelObject popupPoint){
		double offsetValLabel =0;
		String textAnchor = null;
		if(value < 0){
			offsetValLabel = -10;
			textAnchor ="end";
		}
		else{
			offsetValLabel = 10;
			textAnchor = "start";
		}
		Text valueLabel = null;
		if(isBar){
			valueLabel = this.new Text(popupPoint.getBBox().x()+offsetValLabel
					, popupPoint.getBBox().y(), value+"");
			
			valueLabel.attr("text-anchor", textAnchor);
		}
		else{					
			valueLabel = this.new Text(popupPoint.getBBox().x(), 
					popupPoint.getBBox().y()-offsetValLabel, value+"");					
		}		
	}
	
	@Override
	public void clear() {
		chartPanel.clear();
		for(CategoryHoverHandler bhh : handlers){
			bhh = null;
		}
		for(RaphaelObject bubble : bubbles){
			bubble.remove();
		}
	}

}
