package com.rcharts.client.thems;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONString;
import com.rcharts.client.Chart;
import com.rcharts.client.ChartStyles.Theme;
import com.rcharts.client.styles.Keys;
import com.rcharts.client.styles.Style;
import com.rcharts.client.styles.TextStyle;

public class DarkGreenTheme {

	
	public static void apply(Chart chart){
		DarkGreen darkGreen = GWT.create(DarkGreen.class);
		chart.setBackgroundColor(darkGreen.backGroundColor());
		Style style = chart.getPlotStyle();
		style.setFillColor(darkGreen.plot_Fill());
		style.setStrokeColor(darkGreen.plot_Stroke());
		style.setStrokeOpacity(1);
		style.setFillOpacity(1);
		style.setStrokeWidth(1);
		
		style = chart.getAxisStyle();
		style.setFillColor(darkGreen.axis_Stroke());
		style.setStrokeColor(darkGreen.axis_Stroke());
		style.setStrokeOpacity(1);
		chart.setVAxisStyle(style);
		chart.setHAxisStyle(style);
		
		style = chart.getvAxisTickMarkStyle();
		style.setStrokeColor(darkGreen.axisTick_Color());
		
		TextStyle textStyle = chart.getAxisTickLabelStyle();
		textStyle.setFillColor(darkGreen.axisTickLabel_Fill());
		textStyle.setFontWeight(darkGreen.axisTickLabel_FontWeigth());
		textStyle.setFontSize(darkGreen.axisTickLabel_FontSize());
		
		textStyle = chart.getTitleStyle();
		textStyle.setFillColor(darkGreen.axisTitle_Fill());
		textStyle.setFontWeight(darkGreen.axisTitle_FontWeigth());
		textStyle.setFontSize(darkGreen.axisTitle_FontSize());
		
		textStyle = chart.getSubTitleStyle();
		textStyle.setFillColor(darkGreen.axisSubTitle_Fill());
		textStyle.setFontWeight(darkGreen.axisSubTitle_FontWeigth());
		textStyle.setFontSize(darkGreen.axisSubTitle_FontSize());
		
		textStyle = chart.getVAxisTitleStyle();
		textStyle.setFillColor(darkGreen.vAxisTitle_Fill());
		textStyle.setFontWeight(darkGreen.vAaxisTitle_FontWeigth());
		textStyle.setFontSize(darkGreen.vAxisTitle_FontSize());
		textStyle.setRotation(darkGreen.vAxisTitle_Rotation());
		
		textStyle = chart.getLegendLabelStyle();
		textStyle.setFillColor(darkGreen.legendLabel_Fill());
		textStyle.setFontWeight(darkGreen.legendLabel_FontWeigth());
		textStyle.setFontSize(darkGreen.legendLabel_FontSize());
		
		style = chart.getAxisGridLineStyle();
		style.setStrokeOpacity(darkGreen.grid_StrokeOpactiy());
		style.setStrokeColor("#333333");
		style.setStrokeOpacity(0.8);
		
		style = chart.getAxisTickMarkStyle();
		style.setStrokeColor(darkGreen.axisTick_Color());
		
		style = chart.getSpeechRectStyle();
		style.setStrokeWidth(3);
		style.setFillOpacity(0.8);
		style.setFillColor("#fff");
		
		chart.showGridLine(true);
		chart.setTheme(Theme.HIGHCHARTS);
		
		if(chart.isAttached()){
			chart.applyCurrentTheme();
		}
		
	}
	
}
