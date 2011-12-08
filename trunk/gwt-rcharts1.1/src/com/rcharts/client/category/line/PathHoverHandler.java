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
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

public class PathHoverHandler implements MouseOverHandler, MouseOutHandler {

	private String series;
	
	/**
	 * @return the category
	 */
	public String getSeries() {
		return series;
	}

	/**
	 * @param category the category to set
	 */
	public void setSeries(String series) {
		this.series = series;
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		GridHoverHandler.CURRENT_SERIES = series;
	}

}
