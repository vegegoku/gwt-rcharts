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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hydro4ge.raphaelgwt.client.PathBuilder;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.Point;
import com.rcharts.client.category.Axis;
import com.rcharts.client.category.Axis3D;
import com.rcharts.client.category.line.Spline.SPoint;

public class Line3DHelper {

	public static List<SPoint> get3DLine(List<SPoint> pointList){
		double angle = Axis3D.angle;
		double width = Axis.tickMarkLength/3;
		double rad = Math.toRadians(angle);
		double xOffset = width * Math.cos(rad);
		double yOffset = width * Math.cos(rad);
		List<SPoint> pointList2 = new ArrayList<SPoint>();
		for(SPoint point : pointList){
			double x = point.getX() - xOffset;
			double y = point.getY() + yOffset;
			pointList2.add(new SPoint(x, y));
		}
		Collections.reverse(pointList2);
		pointList.addAll(pointList2);
		return pointList;
	}

	public static List<Point> get3DLine(List<Point> pointList, double dummy){
		double angle = Axis3D.angle;
		double width = Axis.tickMarkLength/3;
		double rad = Math.toRadians(angle);
		double xOffset = width * Math.cos(rad);
		double yOffset = width * Math.cos(rad);
		List<Point> pointList2 = new ArrayList<Point>();
		for(Point point : pointList){
			double x = point.getX() - xOffset;
			double y = point.getY() + yOffset;
			pointList2.add(new Point(x, y));
		}
		Collections.reverse(pointList2);
		pointList.addAll(pointList2);
		return pointList;
	}
	
	
	public static Point get3DPoint(Point point){
		double angle = Axis3D.angle;
		double width = Axis.tickMarkLength/3;
		double rad = Math.toRadians(angle);
		double xOffset = width * Math.cos(rad);
		double yOffset = width * Math.cos(rad);
		double x = point.getX() - xOffset;
		double y = point.getY() + yOffset;
		return new Point(x, y);
	}

}
