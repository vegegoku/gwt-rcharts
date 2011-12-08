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
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.hydro4ge.raphaelgwt.client.Raphael.Circle;
import com.hydro4ge.raphaelgwt.client.Raphael.Path;
import com.hydro4ge.raphaelgwt.client.Raphael.Set;
import com.hydro4ge.raphaelgwt.client.Raphael.Text;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.Chart;

public abstract class Slice2D implements Slice {
	private Path slice;
	
	public Slice2D(){
		
	}

	public Slice2D(Path slice) {
		super();
		this.slice = slice;
	}

	/**
	 * @return the slice
	 */
	public Path getSlice() {
		return slice;
	}

	/**
	 * @param slice the slice to set
	 */
	public void setSlice(Path slice) {
		this.slice = slice;
	}
	
	public void addClickHandler(ClickHandler handler){
		slice.addClickHandler(handler);
	}
	
	public void addMouseOverHandler(MouseOverHandler handler){
		slice.addMouseOverHandler(handler);
	}
	
	public void addDOMHandler(Handler handler, Type<Handler> type){
		slice.addDomHandler(handler, type);
	}

	public Circle getSliceOutPoint() {
		return null;
	}


	public Circle getTextOnSlicePoint() {
		return null;
	}


	public Circle getPopupPoint() {
		return null;
	}

	public void setValue(double value) {
	}

	public double getValue() {
		return 0;
	}

	public void setName(String name) {
	}

	public String getName() {
		return null;
	}

	public void setColor(String color) {
	}

	public String getColor() {
		return null;
	}

	public void fillColor(String color) {
	}

	@Override
	public void setSliceOutPoint(Circle sliceOutPoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTextOnSlicePoint(Circle textOnSlicePoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPopupPoint(Circle popupPoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTotal(double total) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setStartAngle(double startAngle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getStartAngle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setEndAngle(double endAngle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getEndAngle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Circle getCenter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCenter(Circle center) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RaphaelObject get() {
		// TODO Auto-generated method stub
		return null;
	}
}
