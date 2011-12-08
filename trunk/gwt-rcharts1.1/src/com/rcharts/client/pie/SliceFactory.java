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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hydro4ge.raphaelgwt.client.PathBuilder;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.hydro4ge.raphaelgwt.client.Raphael.Circle;
import com.hydro4ge.raphaelgwt.client.Raphael.Path;
import com.hydro4ge.raphaelgwt.client.Raphael.Set;
import com.hydro4ge.raphaelgwt.client.Raphael.Text;
import com.rcharts.client.Point;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.pie.PieDataTable.AnglePair;

public class SliceFactory {

	private PieChart chart;
	private Point center;
	private double xRadius;  //Major
	private double yRadius;  // Minor
	private Set plotSet;
	private Map<String, Slice2D> slice2DMap = new HashMap<String, Slice2D>();
	private Map<String, Slice3D> slice3DMap = new HashMap<String, Slice3D>();
	private PieDataTable dataTable;
	private Map<String, String> colorMap;

	public SliceFactory(){
		chart = (PieChart) RaphaelFactory.get();
		plotSet = chart.new Set();
		colorMap = RaphaelFactory.getColorMap();
	}
	
	
	/**
	 * @return the dataTable
	 */
	public PieDataTable getDataTable() {
		return dataTable;
	}



	/**
	 * @param dataTable the dataTable to set
	 */
	public void setDataTable(PieDataTable dataTable) {
		this.dataTable = dataTable;
	}

	
	/**
	 * @return the chart
	 */
	public PieChart getChart() {
		return chart;
	}



	/**
	 * @param chart the chart to set
	 */
	public void setChart(PieChart chart) {
		this.chart = chart;
	}



	/**
	 * @return the center
	 */
	public Point getCenter() {
		return center;
	}



	/**
	 * @param center the center to set
	 */
	public void setCenter(Point center) {
		this.center = center;
	}



	/**
	 * @return the xRadius
	 */
	public double getxRadius() {
		return xRadius;
	}



	/**
	 * @param xRadius the xRadius to set
	 */
	public void setxRadius(double xRadius) {
		this.xRadius = xRadius;
	}



	/**
	 * @return the yRadius
	 */
	public double getyRadius() {
		return yRadius;
	}



	/**
	 * @param yRadius the yRadius to set
	 */
	public void setyRadius(double yRadius) {
		this.yRadius = yRadius;
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
	
	public Slice3D getDoughNut(double startAngle, double endAngle){
		if(!chart.is_3d()){
			yRadius = xRadius;
		}
		double midAngle = startAngle + (endAngle - startAngle)/2;
		int large_arc_flag = 0;
		int sweep_flag = 1;
		int reverse_sweep_flag = 0;
		if((endAngle - startAngle) > 180){
			large_arc_flag = 1;
		}
		double xAxisRotation = 0;
		double pw = xRadius/10;
		
		double xInner = xRadius/2.5;
		double yInner = yRadius/2.5;
		
		Point p1 = getEllipsePoint(center.getX(), center.getY(), xInner, yInner, startAngle);
		Point p8 =  getEllipsePoint(center.getX(), center.getY(), xInner, yInner, endAngle);
		Point p2 = getEllipsePoint(center.getX(), center.getY(), xRadius, yRadius, startAngle);
		Point p3 = getEllipsePoint(center.getX(), center.getY(), xRadius, yRadius, endAngle);
		Point p4 = new Point(p3.getX(), p3.getY() + pw);
		Point p5 = new Point(p2.getX(), p2.getY() + pw);
		Point p6 = new Point(p1.getX(), p1.getY() + pw);
		Point p7 = new Point(p8.getX(), p8.getY() + pw);
		PathBuilder pb = new PathBuilder();

		
		Point popupPoint = getEllipsePoint(center.getX(), center.getY(), xRadius, yRadius, midAngle);
		Point sliceTextPoint = getEllipsePoint(center.getX(), center.getY(), xRadius/2, yRadius/2, midAngle);
		Point sliceOutPoint = getEllipsePoint(center.getX(), center.getY(), xRadius/5, yRadius/5, midAngle);
		
		Circle popupCircle = chart.new Circle(popupPoint.getX(), popupPoint.getY(), 1);
		Circle sliceTextCircle = chart.new Circle(sliceTextPoint.getX(), sliceTextPoint.getY(), 0.5);
		Circle sliceOutCircle = chart.new Circle(sliceOutPoint.getX(), sliceOutPoint.getY(), 1);
		
		//Top view of pie always visible
		pb.M(p1.getX(), p1.getY());
		pb.L(p2.getX(), p2.getY());
		pb.A(xRadius, yRadius, xAxisRotation, large_arc_flag, sweep_flag, p3.getX(), p3.getY());
		pb.L(p8.getX(), p8.getY());
		pb.A(xInner, yInner, xAxisRotation, large_arc_flag, reverse_sweep_flag, p1.getX(), p1.getY());
		pb.Z();
		Path topViewPath = chart.new Path(pb);

		if(!chart.is_3d()){
			Slice3D slice = new Slice3D(topViewPath, null, null, null, null);
			slice.setPopupPoint(popupCircle);
			slice.setTextOnSlicePoint(sliceTextCircle);
			slice.setSliceOutPoint(sliceOutCircle);
			Circle c = chart.new Circle(center.getX(), center.getY(), 0.5);
			c.hide();
			slice.setCenter(c);
			plotSet.push(c);
			plotSet.push(sliceOutCircle);
			slice.setChart(chart);
			popupCircle.hide();
			sliceTextCircle.hide();
			sliceOutCircle.hide();
			return slice;		
		}

		Path LHSViewPath = null;
		Path RHSViewPath = null;
		Path lowerArcPath = null;
		Path innerArcPath = null;
		pb = new PathBuilder();
		//LHS view path attached with startAngle p1, p2, p5, p6, p1 visible between 90 - 270
		if(startAngle > 90 && startAngle <= 270){
			pb.M(p1.getX(), p1.getY());
			pb.L(p2.getX(), p2.getY());
			pb.L(p5.getX(), p5.getY());
			pb.L(p6.getX(), p6.getY());
			pb.Z();
			LHSViewPath = chart.new Path(pb);
		}
		pb = new PathBuilder();
		//RHS side view of edge with endAngle p8, p3, p4, p7 visible between 270 - 90
		if((endAngle >= 0 && endAngle <= 90) || (endAngle > 270 && endAngle <= 360) || startAngle < 0){
			pb.M(p8.getX(), p8.getY());
			pb.L(p3.getX(), p3.getY());
			pb.L(p4.getX(), p4.getY());
			pb.L(p7.getX(), p7.getY());
			pb.Z();
			RHSViewPath = chart.new Path(pb);
		}

		pb = new PathBuilder();
		//Lower arc  fully visible for 0 - 180 otherwise partially
		if(startAngle >= 0 && startAngle <=180 && endAngle >= 0 && endAngle <= 180){
			pb.M(p2.getX(), p2.getY());
			pb.A(xRadius, yRadius, xAxisRotation, large_arc_flag, sweep_flag, p3.getX(), p3.getY());
			pb.L(p4.getX(), p4.getY());
			pb.A(xRadius, yRadius, xAxisRotation, large_arc_flag, reverse_sweep_flag, p5.getX(), p5.getY());
			pb.Z();
			lowerArcPath = chart.new Path(pb);
		}
		
		
		
		if(startAngle > 180 && endAngle <= 180){
			Point vp2 = getEllipsePoint(center.getX(), center.getY(), xRadius, yRadius, 0);
			Point vp5 = new Point(p2.getX(), p2.getY() + pw);
			pb.M(vp2.getX(), vp2.getY());
			pb.A(xRadius, yRadius, xAxisRotation, large_arc_flag, sweep_flag, p3.getX(), p3.getY());
			pb.L(p4.getX(), p4.getY());
			pb.A(xRadius, yRadius, xAxisRotation, large_arc_flag, reverse_sweep_flag, vp5.getX(), vp5.getY());
			pb.Z();
			lowerArcPath = chart.new Path(pb);
		}
		
		if(startAngle <= 180 && endAngle > 180){
			Point vp3 = getEllipsePoint(center.getX(), center.getY(), xRadius, yRadius, 180);
			Point vp4 = new Point(vp3.getX(), vp3.getY() + pw);
			pb.M(p2.getX(), p2.getY());
			pb.A(xRadius, yRadius, xAxisRotation, 0, sweep_flag, vp3.getX(), vp3.getY());
			pb.L(vp4.getX(), vp4.getY());
			pb.A(xRadius, yRadius, xAxisRotation, 0, reverse_sweep_flag, p5.getX(), p5.getY());
			pb.Z();
			pb.M(p1.getX(), p1.getY());
			lowerArcPath = chart.new Path(pb);
		}
		
		pb = new PathBuilder();
		if(startAngle >= 180 && startAngle <= 360 && endAngle >= 180 && endAngle <= 360){
			pb.M(p1.getX(), p1.getY());
			pb.A(xInner, yInner, xAxisRotation, large_arc_flag, sweep_flag, p8.getX(), p8.getY());
			pb.L(p7.getX(), p7.getY());
			pb.A(xInner, yInner, xAxisRotation, large_arc_flag, reverse_sweep_flag, p6.getX(), p6.getY());
			pb.Z();
			innerArcPath = chart.new Path(pb);			
//			Window.alert(startAngle+" : "+endAngle+" 1");
		}
		//inner arc partially visible for  startAngle to 360 degree
		if(startAngle > 180 && endAngle <= 180){// || Math.ceil(endAngle) == 360){
			Point vp8 = getEllipsePoint(center.getX(), center.getY(), xRadius/3, yRadius/3, 360);
			Point vp7 = new Point(p2.getX(), p2.getY() + pw);
			pb.M(p1.getX(), p1.getY());
			pb.A(xInner, yInner, xAxisRotation, large_arc_flag, sweep_flag, vp8.getX(), vp8.getY());
			pb.A(xInner, yInner, xAxisRotation, large_arc_flag, reverse_sweep_flag, p6.getX(), p6.getY());
			pb.Z();
			innerArcPath = chart.new Path(pb);
//			Window.alert(startAngle+" : "+endAngle+" 2");
		}
		
		if(startAngle <= 180 && endAngle >= 180){
			Point vp1 = getEllipsePoint(center.getX(), center.getY(), xRadius/3, yRadius/3, 180);
			pb.M(p1.getX(), p1.getY());
			pb.A(xInner, yInner, xAxisRotation, large_arc_flag, sweep_flag, p8.getX(), p8.getY());
			pb.L(p7.getX(), p7.getY());
			pb.A(xInner, yInner, xAxisRotation, large_arc_flag, reverse_sweep_flag, p6.getX(), p6.getY());
			pb.Z();
			innerArcPath = chart.new Path(pb);
//			Window.alert(startAngle+" : "+endAngle+" 3");
		}
		

		plotSet.push(topViewPath);
		
		if(LHSViewPath != null){
		//	pieSet.push(LHSViewPath);
			plotSet.push(LHSViewPath);
		//	LHSViewPath.toBack();
		}
		if(RHSViewPath != null){
			//pieSet.push(RHSViewPath);
			plotSet.push(RHSViewPath);
		//	RHSViewPath.toBack();			
		}
		if(lowerArcPath != null){
			//pieSet.push(lowerArcPath);
			plotSet.push(lowerArcPath);
//			lowerArcPath.toFront();			
		}
		if(innerArcPath != null){
			plotSet.push(innerArcPath);
		}
		topViewPath.toFront();
		Slice3D slice = new Slice3D(topViewPath, LHSViewPath, RHSViewPath, 
				lowerArcPath, innerArcPath);
		slice.setPopupPoint(popupCircle);
		slice.setTextOnSlicePoint(sliceTextCircle);
		slice.setSliceOutPoint(sliceOutCircle);
		Circle c = chart.new Circle(center.getX(), center.getY(), 0.5);
		c.hide();
		slice.setCenter(c);
		plotSet.push(c);
		plotSet.push(sliceOutCircle);
		plotSet.push(popupCircle);
		slice.setChart(chart);
		popupCircle.hide();
		sliceTextCircle.hide();
		sliceOutCircle.hide();
		sliceOutCircle.toFront();
		return slice;

	}

	public Slice3D getPie(double startAngle, double endAngle){
		if(!chart.is_3d()){
			yRadius = xRadius;
		}
		double midAngle = startAngle + (endAngle - startAngle)/2;
		int large_arc_flag = 0;
		int sweep_flag = 1;
		int reverse_sweep_flag = 0;
		if((endAngle - startAngle) > 180){
			large_arc_flag = 1;
		}
		double xAxisRotation = 0;
		double pw = xRadius/10;//30;
		
		
		Point p1 = center;
		Point p2 = getEllipsePoint(p1.getX(), p1.getY(), xRadius, yRadius, startAngle);
		Point p3 = getEllipsePoint(p1.getX(), p1.getY(), xRadius, yRadius, endAngle);
		Point p4 = new Point(p3.getX(), p3.getY() + pw);
		Point p5 = new Point(p2.getX(), p2.getY() + pw);
		Point p6 = new Point(p1.getX(), p1.getY() + pw);
		PathBuilder pb = new PathBuilder();

		
		Point popupPoint = getEllipsePoint(p1.getX(), p1.getY(), xRadius, yRadius, midAngle);
		Point sliceTextPoint = getEllipsePoint(p1.getX(), p1.getY(), xRadius/2, yRadius/2, midAngle);
		Point sliceOutPoint = getEllipsePoint(p1.getX(), p1.getY(), xRadius/4, yRadius/4, midAngle);
		
		Circle popupCircle = chart.new Circle(popupPoint.getX(), popupPoint.getY(), 1);
		Circle sliceTextCircle = chart.new Circle(sliceTextPoint.getX(), sliceTextPoint.getY(), 0.5);
		Circle sliceOutCircle = chart.new Circle(sliceOutPoint.getX(), sliceOutPoint.getY(), 1);
		
		
		//Top view of pie always visible
		pb.M(p1.getX(), p1.getY());
		pb.L(p2.getX(), p2.getY());
		pb.A(xRadius, yRadius, xAxisRotation, large_arc_flag, sweep_flag, p3.getX(), p3.getY());
		pb.Z();
		
		Path topViewPath = chart.new Path(pb);
		
		if(!chart.is_3d()){
			Slice3D slice = new Slice3D(topViewPath, null, null, null, null);
			slice.setPopupPoint(popupCircle);
			slice.setTextOnSlicePoint(sliceTextCircle);
			slice.setSliceOutPoint(sliceOutCircle);
			Circle c = chart.new Circle(center.getX(), center.getY(), 0.5);
			c.hide();
			slice.setCenter(c);
			plotSet.push(c);
			plotSet.push(sliceOutCircle);
			slice.setChart(chart);
			popupCircle.hide();
			sliceTextCircle.hide();
			sliceOutCircle.hide();
			return slice;		
		}
		
		Path LHSViewPath = null;
		Path RHSViewPath = null;
		Path lowerArcPath = null;
		pb = new PathBuilder();
		//LHS view path
		if(startAngle > 90 && startAngle <= 270){
			pb.M(p1.getX(), p1.getY());
			pb.L(p2.getX(), p2.getY());
			pb.L(p5.getX(), p5.getY());
			pb.L(p6.getX(), p6.getY());
			//pb.L(p1.getX(), p1.getY());
			pb.Z();
			LHSViewPath = chart.new Path(pb);
		}

		pb = new PathBuilder();
		//RHS side view of edge with endAngle p1, p2, p4, p6 visible between 270 - 90
		if((endAngle >= 0 && endAngle <= 90) || (endAngle > 270 && endAngle <= 360) || startAngle < 0){
			pb.M(p1.getX(), p1.getY());
			pb.L(p3.getX(), p3.getY());
			pb.L(p4.getX(), p4.getY());
			pb.L(p6.getX(), p6.getY());
			pb.Z();
			RHSViewPath = chart.new Path(pb);
		}

		pb = new PathBuilder();
		//Lower arc  fully visible for 0 - 180 otherwise partially
		if(startAngle >= 0 && startAngle <=180 && endAngle >= 0 && endAngle <= 180){
			pb.M(p2.getX(), p2.getY());
			pb.A(xRadius, yRadius, xAxisRotation, large_arc_flag, sweep_flag, p3.getX(), p3.getY());
			pb.L(p4.getX(), p4.getY());
			pb.A(xRadius, yRadius, xAxisRotation, large_arc_flag, reverse_sweep_flag, p5.getX(), p5.getY());
			pb.Z();
			lowerArcPath = chart.new Path(pb);
		}
		
		
		if(startAngle > 180 && endAngle <= 180){
			Point vp2 = getEllipsePoint(p1.getX(), p1.getY(), xRadius, yRadius, 0);
			Point vp5 = new Point(p2.getX(), p2.getY() + pw);
			pb.M(vp2.getX(), vp2.getY());
			pb.A(xRadius, yRadius, xAxisRotation, large_arc_flag, sweep_flag, p3.getX(), p3.getY());
			pb.L(p4.getX(), p4.getY());
			pb.A(xRadius, yRadius, xAxisRotation, large_arc_flag, reverse_sweep_flag, vp5.getX(), vp5.getY());
			pb.Z();
			lowerArcPath = chart.new Path(pb);
		}
		
		if(startAngle <= 180 && endAngle > 180){
			Point vp3 = getEllipsePoint(p1.getX(), p1.getY(), xRadius, yRadius, 180);
			Point vp4 = new Point(vp3.getX(), vp3.getY() + pw);
			pb.M(p2.getX(), p2.getY());
			pb.A(xRadius, yRadius, xAxisRotation, 0, sweep_flag, vp3.getX(), vp3.getY());
			pb.L(vp4.getX(), vp4.getY());
			pb.A(xRadius, yRadius, xAxisRotation, 0, reverse_sweep_flag, p5.getX(), p5.getY());
			pb.Z();
			pb.M(p1.getX(), p1.getY());
			lowerArcPath = chart.new Path(pb);
		}

		plotSet.push(topViewPath);
		
		if(LHSViewPath != null){
			plotSet.push(LHSViewPath);
		}
		if(RHSViewPath != null){
			plotSet.push(RHSViewPath);
		}
		if(lowerArcPath != null){
			plotSet.push(lowerArcPath);
		}
		topViewPath.toFront();
		Slice3D slice = new Slice3D(topViewPath, LHSViewPath, RHSViewPath, lowerArcPath, null);
		slice.setPopupPoint(popupCircle);
		slice.setTextOnSlicePoint(sliceTextCircle);
		slice.setSliceOutPoint(sliceOutCircle);
		Circle c = chart.new Circle(center.getX(), center.getY(), 0.5);
		c.hide();
		slice.setCenter(c);
		plotSet.push(c);
		plotSet.push(sliceOutCircle);
		slice.setChart(chart);
		popupCircle.hide();
		sliceTextCircle.hide();
		sliceOutCircle.hide();
		return slice;
	}

	public Point getEllipsePoint(double x, double y, double a, double b, double angle){
		double sinBeta = Math.sin(0);
		double cosBeta = Math.cos(0);
		double alpha = Math.toRadians(angle);
		double sinAlpha = Math.sin(alpha);
		double cosAlpha = Math.cos(alpha);
		double X = x + (a * cosAlpha * cosBeta - b * sinAlpha * sinBeta);
		double Y = y + (a * cosAlpha * sinBeta + b * sinAlpha * cosBeta);
		return new Point(X, Y);
	}

	
	
	public RaphaelObject create3DPieChart(){
		PieDataTable<Double> dataTable = chart.getDataTable();
		Map<String, AnglePair> anglePairMap = dataTable.getAnglePairMap();
		for(String name : anglePairMap.keySet()){//dataTable.getNames()){
			AnglePair anglePair = anglePairMap.get(name);
			double startAngle = anglePair.getStartAngle();
			double endAngle = anglePair.getEndAngle();
			if(endAngle > 360){
				endAngle = 360;
			}
			Slice3D slice = null;
			if(!chart.isDoughNut()){
				slice = getPie(startAngle, endAngle);
			}
			else{
				slice = getDoughNut(startAngle, endAngle);
			}
			String color = colorMap.get(name);
			slice.fillColor(color);
//			slice.setStrokeColor(color);
//			slice.setStrokeWidth(2);
			slice.setName(name);
			slice.setColor(color);
			slice.setValue(dataTable.get(name));
			slice.setTotal(dataTable.getTotal());
			slice.setTitle();
			slice.setStartAngle(startAngle);
			slice.setEndAngle(endAngle);
//			SliceHandler handler = new SliceHandler();
//			handler.setSlice(slice);
//			slice.addClickHandler(handler);
			slice3DMap.put(name, slice);
		}
		if(chart.is_3d()){
			align3D();			
		}
		if(chart.isShowLabels()){
			setLabels();			
		}
		return plotSet;
	}
	
	
	private void align3D(){
		List<Slice3D> list = new ArrayList<Slice3D>(slice3DMap.values());
		Collections.sort(list, new Comparator<Slice3D>() {

			@Override
			public int compare(Slice3D o1, Slice3D o2) {
				if(o1.getStartAngle() < o2.getStartAngle()){
					return -1;
				}
				else if(o1.getStartAngle() == o2.getStartAngle()){
					return 0;
				}
				else if(o1.getStartAngle() > o2.getStartAngle()){
					return 1;
				}
				return 0;
			}
		});
		for(Slice3D slice : list){
			Path in = slice.getInnerArcPath();
			if(in != null){
				in.toBack();
			}
		}
		for(Slice3D slice : list){
			if(slice.getStartAngle() >= 90){
				Path p = slice.getLHSViewPath();
				if(p != null){
					p.toBack();
				}
				if(slice.getStartAngle() <=180 && slice.getEndAngle() >=180){
					if(slice.getInnerArcPath() != null){
						slice.getInnerArcPath().toBack();
					}
				}
			}
			if(slice.getEndAngle() >= 270){
				break;
			}
		}
		Collections.reverse(list);
		for(Slice3D slice : list){
			Path rhs = slice.getRHSViewPath();
			if(slice.getEndAngle() <= 90){
				if(rhs != null){
					rhs.toBack();//slice.getRHSViewPath().toBack();
				}
			}
		}
		for(Slice3D slice : list){
			Path rhs = slice.getRHSViewPath();
			Path innerArc = slice.getInnerArcPath();
			if(slice.getEndAngle() >= 270){
				if(innerArc != null){
					innerArc.toBack();
				}
				if(rhs != null){
					rhs.toBack();
				}
			}
			//Added Line of Code 3 Dec 2011 to avoid misbehave of front slice of pie 3d
			if(slice.getLHSViewPath() == null && slice.getRHSViewPath() == null){
				slice.getLowerArcPath().toFront();
			}
		}
		//list.get(list.size() - 1).getRHSViewPath().toBack();
	}
	
	public void setLabels(){
		dataTable = chart.getDataTable();
		java.util.Set<String> set = slice3DMap.keySet();
		double connectorLength = xRadius/8;
		for(String name : set){
			Slice3D slice = slice3DMap.get(name);
			String text = name;
			text = text + " "+dataTable.get(name);
			Circle c = slice.getPopupPoint();
			double x = c.attrAsDouble("cx");
			double y = c.attrAsDouble("cy");
			double angle = (slice.getStartAngle() + slice.getEndAngle())/2;
			Point endPoint = getEllipsePoint(center.getX(), center.getY(),
					xRadius+connectorLength, yRadius+connectorLength, angle);//x + connectorLength * Math.sin(rad);
			double ex = endPoint.getX();
			double ey = endPoint.getY();//y + connectorLength * Math.cos(rad);
			PathBuilder pb = new PathBuilder();
			pb.M(x, y);
			pb.L(ex-10, ey);
			pb.Z();
			Path connector = chart.new Path(pb);
			Point txtPoint = getEllipsePoint(center.getX(), center.getY(), 
					xRadius+connectorLength+10, yRadius+connectorLength+10, angle);
			Text txt = chart.new Text(txtPoint.getX(), txtPoint.getY(), text);
			if(angle >= 90 && angle <= 270){
				txt.attr("text-anchor", "end");
			}
			else{
				txt.attr("text-anchor", "start");				
			}
			slice.setLabel(connector, txt);
			plotSet.push(connector);
			plotSet.push(txt);
		}
	}
	
	public void showPopup(){
		
	}


	/**
	 * @return the slice3DMap
	 */
	public Map<String, Slice3D> getSlice3DMap() {
		return slice3DMap;
	}


	/**
	 * @param slice3dMap the slice3DMap to set
	 */
	public void setSlice3DMap(Map<String, Slice3D> slice3dMap) {
		slice3DMap = slice3dMap;
	}
	
}
