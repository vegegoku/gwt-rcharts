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
package com.rcharts.client.category.bar.scrollable;

import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.category.bar.BarChart;
import com.rcharts.client.category.bar.BarChartFactory;

//TODO: remove class
public class ScrollableBarChart extends BarChart {

	private double plotWidth;
	private double plotHeight;
	
	public ScrollableBarChart(int width, int height) {
		super(width, height);
	}

	/**
	 * @return the plotWidth
	 */
	public double getPlotWidth() {
		return plotWidth;
	}

	/**
	 * @param plotWidth the plotWidth to set
	 */
	public void setPlotWidth(double plotWidth) {
		this.plotWidth = plotWidth;
	}

	/**
	 * @return the plotHeight
	 */
	public double getPlotHeight() {
		return plotHeight;
	}

	/**
	 * @param plotHeight the plotHeight to set
	 */
	public void setPlotHeight(double plotHeight) {
		this.plotHeight = plotHeight;
	}

	
	@Override
	public void draw(){
		BarChartFactory chartFactory = new BarChartFactory();
		RaphaelObject barPlot = chartFactory.getBarChart();

	}
	
}
