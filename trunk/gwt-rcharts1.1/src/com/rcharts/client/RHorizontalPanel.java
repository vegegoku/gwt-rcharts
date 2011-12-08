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


public class RHorizontalPanel extends RCellPanel {

	public RHorizontalPanel(){
		super();
	}
	
	public RHorizontalPanel(double spacing){
		super(spacing);
	}
	
	public RHorizontalPanel(double x, double y, double spacing){
		super(x, y, spacing);
	}
	
/**
 * only top and bottom alignment are available
 * @param element
 * @param align
 */
	public void add(RaphaelObject element, Align align){
		super.add(element);
		if(align == Align.BOTTOM){
			BBox bbox = element.getBBox();
			double y = bbox.y() - bbox.height();
			//TODO : Test element.translate(0, y) newly added for all charts
			element.translate(0, y);//element.attr("y", y);
			if(element instanceof Path){
	//			element.translate(0, y);
			}
		}
		
	}

	@Override
	public void updateSpacing(double spacing){
		super.setSpacing(spacing);
		if(movement != Movement.LEFT){
			x = x + spacing;
		}
		else{
			x = x - spacing;
		}
	}

	
	@Override	
	public void updateXY(RaphaelObject element){
		BBox bbox = element.getBBox();
		if(movement != Movement.LEFT){
			x = (x + bbox.width() + getSpacing());
		}
		else{
			x = (x - bbox.width() - getSpacing());			
		}
	}
}
