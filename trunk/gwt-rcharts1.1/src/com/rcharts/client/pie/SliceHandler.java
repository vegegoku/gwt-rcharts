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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.hydro4ge.raphaelgwt.client.BBox;
import com.hydro4ge.raphaelgwt.client.Raphael.Circle;
import com.hydro4ge.raphaelgwt.client.Raphael.Path;
import com.hydro4ge.raphaelgwt.client.Raphael.Set;
import com.hydro4ge.raphaelgwt.client.Raphael.Text;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.Chart;
import com.rcharts.client.Point;
import com.rcharts.client.Position;
import com.rcharts.client.RaphaelFactory;
import com.rcharts.client.SpeechBubble;

public class SliceHandler implements MouseOutHandler, MouseOverHandler,
		ClickHandler {

	private Slice slice;
	private boolean popup;
	private boolean sliceOut;
	private boolean showSpeechBubble;
	private boolean showPercentage;
	private SpeechBubble speechBubble;
	private RaphaelObject legend;
	private boolean isSlicedOut;
	public static double SCALE_UP = 1.5;
	private Chart chart;
	private Point center;
	
	public SliceHandler(){
		chart = RaphaelFactory.get();

	}
	
	/**
	 * @return the slice
	 */
	public Slice getSlice() {
		return slice;
	}

	/**
	 * @param slice the slice to set
	 */
	public void setSlice(Slice slice) {
		this.slice = slice;
	}

	/**
	 * @return the showDataPopup
	 */
	public boolean isPopup() {
		return popup;
	}

	/**
	 * @return the sliceOut
	 */
	public boolean isSliceOut() {
		return sliceOut;
	}

	/**
	 * @param showDataPopup the showDataPopup to set
	 */
	public void setPopup(boolean showDataPopup) {
		this.popup = showDataPopup;
	}

	/**
	 * @param sliceOut the sliceOut to set
	 */
	public void setSliceOut(boolean sliceOut) {
		this.sliceOut = sliceOut;
	}

	/**
	 * @return the showDataOnPopup
	 */
	public boolean isShowSpeechBubble() {
		return showSpeechBubble;
	}

	/**
	 * @param showDataOnPopup the showDataOnPopup to set
	 */
	public void setShowSpeechBubble(boolean showDataOnPopup) {
		this.showSpeechBubble = showDataOnPopup;
	}

	/**
	 * @return the showPercentage
	 */
	public boolean isShowPercentage() {
		return showPercentage;
	}

	/**
	 * @param showPercentage the showPercentage to set
	 */
	public void setShowPercentage(boolean showPercentage) {
		this.showPercentage = showPercentage;
	}

	/**
	 * @return the speechBubble
	 */
	public SpeechBubble getSpeechBubble() {
		return speechBubble;
	}

	/**
	 * @param speechBubble the speechBubble to set
	 */
	public void setSpeechBubble(SpeechBubble speechBubble) {
		this.speechBubble = speechBubble;
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
	 * @return the legend
	 */
	public RaphaelObject getLegend() {
		return legend;
	}

	/**
	 * @param legend the legend to set
	 */
	public void setLegend(RaphaelObject legend) {
		this.legend = legend;
	}

	private String getSpeechText(){
		String text = slice.getName();
		double value = slice.getValue();
		if(showPercentage){
			value = (value/slice.getTotal()) * 100;
			text = text + "\n" + value + " %";
		}
		else{
			text = text + "\n" + value;
		}
		return text;
	}
	
	private Position getPosition(){
		double midAngle = slice.getStartAngle() + (slice.getEndAngle() - slice.getStartAngle())/2;
		if((midAngle >= 0 && midAngle <=45) || (midAngle >= 315 && midAngle <= 360)){
			return Position.EAST;
		}
		else if(midAngle > 45 && midAngle <= 135){
			return Position.SOUTH;
		}
		else if(midAngle > 135 && midAngle <= 225){
			return Position.WEST;
		}
		else if(midAngle > 225 && midAngle <= 315){
			return Position.NORTH;
		}
		else{
			return null;
		}
	}
	
	private void init(){
		BBox cBox = slice.getCenter().getBBox();
		center = new Point(cBox.x(), cBox.y());
		if(showSpeechBubble){
			RaphaelObject popupPoint = slice.getPopupPoint();
			String text = getSpeechText();
//			Text txt = chart.new Text(0, 0, text);
			speechBubble = new SpeechBubble();//txt, null, getPosition());
			speechBubble.setSpeechText(text);
			//speechBubble.setPos(slice.getStartAngle() + (slice.getEndAngle()- slice.getStartAngle())/2);
			speechBubble.setPos(getPosition());
			speechBubble.setAnchor(popupPoint);
			speechBubble.setStrokeColor(legend.attrAsString("fill"));
			speechBubble.drawBubble();
			speechBubble.get().attr("opacity", 0);
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		if(center == null){
			init();
		}
		BBox cBox = slice.getSliceOutPoint().getBBox();
		BBox cenBox = slice.getCenter().getBBox();
		double dx = center.getX() - cBox.x();
		double dy = center.getY() - cBox.y();
		RaphaelObject s = slice.get();
		Slice3D slc = (Slice3D)slice;	
		slc.getTopViewPath().toFront();
		if(!isSlicedOut){
			dx = cBox.x() - center.getX();
			dy = cBox.y() - center.getY();
			isSlicedOut = true;
		}
		else{
			dx = center.getX() - cenBox.x();
			dy = center.getY() - cenBox.y();		
			isSlicedOut = false;
		}
		s.translate(dx, dy);
		if(showSpeechBubble){
			speechBubble.get().translate(dx, dy);
		}
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		legend.scale(SCALE_UP, SCALE_UP);
		if(showSpeechBubble){
			if(speechBubble == null){
				init();
			}
			speechBubble.get().attr("opacity", 1);
		}
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		legend.scale(1, 1);
		if(showSpeechBubble){
			if(speechBubble == null){
				init();
			}
			speechBubble.get().attr("opacity", 0);
		}
	}

}
