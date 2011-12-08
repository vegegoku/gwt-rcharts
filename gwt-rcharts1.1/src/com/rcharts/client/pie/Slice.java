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
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.hydro4ge.raphaelgwt.client.Raphael.Circle;

public interface Slice {

	public void addMouseOverHandler(MouseOverHandler handler);

	public void addClickHandler(ClickHandler handler);

	public void setSliceOutPoint(Circle sliceOutPoint);

	public Circle getSliceOutPoint();

	public void setTextOnSlicePoint(Circle textOnSlicePoint);

	public Circle getTextOnSlicePoint();

	public void setPopupPoint(Circle popupPoint);

	public Circle getPopupPoint();

	public void setValue(double value);

	public double getValue();

	public void setName(String name);

	public String getName();

	public void setColor(String color);

	public String getColor();

	public void fillColor(String color);

	public void setTotal(double total);
	
	public double getTotal();
	
	public void setStartAngle(double startAngle);
	
	public double getStartAngle();
	
	public void setEndAngle(double endAngle);
	
	public double getEndAngle();
	
	public Circle getCenter();
	
	public void setCenter(Circle center);
	
	public RaphaelObject get();
}
