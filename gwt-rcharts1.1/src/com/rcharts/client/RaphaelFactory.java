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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * A Class with Singleton pattern that gives singleton Raphael object. The init() method of this class
 * must be called before any rendering and to the body of page(eg.RootLayoutPanel.get().add(
 * RaphaelFactory.getRapheal()). For all shapes to be under the same svg tag as every 
 * raphael method/constructor of inner class like Rect(..),Circle(..) must be invoked on same raphael
 * object. To start with new svg tag repeat the procedure.
 *
 */
public class RaphaelFactory {
	
	private static ChartLocal<Chart> chart = new ChartLocal<Chart>();
	private static ChartLocal<Set<String>> series = new ChartLocal<Set<String>>();
	private static ChartLocal<Set<String>> categories = new ChartLocal<Set<String>>();
	//initialize necessary not injected 
	private static ChartLocal<Map<String, String>> colorMap = new ChartLocal<Map<String,String>>();
	private static ChartLocal<Double> catMaxValue = new ChartLocal<Double>();
	private static Object currentObject;

	
	public static void setCurrentObject(Object o){
		RaphaelFactory.currentObject = o;
	}
	
	public static void setSeries(Set<String> seriesSet){
		series.set(currentObject, seriesSet);
	}

	public static Set<String> getSeriesNames(){
		return series.get(currentObject);
	}
	
	public static void setCategories(Set<String> categorySet){
		categories.set(currentObject, categorySet);
	}
	
	public static Set<String> getCategoriesNames(){
		return categories.get(currentObject);
	}
	
	public static void setCatMaxValue(Double value){
		catMaxValue.set(currentObject, value);
	}
	
	public static double getCatMaxValue(){
		return catMaxValue.get(currentObject);
	}
	
	private static void makeColorMap(){
		Map<String, String> cMap = colorMap.get(currentObject);
		String[] colors = get().getDefaultColors() != null ? 
				get().getDefaultColors() : DefaultAttributes.getColors();
		int ctr = 0;
		for(String seriesName : series.get(currentObject)){
			cMap.put(seriesName, colors[ctr++]);
		}
	}
	
	public static Map<String, String> getColorMap(){
		if(colorMap.get(currentObject)== null){
			colorMap.set(currentObject, new HashMap<String, String>());
			makeColorMap();
		}
		return colorMap.get(currentObject);
	}
	
	/**
	 * Sets the colorMap; mostly useful for ComboChart
	 * @param colorMap
	 */
	public static void setColorMap(Map<String, String> colorsMap){
		colorMap.set(currentObject, colorsMap);
	}
	
	public static void set(Chart c, Chart chart){
		RaphaelFactory.chart.set(c, chart);
	}
	
	public static Chart get(){
		return RaphaelFactory.chart.get(currentObject);
	}
	
	public static Raphael getRaphael(){
		return get();
	}
	
	
	
	public static int getWidth() {
		return get().getWidth();
	}


	public static int getHeight() {
		return get().getHeight();
	}
	
}
