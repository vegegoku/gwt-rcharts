package com.rcharts.client.thems;

import com.google.gwt.core.client.GWT;
import com.rcharts.client.Chart;
import com.rcharts.client.styles.Style;
import com.rcharts.client.styles.TextStyle;

public class GrayTheme {

	public static Gray gray = GWT.create(Gray.class);
	public static void apply(Chart chart){
		chart.setBackgroundColor(gray.backGroundColor());
		
		Style style = chart.getPlotStyle();
		style.setStrokeWidth(gray.plot_StrokeWidth());
		style.setStrokeOpacity(1);
		style.setFillOpacity(0.1);
		style.setFillColor(gray.backGroundColor());
		
		TextStyle textStyle = chart.getTitleStyle();
		textStyle.setFillColor(gray.title_Fill());
		textStyle.setFontWeight(gray.title_FontWeigth());
		textStyle.setFontSize(gray.title_FontSize());
		
		textStyle = chart.getSubTitleStyle();
		textStyle.setFillColor(gray.subTitle_Fill());
		textStyle.setFontWeight(gray.subTitle_FontWeigth());
		textStyle.setFontSize(gray.subTitle_FontSize());
		
		textStyle = chart.getAxisTickLabelStyle();
		textStyle.setFillColor(gray.axisTickLabel_Fill());
		textStyle.setFontWeight(gray.axisTickLabel_FontWeigth());
		textStyle.setFontSize(gray.axisTickLabel_FontSize());
		
		textStyle = chart.getVAxisTitleStyle();
		textStyle.setFillColor(gray.vAxisTitle_Fill());
		textStyle.setFontWeight(gray.vAaxisTitle_FontWeigth());
		textStyle.setFontSize(gray.vAxisTitle_FontSize());
		textStyle.setRotation(gray.vAxisTitle_Rotation());
		
		textStyle = chart.getLegendLabelStyle();
		textStyle.setFillColor(gray.legendLabel_Fill());
		textStyle.setFontWeight(gray.legendLabel_FontWeigth());
		textStyle.setFontSize(gray.legendLabel_FontSize());
		
		style = chart.getAxisGridLineStyle();
		style.setStrokeOpacity(gray.grid_StrokeOpactiy());
		style.setStrokeColor(gray.axisTick_Color());
		
		style = chart.getAxisStyle();
		style.setFillColor(gray.axis_Color());
		style.setStrokeColor(gray.axis_Color());

		if(!chart.is_3d()){
			style = new Style();//.getVAxisStyle();
			style.setStrokeOpacity(0);
			chart.setVAxisStyle(style);			
		}
		
		if(chart.isAttached()){
			chart.applyCurrentTheme();
		}
	}
}
