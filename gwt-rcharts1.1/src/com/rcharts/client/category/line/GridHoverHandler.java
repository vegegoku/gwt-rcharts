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
package com.rcharts.client.category.line;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.Position;
import com.rcharts.client.category.CategoryHoverHandler;

public class GridHoverHandler extends CategoryHoverHandler {
	public static String CURRENT_SERIES;

	public GridHoverHandler(){
		
	}
	
	public GridHoverHandler(String categoryName, String seriesName,
			double value, RaphaelObject legend, RaphaelObject popupPoint,
			Position popupPosition) {
		super(categoryName, seriesName, value, legend, popupPoint, popupPosition);
	}

	/* (non-Javadoc)
	 * @see com.google.chart_gwt.client.category.CategoryHoverHandler#init(java.lang.String, java.lang.String, double, com.hydro4ge.raphaelgwt.client.RaphaelObject, com.hydro4ge.raphaelgwt.client.RaphaelObject, com.google.chart_gwt.client.Position)
	 */
	@Override
	public void init(String categoryName, String seriesName, double value,
			RaphaelObject legend, RaphaelObject popupPoint,
			Position popupPosition) {
		super.init(categoryName, seriesName, value, legend, popupPoint, popupPosition);
	}

	@Override
	public void onMouseOver(MouseOverEvent event){
		if(getSeriesName().equals(CURRENT_SERIES)){
			super.onMouseOver(event);
		}
		else{
			return;
		}
	}
	
	@Override 
	public void onMouseOut(MouseOutEvent event){
		super.onMouseOut(event);
	}

}
