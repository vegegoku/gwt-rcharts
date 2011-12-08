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
package com.rcharts.client.combo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.user.client.ui.Widget;
import com.hydro4ge.raphaelgwt.client.BBox;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.DefaultAttributes;
import com.rcharts.client.Position;
import com.rcharts.client.category.CategoryChart;
import com.rcharts.client.category.CategoryDataTable;
import com.rcharts.client.category.CategoryHoverHandler;
import com.rcharts.client.category.line.LineChart.LineChartType;
import com.rcharts.client.styles.Style;
import com.rcharts.client.styles.TextStyle;

public abstract class ComboUtil extends CategoryChart {

	protected int order = 0;
	protected List<ComboChartStyle> comboChartList = new ArrayList<ComboChartStyle>();
	protected List<String> colors = Arrays.asList(DefaultAttributes.getColors());
	Iterator<String> colorIterator = colors.iterator();
	
	public ComboUtil(int width, int height) {
		super(width, height);
	}

	public abstract class ComboChartStyle{
		private Style chartStyle = new Style();
		private TextStyle titleStyle = new TextStyle();
		private TextStyle subTitleStyle = new TextStyle();
		private TextStyle axisTitleStyle = new TextStyle();
		private TextStyle hAxisTitleStyle = new TextStyle();
		private TextStyle vAxisTitleStyle = new TextStyle();
		private TextStyle axisTickLabelStyle = new TextStyle();
		private TextStyle hAxisTickLabelStyle = new TextStyle();
		private TextStyle vAxisTickLabelStyle = new TextStyle();
		private Style hAxisTickMarkStyle = new Style();
		private Style vAxisTickMarkStyle = new Style();
		private Style axisStyle = new Style();
		private Style hAxisStyle = new Style();
		private Style vAxisStyle = new Style();
		private Style hGridLineStyle = new Style();
		private Style vGridLineStyle = new Style();
		private Style legendMarkStyle = new Style();
		private TextStyle legendLabelStyle = new TextStyle();
		private String[] defaultColors;
		private TextStyle defaultFontStyle = new TextStyle();
		private boolean enableInteractivity;
		private Position legendPosition = Position.NORTH;
		private String xAxisTitle;
		private String yAxisTitle;
		protected boolean _3d;
		protected boolean showLabels;
		protected boolean isBar = true;
		protected CategoryDataTable<Double> dataTable;
		//TODO : remove orderNo *no more useful*
		protected int orderNo;
		protected Map<String, String> colorMap = new LinkedHashMap<String, String>(); 
		private Map<String, RaphaelObject> legendMarkMap;
		
		protected ComboChartStyle(){
			order++;
			orderNo = order;
			comboChartList.add(this);
			//For Now Combo Charts are only suitable for column types
			isBar = false;
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
		 * @return the legendPosition
		 */
		public Position getLegendPosition() {
			return legendPosition;
		}


		/**
		 * @param legendPosition the legendPosition to set
		 */
		public void setLegendPosition(Position legendPosition) {
			this.legendPosition = legendPosition;
		}


		/**
		 * @return the xAxisTitle
		 */
		public String getxAxisTitle() {
			return xAxisTitle;
		}


		/**
		 * @param xAxisTitle the xAxisTitle to set
		 */
		public void setxAxisTitle(String xAxisTitle) {
			this.xAxisTitle = xAxisTitle;
		}


		/**
		 * @return the yAxisTitle
		 */
		public String getyAxisTitle() {
			return yAxisTitle;
		}


		/**
		 * @param yAxisTitle the yAxisTitle to set
		 */
		public void setyAxisTitle(String yAxisTitle) {
			this.yAxisTitle = yAxisTitle;
		}


		/**
		 * @return the _3d
		 */
		public boolean is_3d() {
			return _3d;
		}


		/**
		 * @param _3d the _3d to set
		 */
		public void set_3d(boolean _3d) {
			this._3d = _3d;
		}


		/**
		 * @return the showLabels
		 */
		public boolean isShowLabels() {
			return showLabels;
		}


		/**
		 * @param showLabels the showLabels to set
		 */
		public void setShowLabels(boolean showLabels) {
			this.showLabels = showLabels;
		}


		/**
		 * @return the isBar
		 */
		public boolean isBar() {
			return isBar;
		}


		/**
		 * @param isBar the isBar to set
		 */
		public void setBar(boolean isBar) {
			this.isBar = isBar;
		}


		/**
		 * @return the dataTable
		 */
		public CategoryDataTable<Double> getDataTable() {
			return dataTable;
		}


		/**
		 * @param dataTable the dataTable to set
		 */
		public void setDataTable(CategoryDataTable<Double> dataTable) {
			this.dataTable = dataTable;
		}

		/**
		 * Makes the color Map
		 */
		public void makeColorMap(){
			for(String series : dataTable.getSeriesNames()){
				colorMap.put(series, colorIterator.next());
			}
		}
		
		public Map<String, String> getColorMap(){
			if(colorMap.size() == 0){
				makeColorMap();
			}
			return colorMap;
		}



		/**
		 * @return the legendMarkMap
		 */
		public Map<String, RaphaelObject> getLegendMarkMap() {
			return legendMarkMap;
		}



		/**
		 * @param legendMarkMap the legendMarkMap to set
		 */
		public void setLegendMarkMap(Map<String, RaphaelObject> legendMarkMap) {
			this.legendMarkMap = legendMarkMap;
		}
		
		/**
		 * injects the fields of ComboChart by fields of ComboChartStyle
		 * excluding dataTable, orderNo, colorMap, legendMarkMap
		 */
		public void wrap(ComboChart chart){
			chart.setChartStyle(chartStyle);
			chart.setTitleStyle(titleStyle);
			chart.setSubTitleStyle(subTitleStyle);
			chart.setAxisTitleStyle(axisTitleStyle);
			chart.setHAxisTitleStyle(hAxisTitleStyle);
			chart.setVAxisTitleStyle(vAxisTitleStyle);
			chart.setAxisTickLabelStyle(axisTickLabelStyle);
			chart.setHAxisTickLabelStyle(hAxisTickLabelStyle);
			chart.setVAxisTickLabelStyle(vAxisTickLabelStyle);
			chart.sethAxisTickMarkStyle(hAxisTickMarkStyle);
			chart.setvAxisTickMarkStyle(vAxisTickMarkStyle);
			chart.setAxisStyle(axisStyle);
			chart.setHAxisStyle(hAxisStyle);
			chart.setVAxisStyle(vAxisStyle);
			chart.setHGridLineStyle(hGridLineStyle);
			chart.setVGridLineStyle(vGridLineStyle);
			chart.setLegendMarkStyle(legendMarkStyle);
			chart.setLegendLabelStyle(legendLabelStyle);
			chart.setDefaultColors(defaultColors);
			chart.setDefaultFontStyle(defaultFontStyle);
			chart.setEnableInteractivity(enableInteractivity);
			chart.setxAxisTitle(xAxisTitle);
			chart.setyAxisTitle(yAxisTitle);
			chart.set_3d(_3d);
			chart.setShowLabels(showLabels);
			chart.setBar(isBar);
		}
		
	}
	
	public class ComboBarChart extends ComboChartStyle{
		private double barGap = 0;
		private double barGroupGap = 5;
		private boolean isStacked;
		private CategoryDataTable<RaphaelObject> barDataTable;
		
		protected ComboBarChart(){
			
		}

		/**
		 * @return the barGap
		 */
		public double getBarGap() {
			return barGap;
		}

		/**
		 * @param barGap the barGap to set
		 */
		public void setBarGap(double barGap) {
			this.barGap = barGap;
		}

		/**
		 * @return the barGroupGap
		 */
		public double getBarGroupGap() {
			return barGroupGap;
		}

		/**
		 * @param barGroupGap the barGroupGap to set
		 */
		public void setBarGroupGap(double barGroupGap) {
			this.barGroupGap = barGroupGap;
		}

		/**
		 * @return the isStacked
		 */
		public boolean isStacked() {
			return isStacked;
		}

		/**
		 * @param isStacked the isStacked to set
		 */
		public void setStacked(boolean isStacked) {
			this.isStacked = isStacked;
		}

		
		
		/**
		 * @return the barDataTable
		 */
		public CategoryDataTable<RaphaelObject> getBarDataTable() {
			return barDataTable;
		}

		/**
		 * @param barDataTable the barDataTable to set
		 */
		public void setBarDataTable(CategoryDataTable<RaphaelObject> barDataTable) {
			this.barDataTable = barDataTable;
		}

		public void setBarHandler(){
			Map<String, RaphaelObject> legendMarkMap = getLegendMarkMap();
			java.util.Set<String> seriesNames = barDataTable.getSeriesNames(); 
			java.util.Set<String> catNames = barDataTable.getCategoryNames();
			Position pos = null;
			for(String catName : catNames){
				for(String serName : seriesNames){
					RaphaelObject bar = barDataTable.get(catName, serName);
					double value = dataTable.get(catName, serName);
					RaphaelObject legend = legendMarkMap.get(serName);
					RaphaelObject popupPoint = null;
					if(isBar){
						popupPoint = getBarPopupPoint(bar, value);
						if(value < 0){
							pos = Position.WEST;
						}
						else{
							pos = Position.EAST;
						}
					}
					else{
						popupPoint = getColumnPopupPoint(bar, value);
						if(value < 0){
							pos = Position.SOUTH;
						}
						else{
							pos = Position.NORTH;
						}
					}
					if(showLabels){
						renderLabels(value, popupPoint);
					}
					CategoryHoverHandler handler = GWT.create(CategoryHoverHandler.class);
					handler.init(catName, serName, value, legend, popupPoint, pos);
					bar.addMouseOverHandler(handler);
					((Widget)bar).addDomHandler(handler, MouseOutEvent.getType());	
					//bubbles.add(handler.getBubble().get());
					//handlers.add(handler);

				}
			}
		}

		
		
		protected RaphaelObject getBarPopupPoint(RaphaelObject bar, double value){
			BBox barBox = bar.getBBox();
			double x = barBox.x() + barBox.width()-2;
			double y = barBox.y() + barBox.height()/2;
			if(value < 0){
				x = barBox.x() + 2;
			}
			Shape popupPoint = ComboUtil.this.new Circle(x, y, 1);
			popupPoint.hide();
			return popupPoint;
		}
		
		protected RaphaelObject getColumnPopupPoint(RaphaelObject bar, double value){
			BBox barBox = bar.getBBox();
			double x = barBox.x() + barBox.width()/2;
			double y = barBox.y() + 2;
			if(value < 0){
				y = barBox.y() + barBox.height() -2;
			}
			Shape popupPoint = ComboUtil.this.new Circle(x, y, 1);
			popupPoint.hide();
			return popupPoint;
		}
		
		
		protected void renderLabels(double value, RaphaelObject popupPoint){
			double offsetValLabel =0;
			String textAnchor = null;
			if(value < 0){
				offsetValLabel = -10;
				textAnchor ="end";
			}
			else{
				offsetValLabel = 10;
				textAnchor = "start";
			}
			Text valueLabel = null;
			if(isBar){
				valueLabel = ComboUtil.this.new Text(popupPoint.getBBox().x()+offsetValLabel
						, popupPoint.getBBox().y(), value+"");
				
				valueLabel.attr("text-anchor", textAnchor);
			}
			else{					
				valueLabel = ComboUtil.this.new Text(popupPoint.getBBox().x(), 
						popupPoint.getBBox().y()-offsetValLabel, value+"");					
			}		
		}

	}
	
	
	public class ComboColumnChart extends ComboBarChart{
		
		protected ComboColumnChart(){
			isBar = false;
		}
	}
	
	public class ComboStackedBarChart extends ComboBarChart{
		
		protected ComboStackedBarChart(){
			setStacked(true);
		}
	}
	
	public class ComboStackedColumnChart extends ComboStackedBarChart{
		protected ComboStackedColumnChart(){
			isBar = false;
		}
	}
	
	public class ComboLineChart extends ComboChartStyle{
		private LineChartType type = LineChartType.LINE;
		private boolean splineEffect = false;
		private CategoryDataTable<RaphaelObject> pointDataTable;
		protected ComboLineChart(){
		
		}

		/**
		 * @return the type
		 */
		public LineChartType getType() {
			return type;
		}

		/**
		 * @param type the type to set
		 */
		public void setType(LineChartType type) {
			this.type = type;
		}

		/**
		 * @return the splineEffect
		 */
		public boolean isSplineEffect() {
			return splineEffect;
		}

		/**
		 * @param splineEffect the splineEffect to set
		 */
		public void setSplineEffect(boolean splineEffect) {
			this.splineEffect = splineEffect;
		}
		
		
		/**
		 * @return the pointDataTable
		 */
		public CategoryDataTable<RaphaelObject> getPointDataTable() {
			return pointDataTable;
		}

		/**
		 * @param pointDataTable the pointDataTable to set
		 */
		public void setPointDataTable(CategoryDataTable<RaphaelObject> pointDataTable) {
			this.pointDataTable = pointDataTable;
		}

		public void setHandler(){
			Map<String, RaphaelObject> legendMarkMap = getLegendMarkMap();
			java.util.Set<String> seriesNames = pointDataTable.getSeriesNames(); 
			java.util.Set<String> catNames = pointDataTable.getCategoryNames();
			Position pos = null;
			for(String catName : catNames){
				for(String serName : seriesNames){
					RaphaelObject point = pointDataTable.get(catName, serName);
					double value = dataTable.get(catName, serName);
					if(value < 0){
						pos = Position.SOUTH;
					}
					else{
						pos = Position.NORTH;
					}
					RaphaelObject legend = legendMarkMap.get(serName);
					CategoryHoverHandler handler = GWT.create(CategoryHoverHandler.class);
					handler.init(catName, serName, value, legend, point, pos);
					point.addMouseOverHandler(handler);
					((Widget)point).addDomHandler(handler, MouseOutEvent.getType());
					//bubbles.add(handler.getBubble().get());
					//handlers.add(handler);
				}
			}

		}

	}
	
	public class ComboAreaChart extends ComboLineChart{
	
		protected ComboAreaChart(){
			setType(LineChartType.AREA);
		}
	}
	
	public class ComboScatterChart extends ComboLineChart{
		
		protected ComboScatterChart(){
			setType(LineChartType.SCATTER);
		}
	}
	
	public ComboBarChart getBarChart(){
		return new ComboBarChart();
	}
	
	public ComboColumnChart getColumnChart(){
		return new ComboColumnChart();
	}
	
	public ComboStackedBarChart getComboStackedBarChart(){
		return new ComboStackedBarChart();
	}
	
	public ComboStackedColumnChart getComboStackedColumnChart(){
		return new ComboStackedColumnChart();
	}
	
	public ComboLineChart getComboLineChart(){
		return new ComboLineChart();
	}
	
	public ComboAreaChart getComboAreaChart(){
		return new ComboAreaChart();
	}
	
	public ComboScatterChart getComboScatterChart(){
		return new ComboScatterChart();
	}
	
}
