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

import com.hydro4ge.raphaelgwt.client.Raphael;
import com.hydro4ge.raphaelgwt.client.Raphael.Rect;
import com.hydro4ge.raphaelgwt.client.Raphael.Shape;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;

/**
 * 
 * Gives raphael shape object legend marks. 
 *
 */
public class LegendMarkFactory {

	private static Chart raphael;
	private static double dim;
	/*
	 * Set the Field dimension. All the shapes of this are produced by
	 * this dimension e.g rect(x,y,dimension)
	 */
	public static void setDimension(double dimension){
		LegendMarkFactory.dim = dimension;
	}
	
	public static Shape getdisc(){
		return raphael.new Circle(0, 0, dim);
	}
	
	public static Shape getLine(){
		return raphael.new Rect(- dim, - dim / 5, 2 * dim, 2 * dim / 5);
	}
	
	public static Shape getSquare(){
		double d = dim * 0.7;
		return raphael.new Rect(0, 0, 10, 10);
		
	}
	
	public static Shape getRect(){
		return raphael.new Rect(0, 0, 5, 5);
	}
	
	public static RaphaelObject get(LegendMark mark){
		RaphaelObject s = null;
		raphael = RaphaelFactory.get();
		switch(mark){
		case RECT:
			s = raphael.new Rect(0, 0, 12, 12);
			break;
		}
		return s;
	}
	
	
	public static Shape getFlower(double x, double y){
		return null;
	}
	
	public static RaphaelObject get(LegendMark mark, double x, double y){
		return null;
	}
}
