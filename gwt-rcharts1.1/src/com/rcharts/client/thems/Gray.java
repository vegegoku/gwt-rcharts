package com.rcharts.client.thems;

import com.google.gwt.i18n.client.Constants;

public interface Gray extends Constants{

	public String backGroundColor();
	
	public double plot_StrokeWidth();
	
	@DefaultStringValue("#FFF")
	public String title_Fill();
	public String title_FontWeigth();
	public double title_FontSize();
	
	public String subTitle_Fill();
	public String subTitle_FontWeigth();
	public double subTitle_FontSize();
	
	public String axisTickLabel_Fill();
	public String axisTickLabel_FontWeigth();
	public double axisTickLabel_FontSize();
	
	@DefaultStringValue("#AAA")
	public String vAxisTitle_Fill();
	public String vAaxisTitle_FontWeigth();
	
	@DefaultDoubleValue(12)
	public double vAxisTitle_FontSize();
	public double vAxisTitle_Rotation();
	
	public String legendLabel_Fill();
	public String legendLabel_FontWeigth();
	public double legendLabel_FontSize();
	
	public double grid_StrokeOpactiy();
	
	public String axisTick_Color();
	
	public String axis_Color();
}
