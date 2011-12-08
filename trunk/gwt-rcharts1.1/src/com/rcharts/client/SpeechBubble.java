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

import com.hydro4ge.raphaelgwt.client.BBox;
import com.hydro4ge.raphaelgwt.client.PathBuilder;
import com.hydro4ge.raphaelgwt.client.Raphael;
import com.hydro4ge.raphaelgwt.client.Raphael.Path;
import com.hydro4ge.raphaelgwt.client.Raphael.Rect;
import com.hydro4ge.raphaelgwt.client.Raphael.Shape;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.hydro4ge.raphaelgwt.client.Raphael.Circle;
import com.hydro4ge.raphaelgwt.client.Raphael.Set;
import com.rcharts.client.ChartStyles.Theme;
import com.rcharts.client.styles.Style;
import com.rcharts.client.styles.TextStyle;

public class SpeechBubble {

	
	protected static Chart chart;
	private int stick = 20;
	protected RaphaelObject raphaelSpeechText ;
	private Position pos = Position.NORTH;
	protected Point anchor = new Point(0, 0);
	private RaphaelObject raphaelAnchor;
	protected Set set;
	private static int startPointRadius = 3;
	protected Rect speechBox;
	public static int xPadding = 5;
	public static int yPadding = 5;
	private static SpeechBubble bubble;
	private String speechText;
	private TextStyle speechTextStyle = new TextStyle();
	private Style speechRectStyle = new Style();
	protected String strokeColor = "#000";
	protected Theme theme;
	
	public SpeechBubble(){
		try{
			chart = RaphaelFactory.get();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		set = chart.new Set();
		speechBox = chart.new Rect(0, 0, 10, 10, 10);
		theme = chart.getTheme();
	}
	
	public SpeechBubble(String speechText, Point anchor){
		this();
		this.speechText = speechText;
		this.anchor = anchor;
	}
	
	public SpeechBubble(String speechText, Point anchor, Position pos){
		this(speechText, anchor);
		this.pos = pos;
	}

	
	public int getStick() {
		return stick;
	}

	public void setStick(int stick) {
		this.stick = stick;
	}

	public String getSpeechText() {
		return speechText;
	}

	public void setSpeechText(String speechText) {
		this.speechText = speechText;
		initRaphaelSpeechText();
	}

	public Position getPos() {
		return pos;
	}

	public Point getAnchor() {
		return anchor;
	}

	public void setAnchor(Point anchor) {
		this.anchor = anchor;
	}
	
	public void setAnchor(RaphaelObject anchor){
		raphaelAnchor = anchor;
		setAnchor(new Point(anchor.getBBox().x(), anchor.getBBox().y()));
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	public void setPos(double angle){
		if(angle > 45 && angle < 135){
			pos = Position.NORTH;
		}
		else if(angle > 135 && angle < 225){
			pos = Position.WEST;
		}
		else if(angle > 225 && angle < 315){
			pos = Position.SOUTH;
		}
		else if(angle > 315 || angle < 45){
			pos = Position.EAST;
		}
	}
	
	/**
	 * @return the speechTextStyle
	 */
	public TextStyle getSpeechTextStyle() {
		return speechTextStyle;
	}

	/**
	 * @param speechTextStyle the speechTextStyle to set
	 */
	public void setSpeechTextStyle(TextStyle speechTextStyle) {
		this.speechTextStyle = speechTextStyle;
	}

	/**
	 * @return the speechRectStyle
	 */
	public Style getSpeechRectStyle() {
		return speechRectStyle;
	}

	/**
	 * @param speechRectStyle the speechRectStyle to set
	 */
	public void setSpeechRectStyle(Style speechRectStyle) {
		this.speechRectStyle = speechRectStyle;
	}

	/**
	 * @return the strokeColor
	 */
	public String getStrokeColor() {
		return strokeColor;
	}

	/**
	 * @param strokeColor the strokeColor to set
	 */
	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}

	protected void initRaphaelSpeechText(){
		if(speechText == null){
			throw new NullPointerException(" speechText is null");
		}
		else{
			raphaelSpeechText = chart.new Text(0, 0, speechText);			
			raphaelSpeechText.attr(speechTextStyle);
		}
	}
	
	public void drawBubble(){
		RaphaelObject speechSet = getSpeechSet();
		BBox sbox = speechSet.getBBox();
		Point tx = getTranslationPoint(sbox);
		speechSet.translate(tx.getX(), tx.getY());
		PathBuilder pb = new PathBuilder();
		pb.M(anchor.getX(), anchor.getY());
		if(pos == Position.EAST || pos == Position.WEST){
			pb.L(anchor.getX()+stick, anchor.getY());
		}
		else{
			pb.L(anchor.getX(), anchor.getY() + stick);			
		}
		pb.Z();
		Path path = chart.new Path(pb);
		set.push(path);
		Circle startPoint = chart.new Circle(anchor.getX(), anchor.getY(), startPointRadius);
		startPoint.attr("fill", "#ffffff");
		set.push(startPoint);
		if(chart.getTheme() == Theme.HIGHCHARTS){
			path.hide();
			startPoint.hide();
		}
	}
	
	/*
	 * Make the speechbox i.e a rect enclosing the speechtext  
	 */
	protected RaphaelObject getSpeechSet(){
		if(raphaelSpeechText == null){
			initRaphaelSpeechText();
		}
		BBox bbox = raphaelSpeechText.getBBox();
		//make dimensions for enclosing rect i.e bubble
		double rx = bbox.x() - xPadding;
		double ry = bbox.y() - yPadding;
		double rw = bbox.width() + (2*xPadding);
		double rh = bbox.height() + (2*yPadding);
		speechBox.attr("x", rx);
		speechBox.attr("y", ry);
		speechBox.attr("width", rw);
		speechBox.attr("height", rh);
//		speechBox.attr("opacity", 1);
//		speechBox.attr("fill", "#ffffff");
		speechBox.attr(chart.getSpeechRectStyle());
		speechBox.attr("stroke", strokeColor);
		raphaelSpeechText.attr("opacity", 1);
		//speechText.attr("text-anchor", "start");
		Set speechSet = chart.new Set();
		speechSet.push(speechBox);
		set.push(speechBox);
		if(raphaelSpeechText instanceof Shape){
			speechSet.push((Shape)raphaelSpeechText);
			set.push((Shape)raphaelSpeechText);
		}
		else{
			//TODO : make code for handling speechText if it is Set
		}
		return speechSet;
	}
	
	
	
	/*
	 * Gives the (dx,dy) the translation distance where the speechset( box and text)
	 * needs to be translated appropriately according to startPoint and stick
	 */
	protected Point getTranslationPoint(BBox sbox){
		double sx = 0;
		double sy = 0;
		if(pos == Position.NORTH  || pos == Position.SOUTH){
			sx = anchor.getX() - sbox.width()/2;			
			if(pos == Position.NORTH){
				stick = -stick;
				sy = anchor.getY() - sbox.height();
				
			}
			else if(pos == Position.SOUTH){
				sy = anchor.getY();
			}
			sy = sy + stick;
		}
		else{
			sy = anchor.getY() - sbox.height()/2;
			if(pos == Position.WEST){
				stick = -stick;
				sx = anchor.getX() - sbox.width();			
			}
			else if(pos == Position.EAST){
				sx = anchor.getX();
			}
			sx = sx + stick;
		}
		double dx = sx - sbox.x();
		double dy = sy - sbox.y();

		return new Point(dx, dy);
	}
	
	public RaphaelObject get(){
		return set;
	}
	
	
	public void show(){
		set.toFront();
		set.attr("opacity", 1);		
	}
	
	public void hide(){
		set.toBack();
		set.attr("opacity", 0);
	}
}
