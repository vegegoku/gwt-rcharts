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

import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.hydro4ge.raphaelgwt.client.Raphael;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.category.Axis;
import com.rcharts.client.pie.PieChart;
import com.rcharts.client.styles.Keys;
import com.rcharts.client.styles.Style;
import com.rcharts.client.styles.TextStyle;

public abstract class ChartStyles extends Raphael {

	public static enum Theme{
		DEFAULT, VISUALIZATION, HIGHCHARTS
	}
	
	private Style chartStyle = new Style();
	private TextStyle titleStyle = new TextStyle();
	private TextStyle subTitleStyle = new TextStyle();
	private TextStyle axisTitleStyle = new TextStyle();
	private TextStyle hAxisTitleStyle = axisTitleStyle;//new TextStyle();
	private TextStyle vAxisTitleStyle = axisTitleStyle;//new TextStyle();
	private TextStyle axisTickLabelStyle = new TextStyle();
	private TextStyle hAxisTickLabelStyle = axisTickLabelStyle;//new TextStyle();
	private TextStyle vAxisTickLabelStyle = axisTickLabelStyle;//new TextStyle();
	private Style axisTickMarkStyle = new Style();
	private Style hAxisTickMarkStyle = axisTickMarkStyle;//new Style();
	private Style vAxisTickMarkStyle = axisTickMarkStyle;//new Style();
	private Style axisStyle = new Style();
	private Style hAxisStyle = axisStyle;//new Style();
	private Style vAxisStyle = axisStyle;//new Style();
	private Style axisGridLineStyle = new Style();
	private Style hGridLineStyle = axisGridLineStyle;//new Style();
	private Style vGridLineStyle = axisGridLineStyle;//new Style();
	private Style legendMarkStyle = new Style();
	private TextStyle legendLabelStyle = new TextStyle();
	private String[] defaultColors;
	private TextStyle defaultFontStyle = new TextStyle();
	private boolean enableInteractivity;
	private Style plotStyle = new Style();
	private Style speechRectStyle = new Style();
	private Style _3DAxesStyle = axisStyle;//new Style();
	private Theme theme = Theme.DEFAULT;
	
	
	private RaphaelObject rchartBackGround;
	private RaphaelObject rplotBackGround;
	private RaphaelObject rtitle;
	private RaphaelObject rsubTitle;
	private RaphaelObject rhAxisTitle;
	private RaphaelObject rvAxisTitle;
	private RaphaelObject rlegendMarkSet;
	private RaphaelObject rlegendLabelSet;
	private RaphaelObject _3DAxesSet;
	
	public ChartStyles(int width, int height){
		super(width, height);
//		RaphaelFactory.set(this, this);
		speechRectStyle.put(Keys.FILL, new JSONString("#fff"));
	}
	
	
	/**
	 * @return the chartStyle
	 */
	public Style getChartStyle() {
		return chartStyle;
	}
	/**
	 * @param chartStyle the chartStyle to set
	 */
	public void setChartStyle(Style chartStyle) {
		this.chartStyle = chartStyle;
	}
	/**
	 * @return the titleStyle
	 */
	public TextStyle getTitleStyle() {
		return titleStyle;
	}
	/**
	 * @param titleStyle the titleStyle to set
	 */
	public void setTitleStyle(TextStyle titleStyle) {
		this.titleStyle = titleStyle;
	}
	/**
	 * @return the subTitleStyle
	 */
	public TextStyle getSubTitleStyle() {
		return subTitleStyle;
	}
	/**
	 * @param subTitleStyle the subTitleStyle to set
	 */
	public void setSubTitleStyle(TextStyle subTitleStyle) {
		this.subTitleStyle = subTitleStyle;
	}
	/**
	 * @return the axisStyle
	 */
	public Style getAxisStyle() {
		return axisStyle;
	}


	/**
	 * @param axisStyle the axisStyle to set
	 */
	public void setAxisStyle(Style axisStyle) {
		this.axisStyle = axisStyle;
		setHAxisStyle(axisStyle);
		setVAxisStyle(axisStyle);
	}


	/**
	 * @return the axisTitleStyle
	 */
	public TextStyle getAxisTitleStyle() {
		return axisTitleStyle;
	}


	/**
	 * @param axisTitleStyle the axisTitleStyle to set
	 */
	public void setAxisTitleStyle(TextStyle axisTitleStyle) {
		this.axisTitleStyle = axisTitleStyle;
		setHAxisTitleStyle(axisTitleStyle);
		setVAxisTitleStyle(axisTitleStyle);
	}


	/**
	 * @return the xAxisTitleStyle
	 */
	public TextStyle getHAxisTitleStyle() {
		return hAxisTitleStyle;
	}
	/**
	 * 	Sets style of Horizontal axis's title
	 * @param xAxisTitleStyle the xAxisTitleStyle to set
	 */
	public void setHAxisTitleStyle(TextStyle xAxisTitleStyle) {
		this.hAxisTitleStyle = xAxisTitleStyle;
	}
	/**
	 * @return the yAxisTitleStyle
	 */
	public TextStyle getVAxisTitleStyle() {
		return vAxisTitleStyle;
	}
	/**
	 * 	  Sets style of vertical axis's title
	 * @param yAxisTitleStyle the yAxisTitleStyle to set
	 */
	public void setVAxisTitleStyle(TextStyle yAxisTitleStyle) {
		this.vAxisTitleStyle = yAxisTitleStyle;
	}
	/**
	 * @return the axisTickLabelStyle
	 */
	public TextStyle getAxisTickLabelStyle() {
		return axisTickLabelStyle;
	}


	/**
	 * @param axisTickLabelStyle the axisTickLabelStyle to set
	 */
	public void setAxisTickLabelStyle(TextStyle axisTickLabelStyle) {
		this.axisTickLabelStyle = axisTickLabelStyle;
		setHAxisTickLabelStyle(axisTickLabelStyle);
		setVAxisTickLabelStyle(axisTickLabelStyle);
	}


	/**
	 * @return the xAxisTickLabelStyle
	 */
	public TextStyle getHAxisTickLabelStyle() {
		return hAxisTickLabelStyle;
	}
	/**
	 * @param xAxisTickLabelStyle the xAxisTickLabelStyle to set
	 */
	public void setHAxisTickLabelStyle(TextStyle xAxisTickLabelStyle) {
		this.hAxisTickLabelStyle = xAxisTickLabelStyle;
	}
	/**
	 * @return the yAxisTickLabelStyle
	 */
	public TextStyle getVAxisTickLabelStyle() {
		return vAxisTickLabelStyle;
	}
	/**
	 * @param yAxisTickLabelStyle the yAxisTickLabelStyle to set
	 */
	public void setVAxisTickLabelStyle(TextStyle yAxisTickLabelStyle) {
		this.vAxisTickLabelStyle = yAxisTickLabelStyle;
	}
	/**
	 * @return the xAxisStyle
	 */
	public Style getHAxisStyle() {
		return hAxisStyle;
	}
	/**
	 * @param xAxisStyle the xAxisStyle to set
	 */
	public void setHAxisStyle(Style xAxisStyle) {
		this.hAxisStyle = xAxisStyle;
	}
	/**
	 * @return the yAxisStyle
	 */
	public Style getVAxisStyle() {
		return vAxisStyle;
	}
	/**
	 * @param yAxisStyle the yAxisStyle to set
	 */
	public void setVAxisStyle(Style yAxisStyle) {
		this.vAxisStyle = yAxisStyle;
	}
	/**
	 * @return the axisTickMarkStyle
	 */
	public Style getAxisTickMarkStyle() {
		return axisTickMarkStyle;
	}


	/**
	 * @param axisTickMarkStyle the axisTickMarkStyle to set
	 */
	public void setAxisTickMarkStyle(Style axisTickMarkStyle) {
		this.axisTickMarkStyle = axisTickMarkStyle;
		sethAxisTickMarkStyle(axisTickMarkStyle);
		setvAxisTickMarkStyle(axisTickMarkStyle);
	}


	/**
	 * @return the hAxisTickMarkStyle
	 */
	public Style gethAxisTickMarkStyle() {
		return hAxisTickMarkStyle;
	}


	/**
	 * @return the vAxisTickMarkStyle
	 */
	public Style getvAxisTickMarkStyle() {
		return vAxisTickMarkStyle;
	}


	/**
	 * @param hAxisTickMarkStyle the hAxisTickMarkStyle to set
	 */
	public void sethAxisTickMarkStyle(Style hAxisTickMarkStyle) {
		this.hAxisTickMarkStyle = hAxisTickMarkStyle;
	}


	/**
	 * @param vAxisTickMarkStyle the vAxisTickMarkStyle to set
	 */
	public void setvAxisTickMarkStyle(Style vAxisTickMarkStyle) {
		this.vAxisTickMarkStyle = vAxisTickMarkStyle;
	}


	/**
	 * @return the axisGridLineStyle
	 */
	public Style getAxisGridLineStyle() {
		return axisGridLineStyle;
	}


	/**
	 * @param axisGridLineStyle the axisGridLineStyle to set
	 */
	public void setAxisGridLineStyle(Style axisGridLineStyle) {
		this.axisGridLineStyle = axisGridLineStyle;
		setHGridLineStyle(axisGridLineStyle);
		setVGridLineStyle(axisGridLineStyle);
	}


	/**
	 * @return the xGridLineStyle
	 */
	public Style getHGridLineStyle() {
		return hGridLineStyle;
	}
	/**
	 * @param xGridLineStyle the xGridLineStyle to set
	 */
	public void setHGridLineStyle(Style xGridLineStyle) {
		this.hGridLineStyle = xGridLineStyle;
	}
	/**
	 * @return the yGridLineStyle
	 */
	public Style getVGridLineStyle() {
		return vGridLineStyle;
	}
	/**
	 * @param yGridLineStyle the yGridLineStyle to set
	 */
	public void setVGridLineStyle(Style yGridLineStyle) {
		this.vGridLineStyle = yGridLineStyle;
	}
	/**
	 * @return the legendMarkStyle
	 */
	public Style getLegendMarkStyle() {
		return legendMarkStyle;
	}
	/**
	 * @param legendMarkStyle the legendMarkStyle to set
	 */
	public void setLegendMarkStyle(Style legendMarkStyle) {
		this.legendMarkStyle = legendMarkStyle;
	}
	/**
	 * @return the legendLabelStyle
	 */
	public TextStyle getLegendLabelStyle() {
		return legendLabelStyle;
	}
	/**
	 * @param legendLabelStyle the legendLabelStyle to set
	 */
	public void setLegendLabelStyle(TextStyle legendLabelStyle) {
		this.legendLabelStyle = legendLabelStyle;
	}
	/**
	 * @return the defaultColors
	 */
	public String[] getDefaultColors() {
		return defaultColors;
	}
	/**
	 * @param defaultColors the defaultColors to set
	 */
	public void setDefaultColors(String[] defaultColors) {
		this.defaultColors = defaultColors;
	}
	
	/**
	 * @return the defaultFontStyle
	 */
	public TextStyle getDefaultFontStyle() {
		return defaultFontStyle;
	}
	/**
	 * @param defaultFontStyle the defaultFontStyle to set
	 */
	public void setDefaultFontStyle(TextStyle defaultFontStyle) {
		this.defaultFontStyle = defaultFontStyle;
	}
	/**
	 * @return the enableInteractivity
	 */
	public boolean isEnableInteractivity() {
		return enableInteractivity;
	}
	/**
	 * @param enableInteractivity the enableInteractivity to set
	 */
	public void setEnableInteractivity(boolean enableInteractivity) {
		this.enableInteractivity = enableInteractivity;
	}
	/**
	 * The background color for the main area of the chart. 
	 * Can be either a simple HTML color string, for example: 'red' or '#00cc00',
	 * @param color
	 */
	public void setBackgroundColor(String color){
		chartStyle.setFillColor(color);
	}
	
	/**
	 * The color of the chart border, as an HTML color string.
	 * @param color
	 */
	public void setBorderStroke(String color){
		chartStyle.setStrokeColor(color);
	}

	/**
	 * Same as setBorderStroke(String color);
	 * The color of the chart border, as an HTML color string.
	 * @param color
	 */
	public void setChartBorderColor(String color){
		setBorderStroke(color);
	}
	
	/**
	 * The border width, in pixels.
	 * @param strokeWidth
	 */
	public void setStrokeWidth(double strokeWidth){
		chartStyle.setStrokeWidth(strokeWidth);
	}
	
	/**
	 * Convenient name for setStrokeWidth(double strokeWidth)
	 * @param strokeWidth
	 */
	public void setChartBorderWidth(double strokeWidth){
		setStrokeWidth(strokeWidth);
	}
	
	/**
	 * The default font size, in pixels, of all text in the chart. 
	 * You can override this using properties for specific chart elements.
	 * @param fontSize
	 */
	public void setFontSize(double fontSize){
		setDefaultFontSize(fontSize);
	}
	
	/**
	 * Convenient name for setFontSize(double);
	 * @param fontSize
	 */
	public void setDefaultFontSize(double fontSize){
		defaultFontStyle.setFontSize(fontSize);
	}
	
	/**
	 * The default font face for all text in the chart.
	 *  You can override this using properties for specific chart elements.
	 * @param name
	 */
	public void setFontName(String fontFace){
		defaultFontStyle.setFontName(fontFace);
	}
	
	/**
	 * Convenient name for setFontName(String)
	 */
	
	public void setDefaultFontName(String fontFace){
		defaultFontStyle.setFontName(fontFace);
	}
	
	/**
	 * Sets the color of the baseline for the horizontal axis. 
	 * Can be any HTML color string, for example: 'red' or '#00cc00'.
	 * @param strokeColor
	 */
	public void setHAxisColor(String strokeColor){
		hAxisStyle.setStrokeColor(strokeColor);
	}
	
	/**
	 *The color of the horizontal gridlines inside the chart area. 
	 *Specify a valid HTML color string.
	 * @param strokeColor
	 */
	public void setHAxisGridLineColor(String strokeColor){
		hGridLineStyle.setStrokeColor(strokeColor);
	}
	
	public void setAxisTextStyle(TextStyle style){
		setHAxisTextStyle(style);
		setVAxisTextStyle(style);
	}
	
	/**
	 * sets horizontal axis tick label's text style
	 * @param style
	 */
	public void setHAxisTextStyle(TextStyle style){
		hAxisTickLabelStyle = style;
	}
	
	
	/**
	 * Sets the styles for legend labels
	 * @param style
	 */
	public void setLegendTextStyle(TextStyle style){
		legendLabelStyle = style;
	}
	

	/**
	 * Sets the color of the baseline for the vertical axis. 
	 * Can be any HTML color string, for example: 'red' or '#00cc00'.
	 * @param strokeColor
	 */
	public void setVAxisColor(String strokeColor){
		vAxisStyle.setStrokeColor(strokeColor);
	}
	
	/**
	 *The color of the vertical gridlines inside the chart area. 
	 *Specify a valid HTML color string.
	 * @param strokeColor
	 */
	public void setVAxisGridLineColor(String strokeColor){
		vGridLineStyle.setStrokeColor(strokeColor);
	}
	
	/**
	 * sets vertical axis tick label's text style
	 * @param style
	 */
	public void setVAxisTextStyle(TextStyle style){
		vAxisTickLabelStyle = style;
	}


	/**
	 * @return the plotColor
	 */
	public Style getPlotStyle() {
		return plotStyle;
	}


	/**
	 * @param plotStyle the plotColor to set
	 */
	public void setPlotStyle(Style plotStyle) {
		this.plotStyle = plotStyle;
	}


	/**
	 * @return the speechRectStyle
	 */
	public Style getSpeechRectStyle() {
		return speechRectStyle;
	}


	/**
	 * @param speechRectStyle the speechRectStyle to set
	 */
	public void setSpeechRectStyle(Style speechRectStyle) {
		this.speechRectStyle = speechRectStyle;
	}


	/**
	 * @return the _3DAxesStyle
	 */
	public Style get_3DAxesStyle() {
		return _3DAxesStyle;
	}


	/**
	 * @param _3dAxesStyle the _3DAxesStyle to set
	 */
	public void set_3DAxesStyle(Style _3dAxesStyle) {
		_3DAxesStyle = _3dAxesStyle;
	}


	/**
	 * @return the theme
	 */
	public Theme getTheme() {
		return theme;
	}


	/**
	 * @param theme the theme to set
	 */
	public void setTheme(Theme theme) {
		this.theme = theme;
	}


	/**
	 * @return the rchartBackGround
	 */
	public RaphaelObject getRchartBackGround() {
		return rchartBackGround;
	}


	/**
	 * @param rchartBackGround the rchartBackGround to set
	 */
	public void setRchartBackGround(RaphaelObject rchartBackGround) {
		this.rchartBackGround = rchartBackGround;
	}


	/**
	 * @return the rplotBackGround
	 */
	public RaphaelObject getRplotBackGround() {
		return rplotBackGround;
	}


	/**
	 * @param rplotBackGround the rplotBackGround to set
	 */
	public void setRplotBackGround(RaphaelObject rplotBackGround) {
		this.rplotBackGround = rplotBackGround;
	}


	/**
	 * @return the rtitle
	 */
	public RaphaelObject getRtitle() {
		return rtitle;
	}


	/**
	 * @param rtitle the rtitle to set
	 */
	public void setRtitle(RaphaelObject rtitle) {
		this.rtitle = rtitle;
	}


	/**
	 * @return the rsubTitle
	 */
	public RaphaelObject getRsubTitle() {
		return rsubTitle;
	}


	/**
	 * @param rsubTitle the rsubTitle to set
	 */
	public void setRsubTitle(RaphaelObject rsubTitle) {
		this.rsubTitle = rsubTitle;
	}


	/**
	 * @return the rhAxisTitle
	 */
	public RaphaelObject getRhAxisTitle() {
		return rhAxisTitle;
	}


	/**
	 * @param rhAxisTitle the rhAxisTitle to set
	 */
	public void setRhAxisTitle(RaphaelObject rhAxisTitle) {
		this.rhAxisTitle = rhAxisTitle;
	}


	/**
	 * @return the rvAxisTitle
	 */
	public RaphaelObject getRvAxisTitle() {
		return rvAxisTitle;
	}


	/**
	 * @param rvAxisTitle the rvAxisTitle to set
	 */
	public void setRvAxisTitle(RaphaelObject rvAxisTitle) {
		this.rvAxisTitle = rvAxisTitle;
	}


	/**
	 * @return the rlegendMarkSet
	 */
	public RaphaelObject getRlegendMarkSet() {
		return rlegendMarkSet;
	}


	/**
	 * @param rlegendMarkSet the rlegendMarkSet to set
	 */
	public void setRlegendMarkSet(RaphaelObject rlegendMarkSet) {
		this.rlegendMarkSet = rlegendMarkSet;
	}


	/**
	 * @return the rlegendLabelSet
	 */
	public RaphaelObject getRlegendLabelSet() {
		return rlegendLabelSet;
	}


	/**
	 * @param rlegendLabelSet the rlegendLabelSet to set
	 */
	public void setRlegendLabelSet(RaphaelObject rlegendLabelSet) {
		this.rlegendLabelSet = rlegendLabelSet;
	}
	
	/**
	 * @return the _3DAxesSet
	 */
	public RaphaelObject get_3DAxesSet() {
		return _3DAxesSet;
	}


	/**
	 * @param _3dAxesSet the _3DAxesSet to set
	 */
	public void set_3DAxesSet(RaphaelObject _3dAxesSet) {
		_3DAxesSet = _3dAxesSet;
	}


	/**
	 * Applies the theme after chart is loaded
	 */
	public void applyCurrentTheme(){
		attr(rchartBackGround, chartStyle);		
		attr(rplotBackGround, plotStyle);
		if(!(this instanceof PieChart)){
			rplotBackGround.toBack();			
		}
		rchartBackGround.toBack();
		
		attr(rtitle, titleStyle);
		attr(rsubTitle, subTitleStyle);
		attr(rhAxisTitle, hAxisTitleStyle);
		attr(rvAxisTitle, vAxisTitleStyle);
		attr(rlegendLabelSet, legendLabelStyle);
		attr(rlegendMarkSet, legendMarkStyle);
		if((this instanceof PieChart)){
			return;
		}

		Axis xAxis = getxAxis();
		Axis yAxis = getyAxis();
		attr(xAxis.getAxisLine(), hAxisStyle);
		attr(yAxis.getAxisLine(), vAxisStyle);
		attr(xAxis.getTickMarkSet(), hAxisTickMarkStyle);
		attr(yAxis.getTickMarkSet(), vAxisTickMarkStyle);
		attr(xAxis.getGridLinesSet(), hGridLineStyle);
		attr(yAxis.getGridLinesSet(), vGridLineStyle);
		attr(xAxis.getTickLabelSet(), hAxisTickLabelStyle);
		attr(yAxis.getTickLabelSet(), vAxisTickLabelStyle);
	}
	
	
	private void attr(RaphaelObject ro, Style s){
		if(ro != null){
			ro.attr(s);
		}
	}
	
	public abstract Axis getxAxis();
	public abstract Axis getyAxis();
}
