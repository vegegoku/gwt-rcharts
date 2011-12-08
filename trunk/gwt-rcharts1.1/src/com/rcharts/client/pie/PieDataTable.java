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
package com.rcharts.client.pie;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class PieDataTable<E extends Comparable <? super E>> {

	/**
	 * 
	 * A Comparator class to sort the map according to values in descending order 
	 */
	public static class ValueComparator implements Comparator<String>{

		private Map<String, Double> base;
		
		public ValueComparator(Map base){
			this.base = base;
		}
		
		@Override
		public int compare(String o1, String o2) {
			if(base.get(o1) > base.get(o2)){
				return -1;
			}
			else if(base.get(o1) == base.get(o2)){
				return 0;
			}
			else{
				return 1;
			}
		}
		
	}
	
	public static class AnglePair{
		private double startAngle;
		private double endAngle;
		
		public AnglePair(){
		}
		
		public AnglePair(double startAngle, double endAngle){
			this.startAngle = startAngle;
			this.endAngle = endAngle;
		}

		/**
		 * @return the startAngle
		 */
		public double getStartAngle() {
			return startAngle;
		}

		/**
		 * @param startAngle the startAngle to set
		 */
		public void setStartAngle(double startAngle) {
			this.startAngle = startAngle;
		}

		/**
		 * @return the endAngle
		 */
		public double getEndAngle() {
			return endAngle;
		}

		/**
		 * @param endAngle the endAngle to set
		 */
		public void setEndAngle(double endAngle) {
			this.endAngle = endAngle;
		}
		
		
	}
	
	private Map<String, E> pieData = new LinkedHashMap<String, E>();
	private Map<String, AnglePair> anglePairMap = new LinkedHashMap<String, AnglePair>();
	public static double START_ANGLE = 0;
	
	
	public void add(String name, E value){
		pieData.put(name, value);
	}
	
	public E get(String name){
		return pieData.get(name);
	}
	
	public Set<String> getNames(){
		return pieData.keySet();
	}
	
	public Double getTotal(){
		double total = 0;
		for(String name : pieData.keySet()){
			if(! (pieData.get(name) instanceof Double)){
				return null;
			}
			total = total + (Double)pieData.get(name);
		}
		return total;
	}

	/**
	 * A utility function to sort the piedatatable must be called first after populating 
	 * the data and before anything.
	 * While rendering 3D pie sectors/slices with very small degree gives a bad view
	 * as the arc of pie conflicts with them, so to avoid the situation such sectors 
	 * have to be place in between angle 180 to 360 where arc width is not visible. 
	 * if no. of such sectors extends the limit of 180 i.e 180 sectors having 1 degree 
	 * each, width of 3D pie has to be decreased and in worst case scenario 2D pie could
	 * be the only solution 
	 */
	public void init(){
		double total = getTotal();
		LinkedHashMap<String, E> map = new LinkedHashMap<String, E>();
		//List<String> shorts = new ArrayList<String>();
		TreeMap<String, Double> shorts = new TreeMap<String, Double>(new ValueComparator(pieData));
//		System.out.println("before shorts :"+ pieData);
		for(String name : pieData.keySet()){
			double value = (Double) pieData.get(name);
			double percentage = (value/total);
			if(percentage <= 0.1){
				shorts.put(name, value);
			}
			else{
				map.put(name, pieData.get(name));
			}
		}
		for(String name : shorts.keySet()){
			E v = pieData.get(name);
			map.put(name, v);			
		}
		pieData.clear();
		pieData = map;
	//	System.out.println("After shorts : "+pieData);
	//	System.out.println("Tree :"+shorts);
	}
	
	private void createAnglePairMap(){
		double total = getTotal();
		double startAngle = START_ANGLE;
		double endAngle = START_ANGLE;
		for(String name : pieData.keySet()){
			double value = (Double) pieData.get(name);
			double percentage = (value/total);
			double angle = percentage * 360;
			startAngle = endAngle;
			endAngle = (startAngle + angle);
			AnglePair anglePair = new AnglePair(startAngle, endAngle);
			anglePairMap.put(name, anglePair);
		}
	}
	
	public Map<String, AnglePair> getAnglePairMap(){
		if(anglePairMap.isEmpty()){
			createAnglePairMap();
		}
		return anglePairMap;
	}
}

