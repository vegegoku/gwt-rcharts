package com.rcharts.client.thems;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.rcharts.client.Chart;
import com.rcharts.client.styles.Style;
import com.rcharts.client.styles.TextStyle;

public class DarkBlueTheme {

	public static DarkBlue darkBlue = GWT.create(DarkBlue.class);
	public static void apply(Chart chart){
		chart.setBackgroundColor(darkBlue.backGroundColor());//"320-#07367C-#000000");//
		Style style = chart.getPlotStyle();
		style.setFillColor(darkBlue.plotColor());
		style.setStrokeColor(darkBlue.plotStroke());
		style.setFillOpacity(0.1);
		style.setStrokeOpacity(1);
		style.setStrokeWidth(1);
		
		TextStyle textStyle = chart.getTitleStyle();
		textStyle.setFillColor(darkBlue.titleColor());
		textStyle.setFontWeight(darkBlue.titleFontWeight());
		textStyle.setFontSize(darkBlue.titleFontSize());
		
		textStyle = chart.getSubTitleStyle();
		textStyle.setFillColor(darkBlue.subTitleColor());
		textStyle.setFontWeight(darkBlue.subTitleFontWeight());
		textStyle.setFontSize(darkBlue.subTitleFontSize());
		
		style = chart.getAxisStyle();
		style.setStrokeOpacity(1);
		chart.setVAxisStyle(style);
		chart.setHAxisStyle(style);
		if(chart.is_3d()){
			style.setStrokeColor(darkBlue.gridColor());
			
		}
		else{
			style.setStrokeColor(darkBlue.axisColor());			
		}

		style = chart.getAxisTickMarkStyle();
		if(chart.is_3d()){
			style.setStrokeColor(darkBlue.gridColor());
		}
		else{
			style.setStrokeColor(darkBlue.axisColor());			
		}
		
		
		style = chart.getAxisGridLineStyle();
		style.setStrokeColor(darkBlue.gridColor());
		style.setStrokeOpacity(1);
		
		textStyle = chart.getAxisTickLabelStyle();
		textStyle.setFillColor(darkBlue.tickLabelColor());
		textStyle.setFontWeight(darkBlue.titleFontWeight());
		textStyle.setFontSize(darkBlue.tickLabelFontSize());
		
		textStyle = chart.getVAxisTitleStyle();
		textStyle.setFillColor(darkBlue.vAxisTitleColor());
		textStyle.setFontWeight(darkBlue.vAxisTitleFontWeight());
		textStyle.setFontSize(darkBlue.vAxisTitleFontSize());
		textStyle.setRotation(darkBlue.vAxisTitleRotation());
		
		textStyle = chart.getLegendLabelStyle();
		textStyle.setFontSize(darkBlue.legendLabelFontSize());
		textStyle.setFillColor(darkBlue.legendLabelColor());
		if(chart.isAttached()){
			chart.applyCurrentTheme();
		}
		
	}
}
