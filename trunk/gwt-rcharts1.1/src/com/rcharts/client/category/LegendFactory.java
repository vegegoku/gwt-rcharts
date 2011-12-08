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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.hydro4ge.raphaelgwt.client.Raphael.Shape;
import com.hydro4ge.raphaelgwt.client.Raphael.Text;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.Align;
import com.rcharts.client.Chart;
import com.rcharts.client.DefaultAttributes;
import com.rcharts.client.LegendMark;
import com.rcharts.client.LegendMarkFactory;
import com.rcharts.client.Position;
import com.rcharts.client.RCellPanel;
import com.rcharts.client.RHorizontalPanel;
import com.rcharts.client.RVerticalPanel;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.RCellPanel.Movement;

/**
 * Makes the LegendPanel for charts
 */
public class LegendFactory {

	private Chart chart;
	//private CategoryDataTable<Double> dataTable;
	private Map<String, String> colorMap;
	private Map<String, RaphaelObject> legendMarkMap;
	private Position position = Position.EAST;
	private static String[] colors;// = DefaultAttributes.getColors(); 
	private LegendMark mark = LegendMark.RECT;
	private RaphaelObject legendPanel;
	private double width;
	private double heigth;
	
	public LegendFactory(){
		try{
			chart = RaphaelFactory.get();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		position = chart.getLegendPosition();
		colorMap = RaphaelFactory.getColorMap();//new HashMap<String, String>();
		legendMarkMap = new HashMap<String, RaphaelObject>();
		colors = chart.getDefaultColors() != null ? chart.getDefaultColors() : DefaultAttributes.getColors();
		width = chart.getChartPanel().getWidth();
		heigth = chart.getChartPanel().getHeight();
	}
	
		
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public LegendMark getMark() {
		return mark;
	}

	public void setMark(LegendMark mark) {
		this.mark = mark;
	}

	/**
	 * @return the colorMap
	 */
	public Map<String, String> getColorMap() {
		return colorMap;
	}

	/**
	 * @param colorMap the colorMap to set
	 */
	public void setColorMap(Map<String, String> colorMap) {
		this.colorMap = colorMap;
	}

	/**
	 * @return the legendMap
	 */
	public Map<String, RaphaelObject> getLegendMarkMap() {
		return legendMarkMap;
	}

	/**
	 * @param legendMap the legendMap to set
	 */
	public void setLegendMarkMap(Map<String, RaphaelObject> legendMap) {
		this.legendMarkMap = legendMap;
	}

	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}


	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		this.width = width;
	}


	/**
	 * @return the heigth
	 */
	public double getHeigth() {
		return heigth;
	}


	/**
	 * @param heigth the heigth to set
	 */
	public void setHeigth(double heigth) {
		this.heigth = heigth;
	}


	private void init(){
			generateLegend(RaphaelFactory.getSeriesNames());
	}

	private void generateLegend(Set<String> legendNames) {
		RCellPanel panel = null;
		if(position == Position.NORTH || position == Position.SOUTH){
			panel = generateHorizotalLegend(legendNames);
		}
		else{
			panel = generateVerticalLegend(legendNames);
		}
		legendPanel = panel.get();
	}


	private RCellPanel generateHorizotalLegend(Set<String> legendLabels) {
		
		com.hydro4ge.raphaelgwt.client.Raphael.Set rLegMark = chart.new Set();
		com.hydro4ge.raphaelgwt.client.Raphael.Set rLegLabel = chart.new Set();
		
		RCellPanel legPanel = new RHorizontalPanel();
		RCellPanel panel = new RVerticalPanel();
		panel.setMovement(Movement.DOWNWORD);
		boolean big = false;
		Iterator<String> it = legendLabels.iterator();
		while(it.hasNext()){
			String label = it.next();
			RaphaelObject legShape = LegendMarkFactory.get(mark);
			legShape.attr("fill", colorMap.get(label));
			Text rLabel = chart.new Text(0, 0, label);
			rLabel.attr(RaphaelFactory.get().getLegendLabelStyle());
			legPanel.add(legShape, Align.BOTTOM);
			legPanel.add(rLabel, Align.BOTTOM);
			legendMarkMap.put(label, legShape);
			//TODO : remove method alignTextNShape
//			alignTextNShape(rLabel, legShape);
			if(legPanel.get().getBBox().width() > width){
				panel.setSpacing(legPanel.get().getBBox().height());
				panel.add(legPanel, Align.LEFT);
				legPanel = new RHorizontalPanel();
				big = true;
			}
			
			rLegMark.push((Shape) legShape);
			rLegLabel.push(rLabel);
		}
		if(big){
			panel.add(legPanel);
			chart.setRlegendLabelSet(rLegLabel);
			chart.setRlegendMarkSet(rLegMark);
			return panel;
		}

		chart.setRlegendLabelSet(rLegLabel);
		chart.setRlegendMarkSet(rLegMark);
		
		return legPanel;
	}

	private RCellPanel generateVerticalLegend(Set<String> legendLabels) {
		RCellPanel legPanel = new RVerticalPanel();
		RHorizontalPanel panel = new RHorizontalPanel();
		RHorizontalPanel subPanel = null;
		boolean big = false;
		Iterator<String> it = legendLabels.iterator();
		while(it.hasNext()){
			String label = it.next();
			subPanel = new RHorizontalPanel();
			RaphaelObject legShape = LegendMarkFactory.get(mark);
			legShape.attr("fill", colorMap.get(label));
			Text rLabel = chart.new Text(0, 0, label);
			rLabel.attr(RaphaelFactory.get().getLegendLabelStyle());
			subPanel.add(legShape);
			subPanel.add(rLabel);
			legPanel.add(subPanel);
			legendMarkMap.put(label, legShape);
			alignTextNShape(rLabel, legShape);
			if(legPanel.get().getBBox().height() > heigth){
		//		panel.setSpacing(legPanel.get().getBBox().height());
				panel.add(legPanel, Align.LEFT);
				legPanel = new RVerticalPanel();
				big = true;
			}			
		}
		if(big){
			panel.add(legPanel);
			return panel;
		}
		return legPanel;
	}

	
	private void alignTextNShape(Text rLabel, RaphaelObject legShape) {
		double alignY = rLabel.getBBox().y() + rLabel.getBBox().height()/3;
		legShape.attr("y", alignY);		
	}

	
	public RaphaelObject getLegendPanel(){
		if(legendPanel == null){
			init();
		}
		return legendPanel;
	}
}
