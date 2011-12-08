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
package com.rcharts.client.pie;

import java.util.Map;

import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.Chart;
import com.rcharts.client.Point;
import com.rcharts.client.RDockPanel;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.category.LegendFactory;

public class PieChart extends Chart {

	public static enum PieSliceText{
		PERCENTAGE, VALUE, NAME, NONE;
	}
	
	private PieDataTable<Double> dataTable;
	private PieSliceText sliceText;
	private boolean isDoughNut;
	private boolean showLabels;
	private boolean showSpeechBubble;
	
	public PieChart(int width, int height) {
		super(width, height);
	}

	/**
	 * @return the dataTable
	 */
	public PieDataTable<Double> getDataTable() {
		return dataTable;
	}

	/**
	 * @param dataTable the dataTable to set
	 */
	public void setDataTable(PieDataTable<Double> dataTable) {
		this.dataTable = dataTable;
	}

	/**
	 * @return the sliceText
	 */
	public PieSliceText getSliceText() {
		return sliceText;
	}

	/**
	 * @param sliceText the sliceText to set
	 */
	public void setSliceText(PieSliceText sliceText) {
		this.sliceText = sliceText;
	}

	/**
	 * @return the isDoughNut
	 */
	public boolean isDoughNut() {
		return isDoughNut;
	}

	/**
	 * @param isDoughNut the isDoughNut to set
	 */
	public void setDoughNut(boolean isDoughNut) {
		this.isDoughNut = isDoughNut;
	}

	/**
	 * @return the showLabels
	 */
	public boolean isShowLabels() {
		return showLabels;
	}

	/**
	 * @param showLabels the showLabels to set
	 */
	public void setShowLabels(boolean showLabels) {
		this.showLabels = showLabels;
	}

	/**
	 * @return the showSpeechBubble
	 */
	public boolean isShowSpeechBubble() {
		return showSpeechBubble;
	}

	/**
	 * @param showSpeechBubble the showSpeechBubble to set
	 */
	public void setShowSpeechBubble(boolean showSpeechBubble) {
		this.showSpeechBubble = showSpeechBubble;
	}

	@Override
	protected void initRaphaelFactory() {
		dataTable.init();
		RaphaelFactory.setSeries(dataTable.getNames());
	}
	
	/**
	 * 	 Not Applicable
	 *	TODO: remove the class from chart hierarchy
	 */
	@Override
	public java.util.Set<String> getXAxisLabels() {
		// Not Applicable
		//TODO: remove the class from chart hierarchy
		return null;
	}

	/**
	 * 	 Not Applicable
	 *	TODO: remove the class from chart hierarchy
	 */	
	@Override
	public java.util.Set<String> getYAxisLabels() {
		// TODO remove the class from chart hierarchy
		// Not Applicable
		return null;
	}

	/**
	 * 	 Not Applicable
	 *	TODO: remove the class from chart hierarchy
	 */
	@Override
	public boolean isXValueAxis() {
		// TODO remove the class from chart hierarchy
		// Not Applicable
		return false;
	}

	/**
	 * 	 Not Applicable
	 *	TODO: remove the class from chart hierarchy
	 */
	@Override
	public boolean isYValueAxis() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void draw() {
		chartPanel = new RDockPanel();
		setTitle(chartPanel);		
		LegendFactory legFactory = new LegendFactory();
		legFactory.setWidth(chartPanel.getWidth()-100);
		legFactory.setHeigth(chartPanel.getHeight()-100);
		RaphaelObject legPanel = legFactory.getLegendPanel();
		chartPanel.add(legPanel, getLegendPosition());
		double xRadius = chartPanel.getWidth()/2;
		xRadius = xRadius - getOffset(xRadius);
		double yRadius = xRadius/2;
		SliceFactory factory = new SliceFactory();
		factory.setxRadius(xRadius);
		if(is_3d()){
			factory.setyRadius(yRadius - 50);
		}
		else{
			double r = xRadius > yRadius ? yRadius : xRadius;
			factory.setxRadius(r);
			factory.setyRadius(r);
//			factory.setyRadius(xRadius);
		}
		factory.setCenter(chartPanel.getCenter());
		RaphaelObject plot = factory.create3DPieChart();
		setHandler(factory.getSlice3DMap(), legFactory.getLegendMarkMap());
		setPlotStyle(plot);
	}
	
	
	private void setHandler(Map<String, Slice3D> slice3DMap, 
			Map<String, RaphaelObject> legendMarkMap){
		java.util.Set<String> names = slice3DMap.keySet();
		for(String name : names){
			SliceHandler handler = new SliceHandler();
			handler.setLegend(legendMarkMap.get(name));
			handler.setShowSpeechBubble(showSpeechBubble);
			Slice3D slice = slice3DMap.get(name);
			handler.setSlice(slice);
			slice.addClickHandler(handler);
			slice.addMouseOverHandler(handler);
			slice.addMouseOutHandler(handler);
		}
	}
	
	private double getOffset(double xRadius){
		double offset = xRadius/8 + 10 + 100;
		return offset;
	}
	

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}
