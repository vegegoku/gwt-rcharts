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
package com.rcharts.client.category;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rcharts.client.Console;

/**
 * 
 * Represent/Holds a three dimension data 
 * Axis 1 : holds String values , we say it category so its category axis
 * Axis 2 : holds String values , we say it series so its series axis
 * Axis 3 : holds numeric values , we say it values so its value axis which always holds an axis
 * 			in chart
 * 
 * example
 * 			Revenue from annul coffee consumption (in crore )
 * ------------------------------------------------------
 * country/year| 2001 | 2002 | 2003 | 2004 | 2005 | 2006 |
 * ------------------------------------------------------
 * Austria     |  10  |  20  |  30  |  40  |  50  |  60  |
 * ------------------------------------------------------
 * Bulgaria    |  11  |  22  |  33  |  44  |  55  |  66  |
 * ------------------------------------------------------
 * Denmark     |  9   |  19  |  29  |  39  |  49  |  59  |
 * -------------------------------------------------------
 * 
 * For better visibility chart has to be 2-dimensional i.e comprise of two axes
 * For sure one of axes will be occupied by numeric axis/value axis. Here it is annual coffee consumption
 * Now we have in hand one axis and two candidate for it :
 * 1) Country
 * 2) Year
 * out of which anyone could hold as an
 * 1) Axis
 * and other as
 * 2) Legend
 * 
 * By default Series is legend
 */
public class CategoryDataTable<E extends Comparable <? super E>> {

	private Set<String> categoryNames;
	private Set<String> seriesNames;
	private Map<String, E> values;
	private boolean isSeriesLegend = true;
	private String mostLengthyCat = "";
	
	protected enum KeyType{
		CATEGORY, SERIES
	}
	
	public CategoryDataTable(){
		categoryNames = new LinkedHashSet<String>();
		seriesNames = new LinkedHashSet<String>();
		values = new HashMap<String, E>();
	}
	
	public Set<String> getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(Set<String> categoryNames) {
		this.categoryNames = categoryNames;
	}

	public boolean isSeriesLegend() {
		return isSeriesLegend;
	}

	public void setSeriesLegend(boolean isSeriesLegend) {
		this.isSeriesLegend = isSeriesLegend;
	}

	public Set<String> getSeriesNames() {
		return seriesNames;
	}

	public void setSeriesNames(Set<String> seriesNames) {
		this.seriesNames = seriesNames;
	}

	protected Map<String, E> getValues(){
		return values;
	}
	
	private String makeKey(String category, String series){
		return category + series;
	}


	/**
	 * 
	 * @param value : the value of cell
	 * @param category : row string  / column String
	 * @param series : column string / row String
	 */
	public void add(E value, String category, String series){
		categoryNames.add(category);
		seriesNames.add(series);
		String key = makeKey(category, series);
		values.put(key, value);
		if(mostLengthyCat.length() < category.length()){
			mostLengthyCat = category;
		}
	}
	
	/**
	 * get value for category and series
	 * @param category
	 * @param series
	 * @return
	 */
	public E get(String category, String series){
		return values.get(makeKey(category, series));
	}
	
	/**
	 * Returns Maximum value of the table
	 * @return
	 */
	public double getMax(){
		return getMaxOrMin(1);
	}
	
	
	/**
	 * Returns Minimum value of the table
	 * @return
	 */
	public double getMin(){
		return getMaxOrMin(0);
	}
	
	/**
	 * Returns the no. of categories 
	 * @return
	 */
	public int getCategoryCount(){
		return categoryNames.size();
	}
	
	/**
	 * Returns the no. of series
	 * @return
	 */
	public int getSeriesCount(){
		return seriesNames.size();
	}
	
	//0 : means to be min
	//1 : means to be max
	private double getMaxOrMin(int option){
		Collection<E> list = values.values();
		ArrayList<E> l = new ArrayList<E>(list);
		ArrayList<E> dest = new ArrayList<E>(l);
		Console.log("Making copy of values");
//		Collections.copy(l, dest);
		Collections.sort(dest);
		Console.log("calulating max and min");
		double min = (Double) dest.get(0);
		Console.log("Min:"+min);
		Console.log("max index:"+(l.size()-1));
		//double max = (Double) dest.get(l.size()-1);
		double max = (Double) dest.get(dest.size()-1);
		Console.log("max :"+max);
		if(option == 1){
			return max;
		}
		else{
			return min;
		}
	}

	
	/**
	 * Utility function for making intervals of  chart even i.e round to two digit figure
	 * eg: 300 600 900 1200 rather than 234 468 702 ...
	 * @param interval
	 * @return
	 */
	public double getMaxValue(double interval){
		double maxValue = getMax();
		if(Math.abs(getMin())> maxValue){
			maxValue = Math.abs(getMin());
		}
		maxValue = Math.ceil(maxValue);
		if(maxValue < 1){
			return 1;
		}
		else if(maxValue < 5){
			return 5;
		}
		else if(maxValue < 10){
			return 10;
		}
		double returnMax = 0;
		double quo = maxValue/interval;
		double mod = quo;
		int i = 10;
		while(true){
			mod = (int)mod/i;
			if(mod == 0){
				break;
			}
			else{
				i = i*10;
			}
		}
		i = i/10;
		quo = quo/i;
		quo = Math.ceil(quo);
		quo = quo * i;
		returnMax = quo * interval;
		return returnMax;
	}
	
	public double getMaxValue(){
		return getMaxValue(5);
	}

	
	
	/**
	 * returns the array holding all the series under the category given
	 * @param categoryName
	 * @return
	 */
	public List<E> getValuesByCategory(String categoryName){
		return getValues(categoryName, KeyType.CATEGORY);
	}
	
	/**
	 *  returns the array holding all the category under the series given
	 * @param seriesName
	 * @return
	 */
	public List<E> getValueBySeries(String seriesName){
		return getValues(seriesName, KeyType.SERIES);
	}
	
	/**
	 * Returns array value by categories or series
	 * @param name
	 * @param keyType
	 * @return
	 */
	
	private List<E> getValues(String name, KeyType keyType){
		List<E> valueList = new ArrayList<E>();
		Iterator<String> it = null;
		if(keyType == KeyType.CATEGORY){
			it = seriesNames.iterator();
		}
		else{
			it = categoryNames.iterator();			
		}
		String previous = "";
		while(it.hasNext()){
			String current = it.next();
			if(current.equals(previous)){
				continue;
			}
			String key = null;
			if(keyType == KeyType.CATEGORY){
				key = makeKey(name, current);
			}
			else{
				key = makeKey(current, name);
			}

			if(values.get(key) == null){
				try {
					throw new Exception("No such "+keyType.toString()+" exists "+name);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			E value = values.get(key);
			valueList.add(value);
			previous = current;
		}
		return new ArrayList<E>(valueList);		
	}

	
	public double getMaxForStacked(){
		return getMaxForStacked(5);
	}
	
	public double getMaxForStacked(double interval){
		double max = 0;
		double sum = 0;
		for(String cat : this.getCategoryNames()){
			sum = 0;
			for(String ser : this.getSeriesNames()){			
				Double value = (Double) this.get(cat, ser);
				sum = sum + value;
			}
			if(sum > max){
				max = sum;
			}
		}
		double maxValue = max;
		if(Math.abs(getMin())> maxValue){
			maxValue = Math.abs(getMin());
		}
		maxValue = Math.ceil(maxValue);
		if(maxValue < 1){
			return 1;
		}
		else if(maxValue < 5){
			return 5;
		}
		else if(maxValue < 10){
			return 10;
		}
		double returnMax = 0;
		double quo = maxValue/interval;
		double mod = quo;
		int i = 10;
		while(true){
			mod = (int)mod/i;
			if(mod == 0){
				break;
			}
			else{
				i = i*10;
			}
		}
		i = i/10;
		quo = quo/i;
		quo = Math.ceil(quo);
		quo = quo * i;
		returnMax = quo * interval;
		return returnMax;
		//return max;
	}

	/**
	 * Returns the category most lengthy i.e category ..a String with most characters
	 */
	public String getMostLenthgyCategory(){
		return mostLengthyCat;
	}
	
	@Override
	public String toString(){
		Iterator<String> serIt = seriesNames.iterator();
		Iterator<String> catIt = categoryNames.iterator();
		StringBuffer msg = new StringBuffer();
		while(serIt.hasNext()){
			String serName = serIt.next();
			msg.append(serName +":\n");
			while(catIt.hasNext()){
				String catName = catIt.next();
				msg.append(catName+": ");
				msg.append(get(catName, serName));
				msg.append(" \n");
			}
			catIt = categoryNames.iterator();
		}
		return msg.toString();
	}
}
