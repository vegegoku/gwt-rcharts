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


public class AxisConfiguration {

	private boolean xAxisAtNorth = false;
	private boolean yAxisAtEast = false;
	private boolean doubleXAxis = false;
	private boolean doubleYAxis = false;
	private boolean gridMouseOverStyle = false;
	
	
	
	
	/**
	 * @return the xAxisAtNorth
	 */
	public boolean isxAxisAtNorth() {
		return xAxisAtNorth;
	}
	/**
	 * @param xAxisAtNorth the xAxisAtNorth to set
	 */
	public void setxAxisAtNorth(boolean xAxisAtNorth) {
		this.xAxisAtNorth = xAxisAtNorth;
	}
	/**
	 * @return the yAxisAtEast
	 */
	public boolean isyAxisAtEast() {
		return yAxisAtEast;
	}
	/**
	 * @param yAxisAtEast the yAxisAtEast to set
	 */
	public void setyAxisAtEast(boolean yAxisAtEast) {
		this.yAxisAtEast = yAxisAtEast;
	}
	/**
	 * @return the doubleXAxis
	 */
	public boolean isDoubleXAxis() {
		return doubleXAxis;
	}
	/**
	 * @param doubleXAxis the doubleXAxis to set
	 */
	public void setDoubleXAxis(boolean doubleXAxis) {
		this.doubleXAxis = doubleXAxis;
	}
	/**
	 * @return the doubleYAxis
	 */
	public boolean isDoubleYAxis() {
		return doubleYAxis;
	}
	/**
	 * @param doubleYAxis the doubleYAxis to set
	 */
	public void setDoubleYAxis(boolean doubleYAxis) {
		this.doubleYAxis = doubleYAxis;
	}
	/**
	 * @return the gridMouseOverStyle
	 */
	public boolean isGridMouseOverStyle() {
		return gridMouseOverStyle;
	}
	/**
	 * @param gridMouseOverStyle the gridMouseOverStyle to set
	 */
	public void setGridMouseOverStyle(boolean gridMouseOverStyle) {
		this.gridMouseOverStyle = gridMouseOverStyle;
	}
	
	
}
