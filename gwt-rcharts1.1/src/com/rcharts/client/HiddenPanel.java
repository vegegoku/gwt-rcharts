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

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 *	HiddenPanel is responsible for lazy loading of the charts.
 *  Its essential for Charts that they do not start the rendering process
 *  until the parent is not visible. Even when parent visible a little delay 
 *  is needed for the parent to be rendered by browser. HiddenPanel delays 
 *  the calculations and rendering process by 100 Milliseconds by default.
 * (* Its only a observation that without delay chats are rendered properly
 *   the technical reason behind this may or may not be true *)
 *   
 *  In cases like TabLayoutPanel the charts are needed to wrapped to HiddenPanel
 */
public class HiddenPanel extends SimplePanel {

	private Chart chart;
	private boolean added;
	
	public HiddenPanel(){
		
	}
	
	public HiddenPanel(Chart chart){
		this.chart = chart;
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
		added = false;
		this.chart = chart;
	}
	
	@Override
	public void setVisible(boolean visible){
		if(visible){
			Timer timer = new Timer() {
				
				@Override
				public void run() {
					if(!added){
						add(chart);						
						added = true;
					}
				}
			};
			timer.schedule(100);
		}
	}
}
