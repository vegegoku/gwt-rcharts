package com.rcharts.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.hydro4ge.raphaelgwt.client.PathBuilder;
import com.hydro4ge.raphaelgwt.client.Raphael;
import com.hydro4ge.raphaelgwt.client.Raphael.Path;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gwt_rcharts1_1 implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
//		drawSPline();
	}
	
	private static class SPoint{
		double x;
		double y;
		Point leftCtrPoint;
		Point rightCtrPoint;
		
		public SPoint(){
			
		}

		public SPoint(double x, double y) {
			this.x = x;
			this.y = y;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "SPoint [x=" + x + ", y=" + y + ", leftCtrPoint="
					+ leftCtrPoint + ", rightCtrPoint=" + rightCtrPoint + "]";
		}
		
		
	}
	
	public static void drawSPline(){
		SPoint[] points = makeSpline();
		Raphael r = new Raphael(1000, 1000);
		PathBuilder pb = new PathBuilder();
		PathBuilder pbTan = new PathBuilder();
		pb.M(points[0].x, points[0].y);
		r.new Circle(points[0].x, points[0].y, 2);
		for(int i = 1; i < points.length; i++){
			double x1 = points[i-1].rightCtrPoint.getX();
			double y1 = points[i-1].rightCtrPoint.getY();
			double x2 = points[i].leftCtrPoint.getX();
			double y2 = points[i].leftCtrPoint.getY();
			double x = points[i].x;
			double y = points[i].y;
			r.new Circle(x, y, 2);
			pb.C(x1, y1, x2, y2, x, y);
			pbTan.M(points[i].leftCtrPoint.getX(), points[i].leftCtrPoint.getY());
			pbTan.L(points[i].rightCtrPoint.getX(), points[i].rightCtrPoint.getY());
			if(i > points.length -3){
//				pbTan.M(points[i-1].x, points[i-1].y);
//				pbTan.L(points[i].x, points[i].y);
			}
//			pbTan.l(50,50);
//			Path tan = r.new Path(pbTan);
//			tan.attr("stroke-width", 3);
		}
		Path path = r.new Path(pb);
		
		RootPanel.get().add(r);
	}
	
	public static SPoint[] makeSpline(){
		double[] x = { 10, 100, 200, 300, 400, 500, 600};
		double[] y = { 10, 200, 100, 100, 400, 250, 10};
		for(int i = 0; i < y.length; i++){
			y[i] = (Math.random() * 1000)%600;
		}
		List<SPoint> pointList = new ArrayList<SPoint>();
		for(int i = 0; i < x.length; i++){
			pointList.add(new SPoint(x[i], y[i]));
		}
		SPoint[] pointArray = pointList.toArray(new SPoint[pointList.size()]);
		for(int i = 0; i < pointArray.length; i++){
			SPoint point = null;
			if(i == 0){
				getSPoint(new SPoint(0,0), pointArray[i], pointArray[i+1]);
			}
			else if(i == pointArray.length-1){
				SPoint nextPoint = new SPoint(0, 0);
				nextPoint.x = pointArray[i].x;
				getSPoint(pointArray[i-1], pointArray[i], nextPoint);
			}
			else{
				getSPoint(pointArray[i-1], pointArray[i], pointArray[i+1]);
			}
		}
		return pointArray;
	}
	
	public static SPoint getSPoint(SPoint prevPoint, SPoint currPoint, SPoint nextPoint){
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
		//if(a1 > 90 && a2 > 90 && a2 != 180){
		if((m1*m2) > 0){
//			Window.alert("");
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
		currPoint.leftCtrPoint = leftCtrPoint;
		currPoint.rightCtrPoint = rightCtrPoint;
		return currPoint;		
	}
	
	public static SPoint getSPoint2(SPoint prevPoint, SPoint currPoint, SPoint nextPoint){
		double m1 = (currPoint.y - prevPoint.y)/(currPoint.x - prevPoint.x);
		double m2 = (nextPoint.y - currPoint.y)/(nextPoint.x - currPoint.x);
		double a1 = Math.toDegrees(Math.atan(m1))%360;
		double a2 = Math.toDegrees(Math.atan(m2))%360;
		boolean neg = false;
		boolean con = false;
		if(prevPoint.y > currPoint.y && currPoint.y > nextPoint.y){
			con = true;
		}
		if(nextPoint.y > currPoint.y && currPoint.y > prevPoint.y){
			con = true;
		}
		
		
		if(a1 < 0 && a2 < 0){
			neg = true;
		}
		if(a1 < 0){
			a1 = 180 + a1;
		}
		if(a2 < 0){
			a2 = 180 + a2;
		}
		double offset = (nextPoint.x - currPoint.x)/3;
		Point leftCtrPoint = new Point();
		Point rightCtrPoint = new Point();
		if(a2 >= a1 && a2 != 0 && con){
			double angle = Math.toDegrees(m1) - getAngle(prevPoint, currPoint, nextPoint)/2;
			angle = Math.toRadians(angle);
			Window.alert("angle :"+angle);
			double dx = offset * Math.cos(angle);
			double dy = offset * Math.sin(angle);
			double lx = 0;
			double ly = 0;
			double rx = 0;
			double ry = 0;
			if(neg){
				lx = currPoint.x - dx;
				ly = currPoint.y - dy;
				rx = currPoint.x + dx;
				ry = currPoint.y + dy;
			}
			else{
				Window.alert("yes");
				lx = currPoint.x - dx;
				ly = currPoint.y + dy;
				rx = currPoint.x + dx;
				ry = currPoint.y - dy;		
			}
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
		currPoint.leftCtrPoint = leftCtrPoint;
		currPoint.rightCtrPoint = rightCtrPoint;
		return currPoint;
	}
	
	
	public static double getAngle(SPoint point1, SPoint point2, SPoint point3){
		double dx1 = point2.x - point1.x;
		double dy1 = point2.y - point1.y;
		double dx2 = point3.x - point2.x;
		double dy2 = point3.y - point2.y;
		double dotProd = dx1*dx2 + (dy1*dy2);
		double sqrLen = ((dx1*dx1)+(dy1*dy1))*((dx2*dx2)+(dy2*dy2));
		double angle = Math.acos(dotProd/Math.sqrt(sqrLen));
		angle = Math.toDegrees(angle);
		return angle;
	}
	
	
	public static void main(String ar[]){
		SPoint[] points = makeSpline();
		for(SPoint point : points){
			System.out.println(point);
		}
	}
}
