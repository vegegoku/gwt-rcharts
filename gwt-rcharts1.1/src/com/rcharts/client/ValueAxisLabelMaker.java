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

import java.util.LinkedHashSet;
import java.util.Set;

import com.google.gwt.user.client.Window;

public class ValueAxisLabelMaker {
	
	public static Set<String> getValueAxisLabels(double max, double min){
		return getValueAxisLabels(max, min, -1, 5);
	}
	
	public static Set<String> getValueAxisLabels(double max, double min, double valueInterval){
		return getValueAxisLabels(max, min, valueInterval, 5);
	}
	
	
	public static Set<String> getValueAxisLabels(double max, double min, double valueInterval,
			double noOfPeriods){
		Set<String> valueAxisLabels = new LinkedHashSet<String>();
		max = getMaxValueForEvenDist(max, min, noOfPeriods);
		if(valueInterval <= 0){
			valueInterval = max/noOfPeriods;
		}
		
		if(max < 0){
			max = 0;
			if(valueInterval <= 0){
				valueInterval = Math.abs(min/noOfPeriods);
			}
		}
		
		double i = 0;
		if(min < 0){
			i = -max;
		}
		
		while(i < max){
			valueAxisLabels.add(String.valueOf((int)(i+valueInterval)));
			i = i + valueInterval;
		}		
		return valueAxisLabels;

	}
	
	private static double getMaxValueForEvenDist(double max, double min, double interval){
		double maxValue = max;
		if(Math.abs(min)> maxValue){
			maxValue = Math.abs(min);
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

}
