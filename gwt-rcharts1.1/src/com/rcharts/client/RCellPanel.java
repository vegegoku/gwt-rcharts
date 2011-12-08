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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hydro4ge.raphaelgwt.client.BBox;
import com.hydro4ge.raphaelgwt.client.Raphael;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.hydro4ge.raphaelgwt.client.Raphael.Set;
import com.hydro4ge.raphaelgwt.client.Raphael.Shape;
import com.rcharts.client.category.bar.BarChart;

/**
 * 
 * A base class for layout managers like RHorizontalPanle, RVerticalPanel etc
 *
 */
public abstract class RCellPanel {

	public enum Movement{
		UPWORD, DOWNWORD, RIGHT, LEFT
	}
	
	private double spacing = 10;
	protected Chart raphael;
	protected Set set;
	protected double x = 0;
	protected double y = 0;
	private double startX = 0;
	private double startY = 0;
	//TODO : remove list as no longer needed after support for pushing a 
	//set inside set
	protected List<Shape> list;
	protected Movement movement;
	
	
	public RCellPanel(){
		try{
			raphael = RaphaelFactory.get();
			set = raphael.new Set();
			list = new ArrayList<Raphael.Shape>();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public RCellPanel(double spacing){
		this();
		this.spacing = spacing;
	}
	
	public RCellPanel(double x, double y, double spacing){
		this();
		this.x = x;
		this.y = y;
		startX = x;
		startY = y;
		this.spacing = spacing;
	}
	
	//getters and setters
	/**
	 * returns the Raphael.Set of element arranged in a particular order( i.e panel of shape)
	 */
	public Set get(){
		return set;
	}

	public double getX() {
		return x;
	}


	public double getY() {
		return y;
	}


	public List<Shape> getList() {
		return list;
	}

	
	public double getSpacing(){
		return spacing;
	}
	
	public void setSpacing(double spacing){
		this.spacing = spacing;
	}
	
	
	
	public Movement getMovement() {
		return movement;
	}

	public void setMovement(Movement movement) {
		this.movement = movement;
	}

	/**
	 * update the x or y coordinate for RHorizontalPanel or RVerticalPanel respectively
	 */
	public abstract void updateXY(RaphaelObject element);
	
	public abstract void updateSpacing(double spacing);
	
	public abstract void add(RaphaelObject element, Align align);
	
	
	/**
	 * Add a RVerticalPanel to RHorizontalPanel or vice-versa
	 */
	public void add(RCellPanel element){
		List<Shape> rList = element.getList();
		Iterator<Shape> it = rList.iterator();
		Shape el = null;
		Set s = element.get();
		//TODO : remove the commented code below.
		//REF : StackedBarChart family problem of being translated double of given parameters
		//The code was written when raphael_gwt was not supporting 
		//to push a Set inside a Set. From revision(RCharts_GWT1_4) 
		//this feature is supported. The code is preserved commented 
		//before the whole API is tested.
/*		Set s = raphael.new Set();
		while(it.hasNext()){
			el = it.next();
			set.push(el);
			s.push(el);
			list.add(el);
		}
*/		add(s);
	}


	public void add(RCellPanel element, Align align){
		List<Shape> rList = element.getList();
		Iterator<Shape> it = rList.iterator();
		Shape el = null;
		Set s = element.get();
		//TODO : remove the commented code below.
		//REF : StackedBarChart family problem of being translated double of given parameters
		//The code was written when raphael_gwt was not supporting 
		//to push a Set inside a Set. From revision(RCharts_GWT1_4) 
		//this feature is supported. The code is preserved commented 
		//before the whole API is tested.
/*		Set s = raphael.new Set();
		while(it.hasNext()){
			el = it.next();
			set.push(el);
			s.push(el);
			list.add(el);
		}
*/		
		add(s, align);
	}

	
	public void add(RaphaelObject element){
		// For VerticalPanel if movement is upward the current y value has to 
		// calculate in the reference of current element i.e currentY - element.getBBox().height();
		if(movement == Movement.UPWORD){
			updateXY(element);
		}
		Align align = Align.CENTER;
		BBox bbox = element.getBBox();
		double dx = x - bbox.x();
		double dy = y - bbox.y();
		if(align == Align.CENTER){
			
		}
		element.translate(dx, dy);
		
		if(element instanceof Shape){
			set.push((Shape)element);			
			list.add((Shape)element);
		}
		else{
			set.push((Set)element);				
		}
		// For VerticalPanel if movement is downward the next y value has to 
		// calculate in the reference of current element i.e currentY + element.getBBox().height();
		if(movement != Movement.UPWORD){
			updateXY(element);			
		}
	}

	
	/**
	 * A method to be called in implementing sub- class to add shapes to list
	 */
	
	protected void update(Shape element){
		list.add(element);
	}
	
	/*
	 * Make the state of object as a newly created one, removes all the elements from it
	 * if previous element's set is needed get() method must be called and set should be stored
	 * somewhere to avoid the loss of elements.
	 */
	public void refresh(){
		x = startX;
		y = startY;
		list.clear();
		set = null;
		set = raphael.new Set();
	}
		
	public void flush(){
		refresh();
	}
	
	public void clear(){
		refresh();
	}
}
