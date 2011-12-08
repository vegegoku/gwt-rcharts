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

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.hydro4ge.raphaelgwt.client.Raphael.Circle;
import com.hydro4ge.raphaelgwt.client.Raphael.Path;
import com.hydro4ge.raphaelgwt.client.Raphael.Set;
import com.hydro4ge.raphaelgwt.client.Raphael.Text;
import com.rcharts.client.Chart;
import com.rcharts.client.RaphaelFactory;

public class Slice3D implements Slice {
	private Path topViewPath;
	private Path LHSViewPath;
	private Path RHSViewPath;
	private Path lowerArcPath;
	private Path innerArcPath;
	private Path connector;
	private Text label;
	private String color;
	private String name;
	private double value;
	private Circle popupPoint;
	private Circle textOnSlicePoint;
	private Circle sliceOutPoint;
	private Circle center;
	private double startAngle;
	private double endAngle;
	private Set set;
	private Chart chart;
	
	
	private double total;
	public Slice3D(){
		chart = RaphaelFactory.get();
	}
	
	public Slice3D(Path topViewPath, Path lHSViewPath, Path rHSViewPath,
			Path lowerArcPath, Path innerArcPath) {
		super();
		this.topViewPath = topViewPath;
		LHSViewPath = lHSViewPath;
		RHSViewPath = rHSViewPath;
		this.lowerArcPath = lowerArcPath;
		this.innerArcPath = innerArcPath;
	}

	/**
	 * @return the topViewPath
	 */
	public Path getTopViewPath() {
		return topViewPath;
	}

	/**
	 * @param topViewPath the topViewPath to set
	 */
	public void setTopViewPath(Path topViewPath) {
		this.topViewPath = topViewPath;
	}

	/**
	 * @return the lHSViewPath
	 */
	public Path getLHSViewPath() {
		return LHSViewPath;
	}

	/**
	 * @param lHSViewPath the lHSViewPath to set
	 */
	public void setLHSViewPath(Path lHSViewPath) {
		LHSViewPath = lHSViewPath;
	}

	/**
	 * @return the rHSViewPath
	 */
	public Path getRHSViewPath() {
		return RHSViewPath;
	}

	/**
	 * @param rHSViewPath the rHSViewPath to set
	 */
	public void setRHSViewPath(Path rHSViewPath) {
		RHSViewPath = rHSViewPath;
	}

	/**
	 * @return the lowerArcPath
	 */
	public Path getLowerArcPath() {
		return lowerArcPath;
	}

	/**
	 * @param lowerArcPath the lowerArcPath to set
	 */
	public void setLowerArcPath(Path lowerArcPath) {
		this.lowerArcPath = lowerArcPath;
	}

	/**
	 * @return the innerArcPath
	 */
	public Path getInnerArcPath() {
		return innerArcPath;
	}

	/**
	 * @param innerArcPath the innerArcPath to set
	 */
	public void setInnerArcPath(Path innerArcPath) {
		this.innerArcPath = innerArcPath;
	}

	
	@Override
	public void fillColor(String color){
		if(topViewPath != null){
			topViewPath.attr("fill", color);
		}
		if(LHSViewPath != null){
			LHSViewPath.attr("fill", color);
		}
		if(RHSViewPath != null){
			RHSViewPath.attr("fill", color);
		}
		if(lowerArcPath != null){
			lowerArcPath.attr("fill", color);
		}
		if(innerArcPath != null){
			innerArcPath.attr("fill", color);
		}
		
	}
	
	
	public void setStrokeWidth(double strokeWidth){
		if(topViewPath != null){
			topViewPath.attr("stroke-width", strokeWidth);
		}
		if(LHSViewPath != null){
			LHSViewPath.attr("stroke-width", strokeWidth);
		}
		if(RHSViewPath != null){
			RHSViewPath.attr("stroke-width", strokeWidth);
		}
		if(lowerArcPath != null){
			lowerArcPath.attr("stroke-width", strokeWidth);
		}
		if(innerArcPath != null){
			innerArcPath.attr("stroke-width", strokeWidth);
		}
	}
	
	public void setStrokeColor(String color){
		if(topViewPath != null){
			topViewPath.attr("stroke", color);
		}
		if(LHSViewPath != null){
			LHSViewPath.attr("stroke", color);
		}
		if(RHSViewPath != null){
			RHSViewPath.attr("stroke", color);
		}
		if(lowerArcPath != null){
			lowerArcPath.attr("stroke", color);
		}
		if(innerArcPath != null){
			innerArcPath.attr("stroke", color);
		}		
	}
	
	
	public void setTitle(String title){
		if(topViewPath != null){
			topViewPath.setTitle(title);
		}
		if(LHSViewPath != null){
			LHSViewPath.setTitle(title);
		}
		if(RHSViewPath != null){
			RHSViewPath.setTitle(title);
		}
		if(lowerArcPath != null){
			lowerArcPath.setTitle(title);
		}
		if(innerArcPath != null){
			innerArcPath.setTitle(title);
		}
	}
	
	public void setTitle(){
		PieChart ch = (PieChart) chart;
		if(ch.isShowSpeechBubble()){
			return;
		}
		double per = ((value/total)*100);
		NumberFormat format = NumberFormat.getFormat("##.##");
		String title = name+" : "+value+" \n "+format.format(per)+"%";
		setTitle(title);
		
	}
	
	/**
	 * @return the color
	 */
	@Override
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	@Override
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	@Override
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	@Override
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * @return the popupPoint
	 */
	@Override
	public Circle getPopupPoint() {
		return popupPoint;
	}

	/**
	 * @param popupPoint the popupPoint to set
	 */
	@Override
	public void setPopupPoint(Circle popupPoint) {
		this.popupPoint = popupPoint;
	}

	/**
	 * @return the textOnSlicePoint
	 */
	@Override
	public Circle getTextOnSlicePoint() {
		return textOnSlicePoint;
	}

	/**
	 * @param textOnSlicePoint the textOnSlicePoint to set
	 */
	@Override
	public void setTextOnSlicePoint(Circle textOnSlicePoint) {
		this.textOnSlicePoint = textOnSlicePoint;
	}

	/**
	 * @return the sliceOutPoint
	 */
	@Override
	public Circle getSliceOutPoint() {
		return sliceOutPoint;
	}

	/**
	 * @param sliceOutPoint the sliceOutPoint to set
	 */
	@Override
	public void setSliceOutPoint(Circle sliceOutPoint) {
		this.sliceOutPoint = sliceOutPoint;
	}

	/**
	 * @return the center
	 */
	public Circle getCenter() {
		return center;
	}

	/**
	 * @param center the center to set
	 */
	public void setCenter(Circle center) {
		this.center = center;
	}

	/**
	 * @return the startAngle
	 */
	public double getStartAngle() {
		return startAngle;
	}

	/**
	 * @param startAngle the startAngle to set
	 */
	public void setStartAngle(double startAngle) {
		this.startAngle = startAngle;
	}

	/**
	 * @return the endAngle
	 */
	public double getEndAngle() {
		return endAngle;
	}

	/**
	 * @param endAngle the endAngle to set
	 */
	public void setEndAngle(double endAngle) {
		this.endAngle = endAngle;
	}

	@Override
	public void addClickHandler(ClickHandler handler){
		if(topViewPath != null){
			topViewPath.addClickHandler(handler);
		}
		if(LHSViewPath != null){
			LHSViewPath.addClickHandler(handler);
		}
		if(RHSViewPath != null){
			RHSViewPath.addClickHandler(handler);
		}
		if(lowerArcPath != null){
			lowerArcPath.addClickHandler(handler);
		}
		if(innerArcPath != null){
			innerArcPath.addClickHandler(handler);
		}
	}
	
	@Override
	public void addMouseOverHandler(MouseOverHandler handler){
		if(topViewPath != null){
			topViewPath.addMouseOverHandler(handler);
		}
		if(LHSViewPath != null){
			LHSViewPath.addMouseOverHandler(handler);
		}
		if(RHSViewPath != null){
			RHSViewPath.addMouseOverHandler(handler);
		}
		if(lowerArcPath != null){
			lowerArcPath.addMouseOverHandler(handler);
		}
		if(innerArcPath != null){
			innerArcPath.addMouseOverHandler(handler);
		}
		
	}
	
	public void addMouseOutHandler(MouseOutHandler handler){
		if(topViewPath != null){
			topViewPath.addMouseOutHandler(handler);
		}
		if(LHSViewPath != null){
			LHSViewPath.addMouseOutHandler(handler);
		}
		if(RHSViewPath != null){
			RHSViewPath.addMouseOutHandler(handler);
		}
		if(lowerArcPath != null){
			lowerArcPath.addMouseOutHandler(handler);
		}
		if(innerArcPath != null){
			innerArcPath.addMouseOutHandler(handler);
		}
		
	}
	

	@Override
	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public double getTotal() {
		return total;
	}

	public void setLabel(Path connector, Text label){
		this.connector = connector;
		this.label = label;
	}
	
	public Set get(){
		if(set == null){
			set = chart.new Set();
			set.push(topViewPath);
			if(lowerArcPath != null){
				set.push(lowerArcPath);
			}
			if(LHSViewPath != null){
				set.push(LHSViewPath);
			}
			if(RHSViewPath != null){
				set.push(RHSViewPath);
			}
			if(innerArcPath != null){
				set.push(innerArcPath);
			}
			if(center != null){
				set.push(center);
			}
			if(popupPoint != null){
				set.push(popupPoint);
			}
			if(sliceOutPoint != null){
				set.push(sliceOutPoint);
			}
			if(textOnSlicePoint != null){
				set.push(textOnSlicePoint);
			}
			if(connector != null){
				set.push(connector);
			}
			if(label != null){
				set.push(label);
			}
		}
		return set;
	}

	/**
	 * @return the chart
	 */
	public Chart getChart() {
		return chart;
	}

	/**
	 * @param chart the chart to set
	 */
	public void setChart(Chart chart) {
		this.chart = chart;
	}
}
