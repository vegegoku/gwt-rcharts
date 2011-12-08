package com.rcharts.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.rcharts.client.category.CategoryChart;
import com.rcharts.client.category.CategoryDataTable;
import com.rcharts.client.category.bar.BarChart;
import com.rcharts.client.category.bar.ColumnChart;
import com.rcharts.client.category.line.AreaChart;
import com.rcharts.client.category.line.LineChart;
import com.rcharts.client.category.line.ScatterChart;
import com.rcharts.client.pie.PieChart;
import com.rcharts.client.pie.PieDataTable;
import com.rcharts.client.styles.TextStyle;
import com.rcharts.client.xychart.XYDataTable;
import com.rcharts.client.xychart.XYLineChart;
import com.rcharts.client.xychart.XYScatterChart;

public class Gwt_rcharts1_0 implements EntryPoint {

	@Override
	public void onModuleLoad() {
//		allCharts();
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
