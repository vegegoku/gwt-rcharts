package com.rcharts.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.hydro4ge.raphaelgwt.client.Raphael;
import com.hydro4ge.raphaelgwt.client.Raphael.Circle;
import com.hydro4ge.raphaelgwt.client.RaphaelObject;
import com.rcharts.client.category.Axis;
import com.rcharts.client.category.Axis3D;
import com.rcharts.client.category.AxisFactory;
import com.rcharts.client.category.AxisPositive;
import com.rcharts.client.category.CategoryChart;
import com.rcharts.client.category.CategoryDataTable;
import com.rcharts.client.category.Orientation;
import com.rcharts.client.category.bar.BarChart;
import com.rcharts.client.category.bar.ColumnChart;
import com.rcharts.client.category.line.AreaChart;
import com.rcharts.client.category.line.LineChart;
import com.rcharts.client.category.line.ScatterChart;
import com.rcharts.client.category.line.LineChart.LineChartType;
import com.rcharts.client.combo.ComboChart;
import com.rcharts.client.combo.ComboUtil;
import com.rcharts.client.combo.ComboUtil.ComboAreaChart;
import com.rcharts.client.combo.ComboUtil.ComboBarChart;
import com.rcharts.client.combo.ComboUtil.ComboLineChart;
import com.rcharts.client.combo.ComboUtil.ComboStackedBarChart;
import com.rcharts.client.pie.PieChart;
import com.rcharts.client.pie.PieDataTable;
import com.rcharts.client.styles.TextStyle;
import com.rcharts.client.xychart.XYDataTable;
import com.rcharts.client.xychart.XYLineChart;
import com.rcharts.client.xychart.XYScatterChart;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RCharts_GWT1_4 implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		allCharts();
		//axis3DTest();
		//comboChartTest();
		//axisTest();
		//scrollTest();
		//hiddenTest();
	}
	
	public void hiddenTest(){
		TabLayoutPanel tlp = new TabLayoutPanel(20, Unit.PX);
		Label label = new Label("Hello ");
		final BarChart barChart = new BarChart (500, 500);
		barChart.setDataTable(getDataTable());
//		final SimplePanel sp = new SimplePanel();
		//sp.add(barChart);
		final HiddenPanel sp = new HiddenPanel(barChart);
		sp.addHandler(new Event.NativePreviewHandler() {
			
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				if(sp.isAttached() && sp.isVisible()){
					sp.add(barChart);
				}
			}
		}, Event.NativePreviewEvent.getType());
		sp.addAttachHandler(new Handler() {
			
			@Override
			public void onAttachOrDetach(AttachEvent event) {
				
			}
		});
		tlp.add(label, "Hello");
//		tlp.add(barChart, "barChart");
		tlp.add(sp, "Bar Chart");
		tlp.setHeight("1000px");
		tlp.setWidth("1000px");
		RootPanel.get().add(tlp);
//		RootPanel.get().add(barChart);
	}
	
	public void scrollTest(){
//		Raphael r = new Raphael(1500, 500);
//		final Circle c = r.new Circle(40, 100, 20);
//		c.attr("fill", "red");
		final BarChart r = new BarChart(1500, 500);
//		final LineChart r = new LineChart(5000, 500);
		r.setBar(false);
//		r.setPlotColor("90-#5E4242-#E5DEDE");
		r.set_3d(true);
		r.setDataTable(getBigDataTable());
//		r.setDataTable(getDataTable());
//		r.setShowLabels(true);
		final ScrollPanel sp = new ScrollPanel();
		sp.setHeight("500px");
		sp.setWidth("500px");
/*		sp.addScrollHandler(new ScrollHandler() {
			double dx = 0;
			boolean flag = true;
			@Override
			public void onScroll(ScrollEvent event) {
				int i = sp.getHorizontalScrollPosition();
				System.out.println("sroll postion : "+i);
				System.out.println("offset width :"+sp.getOffsetWidth());
//				c.translate(i+40-c.getBBox().x(), 0);
				Axis axis = r.getyAxis();
				RaphaelObject ra = axis.getFirstAxisSet();
				double d = ra.getBBox().x();// - i;
				if(flag){
					dx = d;
					flag = false;
				}
				ra.translate(i+dx-ra.getBBox().x(), 0);
			}
		});
*/		sp.add(r);
		ChartScrollHandler scrollHandler = new ChartScrollHandler(r, sp);
		sp.addScrollHandler(scrollHandler);
		RootPanel.get().add(sp);		
	}
	
	
	public void axisTest(){
		CategoryChart chart = new CategoryChart(1500, 1500) {
			
			
			@Override
			protected void draw() {
				chartPanel = new RDockPanel();
				List<String> tickLabels = new ArrayList<String>(dataTable.getCategoryNames());
				AxisPositive axis = new AxisPositive(tickLabels, Orientation.VERTICAL, 300);
				axis.showGridLine();
//				axis.setTickLabel(new ArrayList<String>(dataTable.getCategoryNames()));
//				axis.setOrientation(Orientation.VERTICAL);
				axis.setValueAxis(true);
				axis.setGridLineLength(200);
				axis.init();
//				axis.makeSingleInvertAxis(false);
				axis.makeSingleAxis(true);
//				axis.makeAxis();
				chartPanel.add(axis.get(), Position.WEST);
				AxisPositive axis2 = getAxis(Orientation.VERTICAL, true);
				chartPanel.add(axis2.get(), Position.EAST);
				RHorizontalPanel rhp = new RHorizontalPanel();
				for(int i = 0; i < 5; i++){
					Axis a = getAxis(Orientation.VERTICAL, true);
					rhp.add(a.get());
					a.getSecondAxis().remove();

//					rhp.add(this.new Rect(0, 0, 20, 10));
				}
				chartPanel.add(rhp.get(), Position.NORTH);
			}
			
			@Override
			public void clear() {
				// TODO Auto-generated method stub
				
			}
			
			public AxisPositive getAxis(Orientation orient, boolean isInvert){
				List<String> tickLabels = new ArrayList<String>(dataTable.getCategoryNames());
				AxisPositive axis = new AxisPositive(tickLabels, orient, 300);
				axis.showGridLine();
				axis.setValueAxis(false);
				axis.setGridLineLength(10);
				axis.init();
				if(isInvert){
//					axis.makeSingleInvertAxis(false);					
				}
				else{
//					axis.makeSingleAxis(false);					
				}
				axis.makeAxis();
				return axis;
			}
		};
		chart.setDataTable(getDataTable());
		RootPanel.get().add(chart);
	}
	
	public void comboChartTest(){
		ComboChart chart = new ComboChart(800, 800);
		chart.setBar(false);
//		ComboStackedBarChart sbc = chart.getComboStackedBarChart();
//		sbc.setDataTable(getDataTable());
		ComboLineChart lineChart = chart.getComboLineChart();
		lineChart.setDataTable(getDataTable());
		ComboAreaChart areaChart = chart.getComboAreaChart();
		areaChart.setDataTable(getDataTable());
		chart.setShowLinePointsAtMid(true);
		ComboBarChart barChart = chart.getBarChart();
		barChart.setDataTable(getDataTable());
//		chart.set_3d(true);
		RootPanel.get().add(chart);
	}
	
	public void axis3DTest(){
		CategoryChart chart = new CategoryChart(500, 500) {
			String[] cat ={"2001", "2002", "2003", "2004", "2005", "2006"};
			String[] ser = {"Austria", "Bulgaria", "Denmark", "Greece"};
			List<String> tickLabels = new ArrayList<String>(Arrays.asList(cat));
			@Override
			protected void draw() {
				Axis3D axis = new Axis3D();
				axis.setTickLabel(tickLabels);
				axis.setOrientation(Orientation.VERTICAL);
				axis.setAxisLength(300);
				axis.setGridLineLength(300);
				axis.setValueAxis(false);
				axis.init();
				axis.makeAxis();
				axis.get().translate(100, 100);
				axis.get().scale(0.5, 0.5, axis.get().getBBox().x(), axis.get().getBBox().y()+300);
			}
			
			@Override
			public void clear() {
				// TODO Auto-generated method stub
				
			}
		};
		chart.setDataTable(getDataTable());
		RootPanel.get().add(chart);
		
	}
	
	
	public CategoryDataTable<Double> getDataTable(){
		CategoryDataTable<Double> dataTable = new CategoryDataTable<Double>();
		double[] aus = {20, 10, 10, 20, 30, 40};
		double[] bul = {15, 25, 15, 45, 55, 45};
		double[] den = {25, 35, 25, 35, 60, 35};
		double[] gre = {30, 5, 33, 33, 35, 60};
		
		double[][] data = {aus,bul};//,den,gre};
		String[] cat ={"2001", "2002", "2003", "2004", "2005", "2006"};
		String[] ser = {"Austria", "Bulgaria"};//, "Denmark", "Greece"};
		Random rnd = new Random();
		for(int i=0; i < cat.length; i++){
			for(int j=0; j<ser.length; j++){
//				dataTable.add(data[j][i], cat[i], ser[j]);
				dataTable.add((double) rnd.nextInt(60), cat[i], ser[j]);
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

	public CategoryDataTable<Double> getBigDataTable(){
		CategoryDataTable<Double> data = new CategoryDataTable<Double>();
		Random rnd = new Random();
		List<String> cats = new ArrayList<String>();
		List<String> sers = new ArrayList<String>();
		for(int i = 0 ; i < 55; i++){
			cats.add(getRandomString(rnd));
		}
		for(int i = 0; i < 1; i++){
			sers.add(getRandomString(rnd));
		}
		for(String series : sers){
			for(String category : cats){
				int value = rnd.nextInt(100)+50;			
				data.add((double) value, category, series);
			}
		}
		System.out.println(data);
		return data;
	}
	
	public String getRandomString(Random rnd){
		String[] strings = {"a", "b", "c", "d", "e", "f", "g", "h", "i",
				"j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
				"u", "v", "w", "x", "y", "z"};
//		Random rnd = new Random();
		String ret = "";
		for(int i=0; i < 7; i++){
			ret = ret + strings[rnd.nextInt(strings.length - 2)];
		}
		return ret;
	}
}
