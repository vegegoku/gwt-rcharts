package com.rcharts.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hydro4ge.raphaelgwt.client.Attr;
import com.hydro4ge.raphaelgwt.client.BBox;
import com.hydro4ge.raphaelgwt.client.Raphael;
import com.hydro4ge.raphaelgwt.client.Raphael.Rect;
import com.hydro4ge.raphaelgwt.client.Raphael.Set;
import com.hydro4ge.raphaelgwt.client.Raphael.Text;
import com.rcharts.client.ChartStyles.Theme;
import com.rcharts.client.category.CategoryChart;
import com.rcharts.client.category.CategoryDataTable;
import com.rcharts.client.category.bar.BarChart;
import com.rcharts.client.category.bar.ColumnChart;
import com.rcharts.client.category.bar.StackedBarChart;
import com.rcharts.client.category.bar.StackedColumnChart;
import com.rcharts.client.category.line.AreaChart;
import com.rcharts.client.category.line.LineChart;
import com.rcharts.client.category.line.ScatterChart;
import com.rcharts.client.category.line.LineChart.LineChartType;
import com.rcharts.client.pie.PieChart;
import com.rcharts.client.pie.PieDataTable;
import com.rcharts.client.styles.Keys;
import com.rcharts.client.styles.Style;
import com.rcharts.client.styles.TextStyle;
import com.rcharts.client.thems.DarkBlueTheme;
import com.rcharts.client.thems.DarkGreen;
import com.rcharts.client.thems.DarkGreenTheme;
import com.rcharts.client.thems.DefaultTheme;
import com.rcharts.client.thems.GrayTheme;
import com.rcharts.client.xychart.XYDataTable;
import com.rcharts.client.xychart.XYLineChart;
import com.rcharts.client.xychart.XYScatterChart;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RCharts_GWT1_5 implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		String host = Window.Location.getPath();
//		allCharts();
//		plotTest();
//		bubbleAnimTest();
		SimpleLayoutPanel sp = new SimpleLayoutPanel();
//		SimplePanel sp = new SimplePanel();
		int h = Window.getClientHeight() - 200;
		sp.setHeight("530px");
		sp.setWidth("1220px");
		sp.addStyleName("simpleLayout");
		DecoratorPanel dec = new DecoratorPanel();
		dec.add(sp);
//		RootPanel.get().add(dec);
//		themeTab();
//		navPanel();
//		showcaseTest2();
	}
	
/*	public void showcaseTest2(){
		VerticalPanel vp = new VerticalPanel();
		ShowCasePanel chartPanel = new ShowCasePanel();
		NavListFactory.panel = chartPanel;
		Bar2D bar2D = new Bar2D();
		vp.add(bar2D);

		vp.add(new Bar2DNegative());
		vp.add(new Bar3D());
		vp.add(new Bar3DNegative());
		vp.add(new StackedBar());
		vp.add(new StackedBar3D());
		vp.add(new Column2D());
		vp.add(new Column2DNegative());
		vp.add(new Column3D());
		vp.add(new Column3DNegative());
		vp.add(new StackedColumn());
		vp.add(new StackedColumn3D());
		vp.add(new Line2D());
		vp.add(new Line2DSpline());
		vp.add(new Line2DNegative());
		vp.add(new Line3D());
		vp.add(new Line3DSpline());
		vp.add(new Line3DNegative());
		vp.add(new Area2D());
		vp.add(new Area2DSpline());
		vp.add(new Area3D());
		vp.add(new Area3DSpline());
		vp.add(new Scatter2D());
		vp.add(new Scatter3D());
		vp.add(new Combo2D());
		vp.add(new Combo3D());
		vp.add(new Pie2D());
		vp.add(new Pie3D());
		vp.add(new Doughnut());
		vp.add(new Doughnut3D());
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(vp);
		hp.add(chartPanel);
		RootPanel.get().add(hp);
	}
	
	public void showcaseTest(){
		final ShowCasePanel scp = new ShowCasePanel();
		final ColumnChart chart = new ColumnChart(1000, 400);
		chart.setDataTable(getAllChartsData());
		chart.setBarGap(10);
		chart.setBarGroupGap(30);
		DefaultTheme.apply(chart);
		final BarChart barChart = new BarChart(1000, 400);
		barChart.setDataTable(getAllChartsData());
//		barChart.setBarGap(10);
//		barChart.setBarGroupGap(30);
		DefaultTheme.apply(barChart);
		HorizontalPanel hp = new HorizontalPanel();
		Button toggle = new Button("toggle");
		toggle.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(barChart.isAttached()){
					chart.setTheme(null);
					scp.setChart(chart);
				}
				else{
					barChart.setTheme(null);
					scp.setChart(barChart);
				}
			}
		});
		scp.setChart(barChart);
		hp.add(scp);
		hp.add(toggle);
		RootPanel.get().add(hp);
	}
*/	
	public void navPanel(){
		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(1);
//		vp.setWidth("150px");
		Label[] navLabels = {
				new Label(), new Label(), new Label(), new Label(), new Label(),
				new Label(), new Label()
		};
		String[] names = {
			"Bar Chart", "Stacked Bar Chart", "with -ve values", "Stacked with -ve",
			"with Labels", "Stacked with Labels", "-ve Stacked with Labels"
		};
		
		for(int i = 0; i < navLabels.length; i++){
			final Label label = navLabels[i];
			label.setText(names[i]);
			label.setStyleName("chartNavPanelLabel");
			label.setHeight("25px");
			label.addClickHandler(new ClickHandler() {
				boolean selected = false;
				
				@Override
				public void onClick(ClickEvent event) {
					if(selected){
						label.removeStyleName("selectedNavPanelLabel");
						label.setStyleName("chartNavPanelLabel");						
					}
					else{
						label.removeStyleName("chartNavPanelLabel");
						label.setStyleName("selectedNavPanelLabel");						
					}
					selected = !selected;
				}
			});
			vp.add(label);
		}
		RootPanel.get().add(vp);
	}
	
	public void themeTab(){
		TabLayoutPanel tlp = new TabLayoutPanel(30, Unit.PX);
		ColumnChart chart = new ColumnChart(1000, 400);
		chart.setDataTable(getAllChartsData());
		chart.setBarGap(10);
		chart.setBarGroupGap(30);
		DefaultTheme.apply(chart);
		HiddenPanel hp = new HiddenPanel(chart);
		final SimplePanel[] sp = {
			new SimplePanel(), new SimplePanel(), new SimplePanel(), new SimplePanel()	
		};
		sp[0] = hp;
		tlp.add(sp[0], "Default");
		tlp.add(sp[1], "Gray");
		tlp.add(sp[2], "Dark Blue");
		tlp.add(sp[3], "Dark Green");
		tlp.setWidth("1000px");
		tlp.setHeight("600px");
		final Chart ch = chart;
		tlp.addSelectionHandler(new SelectionHandler<Integer>() {
			
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				int i = event.getSelectedItem();
				ch.removeFromParent();
				sp[i].add(ch);
				switch(i){
				case 0:
					DefaultTheme.apply(ch);
					ch.applyCurrentTheme();
					break;
				case 1:
					GrayTheme.apply(ch);
					ch.applyCurrentTheme();
					break;
				case 2:
					DarkBlueTheme.apply(ch);
					ch.applyCurrentTheme();
					break;
				case 3:
					DarkGreenTheme.apply(ch);
					ch.applyCurrentTheme();
					break;
				}
			}
		});
//		VerticalPanel vp = new VerticalPanel();
		HorizontalPanel vp = new HorizontalPanel();
		vp.add(tlp);
		Button clear = new Button("clear");
		clear.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ch.removeFromParent();
			}
		});
		vp.add(clear);
		RootLayoutPanel.get().add(vp);
	}
	
	private class AnimTimer extends Timer{
		double time = 500;
		double dxStart = 0;
		double dyStart = 0;
		double dxEnd = 390;
		double dyEnd = 390;
		boolean end;
		Set set;
		@Override
		public void run() {
			if(dxEnd > 0){
				dxEnd--;// = dxEnd - 10;
			}
			else{
				end = true;
				return;
			}
			if( dyEnd > 0){
				dyEnd--;// = dyEnd - 10;
			}
			else{
				end = true;
				return;
			}
			set.translate(1, 1);
		}
	}
	
	public void bubbleAnimTest(){
		Raphael r = new Raphael(2000, 600);
		final Rect rect = r.new Rect(10, 10, 300, 100);
		rect.attr("fill", "yellow");
		Timer rectTimer = new Timer() {
			int time = 100;
			double sx = 0;
			double sy = 0;
			@Override
			public void run() {
				System.out.println("sx:"+sx+" sy:"+sy);
				rect.scale(sx, 1, rect.getBBox().x(), rect.getBBox().y());
				if(sx >= 1.0 || sy >= 1.0){
					sx = 1;
					sy = 1;
					rect.scale(sx, sy, rect.getBBox().x(), rect.getBBox().y());
					this.cancel();
				}
				else{
					sx = sx + 0.005;
					sy = sy + 0.005;
				}
			}
			public void scheduleRepeating(int ms){
				super.scheduleRepeating(time++);
				time = time + 500;
			}
		};
//		rectTimer.scheduleRepeating(50);
		BBox box = rect.getBBox();
		Text greet = r.new Text(30, 50, "Animation");//(box.x()+box.width()/2, box.y()+box.height()/2, "Animation");
		greet.attr("text-anchor", "start");
		Set set = r.new Set();
		set.push(rect);
		set.push(greet);
		AnimTimer at = new AnimTimer();
		at.set = set;
		at.scheduleRepeating(1);
		
		JSONObject anim = new JSONObject();
		anim.put("x", new JSONNumber(390));
		anim.put("y", new JSONNumber(390));
		JSONObject animt = new JSONObject();
		anim.put("x", new JSONNumber(30+390));
		anim.put("y", new JSONNumber(50+390));
//		rect.animate(anim, 5000, ">");
//		greet.animate(anim, 5000, ">");
		RootPanel.get().add(r);
	}
	
	public void plotTest(){
		String seriesName = "Rainfall";
		String[] cats = { 
				"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
		};
		double[] values = {
				49.9, 71.5, 106.4, 129.2, 144, 176, 135, 148.5, 216.4, 194.1, 95.6, 54.4
		};
		CategoryDataTable<Double> dataTable = new CategoryDataTable<Double>();
		PieDataTable<Double> pieData = new PieDataTable<Double>();
		for(int j = 0; j< cats.length; j++){
			dataTable.add(values[j], cats[j], seriesName);
			pieData.add(cats[j], values[j]);
		}

		ColumnChart chart = new ColumnChart(1000, 500);
//		StackedColumnChart chart = new StackedColumnChart(800, 500);
		chart.setyAxisTitle("Rainfall (mm)");
		chart.setTitle("Monthly Average Rainfall");
		chart.setSubTitle("Source: WorldClimate.com");
		chart.setDataTable(getAllChartsData());
		chart.set_3d(true);
//		chart.setStacked(true);
		chart.setBarGap(5);
		chart.setBarGroupGap(10);
		DefaultTheme.apply(chart);

		PieChart pieChart = new PieChart(500, 500);
		pieChart.setDataTable(pieData);
		pieChart.setShowSpeechBubble(true);
		
		LineChart lineChart = new LineChart(500, 500);
		lineChart.setDataTable(getDataTable());
//		lineChart.setType(LineChartType.AREA);
		lineChart.setTheme(Theme.HIGHCHARTS);
//		lineChart.setChartTheme(new VisualizationTheme());
		lineChart.showPoint(true);
		VerticalPanel vp = new VerticalPanel();
		vp.add(chart);
//		vp.add(lineChart);
//		vp.add(pieChart);
		Button applyBlue = new Button("Blue");
		final Chart ch = chart;
		applyBlue.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				DarkBlueTheme.apply(ch);
			}
		});
		Button applyDefault = new Button("Default");
		applyDefault.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				DefaultTheme.apply(ch);
			}
		});
		Button applyGray = new Button("Gray");
		applyGray.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				GrayTheme.apply(ch);
			}
		});
		Button applyGreen = new Button("Green");
		applyGreen.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				DarkGreenTheme.apply(ch);
			}
		});
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(applyDefault);
		hp.add(applyGray);
		hp.add(applyBlue);
		hp.add(applyGreen);
		
		vp.add(hp);
		RootPanel.get().add(vp);//chart);
	}
	
	public CategoryDataTable<Double> getDataTable(){
		CategoryDataTable<Double> dataTable = new CategoryDataTable<Double>();
		double[] aus = {20, 10, 10, 20, 30, 40};
		double[] bul = {15, 25, 15, 45, 55, 45};
		double[] den = {25, 35, 25, 35, 60, 35};
		double[] gre = {30, 5, 33, 33, 35, 60};
		
		double[][] data = {aus,bul,den,gre};
		String[] cat ={"2001", "2002", "2003", "2004", "2005", "2006"};
		String[] ser = {"Austria", "Bulgaria", "Denmark", "Greece"};
		Random rnd = new Random();
		for(int i=0; i < cat.length; i++){
			for(int j=0; j<ser.length; j++){
//				dataTable.add(data[j][i], cat[i], ser[j]);
				dataTable.add((double) rnd.nextInt(60)+1, cat[i], ser[j]);
			}
		}
		return dataTable;
	}
	
	public CategoryDataTable<Double> getAllChartsData(){
		CategoryDataTable<Double> dataTable = new CategoryDataTable<Double>();
		double[] aus = {20, 10, 10, 20, 30, 40};
		double[] bul = {15, 25, 15, 45, 55, 45};
		double[] den = {25, 35, 25, 35, 60, 35};
		double[] gre = {30, 5, 33, 33, 35, 60};
		
		double[][] data = {aus,bul,den,gre};
		String[] cat ={"2001", "2002", "2003", "2004", "2005", "2006"};
		String[] ser = {"Austria", "Bulgaria", "Denmark", "Greece"};
		for(int i=0; i < cat.length; i++){
			for(int j=0; j<ser.length; j++){
			dataTable.add(data[j][i], cat[i], ser[j]);
			}
		}
		return dataTable;
	}
	
	public void allCharts(){
		CategoryDataTable<Double> dataTable = new CategoryDataTable<Double>();
		CategoryDataTable<Double> dataTable2 = new CategoryDataTable<Double>();
		XYDataTable<Double> xyDataTable = new XYDataTable<Double>();
		CategoryDataTable<Double> areaTable = new CategoryDataTable<Double>();
		
		double[] aus = {20, 10, 10, 20, 30, 40};
		double[] bul = {15, 25, 15, 45, 55, 45};
		double[] den = {25, 35, 25, 35, 60, 35};
		double[] gre = {30, 5, 33, 33, 35, 60};
		
		double[][] data = {aus,bul,den,gre};
		double[][] dataY = {gre, den, bul, aus};
		
		String[] cat ={"2001", "2002", "2003", "2004", "2005", "2006"};
		String[] ser = {"Austria", "Bulgaria", "Denmark", "Greece"};
		for(int i=0; i < cat.length; i++){
			for(int j=0; j<ser.length; j++){
			dataTable.add(data[j][i], cat[i], ser[j]);
			dataTable2.add(data[j][i], ser[j], cat[i]);
			areaTable.add(Math.abs(data[j][i]), cat[i], ser[j]);
			}
		}
		String[] names = {"austria", "bulgaria", "denmark", "berlin", "UK",
				"sdkdk", "dkdkd", "edkkdkkd", "fjdkkdkk", "gkdkkdkd", "ikdkkdkk", 
				"jmdmkdke","kmdmedme", "hmkdkek", "zkdkkd", "xmdkdkd" };
		
		double[] values = {10, 20, 50, 15, 13};
		String[] colorss = DefaultAttributes.getColors();
		Map<String, String> colorMap = new HashMap<String, String>();
		PieDataTable<Double> pieData = new PieDataTable<Double>();
		Random rnd = new Random();
		for(int i = 0; i<15; i++){
			pieData.add(names[i], (double) rnd.nextInt(20)+1);// values[i]);
			colorMap.put(names[i], colorss[i]);
		}
		
		double[] dx = data[0];
		double[] dy = data[1];
		
		for(int i=0; i < dx.length; i++){
			xyDataTable.add(dx[i], dy[i], "Austria");			
		}

		for(int i=0; i< cat.length; i++){
		//	pieData.addRow(cat[i], data[2][i]);
		}
		String backgroudColor = "90-#BCA9A9-#fff";
		TextStyle ts = new TextStyle();
		ts.setFontSize(18);

		BarChart chart = new BarChart(500, 500);
		chart.setTitle("New Design for Charts");
		chart.setSubTitle("Bar chart");
		chart.setDataTable(dataTable);
		chart.setBackgroundColor(backgroudColor);
		chart.setLegendLabelStyle(ts);
		chart.setAxisTickLabelStyle(ts);
		chart.setxAxisTitle("X Axis");
		chart.setyAxisTitle("Y Axis");
		chart.setAxisTitleStyle(ts);

		CategoryChart chart2 = new ColumnChart(500, 500);
		chart2.setTitle("Chart 2");
		chart2.setDataTable(dataTable2);
		chart2.setBackgroundColor(backgroudColor);
		chart2.setxAxisTitle("X Axis");
		chart2.setyAxisTitle("Y Axis");
		chart2.setAxisTitleStyle(ts);
		
		LineChart lineChart = new LineChart(500, 500);
		lineChart.setTitle("Line Chart");
		lineChart.setDataTable(dataTable);
		lineChart.setBar(false);
		lineChart.setSplineEffect(true);
		lineChart.setAxisTextStyle(ts);
		lineChart.setLegendLabelStyle(ts);
		lineChart.setxAxisTitle("X Axis");
		lineChart.setyAxisTitle("Y Axis");
		lineChart.setAxisTitleStyle(ts);
		lineChart.setTitleStyle(ts);
		lineChart.setHAxisGridLineColor("red");
		String[] colors = {
				"#DCDCDC","#FFD700","#808080","#008000",
				"#ADFF2F","#DAA520","#F08080","#7FFFD4"
		};
		lineChart.setDefaultColors(colors);
		lineChart.setBackgroundColor("90-#000-#fff");
		
		ScatterChart scatterChart = new ScatterChart(500, 500);
		scatterChart.setTitle("Scatter Chart");
		scatterChart.setDataTable(dataTable);
		scatterChart.setBackgroundColor(backgroudColor);
		scatterChart.setLegendLabelStyle(ts);
		scatterChart.setxAxisTitle("X Axis");
		scatterChart.setxAxisTitle("Y Axis");
		scatterChart.setAxisTitleStyle(ts);
		
		AreaChart areaChart = new AreaChart(500, 500);
		areaChart.setTitle("Area Chart");
		areaChart.setDataTable(areaTable);
		areaChart.setSplineEffect(true);
		areaChart.setBar(false);
		areaChart.setHAxisTickLabelStyle(ts);
		areaChart.setxAxisTitle("X Axis");
		areaChart.setyAxisTitle("YAxis");
		areaChart.setAxisTitleStyle(ts);
		
		XYLineChart xyLine = new XYLineChart(500, 500);
		xyLine.setDataTable(xyDataTable);
		xyLine.setBackgroundColor(backgroudColor);
		xyLine.setxAxisTitle("X Axis");
		xyLine.setyAxisTitle("Y Axis");
		xyLine.setAxisTitleStyle(ts);
		
		XYScatterChart xyScatter = new XYScatterChart(500, 500);
		xyScatter.setDataTable(xyDataTable);
		xyScatter.setBackgroundColor(backgroudColor);
		xyScatter.setxAxisTitle("X Axis");
		xyScatter.setyAxisTitle("Y Axis");
		xyScatter.setAxisTitleStyle(ts);
		
		PieChart pie = new PieChart(500, 500);
		pie.setDataTable(pieData);
		pie.setTitle("Denmark");
//		pie.setBackgroundColor(backgroudColor);

		FlowPanel hp = new FlowPanel();
		chart.setShowLabels(true);
		hp.setWidth("700px");
		hp.add(pie);
		hp.add(chart);
		hp.add(chart2);
		hp.add(scatterChart);
		hp.add(lineChart);
		hp.add(areaChart);
		hp.add(xyScatter);
		hp.add(xyLine);
		RootPanel.get().add(hp);
	}

}
