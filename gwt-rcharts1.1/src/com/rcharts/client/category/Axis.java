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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.hydro4ge.raphaelgwt.client.BBox;
import com.hydro4ge.raphaelgwt.client.PathBuilder;
import com.hydro4ge.raphaelgwt.client.Raphael;
import com.hydro4ge.raphaelgwt.client.Raphael.Path;
import com.hydro4ge.raphaelgwt.client.Raphael.Rect;
import com.hydro4ge.raphaelgwt.client.Raphael.Shape;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.hydro4ge.raphaelgwt.client.Raphael.Set;
import com.hydro4ge.raphaelgwt.client.Raphael.Text;
import com.rcharts.client.Chart;
import com.rcharts.client.Point;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.ChartStyles.Theme;
import com.rcharts.client.category.line.LineChart;
import com.rcharts.client.combo.ComboChart;
import com.rcharts.client.styles.Style;
import com.rcharts.client.styles.TextStyle;

/**
 * Base class for the axis of all charts having axis including XYCharts
 */
public abstract class Axis {
	
		
  	protected Chart chart;
	protected List<String> tickLabels;
	protected Orientation orient;
	protected double axisLength;
	public static int tickMarkLength = 5;
	protected boolean isValueAxis = false;
	/**
	 * for later references of tick labels as Raphael object to manipulate svg property
	 */
	private List<RaphaelObject> raphaelTickLabels;
	/**
	 * for later references of tick marks as Raphael object to manipulate svg property
	 */
	protected List<RaphaelObject> raphaelTickMarks;
	protected List<Shape> axisList;
	protected Set tickLabelSet;
	protected Set tickMarkSet;
	private Set gridLinesSet;
	private double gridLineLength = 0;
	private boolean showGridLine = true;
	protected Set axisLine;
	protected Set set;
	//for line chart to have mousehover effect like highcharts
	protected Map<String, RaphaelObject> gridMap = new LinkedHashMap<String, RaphaelObject>();
	/**
	 * @return the _3DAxisSet
	 */
	public Set get_3DAxisSet() {
		return _3DAxisSet;
	}



	/**
	 * @param _3dAxisSet the _3DAxisSet to set
	 */
	public void set_3DAxisSet(Set _3dAxisSet) {
		_3DAxisSet = _3dAxisSet;
	}

	/**
	 * For internal use of class
	 */
	protected List<Point> tickPoints;
	protected Point startPoint = new Point(0, 0);
	private boolean isInvert = false;
	protected boolean second = false;
	protected Set firstAxisSet;
	protected Set secondAxisSet;
	
	protected Set _3DAxisSet;
	
	/**
	 * To check if text in tick label collides
	 */
	protected boolean tickLabelCollision = false;
	
	public Axis(){
		try{
			chart = RaphaelFactory.get();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		raphaelTickLabels = new ArrayList<RaphaelObject>();
		raphaelTickMarks = new ArrayList<RaphaelObject>();
		tickPoints = new ArrayList<Point>();
		tickLabelSet = chart.new Set();
		tickMarkSet = chart.new Set();
		gridLinesSet = chart.new Set();
		axisLine = chart.new Set();
		set = chart.new Set();
		axisList = new ArrayList<Shape>();
		firstAxisSet = chart.new Set();
		secondAxisSet = chart.new Set();
		_3DAxisSet = chart.new Set();
		chart.set_3DAxesSet(_3DAxisSet);
	}

	
	
	public Axis(List<String> tickLabels, Orientation orient, double axisLength) {
		this();
		this.tickLabels = tickLabels;
		this.orient = orient;
		this.axisLength = axisLength;
	}

	public double getAxisLength() {
		return axisLength;
	}

	public void setAxisLength(double axisLength) {
		this.axisLength = axisLength;
	}

	public List<String> getTickLabel() {
		return tickLabels;
	}

	public void setTickLabel(List<String> tickLabel) {
		this.tickLabels = tickLabel;
	}

	public Orientation getOrientation() {
		return orient;
	}

	public void setOrientation(Orientation oriect) {
		this.orient = oriect;
	}
	
	
	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public static int getTickMarkLength() {
		return tickMarkLength;
	}

	public static void setTickMarkLength(int tickMarkLength) {
		Axis.tickMarkLength = tickMarkLength;
	}
	
	

	/**
	 * @return the tickLabelSet
	 */
	public Set getTickLabelSet() {
		return tickLabelSet;
	}

	/**
	 * @return the tickMarkSet
	 */
	public Set getTickMarkSet() {
		return tickMarkSet;
	}

	/**
	 * @return the gridLinesSet
	 */
	public Set getGridLinesSet() {
		return gridLinesSet;
	}

	/**
	 * @return the gridLineLength
	 */
	public double getGridLineLength() {
		return gridLineLength;
	}

	/**
	 * @param gridLineLength the gridLineLength to set
	 */
	public void setGridLineLength(double gridLineLength) {
		this.gridLineLength = gridLineLength;
	}

	/**
	 * @return the showGridLine
	 */
	public boolean isShowGridLine() {
		return showGridLine;
	}

	/**
	 * @param showGridLine the showGridLine to set
	 */
	public void setShowGridLine(boolean showGridLine) {
		this.showGridLine = showGridLine;
	}

	public List<Point> getTickPoints(){
		return this.tickPoints;
	}
	
	public void showGridLine(){
		gridLinesSet.attr("opacity", 1);
	}
	
	public void hideGridLine(){
		gridLinesSet.attr("opacity", 0);
	}
	
	/**
	 * @return the isValueAxis
	 */
	public boolean isValueAxis() {
		return isValueAxis;
	}

	/**
	 * @param isValueAxis the isValueAxis to set
	 */
	public void setValueAxis(boolean isValueAxis) {
		this.isValueAxis = isValueAxis;
	}

	/**
	 * @return the axisLine
	 */
	public Set getAxisLine() {
		return axisLine;
	}

	public boolean isAxisInvert(){
		return isInvert;
	}
	
	public abstract void init();

	private void makeSpaceForLabel(){
		if(orient == Orientation.HORIZONTAL){
			updateTickPoints(tickLabelSet.getBBox().height());
		}
		else{
			updateTickPoints(tickLabelSet.getBBox().width());			
		}		
	}
	
	public void makeTickLabel() {
		TextStyle tickLabelStyle = 
			orient == Orientation.HORIZONTAL ? 
					RaphaelFactory.get().getHAxisTickLabelStyle() : RaphaelFactory.get().getVAxisTickLabelStyle();
		
		Iterator<Point> it = tickPoints.iterator();
		Iterator<String> labelIt = tickLabels.iterator();
		while(it.hasNext() && labelIt.hasNext()){
			Point p = it.next();
			Text tickLabel = chart.new Text(p.getX(), p.getY(), labelIt.next());
			tickLabel.attr(tickLabelStyle);

			if(tickLabelCollision){
				tickLabel.rotate(15);//, tickLabel.getBBox().x(), tickLabel.getBBox().y());
			}
			
			tickLabelSet.push(tickLabel);
			// if isInvert than we are just making space dont push the labels to set
			// which latter going to be erased tickLabelSet.remove()
			if(!isInvert){
				set.push(tickLabel);
				raphaelTickLabels.add(tickLabel);
				axisList.add(tickLabel);
				if(second){
					secondAxisSet.push(tickLabel);
				}
				else{
					firstAxisSet.push(tickLabel);
				}
			}
		}
		if(orient == Orientation.HORIZONTAL){
			updateTickPoints(tickLabelSet.getBBox().height());
		}
		else{
			updateTickPoints(tickLabelSet.getBBox().width());			
		}
		//SVG text have quite different property than other shapes so if here text
		// is the last candidate to be added we to make some space for it (otherwise it ll
		//look messy so now updateTickPoints have updated it we will remove labels and 
		//render again
		if(isInvert){
			tickLabelSet.remove();
			isInvert = false;
			makeTickLabel();
		}
	}

	public void checkCollision(){
		// get the largest string out of labels
		String max = "";
		for(String m : tickLabels){
			if(m.length() > max.length()){
				max = m;
			}
		}
		TextStyle tickLabelStyle = 
			orient == Orientation.HORIZONTAL ? 
					RaphaelFactory.get().getHAxisTickLabelStyle() : RaphaelFactory.get().getVAxisTickLabelStyle();
		double maxLabelWidth = axisLength/(tickLabels.size()+1);
		Text t = chart.new Text(0, 0, max);
		t.attr(tickLabelStyle);
		double maxWidth = t.getBBox().width();
		if(maxWidth > maxLabelWidth){
			tickLabelCollision = true;
		}
		t.remove();
	}
	
	public void makeTickMarks() {
		Iterator<Point> it = tickPoints.iterator();
		Style tickMarkStyle = new Style();
		if(orient == Orientation.HORIZONTAL){
			tickMarkStyle = RaphaelFactory.get().gethAxisTickMarkStyle();			
		}
		else{
			tickMarkStyle = RaphaelFactory.get().getvAxisTickMarkStyle();			
		}
		while(it.hasNext()){
			Point p = it.next();
			PathBuilder pb = new PathBuilder();
			pb.M(p.getX(), p.getY());
			if(orient == Orientation.HORIZONTAL){
				//do vertical line i.e increment y
				pb.l(0, tickMarkLength);
			}
			else{
				pb.l(tickMarkLength, 0);
			}
			Path tickPath = chart.new Path(pb);
			tickPath.attr(tickMarkStyle);
			tickMarkSet.push(tickPath);
			raphaelTickMarks.add(tickPath);
			axisList.add(tickPath);
			set.push(tickPath);
			if(second){
				secondAxisSet.push(tickPath);
			}
			else{
				firstAxisSet.push(tickPath);
			}
		}
		
		updateTickPoints(tickMarkLength);
	}

	public void makeAxisLine() {
		Style axisStyle = null;
		Point endPoint = tickPoints.get(0);
		PathBuilder pb = new PathBuilder();
		pb.M(startPoint.getX(), startPoint.getY());
		if(orient == Orientation.HORIZONTAL){
			pb.l(axisLength, 0);
			axisStyle = RaphaelFactory.get().getHAxisStyle();
		}
		else{
			pb.l(0, axisLength);
			axisStyle = RaphaelFactory.get().getVAxisStyle();
		}
		pb.z();
		Path axis = chart.new Path(pb);
		axis.attr(axisStyle);
		axisLine.push(axis);
		axisList.add(axis);
		set.push(axis);
		if(second){
			secondAxisSet.push(axis);
		}
		else{
			firstAxisSet.push(axis);
		}
	}

	/**
	 * Make grid lines over axis and hides them. Must be called 
	 * 1)for invert axis: before axis line drawn
	 * 2)for regular axis: after axis line drawn 
	 */
	public void makeGridLines(){
		Style  gridStyle = null;
/*		if(!isValueAxis() || gridLineLength == 0){
			return;
		}
*/		//Iterator over label for line charts
		Iterator<String> labelIt = tickLabels.iterator();
		double gridHoverWidth = (axisLength-20)/tickLabels.size();
		Style hoverStyle = new Style();
		if(!isValueAxis && (chart instanceof LineChart || chart instanceof ComboChart)
				&& chart.getTheme() == Theme.HIGHCHARTS){
			hoverStyle.put("stroke-width", new JSONNumber(gridHoverWidth));
			hoverStyle.put("stroke", new JSONString("#ffffff"));
			hoverStyle.put("opacity", new JSONNumber(0));			
		}
		
		Iterator<Point> it = tickPoints.iterator();
	
		while(it.hasNext()){
			Point p = it.next();
			PathBuilder pb = new PathBuilder();
			if(!isInvert){
				pb.M(p.getX(), p.getY());
			}
			if(orient == Orientation.HORIZONTAL){
				//do vertical line i.e increment y
				if(isInvert){
					pb.M(p.getX(), p.getY()-gridLineLength);
				}
				pb.l(0, gridLineLength);
				gridStyle = RaphaelFactory.get().getVGridLineStyle();
			}
			else{
				if(isInvert){
					pb.M(p.getX()-gridLineLength, p.getY());
				}
				pb.l(gridLineLength, 0);
				gridStyle = RaphaelFactory.get().getHGridLineStyle();
			}
			
			Path tickPath = chart.new Path(pb);
			tickPath.attr(gridStyle);
			gridLinesSet.push(tickPath);
			axisList.add(tickPath);
			set.push(tickPath);
			if(!isValueAxis && (chart instanceof LineChart || chart instanceof ComboChart)
					&& chart.getTheme() == Theme.HIGHCHARTS){
				if(labelIt.hasNext()){
					String cat = labelIt.next();
					Path hoverPath = chart.new Path(pb);
					hoverPath.attr(hoverStyle);
					hoverPath.toBack();
					gridMap.put(cat, hoverPath);
					set.push(hoverPath);
					axisList.add(hoverPath);
				}
			}
		}
		if(!showGridLine || !isValueAxis){
			gridLinesSet.attr("opacity", 0);
		}
		if(isValueAxis){
			gridLinesSet.attr("opacity", 1);			
		}
		if(!isInvert){
			updateTickPoints(gridLineLength);
		}
	}

	/**
	 * updates the start point and tickPoints x or y coordinate for make space for next set
	 * e.g : Vertical orientation
	 * tickLabels : (0,200) -> (0,0)   update startpoint.x+ = set.bbox.width;(x=5)
	 * tickMarks : (5, 200) -> (5, 0)  ------"-----x=10
	 * axisLine : (10, 200) -> (10, 0) -----"------
	 * @param dim
	 */
	protected void updateTickPoints(double rsetWidthOrHeight) {
		Iterator<Point> it = tickPoints.iterator();
		double x = startPoint.getX() + rsetWidthOrHeight;
		double y = startPoint.getY() + rsetWidthOrHeight;
		boolean startDone = false;
		while(it.hasNext()){
			Point p = it.next();
			if(orient == Orientation.VERTICAL){
				p.setX(x);
				if(!startDone){
					startPoint.setX(x);				
					startDone = true;
				}
			}
			else{
				p.setY(y);
				if(!startDone){
					startPoint.setY(y);				
					startDone = true;
				}
			}
		}			
	}
	
	/**
	 * Makes complete axis with labels -> ticks -> axisLine
	 * X axis : 3rd and 4th quadrant
	 * Y axis : 1st and 4th quadrant
	 */
	public void makeAxis(){
		checkCollision();
		makeTickLabel();
		makeTickMarks();
		makeAxisLine();
		makeGridLines();
		second = true;
		makeAxisLine();
		makeTickMarks();
		makeSpaceForLabel();
		makeTickLabel();
	}
	

	/**
	 * Make a single axis 
	 * for Horizontal on TOP i.e North position
	 * for Vertical on LEFT i.e West position
	 * Ideal case for Vertical i.e Y axis
	 * @param showGrids
	 */
	public void makeSingleAxis(boolean showGrids){
		checkCollision();
		makeTickLabel();
		makeTickMarks();
		makeAxisLine();
		if(showGrids){
			makeGridLines();			
		}
	}
	
	
	/**
	 * Make a single axis 
	 * for Horizontal on Bottom i.e South position
	 * for Vertical on Right i.e East position
	 * Ideal case for Horizontal i.e x axis
	 * @param showGrids
	 */
	public void makeSingleInvertAxis(boolean showGrids){
		checkCollision();
		makeTickLabel();
		second = true;
		makeAxisLine();
		if(showGrids){
			makeGridLines();
		}
		makeTickMarks();
		makeSpaceForLabel();
		tickLabelSet.remove();
		makeTickLabel();		
	}
	
	/**
	 * Makes complete axis with axis line first :- axisLine -> ticks -> labels
	 * X axis : 1st and 2nd quadrant
	 * Y axis : 2nd and 3rd quadrant
	 */
	public void makeInvertAxis(){
		second = true;
		makeTickLabel();
		makeTickMarks();
		makeAxisLine();
		makeGridLines();
		second = false;
		makeAxisLine();
		makeTickMarks();
		makeSpaceForLabel();
		makeTickLabel();
	}
	
	
	public RaphaelObject getFirstAxis(){
		return firstAxisSet;
	}
	
	public RaphaelObject getSecondAxis(){
		return secondAxisSet;
	}
	
	public RaphaelObject get(){
		return set;
	}
	
	public List<Shape> getAxisList(){
		return axisList;
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



	/**
	 * @return the tickLabels
	 */
	public List<String> getTickLabels() {
		return tickLabels;
	}



	/**
	 * @param tickLabels the tickLabels to set
	 */
	public void setTickLabels(List<String> tickLabels) {
		this.tickLabels = tickLabels;
	}



	/**
	 * @return the orient
	 */
	public Orientation getOrient() {
		return orient;
	}



	/**
	 * @param orient the orient to set
	 */
	public void setOrient(Orientation orient) {
		this.orient = orient;
	}



	/**
	 * @return the raphaelTickLabels
	 */
	public List<RaphaelObject> getRaphaelTickLabels() {
		return raphaelTickLabels;
	}



	/**
	 * @param raphaelTickLabels the raphaelTickLabels to set
	 */
	public void setRaphaelTickLabels(List<RaphaelObject> raphaelTickLabels) {
		this.raphaelTickLabels = raphaelTickLabels;
	}



	/**
	 * @return the raphaelTickMarks
	 */
	public List<RaphaelObject> getRaphaelTickMarks() {
		return raphaelTickMarks;
	}



	/**
	 * @param raphaelTickMarks the raphaelTickMarks to set
	 */
	public void setRaphaelTickMarks(List<RaphaelObject> raphaelTickMarks) {
		this.raphaelTickMarks = raphaelTickMarks;
	}



	/**
	 * @return the set
	 */
	public Set getSet() {
		return set;
	}



	/**
	 * @param set the set to set
	 */
	public void setSet(Set set) {
		this.set = set;
	}



	/**
	 * @return the isInvert
	 */
	public boolean isInvert() {
		return isInvert;
	}



	/**
	 * @param isInvert the isInvert to set
	 */
	public void setInvert(boolean isInvert) {
		this.isInvert = isInvert;
	}



	/**
	 * @return the second
	 */
	public boolean isSecond() {
		return second;
	}



	/**
	 * @param second the second to set
	 */
	public void setSecond(boolean second) {
		this.second = second;
	}



	/**
	 * @return the firstAxisSet
	 */
	public Set getFirstAxisSet() {
		return firstAxisSet;
	}



	/**
	 * @param firstAxisSet the firstAxisSet to set
	 */
	public void setFirstAxisSet(Set firstAxisSet) {
		this.firstAxisSet = firstAxisSet;
	}



	/**
	 * @return the secondAxisSet
	 */
	public Set getSecondAxisSet() {
		return secondAxisSet;
	}



	/**
	 * @param secondAxisSet the secondAxisSet to set
	 */
	public void setSecondAxisSet(Set secondAxisSet) {
		this.secondAxisSet = secondAxisSet;
	}



	/**
	 * @return the tickLabelCollision
	 */
	public boolean isTickLabelCollision() {
		return tickLabelCollision;
	}



	/**
	 * @param tickLabelCollision the tickLabelCollision to set
	 */
	public void setTickLabelCollision(boolean tickLabelCollision) {
		this.tickLabelCollision = tickLabelCollision;
	}



	/**
	 * @param axisList the axisList to set
	 */
	public void setAxisList(List<Shape> axisList) {
		this.axisList = axisList;
	}



	/**
	 * @param tickLabelSet the tickLabelSet to set
	 */
	public void setTickLabelSet(Set tickLabelSet) {
		this.tickLabelSet = tickLabelSet;
	}



	/**
	 * @param tickMarkSet the tickMarkSet to set
	 */
	public void setTickMarkSet(Set tickMarkSet) {
		this.tickMarkSet = tickMarkSet;
	}



	/**
	 * @param gridLinesSet the gridLinesSet to set
	 */
	public void setGridLinesSet(Set gridLinesSet) {
		this.gridLinesSet = gridLinesSet;
	}



	/**
	 * @param axisLine the axisLine to set
	 */
	public void setAxisLine(Set axisLine) {
		this.axisLine = axisLine;
	}



	/**
	 * @param tickPoints the tickPoints to set
	 */
	public void setTickPoints(List<Point> tickPoints) {
		this.tickPoints = tickPoints;
	}
	
	/**
	 * gives the Hidden gridline map in case of LineChart to set hover handlers 
	 */
	public Map<String, RaphaelObject> getGridHoverMap(){
		return gridMap;
	}
}
