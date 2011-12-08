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
package com.rcharts.client.category.line;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.user.client.ui.Widget;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.Position;
import com.rcharts.client.RDockPanel;
import com.rcharts.client.category.Axis;
import com.rcharts.client.category.CategoryChart;
import com.rcharts.client.category.CategoryDataTable;
import com.rcharts.client.category.CategoryHoverHandler;
import com.rcharts.client.category.LegendFactory;

public class LineChart extends CategoryChart{

	public enum LineChartType{
		SCATTER, LINE, AREA
	}
	
	private List<CategoryHoverHandler> handlers = new ArrayList<CategoryHoverHandler>();
	private List<RaphaelObject> bubbles = new ArrayList<RaphaelObject>();
	private LineChartType type = LineChartType.LINE;
	private boolean splineEffect = false;
	private boolean mouseHoverShowGrid = false;
	private Map<String, RaphaelObject> pathMap;
	private boolean showPoint;
	
	public LineChart(int width, int height) {
		super(width, height);
		isBar = false;
	}
	
	public void setType(LineChartType type){
		this.type = type;
	}

	
	/**
	 * @return the splienEffect
	 */
	public boolean isSplineEffect() {
		return splineEffect;
	}

	/**
	 * @param splienEffect the splienEffect to set
	 */
	public void setSplineEffect(boolean splineEffect) {
		this.splineEffect = splineEffect;
	}
	/**
	 * @return the mouseHoverShowGrid
	 */
	public boolean isMouseHoverShowGrid() {
		return mouseHoverShowGrid;
	}

	/**
	 * @param mouseHoverShowGrid the mouseHoverShowGrid to set
	 */
	public void setMouseHoverShowGrid(boolean mouseHoverShowGrid) {
		this.mouseHoverShowGrid = mouseHoverShowGrid;
	}


	/**
	 * @return the showPoint
	 */
	public boolean isShowPoint() {
		return showPoint;
	}

	/**
	 * @param showPoint the showPoint to set
	 */
	public void showPoint(boolean showPoint) {
		this.showPoint = showPoint;
	}

	/**
	 * @return the type
	 */
	public LineChartType getType() {
		return type;
	}

	@Override
	protected void draw() {
		chartPanel = new RDockPanel();
		if(is_3d()){
			Axis.tickMarkLength = (int) (chartPanel.getHeight()/15);
		}
		else{
			Axis.tickMarkLength = 5;
		}
		setTitle(chartPanel);
		LegendFactory legFactory = new LegendFactory();
		chartPanel.add(legFactory.getLegendPanel(), Position.NORTH);
		LineChartFactory chartFactory = new LineChartFactory();
		RaphaelObject plot = null;
		switch(type){
		case SCATTER:
			plot = chartFactory.getScatterChart();
			break;
		case LINE:
			plot = chartFactory.getLineChart();
			break;
		case AREA:
			plot = chartFactory.getAreaChart();
			break;
		}
		//No Need to scale after the new implementation of AxisLabelHelper to get the
		// absolute length of x and y axis;
//		BBox plotBox = plot.getBBox();
//		double sx = (chartPanel.getWidth()-15)/plotBox.width();
//		double sy = (chartPanel.getHeight()-15)/plotBox.height();
//		plot.scale(sx, sy, plotBox.x(), plotBox.y());
		chartPanel.add(plot, Position.SOUTH);
		setPlotStyle(plot);
		pathMap = chartFactory.getPathMap();
		setHandler(chartFactory.getPointDataTable(), legFactory.getLegendMarkMap());
	}

	private void setHandler(CategoryDataTable<RaphaelObject> pointDataTable,
			Map<String, RaphaelObject> legendMarkMap){
		java.util.Set<String> seriesNames = pointDataTable.getSeriesNames(); 
		java.util.Set<String> catNames = pointDataTable.getCategoryNames();
		Map<String, RaphaelObject> gridHoverMap = null;
		if(!getxAxis().isValueAxis()){
			gridHoverMap = getxAxis().getGridHoverMap();
		}
		Position pos = null;
		boolean isPathMapInit = false;
		GridHoverHandler.CURRENT_SERIES = seriesNames.iterator().next();
		for(String catName : catNames){
			for(String serName : seriesNames){
				RaphaelObject point = pointDataTable.get(catName, serName);
				double value = dataTable.get(catName, serName);
				if(value < 0){
					pos = Position.SOUTH;
				}
				else{
					pos = Position.NORTH;
				}
				RaphaelObject legend = legendMarkMap.get(serName);
				CategoryHoverHandler handler = GWT.create(CategoryHoverHandler.class);
				handler.init(catName, serName, value, legend, point, pos);
				point.addMouseOverHandler(handler);
				((Widget)point).addDomHandler(handler, MouseOutEvent.getType());
				bubbles.add(handler.getBubble().get());
				handlers.add(handler);
				if(getTheme() == Theme.HIGHCHARTS){
					setHighChartTheme(isPathMapInit, serName, catName, value,
							gridHoverMap, legend, point, pos);
				}
				if(!showPoint && !(type == LineChartType.SCATTER)){
					point.attr("opacity", 0);
					handler.setPopupOpacity(0);
				}
				if(showLabels){
					double ty = 0;
					if(value >= 0){
						ty = point.getBBox().y() - point.getBBox().height() - 5;
					}
					else{
						ty = point.getBBox().y() + point.getBBox().height() + 5;
					}
					double tx = point.getBBox().x();
					this.new Text(tx, ty, value+"");
				}
			}
			isPathMapInit = true;
		}

	}
	
	private void setHighChartTheme(boolean isPathMapInit, String serName, String catName,
			double value, Map<String, RaphaelObject> gridHoverMap, RaphaelObject legend,
			RaphaelObject point, Position pos){
		if(!isPathMapInit && pathMap != null){
			RaphaelObject path = pathMap.get(serName);
			PathHoverHandler phh = new PathHoverHandler();
			phh.setSeries(serName);
			path.addMouseOverHandler(phh);
			path.addMouseOutHandler(phh);
		}
		GridHoverHandler gridHandler = new GridHoverHandler();
		gridHandler.init(catName, serName, value, legend, point, pos);
		RaphaelObject grid = gridHoverMap.get(catName);
		grid.addMouseOverHandler(gridHandler);
		grid.addMouseOutHandler(gridHandler);
		if(!showPoint && !(type == LineChartType.SCATTER)){
			gridHandler.setPopupOpacity(0);						
		}
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}


}
