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

import com.rcharts.client.Chart;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.ValueAxisLabelMaker;

public abstract class XYChart extends Chart{

	protected XYDataTable<Double> dataTable;
	
	public XYChart(int width, int height) {
		super(width, height);
	}
	
	@Override
	public boolean isXValueAxis(){
		return true;
	}
	
	@Override
	public boolean isYValueAxis(){
		return true;
	}

	@Override
	public java.util.Set<String> getXAxisLabels(){
		return ValueAxisLabelMaker.getValueAxisLabels(dataTable.getxMax(), dataTable.getxMin());
	}
	
	@Override
	public java.util.Set<String> getYAxisLabels(){
		return ValueAxisLabelMaker.getValueAxisLabels(dataTable.getyMax(), dataTable.getyMin());
	}

	
	@Override
	public void initRaphaelFactory(){
		RaphaelFactory.setSeries(dataTable.getSeriesNames());
	}
	
	/**
	 * @return the dataTable
	 */
	public XYDataTable<Double> getDataTable() {
		return dataTable;
	}

	/**
	 * @param dataTable the dataTable to set
	 */
	public void setDataTable(XYDataTable<Double> dataTable) {
		this.dataTable = dataTable;
	}
}
