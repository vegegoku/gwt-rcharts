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
import java.util.List;

import com.hydro4ge.raphaelgwt.client.PathBuilder;
import com.rcharts.client.Point;



//TODO : not ideal for all sets of data, deteriorate visual information in some cases
public class Spline {
	public static class SPoint{
		double x;
		double y;
		double rightContX;
		double rightContY;
		double leftContX;
		double leftContY;
		
		public SPoint(double x, double y){
			this.x = x;
			this.y = y;
		}
		
		public SPoint() {
			// TODO Auto-generated constructor stub
		}

		public SPoint(Point point){
			this.x = point.getX();
			this.y = point.getY();
		}
		public double getX(){
			return x;
		}
		
		public double getY(){
			return y;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "SPoint [x=" + x + ", y=" + y + ", rightContX=" + rightContX
					+ ", rightContY=" + rightContY + ", leftContX=" + leftContX
					+ ", leftContY=" + leftContY + "]";
		}
	}

	
	public static List<SPoint> getSList(List<Point> pList){
		List<SPoint> sList = new ArrayList<SPoint>();
		for(Point p : pList){
			sList.add(new SPoint(p));
		}
		return sList;
	}
	
/*	public static SPoint getControlPoint(SPoint[] segment, 
			SPoint point, int i){
		double smoothing = 1.5;
		double denom = smoothing + 1;
		double plotX = point.getX();
		double plotY = point.getY();
		SPoint lastPoint = i > 0 ? segment[i-1] : segment[0];
		SPoint nextPoint = i < segment.length-1 ? segment[i+1] : segment[segment.length-1];
		double leftContX =0;
		double leftContY =0;
		double rightContX = 0;
		double rightContY = 0;
		SPoint ret = new SPoint();
		
		if(i < segment.length -1){
			double lastX = lastPoint.getX();
			double lastY = lastPoint.getY();
			double nextX = nextPoint.getX();
			double nextY = nextPoint.getY();
			double correction =0;
			
			leftContX = (smoothing * plotX + lastX) / denom;
			leftContY = (smoothing * plotY + lastY) / denom;
			rightContX = (smoothing * plotX + nextX) / denom;
			rightContY = (smoothing * plotY + nextY) / denom;
			
			correction = ((rightContY - leftContY) * (rightContX - plotX)) / 
			(rightContX - leftContX) + plotY - rightContY;
			System.out.println("correction :"+correction+" leftConty:"+leftContY);
			leftContY += correction;
			rightContY += correction;

			if (leftContY > lastY && leftContY > plotY) {
				leftContY = Math.max(lastY, plotY);
				rightContY = 2 * plotY - leftContY; // mirror of left control point
			} else if (leftContY < lastY && leftContY < plotY) {
				leftContY = Math.max(lastY, plotY);
				rightContY = 2 * plotY - leftContY;
			} 
			if (rightContY > nextY && rightContY > plotY) {
				rightContY = Math.max(nextY, plotY);
				leftContY = 2 * plotY - rightContY;
			} else if (rightContY < nextY && rightContY < plotY) {
				rightContY = Math.max(nextY, plotY);
				leftContY = 2 * plotY - rightContY;
			}

			point.rightContX = rightContX;
			point.rightContY = rightContY;
		}
		if(i == 0){
			ret.x = plotX;
			ret.y = plotY;
		}
		else{
			ret.rightContX = lastPoint.rightContX > 0 ? lastPoint.rightContX : lastPoint.x;
			ret.rightContY = lastPoint.rightContY > 0 ? lastPoint.rightContY : lastPoint.y;
			ret.leftContX =  leftContX > 0 ? leftContX : plotX;
			ret.leftContY = leftContY > 0 ? leftContY : plotY;
			ret.x = plotX;
			ret.y = plotY;
			
			lastPoint.rightContX = lastPoint.rightContY = 0; // reset for updating series later

		}
		return ret;
	}
*/
	
/**
 * New Implementation for making continuous cubic bezier curve along the given set of points.
 * Its replaces old deprecated getControlPoint(..) method
 * TODO: Test it for all the degrees and cases	
 */
	public static SPoint setControlPoints(SPoint prevPoint, SPoint currPoint, SPoint nextPoint){
		double m1 = (currPoint.y - prevPoint.y)/(currPoint.x - prevPoint.x);
		double m2 = (nextPoint.y - currPoint.y)/(nextPoint.x - currPoint.x);
		double a1 = Math.toDegrees(Math.atan(m1))%360;
		double a2 = Math.toDegrees(Math.atan(m2))%360;
		// Angle with y axis positively
		if(a1 < 0){
			a1 = 180 + a1;
		}
		if(a2 < 0){
			a2 = 180 + a2;
		}
		double offset = (nextPoint.x - currPoint.x)/3;
		Point leftCtrPoint = new Point();
		Point rightCtrPoint = new Point();
		if((m1*m2) > 0){
			double angle = 180 - a1 + (a1 - a2)/2;
			if(m1 > 0){
				angle = angle - 180;
			}
			angle = Math.toRadians(angle);
			double dx = offset * Math.cos(angle);
			double dy = offset * Math.sin(angle);
			double lx = 0;
			double ly = 0;
			double rx = 0;
			double ry = 0;
			lx = currPoint.x - dx;
			ly = currPoint.y + dy;
			rx = currPoint.x + dx;
			ry = currPoint.y - dy;		
			leftCtrPoint.setX(lx);
			leftCtrPoint.setY(ly);
			rightCtrPoint.setX(rx);
			rightCtrPoint.setY(ry);
		}
		else{
			leftCtrPoint.setX(currPoint.x - offset);
			leftCtrPoint.setY(currPoint.y);
			rightCtrPoint.setX(currPoint.x + offset);
			rightCtrPoint.setY(currPoint.y);
		}
//		currPoint.leftCtrPoint = leftCtrPoint;
//		currPoint.rightCtrPoint = rightCtrPoint;
		currPoint.leftContX = leftCtrPoint.getX();
		currPoint.leftContY = leftCtrPoint.getY();
		currPoint.rightContX = rightCtrPoint.getX();
		currPoint.rightContY = rightCtrPoint.getY();
		return currPoint;		
	}

	/*	
	//********* Deprecated/Old implement on spline based on old getControlPoint(...) Methond****
	public static PathBuilder getSpline(List<SPoint> sList){
		SPoint[] segment = sList.toArray(new SPoint[sList.size()]);
		PathBuilder pb = new PathBuilder();
		for(int i = 0; i < segment.length; i++){
			SPoint p = getControlPoint(segment, segment[i], i);
			if(i == 0){
				pb.M(p.x, p.y);
			}
			else{
				pb.C(p.rightContX, p.rightContY, p.leftContX, p.leftContY, p.x, p.y);
			}
			System.out.println("point no :"+i+" :"+p);
		}
		return pb;
	}
*/	
/**
 * New Implmentation of getSpline(..) based on new setControlPoint(...) replacing old 
 * getControlPoint(..) method	
 */
	
	public static SPoint[] getSplinePoints(List<SPoint> pointList){
		SPoint[] pointArray = pointList.toArray(new SPoint[pointList.size()]);
		for(int i = 0; i < pointArray.length; i++){
			SPoint point = null;
			if(i == 0){
				setControlPoints(new SPoint(0,0), pointArray[i], pointArray[i+1]);
			}
			else if(i == pointArray.length-1){
				SPoint nextPoint = new SPoint(0, 0);
				nextPoint.x = pointArray[i].x;
				setControlPoints(pointArray[i-1], pointArray[i], nextPoint);
			}
			else{
				setControlPoints(pointArray[i-1], pointArray[i], pointArray[i+1]);
			}
		}
		
		return pointArray;
	}
	
	
	public static PathBuilder getSpline(List<SPoint> pointList){
		SPoint[] points = getSplinePoints(pointList);
		PathBuilder pb = new PathBuilder();
		pb.M(points[0].x, points[0].y);
		for(int i = 1; i < points.length; i++){
			double x1 = points[i-1].rightContX;
			double y1 = points[i-1].rightContY;
			double x2 = points[i].leftContX;
			double y2 = points[i].leftContY;
			double x = points[i].x;
			double y = points[i].y;
			pb.C(x1, y1, x2, y2, x, y);

		}
		return pb;
	}
	
	public static PathBuilder getSplineForPoints(List<Point> pList){
		List<SPoint> sList = getSList(pList);
		return getSpline(sList);
	}
	
	public static PathBuilder getLineForPoints(List<Point> pList){
		List<SPoint> sList = getSList(pList);
		return getLine(sList);
	}
	
	public static PathBuilder getLine(List<SPoint> sList){
		SPoint[] segment = sList.toArray(new SPoint[sList.size()]);
		PathBuilder pb = new PathBuilder();
		for(int i = 0; i < segment.length; i++){
			SPoint p = segment[i];
			if(i == 0){
				pb.M(p.x, p.y);
			}
			else{
				pb.L(p.x, p.y);
			}
		}
		return pb;
	}
	
	public static void main(String ar[]){
		List<SPoint> list = new ArrayList<Spline.SPoint>();
		double[] x = {10, 20, 30, 40, 50, 60};
		double[] y = {20, 10, 10, 20, 30, 40};
		for(int i = 0; i<6; i++){
			list.add(new SPoint(x[i], y[i]));
		}
		getSpline(list);
		System.out.println("control point list :"+list);
	}
}
