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

import com.rcharts.client.Chart;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.ValueAxisLabelMaker;

/**
 * Base class for all the category charts.
 * Category chart is one having one numeric axis/dimension and other with strings 
 */

public abstract class CategoryChart extends Chart {

	protected boolean isBar = true;
	protected CategoryDataTable<Double> dataTable;
	
	
	public CategoryChart(int width, int height) {
		super(width, height);
	}


	/**
	 * Whether the chart is Bar chart(true) or not i.e column chart
	 * @return the isBar
	 */
	public boolean isBar() {
		return isBar;
	}

	/**
	 * Sets whether the chart is Bar chart or Column chart
	 * @param isBar the isBar to set
	 */
	public void setBar(boolean isBar) {
		this.isBar = isBar;
	}

	/**
	 * For bar chart X axis is value axis hence return true
	 * For column chart X axis is domain axis hence return false
	 * @return the isXValueAxis
	 */
	@Override
	public boolean isXValueAxis() {
		if(isBar){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * For bar chart Y axis is not Value Axis, its domain axis so it returns false
	 * For column chart Y axis is Value Axis, hence returns true
	 * @return the isYValueAxis
	 */
	@Override
	public boolean isYValueAxis() {
		if(isBar){
			return false;
		}
		else{
			//Column chart
			return true;
		}
	}

	/**
	 * @return the Labels for x Axis
	 */
	@Override
	public java.util.Set<String> getXAxisLabels() {
		if(isBar){
			return ValueAxisLabelMaker.getValueAxisLabels(dataTable.getMax(), dataTable.getMin());
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
			return ValueAxisLabelMaker.getValueAxisLabels(dataTable.getMax(), dataTable.getMin());
		}
	}

	@Override
	protected void initRaphaelFactory() {
		RaphaelFactory.setSeries(dataTable.getSeriesNames());
		RaphaelFactory.setCategories(dataTable.getCategoryNames());
		RaphaelFactory.setCatMaxValue(dataTable.getMaxValue());
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

}
