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

import com.rcharts.client.ValueAxisLabelMaker;

public class StackedBarChart extends BarChart {

	public StackedBarChart(int width, int height) {
		super(width, height);
		this.setStacked(true);
	}
	
	/**
	 * @return the Labels for x Axis
	 */
	@Override
	public java.util.Set<String> getXAxisLabels() {
		if(isBar){
			return ValueAxisLabelMaker.getValueAxisLabels(dataTable.getMaxForStacked(), dataTable.getMin());
		}
		else{
			return dataTable.getCategoryNames();						
		}
	}

	/**
	 * @return the Tick Labels for Y axis
	 */
	@Override
	public java.util.Set<String> getYAxisLabels() {
		if(isBar){
			return dataTable.getCategoryNames();
		}
		else{
			return ValueAxisLabelMaker.getValueAxisLabels(dataTable.getMaxForStacked(), dataTable.getMin());
		}
	}


}
