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
package com.rcharts.client.xychart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.user.client.ui.Widget;
import com.hydro4ge.raphaelgwt.client.BBox;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.Point;
import com.rcharts.client.Position;
import com.rcharts.client.RDockPanel;
import com.rcharts.client.category.CategoryHoverHandler;
import com.rcharts.client.category.LegendFactory;
import com.rcharts.client.xychart.XYLineChartFactory.PointData;

public class XYLineChart extends XYChart {

	public enum LineChartType{
		LINE, SCATTER
	}
	
	
	private List<CategoryHoverHandler> handlers = new ArrayList<CategoryHoverHandler>();
	private List<RaphaelObject> bubbles = new ArrayList<RaphaelObject>();
	private LineChartType chartType = LineChartType.LINE;
	
	public XYLineChart(int width, int height) {
		super(width, height);
	}

	public void setChartType(LineChartType chartType){
		this.chartType = chartType;
	}
	
	@Override
	protected void draw() {
		chartPanel = new RDockPanel();
		setTitle(chartPanel);
		LegendFactory legFactory = new LegendFactory();
		chartPanel.add(legFactory.getLegendPanel(), Position.NORTH);
		XYLineChartFactory chartFactory = new XYLineChartFactory();
		RaphaelObject linePlot = null;//chartFactory.getLineChart();
		switch(chartType){
		case SCATTER:
			linePlot = chartFactory.getScatterChart();
			break;
		case LINE:
			linePlot = chartFactory.getLineChart();
			break;
		}
		BBox plotBox = linePlot.getBBox();
		double dx = (chartPanel.getWidth()-15)/plotBox.width();
		double dy = (chartPanel.getHeight()-15)/plotBox.height();
		linePlot.scale(dx, dy, plotBox.x(), plotBox.y());

		chartPanel.add(linePlot, Position.SOUTH);
		
		setHandler(chartFactory.getPointDataTable(), legFactory.getLegendMarkMap());
	}
	
	private void setHandler(Map<String, List<PointData>> pointDataTable, 
			Map<String, RaphaelObject> legMarkMap){
		java.util.Set<String> seriesNames = pointDataTable.keySet();
		for(String series : seriesNames){
			List<PointData> pdataList = pointDataTable.get(series);
			RaphaelObject legMark = legMarkMap.get(series);
			for(PointData pData : pdataList){
				CategoryHoverHandler handler = GWT.create(CategoryHoverHandler.class);
				Point point = pData.getPoint(); 
				RaphaelObject legend = legMarkMap.get(series);
				RaphaelObject popPoint = pData.getRaphaelPoint();
				Position popupPosition = Position.NORTH;
				if(point.getY() < 0){
					popupPosition = Position.SOUTH;
				}
				handler.initXY(series, point.getY(), point.getX(), legend, popPoint, popupPosition);
				popPoint.addMouseOverHandler(handler);
				((Widget)popPoint).addDomHandler(handler, MouseOutEvent.getType());
				handlers.add(handler);
				bubbles.add(handler.getBubble().get());
			}
		}
	}

	@Override
	public void clear() {

	}

}
