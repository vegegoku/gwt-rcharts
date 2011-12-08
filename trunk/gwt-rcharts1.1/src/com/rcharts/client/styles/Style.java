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
package com.rcharts.client.styles;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public class Style extends JSONObject{

	protected JSONObject style;
	
	public Style(){
		super();
		style = this;
	}

	public void setFillColor(String fillColor) {
		style.put(Keys.FILL, new JSONString(fillColor));
	}

	public void setStrokeColor(String storkeColor) {
		style.put(Keys.STROKE, new JSONString(storkeColor));
	}

	public void setStrokeWidth(double strokeWidth){
		style.put(Keys.STROKE_WIDTH, new JSONNumber(strokeWidth));
	}
	
	public void setStrokeOpacity(double strokeOpacity){
		style.put(Keys.STROKE_OPACITY, new JSONNumber(strokeOpacity));
	}
	
	public void setStrokeDashArray(String dashSymbol) {
		style.put(Keys.STROKE_DASHARRAY, new JSONString(dashSymbol));
	}

	public void setHref(String url) {
		style.put(Keys.HREF, new JSONString(url));
	}

	public void setToolTip(String toolTip) {
		style.put(Keys.TITLE, new JSONString(toolTip));
	}

	public void setFillOpacity(double fillOpacity) {
		style.put(Keys.FILL_OPACITY, new JSONNumber(fillOpacity));
	}

	public void setRotation(double angle){
		style.put(Keys.ROTATION, new JSONNumber(angle));
	}
	
	public JSONObject getStyle() {
		return style;
	}
	
}
