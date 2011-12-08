package com.rcharts.client.thems;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;
import com.rcharts.client.Chart;
import com.rcharts.client.ChartStyles.Theme;
import com.rcharts.client.category.bar.BarChart;
import com.rcharts.client.styles.Style;
import com.rcharts.client.styles.TextStyle;

public class DefaultTheme {

	
	public static Default defaultT = GWT.create(Default.class);
	public static void apply(Chart chart){
		
		chart.setBackgroundColor("#fff");
		
		Style style = chart.getPlotStyle();
		style.setFillColor("#fff");
		style.setStrokeWidth(0);
		
		style = chart.getAxisStyle();
		style.setFillColor(defaultT.axisTick_Stroke());
		style.setStrokeColor(defaultT.axisTick_Stroke());
		
		boolean barChart = false;
		if(chart instanceof BarChart){
			BarChart bc = (BarChart) chart;
			if(bc.isBar()){
				barChart = true;
			}
		}
		
		if(!chart.is_3d() && !barChart){
			Style vStyle = new Style();
			vStyle.setStrokeOpacity(0);
			chart.setVAxisStyle(vStyle);			
		}
		else{
			style = chart.getVAxisStyle();
			style.setStrokeOpacity(1);
		}
		
		style = chart.getvAxisTickMarkStyle();
		style.setStrokeColor(defaultT.axisTick_Stroke());
		
		TextStyle textStyle = chart.getAxisTickLabelStyle();
		textStyle.setFillColor(defaultT.axisTickLabel_Fill());
		textStyle.setFontWeight(defaultT.axisTickLabel_FontWeigth());
		textStyle.setFontSize(defaultT.axisTickLabel_FontSize());
		
		textStyle = chart.getTitleStyle();
		textStyle.setFillColor(defaultT.axisTitle_Fill());
		textStyle.setFontWeight(defaultT.axisTitle_FontWeigth());
		textStyle.setFontSize(defaultT.axisTitle_FontSize());
		
		textStyle = chart.getSubTitleStyle();
		textStyle.setFillColor(defaultT.axisSubTitle_Fill());
		textStyle.setFontWeight(defaultT.axisSubTitle_FontWeigth());
		textStyle.setFontSize(defaultT.axisSubTitle_FontSize());
		
		textStyle = chart.getVAxisTitleStyle();
		textStyle.setFillColor(defaultT.vAxisTitle_Fill());
		textStyle.setFontWeight(defaultT.vAaxisTitle_FontWeigth());
		textStyle.setFontSize(defaultT.vAxisTitle_FontSize());
		textStyle.setRotation(defaultT.vAxisTitle_Rotation());
		
		textStyle = chart.getLegendLabelStyle();
		textStyle.setFillColor(defaultT.legendLabel_Fill());
		textStyle.setFontWeight(defaultT.legendLabel_FontWeigth());
		textStyle.setFontSize(defaultT.legendLabel_FontSize());
		
		style = chart.getAxisGridLineStyle();
		style.setStrokeColor(defaultT.grid_Stroke());
		
		style = chart.getAxisTickMarkStyle();
		style.setStrokeColor(defaultT.axisTick_Stroke());
		
		style = chart.getSpeechRectStyle();
		style.setStrokeWidth(3);
		style.setFillOpacity(0.8);
		style.setFillColor("#fff");
				
		chart.setTheme(Theme.HIGHCHARTS);
		
		style = chart.getPlotStyle();
		style.setStrokeOpacity(0);
		
		if(chart.isAttached()){
			chart.applyCurrentTheme();
		}

	}
}
