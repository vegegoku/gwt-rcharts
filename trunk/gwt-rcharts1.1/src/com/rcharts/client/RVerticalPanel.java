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
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.hydro4ge.raphaelgwt.client.Raphael.Path;


public class RVerticalPanel extends RCellPanel {

	public RVerticalPanel(){
		super();
	}
	
	public RVerticalPanel(double spacing){
		super(spacing);
	}
	
	public RVerticalPanel(double x, double y, double spacing){
		super(x, y, spacing);
	}
	
	/**
	 * Only Right and Left(by default) align are available
	 * @param element
	 * @param align
	 */
	public void add(RaphaelObject element, Align align){
		super.add(element);
		if(align == Align.RIGHT){
			BBox bbox = element.getBBox();
			double x = bbox.x() - bbox.width();
			//TODO : Test element.translate(x, 0) newly added for all charts
			element.translate(x, 0);//element.attr("x", x);
			if(element instanceof Path){
//				element.translate(x, 0);
			}
		}
	}
	
	
	public void updateSpacing(double spacing){
		super.setSpacing(spacing);
		if(movement != Movement.UPWORD){
			y = y + spacing;
		}
		else{
			y = y - spacing;
		}
	}
	
	@Override
	public void updateXY(RaphaelObject element) {
		BBox bbox = element.getBBox();
		if(movement != Movement.UPWORD){
			y = (y + bbox.height() + getSpacing());
		}
		else{
			y = (y - bbox.height() - getSpacing());							
		}
		
	}
	
	

}
