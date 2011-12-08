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
package com.rcharts.client.category.candlestick;

import com.hydro4ge.raphaelgwt.client.PathBuilder;
import com.hydro4ge.raphaelgwt.client.Raphael;
import com.hydro4ge.raphaelgwt.client.Raphael.Path;
import com.hydro4ge.raphaelgwt.client.Raphael.Rect;
import com.hydro4ge.raphaelgwt.client.Raphael.Set;
import com.rcharts.client.RaphaelFactory;

public class CandleStick {

	private Raphael raphael;
	private double open;
	private double close;
	private double high;
	private double low;
	private Set set;
	
	public static String GAIN_COLOR = "green";
	public static String LOSS_COLOR = "red";
	public static double CANDLE_WIDTH = 10;
	
	
	public CandleStick(){
		raphael = RaphaelFactory.get();
		set = raphael.new Set();
	}
	
	public void init(double open, double close, double high, double low){
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
	}
	
	public void init(double open, double close, double high, double low, 
			String gainColor, String lossColor){
		init(open, close, high, low);
		this.GAIN_COLOR = gainColor;
		this.LOSS_COLOR = lossColor;
	}
	
	public void init(CandleStickData data){
		init(data.getOpen(), data.getClose(), data.getHigh(), data.getLow());
	}
	
	public void init(CandleStickData data, String gainColor, String lossColor, double candleWidth){
		init(data);
		GAIN_COLOR = gainColor;
		LOSS_COLOR = lossColor;
		CANDLE_WIDTH = candleWidth;
	}
	public static void setCandleWidth(double width){
		CANDLE_WIDTH = width;
	}
	
	private void createGainCandle(){
		double height = close - open;
		Rect body = raphael.new Rect(0, 0, CANDLE_WIDTH, height);
		body.attr("fill", GAIN_COLOR);
		
		double upperLength = high - close;
		Path upperShadow = raphael.new Path(getUpperShadowPath(upperLength));
		
		double lowerLength = open - low;		
		Path lowerShadow = raphael.new Path(getLowerShadowPath(lowerLength, height));
		
		set.push(body);
		set.push(upperShadow);
		set.push(lowerShadow);
	}
	
	
	private void createLossCandle(){
		double height = open - close;
		Rect body = raphael.new Rect(0, 0, CANDLE_WIDTH, height);
		body.attr("fill", LOSS_COLOR);
		
		double upperLength = high - open;
		Path upperShadow = raphael.new Path(getUpperShadowPath(upperLength));
		
		double lowerLength = close - low;
		Path lowerShadow = raphael.new Path(getLowerShadowPath(lowerLength, height));
		
		set.push(body);
		set.push(upperShadow);
		set.push(lowerShadow);
	}
	
	private PathBuilder getUpperShadowPath(double upperShadowLength){
		upperShadowLength = upperShadowLength > 0 ? upperShadowLength : 0;
		PathBuilder upperPath = new PathBuilder();
		upperPath.M(CANDLE_WIDTH/2, 0);
		upperPath.L(CANDLE_WIDTH/2, upperShadowLength);
		upperPath.Z();
		return upperPath;
	}
	
	private PathBuilder getLowerShadowPath(double lowerShadowLength, double bodyHeight){
		lowerShadowLength = lowerShadowLength > 0 ? lowerShadowLength : 0;
		PathBuilder lowerPath = new PathBuilder();
		lowerPath.M(CANDLE_WIDTH/2, bodyHeight);
		lowerPath.L(CANDLE_WIDTH/2, bodyHeight+lowerShadowLength);
		lowerPath.Z();		
		return lowerPath;
	}
	
	
}
