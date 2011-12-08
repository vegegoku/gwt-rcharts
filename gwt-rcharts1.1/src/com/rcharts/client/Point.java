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

/**
 * Represents a point in coordinate system.
 * CAUTION : Point class is not related to raphael library so when sets or shape are translated it doesnt
 * So to keep track of points on graphics make shapes and hide them. So when a Set is translated or scaled
 * or displaced the Raphael object shape will too and that helps to keep track of the point. Then the 
 * point could be made out of that shape.
 */
public class Point {
	private double x;
	private double y;
	
	public Point(){
		
	}
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @return x coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * 
	 * @param x : the x coordinate
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * 
	 * @return y coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * 
	 * @param y the y coordinate 
	 */
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public boolean equals(Object point){
		if(!(point instanceof Point)){
			return false;
		}
		
		Point p = (Point)point;
		if(this.x == p.x && this.y == p.y ){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

}
