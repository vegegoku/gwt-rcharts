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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.hydro4ge.raphaelgwt.client.Raphael;
import com.hydro4ge.raphaelgwt.client.Raphael.Circle;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.hydro4ge.raphaelgwt.client.Raphael.Text;
import com.rcharts.client.Chart;
import com.rcharts.client.Position;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.SpeechBubble;
import com.rcharts.client.ChartStyles.Theme;
import com.rcharts.client.category.line.LineChart;
import com.rcharts.client.category.line.ScatterChart;
import com.rcharts.client.category.line.LineChart.LineChartType;

/**
 * A mousehover handler that aids in visual information while mouse hover over 
 * the plots
 */
public class CategoryHoverHandler implements MouseOutHandler, MouseOverHandler {

	private String categoryName;
	private String seriesName;
	private double value;
	private static double scaleUp = 1.3;
	private SpeechBubble bubble;
	private RaphaelObject legend;
	private RaphaelObject popupPoint;
	private Position popupPosition;
	private Chart raphael;
	private double popupOpacity = 1;
	
	public CategoryHoverHandler(){
		bubble = GWT.create(SpeechBubble.class);
		try {
			raphael = RaphaelFactory.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public CategoryHoverHandler(String categoryName, String seriesName,
			double value, RaphaelObject legend, RaphaelObject popupPoint, Position popupPosition) {
		this();
		this.categoryName = categoryName;
		this.seriesName = seriesName;
		this.value = value;
		this.legend = legend;
		this.popupPoint = popupPoint;
		this.popupPosition =popupPosition;
	}


	public void init(String categoryName, String seriesName,
			double value, RaphaelObject legend, RaphaelObject popupPoint, Position popupPosition) {
		this.categoryName = categoryName;
		this.seriesName = seriesName;
		this.value = value;
		this.legend = legend;
		this.popupPoint = popupPoint;
		this.popupPosition = popupPosition;
		init();
	}


	public void initXY(String seriesName, double yValue, double xValue, 
			RaphaelObject legend, RaphaelObject popupPoint, Position popupPosition){

		this.popupPoint = popupPoint;
		this.legend = legend;
		
		String s = seriesName;
		s+="\nY:"+yValue;
		s+="\nX:"+xValue;
		bubble.setSpeechText(s);
		bubble.setAnchor(popupPoint);
		bubble.setPos(popupPosition);
		bubble.drawBubble();
		bubble.get().attr("opacity", 0);
		bubble.get().toBack();

	}
	
	private void init() {
		String s = seriesName + " : " + categoryName;
		s+="\n   " + value;
		bubble.setSpeechText(s);
		bubble.setAnchor(popupPoint);
		bubble.setPos(popupPosition);
		bubble.setStrokeColor(legend.attrAsString("fill"));
		bubble.drawBubble();
		bubble.get().attr("opacity", 0);
		bubble.get().toBack();
	}


	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}


	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	/**
	 * @return the seriesName
	 */
	public String getSeriesName() {
		return seriesName;
	}


	/**
	 * @param seriesName the seriesName to set
	 */
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}


	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}


	/**
	 * @return the legend
	 */
	public RaphaelObject getLegend() {
		return legend;
	}


	/**
	 * @param legend the legend to set
	 */
	public void setLegend(RaphaelObject legend) {
		this.legend = legend;
	}


	/**
	 * @return the popupPoint
	 */
	public RaphaelObject getPopupPoint() {
		return popupPoint;
	}


	/**
	 * @param popupPoint the popupPoint to set
	 */
	public void setPopupPoint(RaphaelObject popupPoint) {
		this.popupPoint = popupPoint;
	}


	/**
	 * @return the bubble
	 */
	public SpeechBubble getBubble() {
		return bubble;
	}


	/**
	 * @param bubble the bubble to set
	 */
	public void setBubble(SpeechBubble bubble) {
		this.bubble = bubble;
	}


	/**
	 * @return the popupOpacity
	 */
	public double getPopupOpacity() {
		return popupOpacity;
	}


	/**
	 * @param popupOpacity the popupOpacity to set
	 */
	public void setPopupOpacity(double popupOpacity) {
		this.popupOpacity = popupOpacity;
	}


	@Override
	public void onMouseOut(MouseOutEvent event) {
		popupPoint.attr("opacity", popupOpacity);				
		bubble.hide();
		legend.scale(1, 1);
		if(raphael instanceof LineChart){
			if(raphael.getTheme() == Theme.VISUALIZATION){
			}
			if(raphael.getTheme() == Theme.HIGHCHARTS){
				mouseOutH();
			}
		}
		mouseOutH();
		popupPoint.scale(1, 1);		
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		popupPoint.attr("opacity", 1);				
		bubble.show();
		legend.scale(scaleUp, scaleUp);
		if(raphael instanceof LineChart){
			LineChart lc = (LineChart) raphael;
			if(raphael.getTheme() == Theme.VISUALIZATION || lc.getType() == LineChartType.SCATTER){
				popupPoint.scale(scaleUp, scaleUp);
			}
			if(raphael.getTheme() == Theme.HIGHCHARTS){
				mouseOverH();
			}
		}
		mouseOverH();
	}
	
	
	private Circle infoCircle;
	/**
	 *  Mouse out Animation for HighChart theme on LineCharts
	 */
    private void mouseOutH(){
    	if(infoCircle != null){
//    		infoCircle.hide();
        	infoCircle.attr("opacity", 0);
    	}	
    }
    
	/**
	 * Mouser over Animation for HighChart theme on LineCharts
	 */    
    private void mouseOverH(){
    	if(infoCircle == null){
    		infoCircle = raphael.new Circle(popupPoint.attrAsDouble("cx"), 
    				popupPoint.attrAsDouble("cy"), popupPoint.attrAsDouble("r")*2);
        	infoCircle.attr("fill", "#fff");
        	infoCircle.attr("stroke", legend.attrAsString("fill"));
        	infoCircle.attr("stroke-width", 3);
    	}
    	infoCircle.attr("opacity", 1);
    	infoCircle.toBack();
    }
}
