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

import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.category.Axis;
import com.rcharts.client.category.CategoryChart;

public class ChartScrollHandler implements ScrollHandler {

	private CategoryChart chart;
	private ScrollPanel scrollPanel;
	private double initialPosition;
	RaphaelObject raphaelAxis;

	
	
	public ChartScrollHandler(){
		
	}
	
	
	public ChartScrollHandler(CategoryChart chart, ScrollPanel scrollPanel) {
		super();
		this.chart = chart;
		this.scrollPanel = scrollPanel;
	}


	private void init(){
		setRaphaelAxis();
		setInitialPosition();		
	}
	/**
	 * @return the chart
	 */
	public CategoryChart getChart() {
		return chart;
	}

	/**
	 * @param chart the chart to set
	 */
	public void setChart(CategoryChart chart) {
		this.chart = chart;
	}

	/**
	 * @return the scrollPanel
	 */
	public ScrollPanel getScrollPanel() {
		return scrollPanel;
	}

	/**
	 * @param scrollPanel the scrollPanel to set
	 */
	public void setScrollPanel(ScrollPanel scrollPanel) {
		this.scrollPanel = scrollPanel;
	}

	private void setRaphaelAxis(){
		if(chart.isBar()){
			raphaelAxis = chart.getxAxis().getSecondAxisSet();
		}
		else{
			if(chart.is_3d()){
				raphaelAxis = chart.getyAxis().getSecondAxisSet();
			}
			else{
				raphaelAxis = chart.getyAxis().getFirstAxisSet();
			}
		}
	}
	
	private void setInitialPosition(){
		if(raphaelAxis == null){
			setRaphaelAxis();
		}
		if(chart.isBar()){
			initialPosition = raphaelAxis.getBBox().y();
		}
		else{
			initialPosition = raphaelAxis.getBBox().x();
		}
	}


	
	boolean flag = false;
	@Override
	public void onScroll(ScrollEvent event) {
		if(raphaelAxis == null){
			init();
		}
		
		if(chart.isBar()){
			handleVerticalScroll();				
		}
		else{
			handleHorizontalScroll();
		}
	}

	private void handleHorizontalScroll(){
		if(chart.is_3d() && !flag){
			double cw = chart.getWidth();
			double sw = scrollPanel.getOffsetWidth()-20;
			double offset = cw - sw;
			raphaelAxis.translate(-offset, 0);
			raphaelAxis.toFront();
			setInitialPosition();
			flag = true;
		}
		int scrollPosition = scrollPanel.getHorizontalScrollPosition();
		double dx = scrollPosition + initialPosition - raphaelAxis.getBBox().x();
		raphaelAxis.translate(dx, 0);
	}
	
	private void handleVerticalScroll(){
		int scrollPosition = scrollPanel.getVerticalScrollPosition();
		double dy = scrollPosition + initialPosition - raphaelAxis.getBBox().y();
		raphaelAxis.translate(0, dy);
	}
	
}
