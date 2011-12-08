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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.Window;
import com.hydro4ge.raphaelgwt.client.BBox;
import com.hydro4ge.raphaelgwt.client.Raphael;
import com.hydro4ge.raphaelgwt.client.Raphael.Rect;
import com.hydro4ge.raphaelgwt.client.Raphael.Shape;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;


public class RDockPanel {

	private Raphael raphael;
	private Box box;
	private List<Content> list = new ArrayList<RDockPanel.Content>();
	public static int SPACING = 10;
	private Point centerElementScale;
	private Point centerElementScaleCenter;
	private boolean addCalled = false;
	private Set<RaphaelObject> panelRaphaels = new HashSet<RaphaelObject>();
	/**
	 * Takes the current rect drawn and hide by RDockPanel.With the addition of each
	 * new element to RDockPanel its makes an rect around it.
	 * (*rect : SVG rectangle by raphaeljs
	 */
	private Shape currentDockRect;
	
	public RDockPanel() {
		try {
			init();
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}

	public RDockPanel(int width, int height){
		init(width, height);
	}
	
	public void init() throws Exception{
		init(RaphaelFactory.getWidth(),RaphaelFactory.getHeight());
	}
	
	public void init(int width, int height){
		try {
			raphael = RaphaelFactory.getRaphael();
		} catch (Exception e) {
			e.printStackTrace();
		}
		box = new Box(width,height);		
	}

	
	/*
	 * A helper class that store elements and there position 
	 */
	private class Content{
		RaphaelObject object;
		Position position;
		public Content(RaphaelObject object, Position position) {
			super();
			this.object = object;
			this.position = position;
		}
		
		
	}
	
	/*
	 * TODO: make a saperate class rather than the inner class
	 * Box is helper class for RDockPanel that keep track available space after every
	 * element is added to it
	 * NORTH: increment y by element e : e.y + e.height and decrement height by the same
	 * SOUTH: decrement the height by the height of element + PADDING
	 * WEST : increment x by element e : e.x + e.width and decrement width by the same 
	 * EAST : decrement the width by the width of element + PADDING
	 */
	private class Box{
		int x = 0;
		int y = 0;
		double height;
		double width;
		

		public Box(double width, double height) {
			super();
			this.height = height;
			this.width = width;
			//TODO : remove next line of code (only for testing purpose)
			Rect r = raphael.new Rect(x,y,width,height);
			r.hide();			
			panelRaphaels.add(r);
			currentDockRect = r;
		}
		
		/*
		 * Called every time a element is added to a RDockPanel
		 */
		
		public Point getCenter(){
			double cx = x + width/2;
			double cy = y + height/2;
			return new Point(cx, cy);
		}
		
		public double getRadius(){
			double r = 0;
			if(width < height){
				r =width;
			}
			else{
				r = height;
			}
			return (r-SPACING)/2;
		}
		
		public void update(Content content){
			if(list.indexOf(content) == -1){
				list.add(content);
			}
			update();
		}
		
		public void update(){
			// get the last element from list
			Content current = list.get(list.size()-1);
			Position pos = current.position;
			RaphaelObject element = current.object;
			switch(pos){
			case NORTH:
				updateNorth(element);
				break;
			case SOUTH:
				updateSouth(element);
				break;
			case EAST:
				updateEast(element);
				break;
			case WEST:
				updateWest(element);
			}
		}

		private void updateWest(RaphaelObject element) {
			BBox bbox = element.getBBox();
			double x = bbox.x() + bbox.width();
			this.x = (int) Math.ceil(x);
			this.width = this.width - bbox.width() -SPACING;
		}

		private void updateEast(RaphaelObject element) {
			double offset = element.getBBox().width() + SPACING;
			width = width - offset;
		}

		private void updateSouth(RaphaelObject element) {
			double offset = element.getBBox().height() + SPACING;
			height = height - offset;
		}

		private void updateNorth(RaphaelObject element) {
				BBox bbox = element.getBBox();
				double y = bbox.y() + bbox.height();
				this.y = (int) Math.ceil(y);
				this.height = this.height - bbox.height() - SPACING;
		}

		@Override
		public String toString() {
			return "Box [x=" + x + ", y=" + y + ", height=" + height
					+ ", width=" + width + "]";
		}
		
		
		
	}
	
	
	public void add(RaphaelObject element, Position pos){
		//TODO : remove next line of code only for testing purpose
		Rect r = raphael.new Rect(box.x, box.y, box.width, box.height);
		r.hide();
		if(pos == Position.EAST || pos == Position.WEST){
			add(element, pos, Align.CENTER);
		}
		else{
			add(element,pos, Align.CENTER);
		}
		panelRaphaels.add(r);
		currentDockRect = r;
	}
	
	
	public void add(RaphaelObject element, Position pos, Align align){
		try{
			if(addCalled){
				throw new IllegalStateException("Call to method add must be the last call");
			}
		}
		catch(IllegalStateException e){
			e.printStackTrace();
		}
		switch(pos){
		case NORTH:
			addToNorth(element,align);
			break;
		case SOUTH:
			addToSouth(element, align);
			break;
		case EAST:
			addToEast(element, align);
			break;
		case WEST:
			addToWest(element, align);
			break;
		}
	}
	
	private double getAlignedX(RaphaelObject element, Align align){
		double x ;
		BBox bbox = element.getBBox();
		switch(align){
		case LEFT:
			x = box.x + SPACING;
			break;
		case RIGHT:
			x = box.x + box.width - SPACING - bbox.width();
			break;
			//center
		default:
			x = box.x + box.width/2 - bbox.width()/2;
		}
		return x;
	}
	
	private double getAlignedY(RaphaelObject element, Align align){
		double y ;
		BBox bbox = element.getBBox();
		switch(align){
		case TOP:
			y = box.y + SPACING;
			break;
		case BOTTOM:
			y = box.y + box.height - SPACING - bbox.height();
			break;
		default:
			y = box.y + box.height/2 - bbox.height()/2;
		}
		return y;
	}
	
	private void addToNorth(RaphaelObject element, Align align){
		BBox bbox = element.getBBox();
		double x = getAlignedX(element, align);
		double y = box.y + SPACING;		
		double dx = x - bbox.x();
		double dy = y - bbox.y();
		element.translate(dx, dy);
		box.update(new Content(element, Position.NORTH));

	}
	private void addToSouth(RaphaelObject element, Align align){
		BBox bbox = element.getBBox();
		double x = getAlignedX(element, align);
		double y = box.y + box.height - SPACING - bbox.height();
		double dx = x - bbox.x();
		double dy = y - bbox.y();
		element.translate(dx, dy);
		box.update(new Content(element, Position.SOUTH));
	}

	private void addToEast(RaphaelObject element, Align align){
		BBox bbox = element.getBBox();
		double x = box.x + box.width - SPACING - bbox.width();
		double y = getAlignedY(element, align);
		double dx = x - bbox.x();
		double dy = y - bbox.y();
		element.translate(dx, dy);
		box.update(new Content(element, Position.EAST));
	}
	
	private void addToWest(RaphaelObject element, Align align){
		BBox bbox = element.getBBox();
		double x = box.x + SPACING;
		double y = getAlignedY(element, align);
		double dx = x - bbox.x();
		double dy = y - bbox.y();
		element.translate(dx, dy);
		box.update(new Content(element, Position.WEST));
	}
	
	/*
	 * Adds element to the center of RDockPanel and optionally scales it to the rest of the area
	 * @param element : element to be added
	 * @param fitSize : true/false to adjust the dimension of element according to remaining space
	 */
	public void add(RaphaelObject element, boolean fitSize, Align align){
		addCalled = true;
		BBox bbox = element.getBBox();
		double dx = box.x - bbox.x();
		double dy = box.y - bbox.y();
		if(align == Align.CENTER){
			dx = (box.x + box.width/2) - (bbox.x() + bbox.width());
			dy = (box.y + box.height/2) - (bbox.y() + bbox.height());
		}
		element.translate(dx, dy);
	}
	
	public void add(RaphaelObject element){
		add(element, true, null);
	}

	/*
	 * Gives the center point of remaining Area in dock panel
	 */
	public Point getCenter(){
		return box.getCenter();
	}

	public double getRadius(){
		return box.getRadius();
	}

	public int getX(){
		return box.x;
	}
	
	public int getY(){
		return box.y;
	}
	/**
	 * Returns the remaining width of RDockPanel 
	 * @return
	 */
	public double getWidth() {

		return box.width;
	}

	/**
	 * returns the remaining height of RDockPanel
	 * @return
	 */
	public double getHeight(){
		return box.height;
	}
	/*
	 * Returns how much the element added to center has been scaled. if its not
	 * it return Point(1,1)
	 */
	public Point getCenterElementScale() {
		return centerElementScale;
	}

	/*
	 * Returns the point of reference in which the center element scaled
	 * if its none returns null 
	 */
	public void setCenterElementScale(Point centerElementScale) {
		this.centerElementScale = centerElementScale;
	}

	public Point getCenterElementScaleCenter() {
		return centerElementScaleCenter;
	}

	public void setCenterElementScaleCenter(Point centerElementScaleCenter) {
		this.centerElementScaleCenter = centerElementScaleCenter;
	}

	/**
	 * Returns a Raphael SVG rect enclosing the element currently added or if no element is added
	 * its just returns a Raphael SVG rect object with width and height of RDockPanel instance.
	 * Could be used to style the whole RDockPanel or a particular area in it.
	 * @return
	 */
	public Shape getCurrentDock(){
		return currentDockRect;
	}

	
	public void clear(){
		Iterator<Content> it = list.iterator();
		while(it.hasNext()){
			Content content = it.next();
			content.object.remove();
		}
		list.clear();
		for(RaphaelObject ro : panelRaphaels){
			ro.remove();
		}
	}

}
