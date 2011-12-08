package com.rcharts.client.thems;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.Constants.DefaultDoubleValue;

public interface Default extends Constants{

	String axisTickLabel_Fill();
	String axisTickLabel_FontWeigth();
	double axisTickLabel_FontSize();
	
	String axisTitle_Fill();
	String axisTitle_FontWeigth();
	double axisTitle_FontSize();

	String axisSubTitle_Fill();
	String axisSubTitle_FontWeigth();
	double axisSubTitle_FontSize();

	String vAxisTitle_Fill();
	String vAaxisTitle_FontWeigth();
	@DefaultDoubleValue(12)
	double vAxisTitle_FontSize();
	double vAxisTitle_Rotation();

	String legendLabel_Fill();
	String legendLabel_FontWeigth();
	double legendLabel_FontSize();

	String grid_Stroke();

	String axisTick_Stroke();


}
