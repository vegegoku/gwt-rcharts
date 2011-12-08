package com.rcharts.client.thems;

import com.google.gwt.i18n.client.Constants;

public interface DarkBlue extends Constants {

	@DefaultStringValue("320-#07367C-#000000")
	public String backGroundColor();
	
	@DefaultStringValue("320-#3A557C-#052F6D")
	public String plotColor();
	
	@DefaultStringValue("#CCCCCC")
	public String plotStroke();
	
	@DefaultStringValue("#333333")
	public String gridColor();
	
	@DefaultStringValue("#C0C0C0")
	public String titleColor();
	
	@DefaultStringValue("bold")
	public String titleFontWeight();
	
	@DefaultDoubleValue(16)
	public double titleFontSize();

	@DefaultStringValue("#666666")
	public String subTitleColor();
	@DefaultStringValue("bold")
	public String subTitleFontWeight();
	@DefaultDoubleValue(12)
	public double subTitleFontSize();
	
	@DefaultStringValue("#A0A0A0")
	public String tickLabelColor();
	
	@DefaultDoubleValue(11)
	public double tickLabelFontSize();
	
	@DefaultStringValue("#CCC")
	public String vAxisTitleColor();
	@DefaultStringValue("bold")
	public String vAxisTitleFontWeight();
	@DefaultDoubleValue(12)
	public double vAxisTitleFontSize();
	@DefaultDoubleValue(-90)
	public double vAxisTitleRotation();
	
	@DefaultStringValue("#CCCCCC")
	public String axisColor();
	
	@DefaultDoubleValue(12)
	public double legendLabelFontSize();
	
	@DefaultStringValue("#CCC")
	public String legendLabelColor();
}
